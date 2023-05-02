package com.xvanop01.isregatta.user.mapper;

import com.xvanop01.isregatta.api.dto.CreateUserDto;
import com.xvanop01.isregatta.api.dto.UserDetailDto;
import com.xvanop01.isregatta.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract User map(CreateUserDto dto);

    public abstract UserDetailDto map(User user);
}
