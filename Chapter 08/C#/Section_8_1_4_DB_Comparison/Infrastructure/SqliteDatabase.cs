// Infrastructure/SqliteDatabase.cs
using System;
using System.Collections.Generic;
using Microsoft.Data.Sqlite;

namespace Chapter08.DatabaseCode.Infrastructure
{
    /// <summary>
    /// ARCHITECTURAL NOTE: THE QUARANTINE ZONE
    /// This class belongs in the 'Infrastructure' folder because it represents 
    /// the "Dirty" Outside World. 
    /// 
    /// This is a raw ENGINE. It understands technical implementation details 
    /// like SQL syntax and memory allocation. Crucially, it knows absolutely 
    /// NOTHING about our business rules. 
    /// 
    /// THE DATABASE (INFRASTRUCTURE LAYER): Strict, organized. Like a filing cabinet.
    /// </summary>
    public class SqliteDatabase : IDisposable
    {
        private readonly SqliteConnection _connection;

        public SqliteDatabase()
        {
            // We use an in-memory SQLite database so it runs instantly without file setup
            _connection = new SqliteConnection("Data Source=:memory:");
            _connection.Open();

            using var command = _connection.CreateCommand();
            command.CommandText = "CREATE TABLE Recipes (id INTEGER, name TEXT, type TEXT)";
            command.ExecuteNonQuery();
        }

        public void Insert(int id, string name, string type)
        {
            using var command = _connection.CreateCommand();
            command.CommandText = "INSERT INTO Recipes (id, name, type) VALUES ($id, $name, $type)";
            command.Parameters.AddWithValue("$id", id);
            command.Parameters.AddWithValue("$name", name);
            command.Parameters.AddWithValue("$type", type);
            command.ExecuteNonQuery();
        }

        // The naive literal search
        public List<string> QueryByName(string name)
        {
            var results = new List<string>();
            using var command = _connection.CreateCommand();
            command.CommandText = "SELECT name FROM Recipes WHERE name = $name";
            command.Parameters.AddWithValue("$name", name);

            using var reader = command.ExecuteReader();
            while (reader.Read())
            {
                results.Add(reader.GetString(0));
            }
            return results;
        }

        public List<string> QueryByType(string type)
        {
            // Exact keyword match required. If you search for "Italian", you find NOTHING.
            var results = new List<string>();
            using var command = _connection.CreateCommand();
            command.CommandText = "SELECT name FROM Recipes WHERE type = $type";
            command.Parameters.AddWithValue("$type", type);

            using var reader = command.ExecuteReader();
            while (reader.Read())
            {
                results.Add(reader.GetString(0));
            }
            return results;
        }

        public void ExecuteRaw(string query)
        {
            using var command = _connection.CreateCommand();
            command.CommandText = query;
            command.ExecuteNonQuery();
        }

        public void Dispose()
        {
            _connection.Close();
        }
    }
}