package com.devkh.onlinestore.api.user;

import com.devkh.onlinestore.api.user.web.NewUserDto;
import com.devkh.onlinestore.api.user.web.UpdateUserDto;
import com.devkh.onlinestore.api.user.web.UserDto;

public interface UserService {

    // Create a new user
    void createNewUser(NewUserDto newUserDto);

    // Update an existing user by UUID
    void updateByUuid(String uuid, UpdateUserDto updateUserDto);

    // Find users by pagination and filter

    // Find a user by UUID
    UserDto findByUuid(String uuid);

    // Delete a user by UUID (Permanently)
    void deleteByUuid(String uuid);

    // Update status: `is_deleted` by UUID
    void updateIsDeletedByUuid(String uuid, Boolean isDeleted);
}
