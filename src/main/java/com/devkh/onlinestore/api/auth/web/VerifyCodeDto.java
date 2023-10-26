package com.devkh.onlinestore.api.auth.web;

import lombok.Builder;

@Builder
public record VerifyCodeDto(Integer digit1,
                            Integer digit2,
                            Integer digit3,
                            Integer digit4,
                            Integer digit5,
                            Integer digit6
                            ) {
    public String get6Digits() {
        return "" + digit1 + digit2 + digit3 + digit4 + digit5 + digit6;
    }
}
