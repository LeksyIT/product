package com.leksyit.task14vtb.mapper;

import com.leksyit.task14vtb.dto.UserRegistrationDto;
import com.leksyit.task14vtb.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {

    @Mapping(target = "userRoles", source = "user.roles")
    UserRegistrationDto userToUserRegistrationDto(User user);

    User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto);
}
