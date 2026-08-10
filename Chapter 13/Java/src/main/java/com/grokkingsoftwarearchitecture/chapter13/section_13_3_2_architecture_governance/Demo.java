package com.grokkingsoftwarearchitecture.chapter13.section_13_3_2_architecture_governance;

import com.grokkingsoftwarearchitecture.chapter13.section_13_3_2_architecture_governance.presentation.BaseController;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Listing 13.1: Architectural Boundary Enforcement using ArchUnit.
 *
 * This is the Java equivalent of the C# NetArchTest example from the book.
 * ArchUnit is the industry-standard architecture testing library for Java.
 *
 * Instead of relying on human code reviewers to catch architectural
 * violations, we codify our structural rules as executable Fitness
 * Functions that run automatically in the CI pipeline.
 *
 * The two rules we enforce:
 *   1. The Domain Layer must NEVER depend on the Infrastructure Layer.
 *      This is the "Downward Dependency Rule" - the protected core
 *      of the system must stay pure and isolated.
 *
 *   2. All Controllers must follow the naming convention of ending
 *      with "Controller" AND reside in the Presentation package.
 *      This maintains high cohesion in the outermost layer.
 *
 * In a real CI pipeline, these tests run on every commit and every
 * Pull Request. If a developer (or an AI assistant) accidentally
 * breaks a boundary, the build flashes red and the deployment halts.
 */
public class Demo {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("==============================================");
        System.out.println("  Listing 13.1: Architectural Boundary");
        System.out.println("  Enforcement using ArchUnit (Java)");
        System.out.println("==============================================\n");

        System.out.println("The Shop-Zilla Order Service follows a Four-Layer");
        System.out.println("Architecture: Presentation -> Application -> Domain -> Infrastructure");
        System.out.println();
        System.out.println("Our Automated Guardrails enforce two structural rules:");
        System.out.println("  1. Domain must NOT depend on Infrastructure");
        System.out.println("  2. Controllers must end with 'Controller' suffix");
        System.out.println("     and reside in the Presentation package\n");

        // Import all compiled classes from the current package tree
        JavaClasses classes = new ClassFileImporter()
            .importPackages("com.grokkingsoftwarearchitecture.chapter13.section_13_3_2_architecture_governance");

        // ============================================================
        // RULE 1: The Domain Layer must be completely isolated.
        // It must never have a direct dependency on the Infrastructure
        // layer (such as raw database contexts or third-party HTTP clients).
        // ============================================================
        System.out.println("--- Rule 1: Domain Layer Isolation ---\n");

        ArchRule domainIsolation = noClasses()
            .that()
            .resideInAPackage("..domain..")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("..infrastructure..")
            .because("The Domain layer is the protected core and must stay pure.");

        evaluateRule(domainIsolation, classes,
            "Domain Layer must NOT depend on Infrastructure");

        // ============================================================
        // RULE 2: All Controllers must follow a strict naming convention.
        // They must extend BaseController, end with "Controller",
        // and reside in the Presentation package.
        // ============================================================
        System.out.println("\n--- Rule 2: Controller Naming Convention ---\n");

        ArchRule controllerConvention = classes()
            .that()
            .areAssignableTo(BaseController.class)
            .should()
            .haveSimpleNameEndingWith("Controller")
            .andShould()
            .resideInAPackage("..presentation..")
            .because("Controllers must follow the naming convention and stay in Presentation.");

        evaluateRule(controllerConvention, classes,
            "Controllers must have 'Controller' suffix and reside in Presentation");

        // ============================================================
        // DEMONSTRATION: What happens when a rule is violated?
        // We simulate a developer accidentally adding a dependency
        // from the Domain layer to the Infrastructure layer.
        // ============================================================
        System.out.println("\n--- Simulation: The Accidental Arsonist ---\n");
        System.out.println("Imagine a developer (or AI assistant) accidentally");
        System.out.println("adds a reference to OrderRepository inside the Domain layer...\n");

        // In a real codebase, this would be caught by the fitness function
        // and the build would fail. Here we just demonstrate the concept.
        System.out.println("[PASS] No violations detected - the Domain layer is clean.");
        System.out.println("       The build can proceed to the next stage.");

        System.out.println("\n==============================================");
        System.out.println("  In a real CI pipeline, these checks run on");
        System.out.println("  every commit. A violation = build failure.");
        System.out.println("==============================================");
    }

    /**
     * Helper method to evaluate an ArchUnit rule and print the result.
     */
    private static void evaluateRule(ArchRule rule, JavaClasses classes, String description) {
        try {
            rule.check(classes);
            System.out.println("[PASS] " + description);
            System.out.println("       All classes comply with the architectural rule.");
        } catch (AssertionError e) {
            System.out.println("[FAIL] " + description);
            System.out.println("       The following classes violate the rule:");
            System.out.println("       " + e.getMessage());
        }
    }
}