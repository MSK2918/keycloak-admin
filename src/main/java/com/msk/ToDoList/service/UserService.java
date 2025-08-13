package com.msk.ToDoList.service;

import com.msk.ToDoList.model.User;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

public interface UserService {

    void createUser (User user);

    void sendVerificationEmail(String userId);

    void deleteUser (String userId);

    void forgotPassword(String username);

    UserResource getUser(String userId);

    List<RoleRepresentation> getUserRoles(String userId);
    List<GroupRepresentation> getUserGroups(String userId);
}
