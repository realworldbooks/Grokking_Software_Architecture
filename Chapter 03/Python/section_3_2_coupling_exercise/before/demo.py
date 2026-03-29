from .user_report_generator import UserReportGenerator

class Demo:
    """
    Demonstrates high coupling and 'chatty' interfaces.
    """

    @staticmethod
    def run():
        print("=== Chapter 3: Coupling Test (BEFORE) ===")
        print("Notice how many 'chatty' calls the client has to make!\n")

        # In this 'Before' state, the generator might be doing 
        # too much or requiring too much orchestration from the caller.
        generator = UserReportGenerator()
        result = generator.generate_report(1)

        print(f"\nRESULT: {result}")
        print("=========================================\n")