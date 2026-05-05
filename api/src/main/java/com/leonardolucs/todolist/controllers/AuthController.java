package com.leonardolucs.todolist.controllers;

import com.leonardolucs.todolist.models.dto.LoginDTO;
import com.leonardolucs.todolist.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody @Valid LoginDTO user) {
        userService.login(user);
        return ResponseEntity.ok().build();
    }
}