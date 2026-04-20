import { map } from 'rxjs/operators';

/**
 * CLOUD 2: AZURE FUNCTIONS (The Declarative App - Reactive)
 * * THE ARCHITECTURAL LESSON: 
 * Azure uses "Bindings" to abstract network plumbing. Your code doesn't 
 * "fetch" data; it "receives" data. However, the platform "Owns" your 
 * function's signature.
 * * @param {MockAzureContext} context - The proprietary Azure context.
 * @returns {Observable} A stream emitting the result of the mutation.
 */
export const execute$ = (context) => {
    return context.asObservable$().pipe(
        /**
         * TEACHING NOTE:
         * Notice the difference from AWS. We don't have to map a 'key' 
         * to a manual SDK call. The 'blobData' is already here. 
         * But look at the return: we are mutating 'context.res'. 
         * This "Platform Contamination" makes it nearly impossible to 
         * test this reactive pipe without bringing the whole Azure 
         * Mock object with it. The infrastructure has leaked into the 
         * very heart of the stream.
         */
        map(ctx => {
            const fileName = ctx.bindingData.name;
            const fileSize = ctx.blobData ? ctx.blobData.length : 0; // Safe access

            ctx.log(`      [Azure Function] Stream observed: ${fileName} (${fileSize} bytes)`);
            ctx.log(`      [Azure Function] Declarative logic: Processing resize...`);

            ctx.res = {
                status: 200,
                body: `Azure reactive processed ${fileName}`
            };

            return ctx.res;
        })
    );
};