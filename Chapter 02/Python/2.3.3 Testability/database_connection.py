class DatabaseConnection:
    def __init__(self, connection_string):
        print(f"\n  [DB] Connecting to... {connection_string}")
        
    def get_data(self, query):
        return ["real_data_row1", "real_data_row2"]