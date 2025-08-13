package com.msk.ToDoList.service.impl;


import com.msk.ToDoList.model.ToDo;
import com.msk.ToDoList.model.User;
import com.msk.ToDoList.repository.ToDoRepository;
import com.msk.ToDoList.service.TodoService;
import com.msk.ToDoList.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements TodoService {

    private final UserService userService;

    private final ToDoRepository toDoRepository;
    @Override
    public void createTodo(String userId, ToDo toDo) {

        UserResource userResource = userService.getUser(userId);
        User user = new User();
        user.setId(userResource.toRepresentation().getId());
        user.setUserName(userResource.toRepresentation().getUsername());
        user.setUserName(userResource.toRepresentation().getEmail());
        user.setFirstName(userResource.toRepresentation().getFirstName());
        user.setLastName(userResource.toRepresentation().getLastName());
        toDo.setUser(user);
        toDoRepository.save(toDo);
    }




}
