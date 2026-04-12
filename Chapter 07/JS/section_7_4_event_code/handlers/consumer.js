/**
 * THE HANDLER CONTRACT: The abstraction for all background workers.
 * In a reactive architecture, consumers do not return simple Promises. 
 * Instead, they must return an RxJS Observable. This allows the system to 
 * chain complex retry logic, debouncing, or error handling later on.
 */
export class Consumer {
    /**
     * @param  event - The domain event to be processed.
     * @returns An Observable stream representing the asynchronous work.
     */
    handle(event) {
        throw new Error("Method 'handle()' must be implemented by concrete consumers.");
    }
}