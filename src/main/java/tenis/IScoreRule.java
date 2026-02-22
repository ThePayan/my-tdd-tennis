package tenis;

public interface IScoreRule {
    boolean applies(int playerOnePoints, int playerTwoPoints);
    String getScore(int playerOnePoints, int playerTwoPoints);
}