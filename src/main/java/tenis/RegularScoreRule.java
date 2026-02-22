package tenis;

public class RegularScoreRule implements IScoreRule {
    private static final String[] SCORE_NAMES = {"Love", "Fifteen", "Thirty", "Forty"};

    @Override
    public boolean applies(int playerOnePoints, int playerTwoPoints) {
        return playerOnePoints < 4 && playerTwoPoints < 4 && !(playerOnePoints >= 3 && playerTwoPoints >= 3);
    }

    @Override
    public String getScore(int playerOnePoints, int playerTwoPoints) {
        if (playerOnePoints == playerTwoPoints) {
            return playerOnePoints == 0
                ? "Love-Love"
                : SCORE_NAMES[playerOnePoints] + "-All";
        }
        return SCORE_NAMES[playerOnePoints] + "-" + SCORE_NAMES[playerTwoPoints];
    }
    
}
