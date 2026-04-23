package com.leonardolucs.todolist.services;

import com.leonardolucs.todolist.models.dto.LoginDTO;
import com.leonardolucs.todolist.models.dto.LoginUserDTO;
import com.leonardolucs.todolist.models.dto.PublicUserDTO;
import com.leonardolucs.todolist.models.dto.UserDTO;
import com.leonardolucs.todolist.models.entities.Task;
import com.leonardolucs.todolist.models.entities.User;
import com.leonardolucs.todolist.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private Optional<User> findUserById(UUID id) {
        return userRepository.findById(id);
    }

    public LoginUserDTO createLoginUserDTO(User user){
        return new LoginUserDTO(user.getId(), user.getName());
    }


    public User findEntityById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private PublicUserDTO createPublicUserDTO(User user) {

        return new PublicUserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getTasks().stream().map(Task::toDto).collect(Collectors.toList()));

    }

    public ResponseEntity<?> getUser(UUID id) {
        Optional<User> foundUser = findUserById(id);
        return foundUser.isEmpty()
                ? ResponseEntity.badRequest().body("User not registered.")
                : ResponseEntity.ok(createPublicUserDTO(foundUser.get()));
    }

    public List<PublicUserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<PublicUserDTO> publicUsers = new ArrayList<>();

        for (User user : users) {
            PublicUserDTO publicUserDTO = createPublicUserDTO(user);
            publicUsers.add(publicUserDTO);
        }

        return publicUsers;
    }

    public void createUser(UserDTO userDTO) {
        User newUser = new User(userDTO.name(), userDTO.email(), userDTO.password());
        userRepository.save(newUser);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public ResponseEntity<?> updateUser(UUID id, UserDTO userDTO) {
        Optional<User> foundUser = findUserById(id);

        if (foundUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        User user = foundUser.get();
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        userRepository.save(user);

        return ResponseEntity.ok(createPublicUserDTO(user));
    }
    public ResponseEntity<?> login(LoginDTO user) {
        Optional<User> foundUser = userRepository.findUserByEmailAndPassword(user.email(), user.password());

        if (foundUser.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of(
                        "success", false,
                        "message", "User not found."
            ));
        }
        if (!foundUser.get().getEmail().equals(user.email()) || !foundUser.get().getPassword().equals(user.password())) {
            return ResponseEntity.status(404).body(Map.of(
                    "success", false,
                    "message", "Invalid Credentials"
            ));
        }
        LoginUserDTO userDTO = createLoginUserDTO(foundUser.get());
        return ResponseEntity.ok(userDTO);


    }
    // public ResponseEntity<?> findUserByEmail(String email){
    //     Optional<User> foundUser = searchUserByEmail(email);

    //     if(foundUser.isEmpty()){
    //         return ResponseEntity.notFound().build();
    //     }

    //     UserDTO userDTO = createUserDTO(foundUser.get());
    //     return ResponseEntity.ok().body(userDTO);
    // }
}
