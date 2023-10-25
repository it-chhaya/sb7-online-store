package com.devkh.onlinestore.api.auth;

import com.devkh.onlinestore.api.auth.web.LoginDto;
import com.devkh.onlinestore.api.auth.web.RegisterDto;
import com.devkh.onlinestore.api.auth.web.VerifyDto;
import jakarta.mail.MessagingException;

public interface AuthService {

    void register(RegisterDto registerDto) throws MessagingException;

    void verify(VerifyDto verifyDto);

    void login(LoginDto loginDto);

}
