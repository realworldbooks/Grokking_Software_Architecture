package com.grokkingsoftwarearchitecture.chapter03.couplingexercise.after;

/**
 * THE SOLUTION: The "Chunky" API and High Cohesion.
 * * ARCHITECTURE NOTE: The service has taken back its rightful responsibilities!
 * Instead of forcing the client to orchestrate the data gathering, the service 
 * now does the heavy lifting internally. 
 * * By exposing a single, coarse-grained ("chunky") method, we eliminate the 
 * Chatty API problem, minimizing potential network round-trips and completely 
 * encapsulating the internal complexity of how an order total is calculated.
 */
public class UserDataService {
    
    /**
     * Assembles the complete user report internally and returns a single payload.
     */
    public UserReportData getUserReport(int userId) {
        System.out.println("    [Service] Building chunky report payload internally...");
        
        // The service now handles all the internal database fetching and 
        // math, keeping the client completely ignorant of the details!
        return new UserReportData("Jane Doe", "jane.doe@example.com", 199.90);
    }
}