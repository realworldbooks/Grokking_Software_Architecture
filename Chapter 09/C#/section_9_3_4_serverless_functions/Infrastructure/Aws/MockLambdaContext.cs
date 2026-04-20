namespace Chapter09.ServerlessFunctions.Infrastructure.Aws;

/// <summary>
/// THE AWS RUNTIME CONTEXT:
/// 
/// TEACHING NOTE:
/// This simulates the 'ILambdaContext' provided by the AWS Runtime. It exposes 
/// infrastructure-level metadata like the RequestId and memory limits. It represents 
/// the "Physical Reality" of the hosting environment that the cloud vendor 
/// injects into your execution thread.
/// </summary>
public interface IMockLambdaContext { string AwsRequestId { get; } }
public class MockLambdaContext : IMockLambdaContext 
{ 
    public string AwsRequestId => "aws-req-rx-12345"; 
}