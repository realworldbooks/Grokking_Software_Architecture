from user_report_generator import UserReportGenerator

if __name__ == "__main__":
    print("=== Chapter 3: Coupling Test (BEFORE) ===")
    print("Notice how many 'chatty' calls the client has to make!\n")

    generator = UserReportGenerator()
    result = generator.generate_report(1)

    print(f"\nRESULT: {result}")
    print("=========================================\n")