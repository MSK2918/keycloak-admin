package com.msk.ToDoList.controller;

import com.msk.ToDoList.model.User;
import com.msk.ToDoList.service.RoleService;
import com.msk.ToDoList.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;


    @PutMapping("/assign/users/{userId}")
    public ResponseEntity<?> assignRoleToUser (@PathVariable String userId, @RequestParam String roleName) {
        roleService.assignRoleToUser(userId, roleName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

//    @PutMapping("/remove/users/{userId}")
//    public ResponseEntity<?> removeRoleFromUser (@PathVariable String userId, @RequestParam String roleName) {
//
//        roleService.deleteRoleFromUser(userId, roleName);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }





}
