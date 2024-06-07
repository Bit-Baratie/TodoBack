package com.example.todolist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class UsersController {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final TodoListUserDetails todoListUserDetails;

    @PostMapping("/insert")
    public ResponseEntity<String> regist(@RequestBody Users user) {
        ResponseEntity<String> response = null;

        try{
            if(!usersRepository.existsByEmail(user.getEmail())) {
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                usersRepository.save(user);

                response = ResponseEntity.status(HttpStatus.CREATED).body("회원가입에 성공하였습니다.");

            } else {
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 아이디입니다.");
            }
        }catch(Exception e){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입에 실패하였습니다.");
        }
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        UserDetails userDetails = todoListUserDetails.loadUserByUsername(user.getEmail());
        Long id = usersRepository.findUserIdByEmail(userDetails.getUsername());


        ResponseEntity<?> response = null;

        boolean matches = passwordEncoder.matches(user.getPassword(), userDetails.getPassword());
        if(matches) {
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body(id);
        }else{
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 틀렸습니다.");
        }

        return response;
    }


}
