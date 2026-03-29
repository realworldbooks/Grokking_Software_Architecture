from .export_controller import ExportController

class Demo:
    """
    Simulator for the ExportController to see constraints in action.
    """

    @staticmethod
    async def run():
        print("--- Constraints In Action Example ---")

        # ARCHITECTURAL NOTE: Interacting only with the Controller 
        # to respect layer boundaries.
        controller = ExportController()

        # SCENARIO 1: Valid user request
        print("\n[SCENARIO 1: Simulating GET /export-user-data for a valid user]")
        await controller.export_user_data_async("User123")

        # SCENARIO 2: Non-existent user
        print("\n[SCENARIO 2: Simulating GET /export-user-data for a non-existent user]")
        await controller.export_user_data_async("UnknownUser")

        print("\n-------------------------------------\n")