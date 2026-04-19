package com.grokking.chapter09.section_9_3_4_serverless_functions.infrastructure.aws;

/**
 * THE AWS RESPONSE CONTRACT:
 *
 * DESIGN NOTE:
 * AWS Lambda functions integrated with API Gateway must return a specific 
 * JSON structure. 
 *
 * ARCHITECTURAL CRITIQUE:
 * Notice that even our 'Return Type' is dictated by the vendor. This is 
 * another "Abstraction Leak." Your method cannot simply return a 'User' 
 * or a 'String'; it must return a 'ProxyResponse' wrapper. The vendor's 
 * requirements have effectively "owned" the exit point of your reactive pipeline.
 */
public record APIGatewayProxyResponse(int statusCode, String body) {}