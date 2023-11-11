package com.devkh.onlinestore.api.user.web;

import com.devkh.onlinestore.api.user.UserService;
import com.devkh.onlinestore.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('SCOPE_user:profile')")
    @GetMapping("/me")
    public UserDto me(Authentication auth) {
        return userService.me(auth);
    }

    @PreAuthorize("hasAuthority('SCOPE_user:update')")
    @PatchMapping("/{uuid}")
    public void updateByUuid(@PathVariable String uuid,
                             @RequestBody UpdateUserDto updateUserDto) {
        userService.updateByUuid(uuid, updateUserDto);
    }

    @PreAuthorize("hasAuthority('SCOPE_user:write')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNewUser(@RequestBody @Valid NewUserDto newUserDto) {
        userService.createNewUser(newUserDto);
    }

    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    @GetMapping("/{uuid}")
    public UserDto findByUuid(@PathVariable String uuid) {
        return userService.findByUuid(uuid);
    }

    @PreAuthorize("hasAuthority('SCOPE_user:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public void deleteByUuid(@PathVariable String uuid) {
        userService.deleteByUuid(uuid);
    }

    @PreAuthorize("hasAuthority('SCOPE_user:update')")
    @PutMapping("/{uuid}")
    public void updateIsDeletedByUuid(@PathVariable String uuid, @RequestBody IsDeleteDto isDeleteDto) {
        userService.updateIsDeletedByUuid(uuid, isDeleteDto.isDeleted());
    }
}
