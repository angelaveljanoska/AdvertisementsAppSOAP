package com.example.demo.soap.domain.endpoint;

import com.example.demo.soap.*;
import com.example.demo.soap.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

public class UserEndpoint {

    private static final String NAMESPACE_URI = "http://demo.example.com/soap";

    private final UserRepository userRepository;

    @Autowired
    public UserEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Status mapStatus(UserRepository.Status status) {
        if(status== UserRepository.Status.FAILURE){
            return Status.FAILURE;
        }
        return Status.SUCCES;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();
        response.setUser(UserRepository.findUser(request.getId()));

        if (UserRepository.findUser(request.getId())==null){
            throw new RuntimeException("Error: This User is not currently in the db.");
        }

        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        UserRepository.Status status = UserRepository.deleteUser(request.getId());

        DeleteUserResponse response = new DeleteUserResponse();
        response.setStatus(mapStatus(status));

        if (UserRepository.deleteUser(request.getId())==null){
            throw new RuntimeException("Error: This User is not currently in the db.");
        }

        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "setUserRequest")
    @ResponsePayload
    public SetUserResponse setUser(@RequestPayload SetUserRequest request) {
        UserRepository.Status status = UserRepository.saveUser(request.getUser());

        UserRepository.saveUser(request.getUser());

        SetUserResponse response = new SetUserResponse();
        response.setStatus(mapStatus(status));

        return response;
    }
}
