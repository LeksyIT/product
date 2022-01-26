package com.leksyit.task14vtb.service;

import com.leksyit.task14vtb.dto.UserRegistrationDto;
import com.leksyit.task14vtb.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(UserRegistrationDto registrationDto);

}
