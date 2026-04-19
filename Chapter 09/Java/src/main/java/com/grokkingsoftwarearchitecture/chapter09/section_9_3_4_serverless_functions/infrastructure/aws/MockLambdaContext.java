package com.grokking.chapter09.section_9_3_4_serverless_functions.infrastructure.aws;

/**
 * THE AWS RUNTIME CONTEXT:
 *
 * DESIGN NOTE:
 * In a real AWS Lambda environment, this object is injected by the Java runtime. 
 * It contains metadata such as the Request ID, memory limits, and remaining 
 * execution time. 
 *
 * ARCHITECTURAL CRITIQUE:
 * This represents the "Physical Reality" of the hosting environment. By accepting 
 * this object, your method acknowledges it is a guest in Amazon's house. 
 * A Clarity Engineer recognizes this as a boundary marker: everything inside 
 * this object is "Infrastructure," and it must never be allowed to bleed 
 * into your pure business logic.
 */
public record MockLambdaContext(String awsRequestId, int memoryLimitInMB) {
    public MockLambdaContext() {
        this("aws-req-rx-java-777", 512);
    }
}