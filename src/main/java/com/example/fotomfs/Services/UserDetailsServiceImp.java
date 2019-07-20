package com.example.fotomfs.Services;

import com.example.fotomfs.Model.Role;
import com.example.fotomfs.Model.User;
import com.example.fotomfs.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {
    private final
    UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(s);
        org.springframework.security.core.userdetails.User.UserBuilder builder;
        if (user != null) {
            builder= org.springframework.security.core.userdetails.User.withUsername(user.getLogin());
            builder.disabled(false);
            builder.password(user.getPassword());
            String[] authorities = user.getRoles()
                    .stream().map(Role::getName).toArray(String[]::new);
            builder.authorities(authorities);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        UserDetails build = builder.build();
        return build;
    }
}
