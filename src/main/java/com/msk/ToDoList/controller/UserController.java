package com.msk.ToDoList.controller;

import com.msk.ToDoList.model.User;
import com.msk.ToDoList.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<?> createUser (@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Please check your email to verify your account");
    }




//    @PutMapping("/{id}/send-verification-email")
//    public ResponseEntity<?> sendVerificationEmail (@PathVariable String id) {
//        userService.sendVerificationEmail(id);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteUser (@PathVariable String id) {
//        userService.deleteUser(id);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }


//    @PutMapping("/forgot-password")
//    public ResponseEntity<?> forgotPassword (@RequestParam String username) {
//        userService.forgotPassword(username);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
//
    @GetMapping("/{userId}/roles")
    public ResponseEntity<?> getUserRoles (@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserRoles(userId));
    }

    @GetMapping("/{userId}/groups")
    public ResponseEntity<?> getUserGroups (@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserGroups(userId));
    }




}
