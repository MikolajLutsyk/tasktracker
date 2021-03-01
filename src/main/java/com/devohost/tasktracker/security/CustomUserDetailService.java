package com.devohost.tasktracker.security;

import com.devohost.tasktracker.dto.UserDTO;
import com.devohost.tasktracker.exceptions.UserException;
import com.devohost.tasktracker.service.interfaces.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDTO userDTO = userService.getUserByEmail(s);

        if (userDTO != null) {
            List<GrantedAuthority> authorities = getUserAuthorities(userDTO);
            return new User(userDTO.getEmail(), userDTO.getPassword(), authorities);
        }else{
            throw new UserException("User Not Found");
        }
    }

    public List<GrantedAuthority> getUserAuthorities(UserDTO userDTO) {
        List<GrantedAuthority> roles = new ArrayList<>();

        roles.add(new SimpleGrantedAuthority(userDTO.getRole().getName()));
        return roles;
    }
}
