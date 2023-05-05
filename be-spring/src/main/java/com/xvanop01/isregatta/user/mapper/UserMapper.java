package com.xvanop01.isregatta.user.mapper;

import com.xvanop01.isregatta.api.dto.CreateUserDto;
import com.xvanop01.isregatta.api.dto.UpdateUserDto;
import com.xvanop01.isregatta.api.dto.UserDetailDto;
import com.xvanop01.isregatta.user.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract User map(CreateUserDto dto);

    public abstract User map(UpdateUserDto dto);

    public abstract UserDetailDto map(User user);

    public abstract List<UserDetailDto> map(List<User> users);
}
