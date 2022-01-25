package com.leksyit.task14vtb.mapper;

import com.leksyit.task14vtb.dto.RoleDto;
import com.leksyit.task14vtb.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target="name", source="roleDto.name")
    Role roleDtoToRole(RoleDto roleDto);

    @Mapping(target="name", source="role.name")
    RoleDto roleToRoleDto(Role role);


    List<Role> listOfRolesDtoTolistOfRoles(List<RoleDto> listRoleDto);


    List<RoleDto> listOfRolesTolistOfRolesDto(List<Role> listRole);
}
