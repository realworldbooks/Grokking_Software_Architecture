import { of, throwError } from 'rxjs';

/**
 * THE STATELESS ADAPTER (Cloud Native Design):
 * * TEACHING NOTE:
 * This adapter moves the "state" out of the individual web servers and into 
 * a centralized, external location (simulating Amazon S3). 
 * * Because our UserService relies on Dependency Injection, we can swap 
 * from the fragile LocalStorage to this robust Cloud provider without 
 * changing a single line of our business logic!
 * * We use the `of()` operator from RxJS to create an Observable that 
 * instantly emits our simulated S3 data and then completes the stream.
 */

// This Map acts as our "External Cloud". Because it lives outside the class instance,
// any server that instantiates this provider will access the exact same data store.
const s3BucketSimulator = new Map();

export class SimulatedCloudStorageProvider {
    
    /**
     * @param {string} bucketName - The name of the central cloud bucket.
     */
    constructor(bucketName) {
        this.bucketName = bucketName;
        // In real life, you would initialize the AWS SDK S3 client here.
    }

    /**
     * Saves data to the centralized cloud bucket.
     * @param {string} fileName 
     * @param {string} data 
     * @returns {import('rxjs').Observable<void>}
     */
    save(fileName, data) {
        const s3Key = `${this.bucketName}/${fileName}`;
        s3BucketSimulator.set(s3Key, data);
        
        // Return an empty observable that completes immediately
        return of(void 0); 
    }

    /**
     * Retrieves data from the centralized cloud bucket.
     * @param {string} fileName 
     * @returns {import('rxjs').Observable<string>}
     */
    get(fileName) {
        const s3Key = `${this.bucketName}/${fileName}`;
        
        if (!s3BucketSimulator.has(s3Key)) {
            return throwError(() => new Error(`404 Not Found in S3 Bucket: ${s3Key}`));
        }
        
        return of(s3BucketSimulator.get(s3Key));
    }
}