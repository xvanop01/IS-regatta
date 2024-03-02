package com.xvanop01.isregatta.user.tableDataService;

import com.xvanop01.isregatta.api.user.model.UserDetailDto;
import com.xvanop01.isregatta.base.support.template.TableDataResponseMapper;
import com.xvanop01.isregatta.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserTableMapper extends TableDataResponseMapper<User, UserDetailDto> {

    @Mapping(target = "email", source = "username")
    public abstract UserDetailDto map(User user);
}
