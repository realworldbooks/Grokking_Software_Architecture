from export_controller import ExportController

if __name__ == "__main__":
    print("=== Chapter 2: Constraints In Action Example ===\n")

    controller = ExportController()

    print("[Simulating GET /export-user-data for User123]")
    controller.export_user_data("User123")

    print("\n[Simulating GET /export-user-data for UnknownUser]")
    controller.export_user_data("UnknownUser")

    print("\n==============================================")