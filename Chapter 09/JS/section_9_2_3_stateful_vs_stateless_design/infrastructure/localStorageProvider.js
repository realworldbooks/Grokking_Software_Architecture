import { promises as fsPromises, mkdirSync, existsSync } from 'fs';
import path from 'path';
import { from, throwError } from 'rxjs';

/**
 * THE STATEFUL ADAPTER (The Fragile Monolith):
 * * TEACHING NOTE:
 * This fulfills our storage contract by writing directly to the server's local disk.
 * WARNING: This is an anti-pattern for modern cloud applications!
 * If we put a Load Balancer in front of two servers using this code, Server B will 
 * have no idea about the files saved on Server A's local drive. 
 * If Server A crashes, that user's profile picture is gone forever.
 * * We use `from()` to convert native Node.js Promise-based file system calls 
 * into Reactive Observables. We use `throwError()` to immediately push a 
 * failure down the stream if the file doesn't exist on this specific server.
 */
export class LocalStorageProvider {
    
    /**
     * @param {string} serverName - Used to simulate separate server hard drives.
     */
    constructor(serverName) {
        this.drivePath = `${serverName}_drive`;
        
        // Ensure the directory exists when the server boots
        if (!existsSync(this.drivePath)) {
            mkdirSync(this.drivePath, { recursive: true });
        }
    }

    /**
     * Saves data to the local file system.
     * @param {string} fileName 
     * @param {string} data 
     * @returns {import('rxjs').Observable<void>}
     */
    save(fileName, data) {
        const filePath = path.join(this.drivePath, fileName);
        return from(fsPromises.writeFile(filePath, data, 'utf-8'));
    }

    /**
     * Retrieves data from the local file system.
     * @param {string} fileName 
     * @returns {import('rxjs').Observable<string>}
     */
    get(fileName) {
        const filePath = path.join(this.drivePath, fileName);
        
        if (!existsSync(filePath)) {
            // Emits an error into the Observable stream
            return throwError(() => new Error(`File not found on local drive: ${filePath}`));
        }
        
        return from(fsPromises.readFile(filePath, 'utf-8'));
    }
}