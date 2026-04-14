package com.grokkingsoftwarearchitecture.chapter08.section_8_1_4_database_comparison.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoSqlDocument {
    public String name = "";
    public List<String> tags = new ArrayList<>();
    
    /**
     * JAVA ARCHITECTURAL NOTE: 
     * In Java, objects are strictly typed. To simulate the schema-less, 
     * flexible nature of a JSON Document DB (like MongoDB), we use a 
     * Map<String, Object> to hold arbitrary data added at runtime.
     */
    public Map<String, Object> flexibleData = new HashMap<>();
}