package com.grokking.chapter09.section_9_2_3_stateful_vs_stateless.infrastructure;

import com.grokking.chapter09.section_9_2_3_stateful_vs_stateless.services.StorageProvider;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * THE STATELESS ADAPTER (Cloud Native Design):
 * * TEACHING NOTE:
 * This adapter moves the "state" out of the individual web servers and into 
 * a centralized, external location (simulating Amazon S3). 
 * * Because our UserService depends entirely on the StorageProvider interface, 
 * we can swap from the fragile LocalStorageProvider to this robust Cloud provider 
 * without changing a single line of our business logic!
 */
public class SimulatedCloudStorageProvider implements StorageProvider {
    
    // The static Map acts as our "External Cloud". 
    // Even if we instantiate 100 web servers, they all point to this exact same data store.
    private static final Map<String, String> s3BucketSimulator = new ConcurrentHashMap<>();
    private final String bucketName;

    public SimulatedCloudStorageProvider(String bucketName) {
        this.bucketName = bucketName;
        // In real life, you would initialize the AWS SDK S3 client here.
    }

    @Override
    public void save(String fileName, String data) {
        String s3Key = this.bucketName + "/" + fileName;
        s3BucketSimulator.put(s3Key, data);
    }

    @Override
    public String get(String fileName) throws Exception {
        String s3Key = this.bucketName + "/" + fileName;
        
        if (!s3BucketSimulator.containsKey(s3Key)) {
            throw new FileNotFoundException("404 Not Found in S3 Bucket: " + s3Key);
        }
        
        return s3BucketSimulator.get(s3Key);
    }
}