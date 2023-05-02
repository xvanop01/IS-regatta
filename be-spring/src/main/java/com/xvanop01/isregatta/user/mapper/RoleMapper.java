package com.xvanop01.isregatta.user.mapper;

import com.xvanop01.isregatta.api.dto.RoleDto;
import com.xvanop01.isregatta.api.dto.RoleListDto;
import com.xvanop01.isregatta.user.model.Role;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {

    public abstract RoleDto map(Role role);

    public abstract List<RoleDto> map(List<Role> roleList);

    public RoleListDto mapRoleList(List<Role> roleList) {
        RoleListDto roleListDto = new RoleListDto();
        roleListDto.setRoles(map(roleList));
        return roleListDto;
    }

    public Integer map(RoleDto dto) {
        return dto == null ? null : dto.getId();
    }

    public List<Integer> map(RoleListDto listDto) {
        List<Integer> idList = new ArrayList<>();
        listDto.getRoles().forEach(roleDto -> idList.add(map(roleDto)));
        return idList;
    }
}
