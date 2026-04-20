namespace Chapter09.ServerlessFunctions.Infrastructure.Web;

/// <summary>
/// THE INFRASTRUCTURE CONTRACT (Standard Webhook Payload):
/// 
/// DESIGN NOTE:
/// This represents a platform-agnostic JSON payload sent via a standard HTTP POST. 
/// We name this 'MockWebhookPayload' to remain consistent with our AWS and Azure 
/// infrastructure mocks. It simulates the data structure sent by modern DBaaS 
/// platforms like Supabase or Hasura.
/// 
/// ARCHITECTURAL CRITIQUE:
/// Notice the contrast between this and the AWS S3 mock. This is a "Flat" and 
/// "Standard" contract. Because it relies on standard primitive types rather 
/// than vendor-specific SDK classes, the 'Contract Coupling' here is minimal. 
/// If you move from one provider to another, this file likely remains unchanged. 
/// This represents the highest level of architectural stability on the 
/// Compute Spectrum because the contract is based on Web Standards, not 
/// Vendor Propriety.
/// </summary>
public record MockWebhookPayload(string Type, string Table, MockWebhookRecord Record);

/// <summary>
/// Represents the specific database row telemetry within the mock webhook.
/// </summary>
public record MockWebhookRecord(string BucketId, string Name);