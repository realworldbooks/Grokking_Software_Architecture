/**
 * THE DTO (The AI's Input Form)
 * * @swagger
 * components:
 * schemas:
 * ShippingRequest:
 * type: object
 * required:
 * - productId
 * - zipCode
 * properties:
 * productId:
 * type: string
 * description: The unique ID of the physical product. Do NOT send digital product IDs (like MP3s or eBooks).
 * zipCode:
 * type: string
 * description: The destination zip code. Must be exactly 5 digits.
 */
module.exports = {}; // Purely structural for Swagger parsing