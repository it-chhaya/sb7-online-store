package com.devkh.onlinestore.api.user.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserDto(@NotBlank
                            @Email
                            String email,
                            @NotBlank
                            String nickName) {
}
