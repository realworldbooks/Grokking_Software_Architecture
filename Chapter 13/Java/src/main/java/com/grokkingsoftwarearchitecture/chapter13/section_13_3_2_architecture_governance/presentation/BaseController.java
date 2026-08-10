package com.grokkingsoftwarearchitecture.chapter13.section_13_3_2_architecture_governance.presentation;

/**
 * The base class for all API controllers in the Presentation layer.
 *
 * ARCHITECTURAL RULE: Every controller in the system must extend
 * this class AND follow the naming convention of ending with
 * the suffix "Controller". Our fitness function (Listing 13.1)
 * enforces both rules automatically in the CI pipeline.
 *
 * If a developer creates a new controller that forgets the suffix,
 * or places it outside the Presentation package, the build fails.
 */
public abstract class BaseController {
    // Shared helper methods for all controllers would go here.
    // In a real Spring Boot application, this would extend
    // a framework base class and provide common response helpers.
}