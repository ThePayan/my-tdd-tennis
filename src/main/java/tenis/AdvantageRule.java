package tenis;

public class AdvantageRule implements IScoreRule {
    @Override
    public boolean applies(int playerOnePoints, int playerTwoPoints) {
        return playerOnePoints >= 3 && playerTwoPoints >= 3 && Math.abs(playerOnePoints - playerTwoPoints) == 1;
    }

    @Override
    public String getScore(int playerOnePoints, int playerTwoPoints) {
        return playerOnePoints > playerTwoPoints ? "Advantage Player 1" : "Advantage Player 2";
    }
    
}
