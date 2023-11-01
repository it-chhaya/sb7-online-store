package com.devkh.onlinestore.api.auth.web;

import lombok.Builder;

@Builder
public record AuthDto(String accessToken,
                      String refreshToken,
                      String type) {
}
