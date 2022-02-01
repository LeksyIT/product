package com.leksyit.task14vtb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {
    private String login;
    private String password;
    private List<RoleDto> userRoles;

}
