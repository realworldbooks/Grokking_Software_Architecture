import { map } from 'rxjs/operators';

/**
 * CLOUD 1: AWS LAMBDA (The Imperative Island - Reactive)
 * * THE ARCHITECTURAL LESSON: 
 * Even in a reactive model, AWS remains "Infrastructure-Heavy." 
 * Because AWS doesn't fetch the file for you, your stream must 
 * contain the plumbing to go get the data.
 * * @param {Observable} event$ - A stream containing the AWS S3 JSON metadata.
 * @param {Object} context - The AWS Lambda context.
 * @returns {Observable} A stream emitting the AWS-compliant response object.
 */
export const handleRequest$ = (event$, context) => {
    return event$.pipe(
        /**
         * TEACHING NOTE:
         * Notice the 'map' operator. We are observing the stream and 
         * projecting the S3 metadata into our logic. But because AWS 
         * only gives us the name (the 'key'), we would have to chain 
         * another observable (like a 'switchMap') to imperatively 
         * call the S3 SDK. The "Island" architecture forces you to 
         * build your own bridge to your data within the pipeline.
         */
        map(event => {
            const fileName = event.Records[0].s3.object.key;
            console.log(`      [AWS Lambda] Stream observed. File: ${fileName}`);
            console.log(`      [AWS Lambda] Plumbing: Projecting SDK fetch into the stream...`);
            
            return {
                statusCode: 200,
                body: JSON.stringify({ message: "AWS processed reactive stream" })
            };
        })
    );
};