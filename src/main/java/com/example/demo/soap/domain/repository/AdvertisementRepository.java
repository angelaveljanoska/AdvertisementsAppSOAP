package com.example.demo.soap.domain.repository;

import com.example.demo.soap.Advertisement;

public class AdvertisementRepository {

    //This is dummy advertisement
    public Advertisement advertisement = new Advertisement();
    public Advertisement findAdvertisement(long id) {
        return advertisement;
    }
    //If this was a standalone project, we would connest this with database just as we did with the REST version.
    //Same goes with delete and save methods.
}
