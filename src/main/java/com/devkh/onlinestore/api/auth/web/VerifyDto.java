package com.devkh.onlinestore.api.auth.web;

import jakarta.validation.constraints.NotBlank;

public record VerifyDto(@NotBlank String email,
                        @NotBlank String verifiedCode) {
}
