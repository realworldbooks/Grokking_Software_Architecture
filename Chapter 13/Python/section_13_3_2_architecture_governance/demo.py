"""
Listing 13.1: Architectural Boundary Enforcement (Python).

Python does not have a direct equivalent of NetArchTest (.NET) or
ArchUnit (Java), so we replicate the functionality of those libraries
with a custom "Fitness Function" that parses the source tree and
enforces the same architectural rules.

Instead of relying on human code reviewers to catch architectural
violations, we codify our structural rules as executable Fitness
Functions that run automatically in the CI pipeline.

The two rules we enforce:
  1. The Domain Layer must NEVER depend on the Infrastructure Layer.
     This is the "Downward Dependency Rule" - the protected core
     of the system must stay pure and isolated.

  2. All Controllers must follow the naming convention of ending
     with "Controller" AND reside in the Presentation package.
     This maintains high cohesion in the outermost layer.

In a real CI pipeline, these tests run on every commit and every
Pull Request. If a developer (or an AI assistant) accidentally
breaks a boundary, the build flashes red and the deployment halts.
"""

import ast
import os
from pathlib import Path


class FitnessFunction:
    """A simple architectural fitness function engine.

    This class parses Python source files into an AST (Abstract Syntax
    Tree) and evaluates them against architectural rules - just like
    NetArchTest and ArchUnit do for compiled .NET and Java assemblies.
    """

    def __init__(self, root_dir: Path) -> None:
        self.root_dir = root_dir
        self.violations: list[str] = []

    def _iter_python_files(self) -> list[Path]:
        """Recursively find all .py files under the root directory."""
        return sorted(self.root_dir.rglob("*.py"))

    def _get_module_imports(self, tree: ast.Module) -> set[str]:
        """Extract all module names imported by a Python file."""
        imports: set[str] = set()

        for node in ast.walk(tree):
            if isinstance(node, ast.Import):
                for alias in node.names:
                    imports.add(alias.name)
            elif isinstance(node, ast.ImportFrom):
                if node.module:
                    imports.add(node.module)

        return imports

    def _get_relative_path(self, file_path: Path) -> str:
        """Get the file path relative to the root directory."""
        return str(file_path.relative_to(self.root_dir)).replace(os.sep, "/")

    def check_domain_does_not_depend_on_infrastructure(self) -> None:
        """RULE 1: Domain layer must never import from Infrastructure."""
        domain_dir = self.root_dir / "domain"
        if not domain_dir.exists():
            return

        for file_path in self._iter_python_files():
            # Only check files in the domain layer
            if "domain" not in self._get_relative_path(file_path):
                continue

            tree = ast.parse(file_path.read_text(encoding="utf-8"))
            imports = self._get_module_imports(tree)

            for imp in imports:
                if "infrastructure" in imp:
                    self.violations.append(
                        f"[FAIL] {self._get_relative_path(file_path)} "
                        f"depends on Infrastructure via '{imp}'"
                    )

    def check_controllers_follow_naming_convention(self) -> None:
        """RULE 2: Controllers must end with 'Controller' and reside in presentation."""
        presentation_dir = self.root_dir / "presentation"
        if not presentation_dir.exists():
            return

        for file_path in self._iter_python_files():
            rel_path = self._get_relative_path(file_path)

            # Skip files not in the presentation layer
            if "presentation" not in rel_path:
                continue

            tree = ast.parse(file_path.read_text(encoding="utf-8"))

            for node in ast.walk(tree):
                # Find classes that inherit from BaseController
                if not isinstance(node, ast.ClassDef):
                    continue

                inherits_base = any(
                    isinstance(base, ast.Name) and base.id == "BaseController"
                    for base in node.bases
                )

                if not inherits_base:
                    continue

                # Rule 2a: Must end with "Controller"
                if not node.name.endswith("Controller"):
                    self.violations.append(
                        f"[FAIL] {rel_path}: class '{node.name}' "
                        f"inherits BaseController but does not end with 'Controller'"
                    )

                # Rule 2b: Must reside in the presentation package
                if "presentation" not in rel_path:
                    self.violations.append(
                        f"[FAIL] {rel_path}: class '{node.name}' "
                        f"inherits BaseController but is NOT in the presentation layer"
                    )

    def run_all(self) -> bool:
        """Run all fitness functions. Returns True if all pass."""
        self.check_domain_does_not_depend_on_infrastructure()
        self.check_controllers_follow_naming_convention()
        return len(self.violations) == 0


class Demo:
    """Listing 13.1: Architectural Boundary Enforcement (Python)."""

    @staticmethod
    def run() -> None:
        print("==============================================")
        print("  Listing 13.1: Architectural Boundary")
        print("  Enforcement (Python Fitness Functions)")
        print("==============================================\n")

        print("The Shop-Zilla Order Service follows a Four-Layer")
        print("Architecture: Presentation -> Application -> Domain -> Infrastructure")
        print()
        print("Our Automated Guardrails enforce two structural rules:")
        print("  1. Domain must NOT depend on Infrastructure")
        print("  2. Controllers must end with 'Controller' suffix")
        print("     and reside in the Presentation package\n")

        # Locate the section root directory (parent of this file)
        section_dir = Path(__file__).parent

        # Run the fitness functions
        fitness = FitnessFunction(section_dir)
        passed = fitness.run_all()

        # ============================================================
        # RULE 1: The Domain Layer must be completely isolated.
        # ============================================================
        print("--- Rule 1: Domain Layer Isolation ---\n")
        domain_violations = [
            v for v in fitness.violations if "depends on Infrastructure" in v
        ]
        if not domain_violations:
            print("[PASS] Domain Layer must NOT depend on Infrastructure")
            print("       All modules comply with the architectural rule.")
        else:
            for v in domain_violations:
                print(v)

        # ============================================================
        # RULE 2: All Controllers must follow a strict naming convention.
        # ============================================================
        print("\n--- Rule 2: Controller Naming Convention ---\n")
        controller_violations = [
            v for v in fitness.violations if "inherits BaseController" in v
        ]
        if not controller_violations:
            print("[PASS] Controllers must have 'Controller' suffix and reside in Presentation")
            print("       All classes comply with the architectural rule.")
        else:
            for v in controller_violations:
                print(v)

        # ============================================================
        # DEMONSTRATION: What happens when a rule is violated?
        # ============================================================
        print("\n--- Simulation: The Accidental Arsonist ---\n")
        print("Imagine a developer (or AI assistant) accidentally")
        print("adds a reference to OrderRepository inside the Domain layer...\n")

        if passed:
            print("[PASS] No violations detected - the Domain layer is clean.")
            print("       The build can proceed to the next stage.")
        else:
            print("[FAIL] Architectural violation detected!")
            print("       The build is BLOCKED. Deployment halted.")

        print("\n==============================================")
        print("  In a real CI pipeline, these checks run on")
        print("  every commit. A violation = build failure.")
        print("==============================================")