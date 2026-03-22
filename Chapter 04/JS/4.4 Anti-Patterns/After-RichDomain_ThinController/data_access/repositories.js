// Chapter 04/JavaScript/4.4 Anti-Patterns/After-RichDomain/data_access/emailService.js
const { IEmailService } = require('./dataAccessInterfaces');

/**
 * ARCHITECTURE NOTE: By isolating Email logic here, we prevent 
 * database concerns from "leaking" into the Presentation or 
 * Business layers.
 * * @implements {IEmailService}
 */
class SmtpEmailService extends IEmailService {
    // Concrete implementation for an email provider
    send(to, subject, body) {
        // Implementation logic would go here
    }
}

module.exports = { SmtpEmailService };