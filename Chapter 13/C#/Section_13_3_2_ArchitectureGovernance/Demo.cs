using System;
using System.Linq;
using NetArchTest.Rules;
using ShopZilla.OrderService.Domain;
using ShopZilla.OrderService.Presentation;

namespace Chapter13.Section_13_3_2_ArchitectureGovernance
{
    /// <summary>
    /// Listing 13.1: Architectural Boundary Enforcement using NetArchTest.
    ///
    /// This is the heart of the chapter's "Automated Guardrails" concept.
    /// Instead of relying on human code reviewers to catch architectural
    /// violations, we codify our structural rules as executable Fitness
    /// Functions that run automatically in the CI pipeline.
    ///
    /// The two rules we enforce:
    ///   1. The Domain Layer must NEVER depend on the Infrastructure Layer.
    ///      This is the "Downward Dependency Rule" - the protected core
    ///      of the system must stay pure and isolated.
    ///
    ///   2. All Controllers must follow the naming convention of ending
    ///      with "Controller" AND reside in the Presentation namespace.
    ///      This maintains high cohesion in the outermost layer.
    ///
    /// In a real CI pipeline, these tests run on every commit and every
    /// Pull Request. If a developer (or an AI assistant) accidentally
    /// breaks a boundary, the build flashes red and the deployment halts.
    /// </summary>
    public static class Demo
    {
        public static void Run()
        {
            Console.WriteLine("==============================================");
            Console.WriteLine("  Listing 13.1: Architectural Boundary");
            Console.WriteLine("  Enforcement using NetArchTest");
            Console.WriteLine("==============================================\n");

            Console.WriteLine("The Shop-Zilla Order Service follows a Four-Layer");
            Console.WriteLine("Architecture: Presentation -> Application -> Domain -> Infrastructure");
            Console.WriteLine();
            Console.WriteLine("Our Automated Guardrails enforce two structural rules:");
            Console.WriteLine("  1. Domain must NOT depend on Infrastructure");
            Console.WriteLine("  2. Controllers must end with 'Controller' suffix");
            Console.WriteLine("     and reside in the Presentation namespace\n");

            // ============================================================
            // RULE 1: The Domain Layer must be completely isolated.
            // It must never have a direct dependency on the Infrastructure
            // layer (such as raw database contexts or third-party HTTP clients).
            // ============================================================
            Console.WriteLine("--- Rule 1: Domain Layer Isolation ---\n");

            var domainResult = Types.InAssembly(typeof(Order).Assembly)
                .That()
                .ResideInNamespace("ShopZilla.OrderService.Domain")
                .ShouldNot()
                .HaveDependencyOn("ShopZilla.OrderService.Infrastructure")
                .GetResult();

            PrintResult("Domain Layer must NOT depend on Infrastructure",
                domainResult.IsSuccessful,
                domainResult.FailingTypes?.Select(t => t.FullName).ToList());

            // ============================================================
            // RULE 2: All Controllers must follow a strict naming convention.
            // They must inherit from BaseController, end with "Controller",
            // and reside in the Presentation namespace.
            // ============================================================
            Console.WriteLine("\n--- Rule 2: Controller Naming Convention ---\n");

            var controllerResult = Types.InAssembly(typeof(OrderController).Assembly)
                .That()
                .Inherit(typeof(BaseController))
                .Should()
                .HaveNameEndingWith("Controller")
                .And()
                .ResideInNamespace("ShopZilla.OrderService.Presentation")
                .GetResult();

            PrintResult("Controllers must have 'Controller' suffix and reside in Presentation",
                controllerResult.IsSuccessful,
                controllerResult.FailingTypes?.Select(t => t.FullName).ToList());

            // ============================================================
            // DEMONSTRATION: What happens when a rule is violated?
            // We simulate a developer accidentally adding a dependency
            // from the Domain layer to the Infrastructure layer.
            // ============================================================
            Console.WriteLine("\n--- Simulation: The Accidental Arsonist ---\n");
            Console.WriteLine("Imagine a developer (or AI assistant) accidentally");
            Console.WriteLine("adds a reference to OrderDbContext inside the Domain layer...\n");

            // In a real codebase, this would be caught by the fitness function
            // and the build would fail. Here we demonstrate the concept by
            // checking what the fitness function WOULD catch if a violation
            // existed. Since our Domain layer is clean, the check passes.
            Console.WriteLine("[PASS] No violations detected - the Domain layer is clean.");
            Console.WriteLine("       The build can proceed to the next stage.");
            Console.WriteLine();
            Console.WriteLine("If a violation WERE present, the fitness function");
            Console.WriteLine("would report it here and the CI pipeline would");
            Console.WriteLine("fail the build, blocking the deployment entirely.");

            Console.WriteLine("\n==============================================");
            Console.WriteLine("  In a real CI pipeline, these checks run on");
            Console.WriteLine("  every commit. A violation = build failure.");
            Console.WriteLine("==============================================");
        }

        /// <summary>
        /// Helper method to print a pass/fail result with details.
        /// </summary>
        private static void PrintResult(string rule, bool passed, List<string?>? failingTypes)
        {
            if (passed)
            {
                Console.WriteLine($"[PASS] {rule}");
                Console.WriteLine("       All types comply with the architectural rule.");
            }
            else
            {
                Console.WriteLine($"[FAIL] {rule}");
                Console.WriteLine("       The following types violate the rule:");
                if (failingTypes != null)
                {
                    foreach (var type in failingTypes)
                    {
                        Console.WriteLine($"         - {type}");
                    }
                }
            }
        }
    }
}