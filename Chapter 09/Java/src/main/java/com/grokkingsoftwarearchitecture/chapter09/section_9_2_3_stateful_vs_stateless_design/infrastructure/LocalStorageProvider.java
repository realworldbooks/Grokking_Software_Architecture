package com.grokkingsoftwarearchitecture.chapter09.section_9_2_3_stateful_vs_stateless_design.infrastructure;

import com.grokkingsoftwarearchitecture.chapter09.section_9_2_3_stateful_vs_stateless_design.services.StorageProvider;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileNotFoundException;

/**
 * THE STATEFUL ADAPTER (The Fragile Monolith):
 * * TEACHING NOTE:
 * This implements our StorageProvider by writing directly to the server's local disk.
 * WARNING: This is an anti-pattern for modern cloud applications!
 * If we put a Load Balancer in front of two servers using this code, Server B will 
 * have no idea about the files saved on Server A's local drive. 
 * If Server A crashes, that user's profile picture is gone forever.
 */
public class LocalStorageProvider implements StorageProvider {
    
    private final String drivePath;

    public LocalStorageProvider(String serverName) throws Exception {
        this.drivePath = serverName + "_drive";
        Path path = Paths.get(this.drivePath);
        
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    @Override
    public void save(String fileName, String data) throws Exception {
        Path filePath = Paths.get(this.drivePath, fileName);
        Files.writeString(filePath, data);
    }

    @Override
    public String get(String fileName) throws Exception {
        Path filePath = Paths.get(this.drivePath, fileName);
        
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found on local drive: " + filePath.toString());
        }
        
        return Files.readString(filePath);
    }
}