package com.devkh.onlinestore.api.user.web;

import com.devkh.onlinestore.api.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PatchMapping("/{uuid}")
    public void updateByUuid(@PathVariable String uuid,
                             @RequestBody UpdateUserDto updateUserDto) {
        userService.updateByUuid(uuid, updateUserDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNewUser(@RequestBody @Valid NewUserDto newUserDto) {
        userService.createNewUser(newUserDto);
    }

    @GetMapping("/{uuid}")
    public UserDto findByUuid(@PathVariable String uuid) {
        return userService.findByUuid(uuid);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public void deleteByUuid(@PathVariable String uuid) {
        userService.deleteByUuid(uuid);
    }

    @PutMapping("/{uuid}")
    public void updateIsDeletedByUuid(@PathVariable String uuid, @RequestBody IsDeleteDto isDeleteDto) {
        userService.updateIsDeletedByUuid(uuid, isDeleteDto.isDeleted());
    }
}
