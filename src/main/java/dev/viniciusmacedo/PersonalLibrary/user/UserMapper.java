package dev.viniciusmacedo.PersonalLibrary.user;

import org.springframework.stereotype.Component;

@Component
public final class UserMapper {
    public static UserModel toUserModel(UserRequest userRequest) {
        return new UserModel(
                userRequest.id(),
                userRequest.name(),
                userRequest.email(),
                userRequest.password()
        );
    }

    public static UserResponse toUserResponse(UserModel userModel) {
        return new UserResponse(
                userModel.getId(),
                userModel.getName(),
                userModel.getEmail()
        );
    }
}
