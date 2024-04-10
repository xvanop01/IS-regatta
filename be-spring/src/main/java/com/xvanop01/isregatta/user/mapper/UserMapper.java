package com.xvanop01.isregatta.user.mapper;

import com.xvanop01.isregatta.api.user.model.UpdateUserDto;
import com.xvanop01.isregatta.api.user.model.UserDetailDto;
import com.xvanop01.isregatta.base.support.template.TableDataResponseMapper;
import com.xvanop01.isregatta.user.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper extends TableDataResponseMapper<User, UserDetailDto> {

    public abstract User map(UpdateUserDto dto);

    public abstract UserDetailDto map(User user);

    public abstract List<UserDetailDto> map(List<User> users);
}
