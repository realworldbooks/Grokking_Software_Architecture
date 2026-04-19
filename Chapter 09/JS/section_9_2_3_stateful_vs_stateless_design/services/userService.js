/**
 * THE APPLICATION LAYER (Core Business Logic):
 * * TEACHING NOTE:
 * Look at the top of this file. There are no 'fs' (File System) or 'aws-sdk' imports. 
 * This class is blissfully ignorant of where the files actually go. 
 * By keeping infrastructure out of our domain, this class becomes incredibly easy 
 * to test and completely cloud-agnostic.
 * * Notice that we return `Observable` streams instead of Promises. 
 * In a Reactive Architecture, everything is a stream of data. The application 
 * layer defines the "plumbing" (what the stream should do), but the data 
 * doesn't actually flow until someone down the line calls `.subscribe()`.
 */
export class UserService {
    
    /**
     * We inject the dependency (The Adapter) through the constructor.
     * JavaScript uses "duck typing" here: as long as the provider object passed in 
     * has 'save' and 'get' methods, this service will work perfectly!
     * * @param {Object} storageProvider - The injected infrastructure adapter.
     * @param {function} storageProvider.save - Method returning an Observable.
     * @param {function} storageProvider.get - Method returning an Observable.
     */
    constructor(storageProvider) {
        this._storage = storageProvider;
    }

    /**
     * Uploads a user's avatar.
     * @param {string} userId 
     * @param {string} imageData 
     * @returns {import('rxjs').Observable<void>} A stream that emits when the save is complete.
     */
    uploadAvatar(userId, imageData) {
        const fileName = `profile_${userId}.jpg`;
        return this._storage.save(fileName, imageData);
    }

    /**
     * Retrieves a user's avatar.
     * @param {string} userId 
     * @returns {import('rxjs').Observable<string>} A stream containing the image data.
     */
    viewAvatar(userId) {
        const fileName = `profile_${userId}.jpg`;
        return this._storage.get(fileName);
    }
}