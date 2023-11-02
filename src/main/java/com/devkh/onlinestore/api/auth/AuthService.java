package com.devkh.onlinestore.api.auth;

import com.devkh.onlinestore.api.auth.web.*;
import jakarta.mail.MessagingException;

public interface AuthService {

    AuthDto refreshToken(RefreshTokenDto refreshTokenDto);

    void register(RegisterDto registerDto) throws MessagingException;

    void verify(VerifyDto verifyDto);

    AuthDto login(LoginDto loginDto);

}
