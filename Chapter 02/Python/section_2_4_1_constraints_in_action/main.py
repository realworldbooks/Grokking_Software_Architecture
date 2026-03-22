from export_controller import ExportController

def run_constraints_demo():
    """
    This file acts as a simple simulator for the export controller.
    It demonstrates how the controller responds to different requests.
    """
    print("--- Constraints In Action Example ---")

    controller = ExportController()

    # SCENARIO 1: A valid request for an existing user.
    # We expect a CSV file and an HTTP 200 OK status.
    print("\\n[SCENARIO 1: Simulating GET /export-user-data for a valid user]")
    controller.export_user_data("User123")

    # SCENARIO 2: A request for a user who does not exist.
    # We expect an error message and an HTTP 404 Not Found status.
    print("\\n[SCENARIO 2: Simulating GET /export-user-data for a non-existent user]")
    controller.export_user_data("UnknownUser")

    print("\\n-------------------------------------\\n")

if __name__ == "__main__":
    run_constraints_demo()