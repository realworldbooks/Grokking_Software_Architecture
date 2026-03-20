package com.grokkingsoftwarearchitecture.chapter02.testability;

import java.util.List;

public interface DatabaseConnection {
    List<String> getData(String query);
}