package com.devkh.onlinestore.api.auth;

import com.devkh.onlinestore.api.auth.web.LoginDto;
import com.devkh.onlinestore.api.auth.web.RegisterDto;
import com.devkh.onlinestore.api.auth.web.VerifyDto;
import com.devkh.onlinestore.api.user.User;
import com.devkh.onlinestore.api.user.UserRepository;
import com.devkh.onlinestore.api.user.UserService;
import com.devkh.onlinestore.api.user.web.NewUserDto;
import com.devkh.onlinestore.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthRepository authRepository;
    private final AuthMapper authMapper;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String adminMail;

    @Transactional
    @Override
    public void register(RegisterDto registerDto) throws MessagingException {
        NewUserDto newUserDto = authMapper.mapRegisterDtoToNewUserDto(registerDto);
        userService.createNewUser(newUserDto);

        String verifiedCode = RandomUtil.generateCode();
        // Store verifiedCode in database
        authRepository.updateVerifiedCode(registerDto.username(), verifiedCode);
        // Send verifiedCode via email
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(newUserDto.email());
        helper.setFrom(adminMail);
        helper.setSubject("Online Store - Email Verification");

        Context context = new Context();
        context.setVariable("verifiedCode", verifiedCode);
        String htmlTemplate = templateEngine.process("auth/verify-mail", context);
        helper.setText(htmlTemplate, true);

        javaMailSender.send(mimeMessage);

    }

    @Override
    public void verify(VerifyDto verifyDto) {

        User verifiedUser =
                authRepository.findByEmailAndVerifiedCodeAndIsDeletedFalse(verifyDto.email(),
                        verifyDto.verifiedCode())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Verify email has been failed..!"
                        ));

        verifiedUser.setIsVerified(true);
        verifiedUser.setVerifiedCode(null);

        authRepository.save(verifiedUser);

    }

    @Override
    public void login(LoginDto loginDto) {

    }

}
