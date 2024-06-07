package com.example.todolist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean status;

    @Column(nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;
}
