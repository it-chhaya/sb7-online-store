package com.devkh.onlinestore.api.user;

import com.devkh.onlinestore.api.user.web.NewUserDto;
import com.devkh.onlinestore.api.user.web.UpdateUserDto;
import com.devkh.onlinestore.api.user.web.UserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User fromNewUserDto(NewUserDto newUserDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdateUserDto(@MappingTarget User user, UpdateUserDto updateUserDto);

}
