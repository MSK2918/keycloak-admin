package com.msk.ToDoList.service.impl;

import com.msk.ToDoList.service.RoleService;
import com.msk.ToDoList.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Value("${spring.app.keycloak.realm}")
    private String realm;
    private final Keycloak keycloak;

    @Override
    public void assignRoleToUser(String userId, String roleName) {

        //retrieve the role from keycloak
        RoleRepresentation role = keycloak.realm(realm).roles().get(roleName).toRepresentation();

        //assign the role to the user
        keycloak.realm(realm).users().get(userId).roles().realmLevel().add(Collections.singletonList(role));
    }

//    @Override
//    public void deleteRoleFromUser(String userId, String roleName) {
//
////        UserResource user = userService.getUser(userId);
//        RolesResource roles = getRolesResource();
//        RoleRepresentation role = roles.get(roleName).toRepresentation();
//        user.roles().realmLevel().remove(Collections.singletonList(role));
//    }

    private RolesResource getRolesResource () {
        return keycloak.realm(realm).roles();
    }
}
