package com.devkh.onlinestore.api.auth;

import com.devkh.onlinestore.api.auth.web.RegisterDto;
import com.devkh.onlinestore.api.user.web.NewUserDto;
import com.devkh.onlinestore.api.user.web.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    NewUserDto mapRegisterDtoToNewUserDto(RegisterDto registerDto);

}
