"""
The base class for all API controllers in the Presentation layer.

ARCHITECTURAL RULE: Every controller in the system must inherit
from this class AND follow the naming convention of ending with
the suffix "Controller". Our fitness function (Listing 13.1)
enforces both rules automatically in the CI pipeline.

If a developer creates a new controller that forgets the suffix,
or places it outside the Presentation package, the build fails.
"""


class BaseController:
    """Base class for all HTTP API controllers.

    In a real web framework (Flask, FastAPI, Django), this would
    provide shared request/response helpers. Here we keep it simple
    to focus on the architectural boundary enforcement.
    """

    # Shared helper methods for all controllers would go here.
    pass