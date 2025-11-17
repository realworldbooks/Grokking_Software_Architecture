namespace GoodWay
{
    // The abstraction (interface)
    public interface IOrderRepository
    {
        void Save(Order order);
    }
}