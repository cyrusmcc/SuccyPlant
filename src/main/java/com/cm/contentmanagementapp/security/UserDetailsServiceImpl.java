package com.cm.contentmanagementapp.security;

import com.cm.contentmanagementapp.user.User;
import com.cm.contentmanagementapp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        if (!userRepository.existsByUsername(s)) throw new UsernameNotFoundException("User not foudn");
        else {
            User user = userRepository.findByUsername(s);

            return UserDetailsImpl.build(user);
        }

    }
}
