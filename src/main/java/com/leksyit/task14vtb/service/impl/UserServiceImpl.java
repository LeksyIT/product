package com.leksyit.task14vtb.service.impl;

import com.leksyit.task14vtb.dto.UserRegistrationDto;
import com.leksyit.task14vtb.entity.Role;
import com.leksyit.task14vtb.entity.User;
import com.leksyit.task14vtb.repository.UserRepository;
import com.leksyit.task14vtb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User save(UserRegistrationDto registrationDto) {
        if (userRepository.findByLogin(registrationDto.getLogin()) != null){
            return null;
        }
        else{
            User user = new User(registrationDto.getLogin(), passwordEncoder.encode(registrationDto.getPassword()), List.of(new Role("ROLE_USER")));
            return userRepository.save(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }
}
