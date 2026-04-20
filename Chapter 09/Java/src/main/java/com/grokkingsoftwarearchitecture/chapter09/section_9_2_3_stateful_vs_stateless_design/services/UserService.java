package com.grokkingsoftwarearchitecture.chapter09.section_9_2_3_stateful_vs_stateless_design.services;

/**
 * THE APPLICATION LAYER (Core Business Logic):
 * * TEACHING NOTE:
 * Look at the imports at the top of this file. There is no 'java.io.File' 
 * and no 'com.amazonaws.services.s3'. This class is blissfully ignorant of where 
 * the files actually go. By keeping infrastructure out of our domain, this class 
 * becomes incredibly easy to test and completely cloud-agnostic.
 */
public class UserService {
    
    private final StorageProvider storage;

    // We inject the dependency (The Adapter) through the constructor.
    // The UserService doesn't build its own database/file system; it asks for one to be provided.
    public UserService(StorageProvider storage) {
        this.storage = storage;
    }

    public void uploadAvatar(String userId, String imageData) throws Exception {
        String fileName = "profile_" + userId + ".jpg";
        this.storage.save(fileName, imageData);
    }

    public String viewAvatar(String userId) throws Exception {
        String fileName = "profile_" + userId + ".jpg";
        return this.storage.get(fileName);
    }
}