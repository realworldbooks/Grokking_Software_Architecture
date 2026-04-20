package com.grokkingsoftwarearchitecture.chapter09.section_9_2_3_stateful_vs_stateless_design.services;

/**
 * THE PORT (Dependency Inversion Principle):
 * * TEACHING NOTE:
 * This interface is the "Airlock" between our core business logic and the outside world.
 * Notice that it says absolutely nothing about local hard drives, file paths, 
 * or Amazon S3 buckets. It only defines WHAT the application needs (save and get), 
 * leaving the HOW to the infrastructure layer. 
 */
public interface StorageProvider {
    void save(String fileName, String data) throws Exception;
    String get(String fileName) throws Exception;
}