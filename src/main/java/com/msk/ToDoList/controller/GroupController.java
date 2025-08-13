package com.msk.ToDoList.controller;

import com.msk.ToDoList.service.GroupService;
import com.msk.ToDoList.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;


    @PutMapping("/{groupId}/assign/users/{userId}")
    public ResponseEntity<?> assignGroupToUser (@PathVariable String userId, @PathVariable String groupId) {
        groupService.assignGroup(userId, groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{groupId}/remove/users/{userId}")
    public ResponseEntity<?> removeGroupFromUser (@PathVariable String userId, @PathVariable String groupId) {
       groupService.deleteGroup(userId, groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }





}
