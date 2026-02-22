package tenis;

public class DeuceRule implements IScoreRule {
    @Override
    public boolean applies(int playerOnePoints, int playerTwoPoints) {
        return playerOnePoints >= 3 && playerTwoPoints == playerOnePoints;
    }

    @Override
    public String getScore(int playerOnePoints, int playerTwoPoints) {
        return "Deuce";
    }
    
}
