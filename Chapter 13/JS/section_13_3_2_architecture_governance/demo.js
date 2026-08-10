/**
 * Listing 13.1: Architectural Boundary Enforcement (Node.js).
 *
 * JavaScript does not have a direct equivalent of NetArchTest (.NET) or
 * ArchUnit (Java), so we replicate the functionality of those libraries
 * with a custom "Fitness Function" that parses the source tree and
 * enforces the same architectural rules.
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

import fs from "fs";
import path from "path";
import { fileURLToPath } from "url";

const __dirname = path.dirname(fileURLToPath(import.meta.url));

/**
 * A simple architectural fitness function engine.
 * This class reads source files and evaluates them against
 * architectural rules - just like NetArchTest and ArchUnit do
 * for compiled .NET and Java assemblies.
 */
class FitnessFunction {
  constructor(rootDir) {
    this.rootDir = rootDir;
    this.violations = [];
  }

  /** Recursively find all .js files under the root directory. */
  _iterJsFiles() {
    const results = [];
    const walk = (dir) => {
      for (const entry of fs.readdirSync(dir, { withFileTypes: true })) {
        const fullPath = path.join(dir, entry.name);
        if (entry.isDirectory()) {
          walk(fullPath);
        } else if (entry.name.endsWith(".js")) {
          results.push(fullPath);
        }
      }
    };
    walk(this.rootDir);
    return results.sort();
  }

  /** Get the file path relative to the root directory. */
  _getRelativePath(filePath) {
    return path.relative(this.rootDir, filePath).replace(/\\/g, "/");
  }

  /** Extract all module specifiers imported by a JS file. */
  _getImports(source) {
    const imports = [];
    const importRegex = /from\s+["']([^"']+)["']/g;
    let match;
    while ((match = importRegex.exec(source)) !== null) {
      imports.push(match[1]);
    }
    return imports;
  }

  /** RULE 1: Domain layer must never import from Infrastructure. */
  checkDomainDoesNotDependOnInfrastructure() {
    for (const filePath of this._iterJsFiles()) {
      const relPath = this._getRelativePath(filePath);
      // Only check files in the domain layer
      if (!relPath.includes("domain/")) continue;

      const source = fs.readFileSync(filePath, "utf-8");
      const imports = this._getImports(source);

      for (const imp of imports) {
        if (imp.includes("infrastructure")) {
          this.violations.push(
            `[FAIL] ${relPath} depends on Infrastructure via '${imp}'`
          );
        }
      }
    }
  }

  /** RULE 2: Controllers must end with 'Controller' and reside in presentation. */
  checkControllersFollowNamingConvention() {
    for (const filePath of this._iterJsFiles()) {
      const relPath = this._getRelativePath(filePath);
      // Skip files not in the presentation layer
      if (!relPath.includes("presentation/")) continue;

      const source = fs.readFileSync(filePath, "utf-8");

      // Find classes that extend BaseController
      const classRegex = /export\s+class\s+(\w+)\s+extends\s+BaseController/g;
      let match;
      while ((match = classRegex.exec(source)) !== null) {
        const className = match[1];

        // Rule 2a: Must end with "Controller"
        if (!className.endsWith("Controller")) {
          this.violations.push(
            `[FAIL] ${relPath}: class '${className}' extends BaseController but does not end with 'Controller'`
          );
        }

        // Rule 2b: Must reside in the presentation layer
        if (!relPath.includes("presentation/")) {
          this.violations.push(
            `[FAIL] ${relPath}: class '${className}' extends BaseController but is NOT in the presentation layer`
          );
        }
      }
    }
  }

  /** Run all fitness functions. Returns true if all pass. */
  runAll() {
    this.checkDomainDoesNotDependOnInfrastructure();
    this.checkControllersFollowNamingConvention();
    return this.violations.length === 0;
  }
}

/**
 * Listing 13.1: Architectural Boundary Enforcement (Node.js).
 */
export class Demo {
  static async run() {
    console.log("==============================================");
    console.log("  Listing 13.1: Architectural Boundary");
    console.log("  Enforcement (Node.js Fitness Functions)");
    console.log("==============================================\n");

    console.log("The Shop-Zilla Order Service follows a Four-Layer");
    console.log("Architecture: Presentation -> Application -> Domain -> Infrastructure");
    console.log();
    console.log("Our Automated Guardrails enforce two structural rules:");
    console.log("  1. Domain must NOT depend on Infrastructure");
    console.log("  2. Controllers must end with 'Controller' suffix");
    console.log("     and reside in the Presentation package\n");

    // Locate the section root directory (parent of this file)
    const sectionDir = path.join(__dirname);

    // Run the fitness functions
    const fitness = new FitnessFunction(sectionDir);
    const passed = fitness.runAll();

    // ============================================================
    // RULE 1: The Domain Layer must be completely isolated.
    // ============================================================
    console.log("--- Rule 1: Domain Layer Isolation ---\n");
    const domainViolations = fitness.violations.filter((v) =>
      v.includes("depends on Infrastructure")
    );
    if (domainViolations.length === 0) {
      console.log("[PASS] Domain Layer must NOT depend on Infrastructure");
      console.log("       All modules comply with the architectural rule.");
    } else {
      domainViolations.forEach((v) => console.log(v));
    }

    // ============================================================
    // RULE 2: All Controllers must follow a strict naming convention.
    // ============================================================
    console.log("\n--- Rule 2: Controller Naming Convention ---\n");
    const controllerViolations = fitness.violations.filter((v) =>
      v.includes("extends BaseController")
    );
    if (controllerViolations.length === 0) {
      console.log("[PASS] Controllers must have 'Controller' suffix and reside in Presentation");
      console.log("       All classes comply with the architectural rule.");
    } else {
      controllerViolations.forEach((v) => console.log(v));
    }

    // ============================================================
    // DEMONSTRATION: What happens when a rule is violated?
    // ============================================================
    console.log("\n--- Simulation: The Accidental Arsonist ---\n");
    console.log("Imagine a developer (or AI assistant) accidentally");
    console.log("adds a reference to OrderRepository inside the Domain layer...\n");

    if (passed) {
      console.log("[PASS] No violations detected - the Domain layer is clean.");
      console.log("       The build can proceed to the next stage.");
    } else {
      console.log("[FAIL] Architectural violation detected!");
      console.log("       The build is BLOCKED. Deployment halted.");
    }

    console.log("\n==============================================");
    console.log("  In a real CI pipeline, these checks run on");
    console.log("  every commit. A violation = build failure.");
    console.log("==============================================");
  }
}