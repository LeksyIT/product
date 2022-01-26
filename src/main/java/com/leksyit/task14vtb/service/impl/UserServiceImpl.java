package com.leksyit.task14vtb.service.impl;

import com.leksyit.task14vtb.dto.RoleDto;
import com.leksyit.task14vtb.dto.UserRegistrationDto;
import com.leksyit.task14vtb.entity.Role;
import com.leksyit.task14vtb.entity.User;
import com.leksyit.task14vtb.mapper.UserRegistrationMapper;
import com.leksyit.task14vtb.repository.UserRepository;
import com.leksyit.task14vtb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRegistrationMapper userRegistrationMapper;

    @Override
    @Transactional
    public User save(UserRegistrationDto registrationDto) {
        try {
            User user = new User(registrationDto.getLogin(),
                    passwordEncoder.encode(registrationDto.getPassword()),
                    List.of(new Role("ROLE_ADMIN")));
            return userRepository.save(user);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRegistrationDto user = userRegistrationMapper.userToUserRegistrationDto(userRepository.findByLogin(username));
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(), user.getPassword(), mapRolesToAuthorities(user.getUserRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<RoleDto> rolesDto) {
        return rolesDto.stream().map(roleDto -> new SimpleGrantedAuthority(roleDto.getName())).toList();
    }
}