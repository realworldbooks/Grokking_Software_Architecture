package com.grokkingsoftwarearchitecture.chapter09.section_9_3_4_serverless_functions.infrastructure.aws;

import io.reactivex.rxjava3.core.Observable;
import java.util.List;

/**
 * THE AWS INFRASTRUCTURE CONTRACT (Reactive S3 Event):
 *
 * DESIGN NOTE:
 * This class simulates the proprietary strongly-typed object tree provided by 
 * the AWS Lambda Java Events library, wrapped in an Observable stream.
 *
 * ARCHITECTURAL CRITIQUE:
 * In a reactive system, we treat cloud triggers as an infinite stream of incoming 
 * telemetry. However, notice the deep nesting: Records -> S3 -> Object -> Key. 
 * This is a textbook "Abstraction Leak." By forcing your business logic to 
 * navigate this specific hierarchy, the vendor has effectively "leaked" its 
 * internal data model into your application code. Your resizer logic is no 
 * longer a pure function; it is a downstream dependent of Amazon's design choices.
 */
public record MockS3Event(List<MockS3EventRecord> Records) {
    public record MockS3EventRecord(MockS3Entity s3) {}
    public record MockS3Entity(MockS3Bucket bucket, MockS3Object object) {}
    public record MockS3Bucket(String name) {}
    public record MockS3Object(String key) {}

    /**
     * Factory method to wrap a static event into a Reactive stream.
     */
    public static Observable<MockS3Event> createStream(String bucket, String key) {
        return Observable.just(new MockS3Event(List.of(
            new MockS3EventRecord(new MockS3Entity(new MockS3Bucket(bucket), new MockS3Object(key)))
        )));
    }
}