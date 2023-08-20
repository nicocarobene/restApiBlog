package com.sistem.blog.Security;

import com.sistem.blog.model.Rol;
import com.sistem.blog.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRespository userEm;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        System.out.println("holaa estamos en detailuser");
        com.sistem.blog.model.User user= userEm.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with username or email"));
        return new User(user.getEmail(),user.getPassword(),mapperRoles(user.getRol()));
    }
    private Collection<? extends GrantedAuthority> mapperRoles(Set<Rol> rolSet){
        return rolSet.stream().map(rol-> new SimpleGrantedAuthority(rol.getName())).toList();
    }
}
