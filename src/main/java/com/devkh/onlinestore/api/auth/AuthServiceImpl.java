package com.devkh.onlinestore.api.auth;

import com.devkh.onlinestore.api.auth.web.*;
import com.devkh.onlinestore.api.mail.Mail;
import com.devkh.onlinestore.api.mail.MailService;
import com.devkh.onlinestore.api.user.User;
import com.devkh.onlinestore.api.user.UserService;
import com.devkh.onlinestore.api.user.web.NewUserDto;
import com.devkh.onlinestore.util.RandomUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthRepository authRepository;
    private final AuthMapper authMapper;

    private final MailService mailService;

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtEncoder jwtEncoder;

    private JwtEncoder jwtRefreshTokenEncoder;

    @Autowired
    public void setJwtRefreshTokenEncoder(@Qualifier("jwtRefreshTokenEncoder") JwtEncoder jwtRefreshTokenEncoder) {
        this.jwtRefreshTokenEncoder = jwtRefreshTokenEncoder;
    }

    @Value("${spring.mail.username}")
    private String adminMail;

    @Override
    public AuthDto refreshToken(RefreshTokenDto refreshTokenDto) {

        Authentication auth = new BearerTokenAuthenticationToken(refreshTokenDto.refreshToken());
        auth = jwtAuthenticationProvider.authenticate(auth);

        Jwt jwt = (Jwt) auth.getPrincipal();
        log.info("Name: {}", jwt.getId());
        log.info("Name: {}", jwt.getSubject());

        Instant now = Instant.now();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .issuer("public")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.SECONDS))
                .subject("Access Token")
                .audience(List.of("Public Client"))
                .claim("scope", jwt.getClaimAsString("scope"))
                .build();

        JwtClaimsSet jwtRefreshTokenClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .issuer("public")
                .issuedAt(now)
                .expiresAt(now.plus(24, ChronoUnit.HOURS))
                .subject("Refresh Token")
                .audience(List.of("Public Client"))
                .claim("scope", jwt.getClaimAsString("scope"))
                .notBefore(now.plus(1, ChronoUnit.SECONDS))
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        String refreshToken = jwtRefreshTokenEncoder.encode(JwtEncoderParameters.from(jwtRefreshTokenClaimsSet)).getTokenValue();

        return AuthDto.builder()
                .type("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }



    @Override
    public AuthDto login(LoginDto loginDto) {

        Authentication auth = new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password());
        auth = daoAuthenticationProvider.authenticate(auth);

        log.info("AUTH = {}", auth.getName());
        log.info("AUTH = {}", auth.getAuthorities());

        Instant now = Instant.now();
        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .issuer("public")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.SECONDS))
                .subject("Access Token")
                .audience(List.of("Public Client"))
                .claim("scope", scope)
                .build();

        JwtClaimsSet jwtRefreshTokenClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .issuer("public")
                .issuedAt(now)
                .expiresAt(now.plus(24, ChronoUnit.HOURS))
                .subject("Refresh Token")
                .audience(List.of("Public Client"))
                .claim("scope", scope)
                .notBefore(now.plus(1, ChronoUnit.SECONDS))
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        String refreshToken = jwtRefreshTokenEncoder.encode(JwtEncoderParameters.from(jwtRefreshTokenClaimsSet)).getTokenValue();

        return AuthDto.builder()
                .type("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    @Override
    public void register(RegisterDto registerDto) throws MessagingException {
        NewUserDto newUserDto = authMapper.mapRegisterDtoToNewUserDto(registerDto);
        userService.createNewUser(newUserDto);

        String verifiedCode = RandomUtil.generateCode();
        // Store verifiedCode in database
        authRepository.updateVerifiedCode(registerDto.username(), verifiedCode);

        // Send verifiedCode via email
        Mail<String> verifiedMail = new Mail<>();
        verifiedMail.setSubject("Email Verification");
        verifiedMail.setSender(adminMail);
        verifiedMail.setReceiver(newUserDto.email());
        verifiedMail.setTemplate("auth/verify-mail");
        verifiedMail.setMetaData(verifiedCode);

        mailService.sendMail(verifiedMail);
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

}
