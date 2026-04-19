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
    constructor(fileName) {
        this.bindingData = { name: fileName };
        this.res = { status: 200, body: "" };
    }

    log(message) {
        console.log(`[Azure Log] ${message}`);
    }
}