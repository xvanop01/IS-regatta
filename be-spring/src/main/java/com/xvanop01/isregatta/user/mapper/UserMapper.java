package com.xvanop01.isregatta.user.mapper;

import com.xvanop01.isregatta.api.user.model.UpdateUserDto;
import com.xvanop01.isregatta.api.user.model.UserDetailDto;
import com.xvanop01.isregatta.base.support.template.TableDataResponseMapper;
import com.xvanop01.isregatta.user.model.User;
import org.mapstruct.Mapper;

import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * UserMapper
 * Mapper zabezpecujuci mapovanie pouzivatelov (aj pre UserTableDataService)
 * @author 2024 Peter Vano
 */
@Mapper(componentModel = "spring")
public abstract class UserMapper extends TableDataResponseMapper<User, UserDetailDto> {

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    public abstract User map(UpdateUserDto dto);

    public abstract List<UserDetailDto> map(List<User> users);
}
