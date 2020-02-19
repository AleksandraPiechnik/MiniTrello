package com.ii_rest_api.project.security;

import com.ii_rest_api.project.db.model.User;
import com.ii_rest_api.project.db.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService{


    private final UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=null;
        user = this.userRepository.findByUsername(username);
         if (user == null) throw new UsernameNotFoundException("No user found for " + username + ".");

        UserPrincipal userPrincipal = new UserPrincipal(user);
        return userPrincipal;
    }


}
