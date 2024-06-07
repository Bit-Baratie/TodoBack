package com.example.todolist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);
    Optional<Users> findByEmail(String email);

    @Query("SELECT id FROM Users WHERE email= :email")
    Long findUserIdByEmail(@Param("email") String email);
}
