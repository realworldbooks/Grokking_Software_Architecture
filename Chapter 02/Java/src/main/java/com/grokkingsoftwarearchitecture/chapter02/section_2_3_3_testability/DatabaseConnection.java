package com.grokkingsoftwarearchitecture.chapter02.section_2_3_3_testability;
import java.util.List;

public interface DatabaseConnection {
    List<String> getData(String query);
}