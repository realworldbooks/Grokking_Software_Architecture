/**
 * INFRASTRUCTURE LAYER: EMAIL.
 * ARCHITECTURE NOTE: Real-world infrastructure like this 
 * should be hidden behind an interface.
 */
class SmtpEmailService {
    send(email, message) {
        console.log(`  [Email] SMTP: Sending "${message}" to ${email}`);
    }
}

module.exports = SmtpEmailService;