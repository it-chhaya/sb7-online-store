package com.devkh.onlinestore.api.user.web;

import lombok.Builder;

@Builder
public record UserDto(String uuid,
                      String username,
                      String email,
                      String nickName) {
}
