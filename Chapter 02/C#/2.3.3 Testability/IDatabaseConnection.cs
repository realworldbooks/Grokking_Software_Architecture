using System.Collections.Generic;

namespace Chapter02.Testability;

public interface IDatabaseConnection
{
    List<string> GetData(string query);
}