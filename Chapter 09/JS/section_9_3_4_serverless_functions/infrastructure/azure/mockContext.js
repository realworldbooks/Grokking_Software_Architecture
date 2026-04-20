import { of } from 'rxjs';
/**
 * THE AZURE INFRASTRUCTURE CONTRACT (Context Object):
 * * DESIGN NOTE:
 * Azure Functions in Node.js inject a 'context' object that manages logging 
 * and response state. 
 * * ARCHITECTURAL CRITIQUE:
 * This is a "Signature Leak." While Azure hides network plumbing via bindings, 
 * it forces you to use their proprietary 'context.log' and 'context.res' APIs 
 * instead of standard language features.
 */
export class MockAzureContext {
    constructor(fileName, blobData) {
        this.bindingData = { name: fileName };
        this.blobData = blobData; // FIX: Ensure blob data is stored for the handler
        this.res = { status: 200, body: "" };
    }

    /**
     * Lifts the proprietary context into the Reactive Way.
     * @returns {Observable<MockAzureContext>}
     */
    asObservable$() {
        // We wrap 'this' in an observable so the pipeline can observe 
        // the platform-injected data.
        return of(this);
    }

    log(message) {
        console.log(`[Azure Log] ${message}`);
    }
}