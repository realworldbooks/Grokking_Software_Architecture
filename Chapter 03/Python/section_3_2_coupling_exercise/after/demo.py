from user_report_generator import UserReportGenerator

class Demo:
    """
    Demonstrates loose coupling and 'chunky' interfaces.
    """
@staticmethod   
def run():
    print("=== Chapter 3: Coupling Test (AFTER) ===")
    print("Notice how clean and 'chunky' the interaction is now!\n")

    generator = UserReportGenerator()
    result = generator.generate_report(1)

    print(f"\nRESULT: {result}")
    print("========================================\n")