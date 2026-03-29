namespace Chapter04.Section_4_2.Before
{
    public class Demo
    {
        public static void Run()
        {
            Console.WriteLine("--- Running 'Before Refactoring' (Upward Dependency) ---");
            
            // This represents the tightly coupled version where a low-level 
            // component might be trying to control a high-level one.
            var beforeRepo = new SomeRepository();
            beforeRepo.UpdateData(123, "New Data");
            
            Console.WriteLine("---------------------------------------------");
        }
    }
}