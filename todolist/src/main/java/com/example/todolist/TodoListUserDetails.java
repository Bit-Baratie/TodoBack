package com.example.todolist;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
@Service
@RequiredArgsConstructor
public class TodoListUserDetails implements UserDetailsService {

    private final UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        Users findUser = usersRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(email)
        );

        List authorities = new ArrayList();
        authorities.add(new SimpleGrantedAuthority(findUser.getRole()));

        return new User(findUser.getEmail(), findUser.getPassword(), authorities);
    }


}
