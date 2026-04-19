import { of } from 'rxjs';

/**
 * THE AWS INFRASTRUCTURE CONTRACT (Reactive S3 Event):
 * * DESIGN NOTE:
 * This class simulates the proprietary JSON structure that Amazon S3 sends 
 * to a Lambda handler, but wrapped in an Observable. 
 * * ARCHITECTURAL CRITIQUE:
 * In a reactive system, the cloud trigger is an infinite stream of events. 
 * Notice the nesting (Records -> s3 -> object). This forces the developer 
 * to write "Infrastructure-Aware" code. Even in a reactive pipeline, 
 * you are forced to pipe through Amazon's proprietary schema. This is an 
 * Abstraction Leak—the vendor's data structure is now a mandatory dependency 
 * in your reactive stream transformation.
 */
export class MockS3Event {
    /**
     * @param {string} bucketName 
     * @param {string} fileName 
     * @returns {Observable<Object>} A stream emitting the proprietary AWS JSON.
     */
    static create$(bucketName, fileName) {
        return of({
            Records: [{
                s3: {
                    bucket: { name: bucketName },
                    object: { key: fileName }
                }
            }]
        });
    }
}

/**
 * THE AWS RUNTIME CONTEXT (Reactive):
 * Simulates the environment stream provided by the AWS Lambda engine.
 */
export class MockLambdaContext {
    constructor() {
        this.awsRequestId = "aws-req-reactive-12345";
    }
}