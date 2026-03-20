from user_report_generator import UserReportGenerator

if __name__ == "__main__":
    print("=== Chapter 3: Coupling Test (AFTER) ===")
    print("Notice how clean and 'chunky' the interaction is now!\n")

    generator = UserReportGenerator()
    result = generator.generate_report(1)

    print(f"\nRESULT: {result}")
    print("========================================\n")