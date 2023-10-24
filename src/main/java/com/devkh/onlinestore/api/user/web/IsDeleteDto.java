package com.devkh.onlinestore.api.user.web;

import jakarta.validation.constraints.NotNull;

public record IsDeleteDto(@NotNull Boolean isDeleted) {
}
