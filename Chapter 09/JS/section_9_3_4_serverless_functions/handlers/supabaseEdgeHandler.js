import { map } from 'rxjs/operators';

/**
 * CLOUD 3: SUPABASE EDGE FUNCTIONS (The Reactive Web Standard)
 * * THE ARCHITECTURAL LESSON: 
 * Standardized streams provide the highest portability and smallest 
 * footprint. This is the "Cleanest" serverless architecture.
 * * @param {MockRequest} req - The reactive request object.
 * @returns {Observable} A stream emitting the response metadata.
 */
export const handler$ = (req) => {
    /**
     * TEACHING NOTE:
     * This implementation is "Standard-Fluent." We subscribe to the 
     * request body stream directly. There is zero cloud-vendor code here. 
     * This pipeline treats a cloud trigger exactly like a browser 
     * treats a standard fetch request. This is the ultimate goal of 
     * a Clarity Engineer: making the hosting environment an 
     * invisible detail rather than a dominant force in the code.
     */
    return req.getBody$().pipe(
        map(payload => {
            const fileName = payload.record.name;
            console.log(`      [Supabase Edge] Body stream observed. File: ${fileName}`);
            console.log(`      [Supabase Edge] Processing image resize reactively...`);
            
            return {
                status: 200,
                body: `Supabase reactive processed ${fileName}`
            };
        })
    );
};