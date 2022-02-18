package com.example.demo.soap.domain.endpoint;

import com.example.demo.soap.*;
import com.example.demo.soap.domain.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

public class AdvertisementEndpoint {
    private static final String NAMESPACE_URI = "http://demo.example.com/soap";

    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementEndpoint(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    private Status mapStatus(AdvertisementRepository.Status status) {
        if(status== AdvertisementRepository.Status.FAILURE){
            return Status.FAILURE;
        }
        return Status.SUCCES;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAdvertisementRequest")
    @ResponsePayload
    public GetAdvertisementResponse getAdvertisement(@RequestPayload GetAdvertisementRequest request) {
        GetAdvertisementResponse response = new GetAdvertisementResponse();
        response.setAdvertisement(advertisementRepository.findAdvertisement(request.getId()));

        if (advertisementRepository.findAdvertisement(request.getId())==null){
            throw new RuntimeException("Error: This advertisement is not currently in the db.");
        }

        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAdvertisementRequest")
    @ResponsePayload
    public DeleteAdvertisementResponse deleteAdvertisement(@RequestPayload DeleteAdvertisementRequest request) {
        AdvertisementRepository.Status status = advertisementRepository.deleteAdvertisement(request.getId());

        DeleteAdvertisementResponse response = new DeleteAdvertisementResponse();
        response.setStatus(mapStatus(status));

        if (advertisementRepository.deleteAdvertisement(request.getId())==null){
            throw new RuntimeException("Error: This advertisement is not currently in the db.");
        }

        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "setAdvertisementRequest")
    @ResponsePayload
    public SetAdvertisementResponse setAdvertisement(@RequestPayload SetAdvertisementRequest request) {
        AdvertisementRepository.Status status = advertisementRepository.saveAdvertisement(request.getAdvertisement());

        advertisementRepository.saveAdvertisement(request.getAdvertisement());

        SetAdvertisementResponse response = new SetAdvertisementResponse();
        response.setStatus(mapStatus(status));

        return response;
    }
}
