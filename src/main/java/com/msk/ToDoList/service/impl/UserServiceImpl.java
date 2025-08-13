package com.msk.ToDoList.service.impl;


import com.msk.ToDoList.model.User;
import com.msk.ToDoList.repository.UserRepository;
import com.msk.ToDoList.service.RoleService;
import com.msk.ToDoList.service.UserService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${spring.app.keycloak.realm}")
    private String realm;

    private final RoleService roleService;

    private final UserRepository userRepository;

    private final Keycloak keycloak; //by using this configuration we will save users in keycloak

    @Override
    public void createUser(User user) {

        //setting the user representation

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setId(user.getId());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setUsername(user.getUserName());
        userRepresentation.setEmail(user.getUserName());


//        userRepresentation.setAttributes(Collections.singletonMap("customUserId", Collections.singletonList(customUserId)));

        userRepresentation.setEmailVerified(false);

        //setting the password
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(user.getPassword());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        //setting the user credentials
        userRepresentation.setCredentials(List.of(credentialRepresentation));

        //creating the user
        UsersResource usersResource = getUsersResource();


        Response response = usersResource.create(userRepresentation);


        userRepository.save(user);




        //creating the user

        log.info("Status Code : "+ response.getStatus());

        if (response.getStatus() == 201) {
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            roleService.assignRoleToUser(userId, "MEMBER");
            log.info(userId);
        }
        log.info("User created successfully");

         //when the user sign up he will automatically get a confirmation link on his email
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(user.getUserName(), true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);
        sendVerificationEmail(userRepresentation1.getId());


    }

    @Override
    public void sendVerificationEmail(String userId) {

        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    @Override
    public void deleteUser(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.delete(userId);
    }

    @Override
    public void forgotPassword(String username) {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(username, true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);

        UserResource userResource = usersResource.get(userRepresentation1.getId());

        userResource.executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    @Override
    public UserResource getUser(String userId) {
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }


    private UsersResource getUsersResource () {
        return keycloak.realm(realm).users();
    }
    @Override
    public List<RoleRepresentation> getUserRoles(String userId) {

        UserResource user = getUser(userId);

        return user.roles().realmLevel().listAll();
    }

    @Override
    public List<GroupRepresentation> getUserGroups(String userId) {
        return getUser(userId).groups();
    }


}
