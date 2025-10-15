package dev.viniciusmacedo.PersonalLibrary.user;

import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserResponse> findUserById(Long id) {
        return userRepository.findById(id).map(UserMapper::toUserResponse);
    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public UserModel saveUser(UserModel user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new NoResultException("Usuário não encontrado");
        }
    }

    public Optional<UserModel> updateUser(Long id, UserModel user) {
        Optional<UserModel> optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            UserModel updatedUser = new UserModel(
                    optUser.get().getId(),
                    user.getName().isBlank() ? optUser.get().getName() : user.getName(),
                    user.getEmail().isBlank() ? optUser.get().getEmail() : user.getEmail(),
                    user.getPassword().isBlank() ? optUser.get().getPassword() : user.getPassword()
            );
            return Optional.of(updatedUser);
        }
        return Optional.empty();
    }
}
