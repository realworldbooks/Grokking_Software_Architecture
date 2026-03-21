namespace Chapter03.LSP.Before;

/// <summary>
/// The Base Contract.
/// 
/// ARCHITECTURE NOTE: By placing this method in the base abstract class, we are 
/// creating a strict contract: "Every single class that inherits from Player 
/// MUST be able to execute PlayFieldPosition() successfully."
/// </summary>
public abstract class Player
{
    public abstract void PlayFieldPosition();
}