package com.leonardolucs.todolist.controllers;

import com.leonardolucs.todolist.models.dto.PublicUserDTO;
import com.leonardolucs.todolist.models.dto.UserDTO;
import com.leonardolucs.todolist.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class UsersController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PublicUserDTO>> getUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @Valid @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        return userService.getUser(id);
    }
}
