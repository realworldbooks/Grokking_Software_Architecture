package com.architecturebook.chapter2.testability;

import java.util.List;

public interface DatabaseConnection {
    List<String> getData(String query);
}