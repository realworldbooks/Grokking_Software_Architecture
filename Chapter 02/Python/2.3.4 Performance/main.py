from dashboard_after import DashboardAfter
# (Import DashboardBefore as well)

if __name__ == "__main__":
    print("=== Chapter 2: Performance Example ===\n")
    
    print("--- Running After: Smart Cache Architecture ---")
    dashboard_after = DashboardAfter()
    
    print("\n[Call 1: User logs in for the first time]")
    dashboard_after.get_dashboard_summary("User999")
    
    print("\n[Call 2: User refreshes the page a minute later]")
    dashboard_after.get_dashboard_summary("User999")
    
    print("\n======================================")