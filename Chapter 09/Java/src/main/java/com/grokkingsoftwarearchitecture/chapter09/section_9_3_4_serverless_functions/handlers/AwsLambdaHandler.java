package com.grokkingsoftwarearchitecture.chapter09.section_9_3_4_serverless_functions.handlers;

import com.grokkingsoftwarearchitecture.chapter09.section_9_3_4_serverless_functions.infrastructure.aws.APIGatewayProxyResponse;
import com.grokkingsoftwarearchitecture.chapter09.section_9_3_4_serverless_functions.infrastructure.aws.MockS3Event;
import io.reactivex.rxjava3.core.Observable;

public class AwsLambdaHandler {
    /**
     * CLOUD 1: AWS LAMBDA (The Imperative Island - Reactive)
     *
     * THE ARCHITECTURAL LESSON: 
     * Even when using modern Observables, AWS remains "Infrastructure-Heavy." 
     * Because the platform only hands you metadata, your reactive pipeline 
     * must contain the "Plumbing" to go fetch the actual data bytes.
     */
    public Observable<APIGatewayProxyResponse> handleStream(Observable<MockS3Event> eventStream) {
        return eventStream.map(event -> {
            /* * TEACHING NOTE:
             * Notice the 'map' operator. We are observing the stream and 
             * projecting the S3 metadata into our logic. But because AWS 
             * only gives us the file's "Key", a real implementation would 
             * need to chain an SDK call (e.g. .flatMap()) here. The "Island" 
             * architecture forces the developer to bridge the gap between 
             * "Event" and "Data" manually within the logic.
             */
            String fileName = event.Records().get(0).s3().object().key();
            System.out.println("      [AWS Lambda] Reactive stream observed: " + fileName);
            System.out.println("      [AWS Lambda] Plumbing: Projecting SDK fetch into the pipe...");

            return new APIGatewayProxyResponse(200, "{\"message\": \"AWS processed stream\"}");
        });
    }
}