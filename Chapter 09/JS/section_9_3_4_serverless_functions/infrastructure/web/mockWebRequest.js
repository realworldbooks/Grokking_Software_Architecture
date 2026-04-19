import { of } from 'rxjs';

/**
 * THE WEB STANDARDS CONTRACT (Reactive Fetch API):
 * * DESIGN NOTE:
 * Supabase Edge Functions use standard Web APIs. In this reactive model, 
 * we treat the Request body itself as a stream (Observable).
 * * ARCHITECTURAL CRITIQUE:
 * This is the "Gold Standard" for portability. By using standard HTTP 
 * request/response models instead of vendor SDKs, your reactive pipeline 
 * remains "Standard-Coupled" rather than "Vendor-Locked." This code 
 * doesn't care if it's running on Supabase, Deno Deploy, or a local 
 * Node server—it only cares about the shape of a standard HTTP Request.
 */
export class MockRequest {
    /**
     * @param {Object} body - The JSON payload from a standard Webhook.
     */
    constructor(body) {
        this._body = body;
    }

    /**
     * Returns the body as a Reactive Stream.
     * @returns {Observable<Object>}
     */
    getBody$() {
        return of(this._body);
    }
}