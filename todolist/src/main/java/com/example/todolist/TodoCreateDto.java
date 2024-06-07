package com.example.todolist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoCreateDto {


    private String content;
    private boolean status;
    private long userId;

    public TodoList toEntity(Users user) {

        return TodoList.builder()
                .content(this.getContent())
                .status(this.isStatus())
                .user(user)
                .build();
    }
}
