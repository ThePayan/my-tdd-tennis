package tenis;

public class WinRule implements IScoreRule {
    @Override
    public boolean applies(int playerOnePoints, int playerTwoPoints) {
        return (playerOnePoints >= 4 || playerTwoPoints >= 4) && Math.abs(playerOnePoints - playerTwoPoints) >= 2;
    }

    @Override
    public String getScore(int playerOnePoints, int playerTwoPoints) {
        return playerOnePoints > playerTwoPoints ? "Player 1 wins" : "Player 2 wins";
    }
    
}
