package tenis;

public class TennisGame {
    private int playerOnePoints = 0;
    private int playerTwoPoints = 0;
    private final IScoreRule[] rules;

    public TennisGame() {
        // El orden importa: de la regla más específica a la más general
        this.rules = new IScoreRule[]{
            new WinRule(),
            new AdvantageRule(),
            new DeuceRule(),
            new RegularScoreRule()
        };
    }

    public void pointToPlayerOne() { playerOnePoints++; }
    public void pointToPlayerTwo() { playerTwoPoints++; }

    public String getScore() {
        for (IScoreRule rule : rules) {
            if (rule.applies(playerOnePoints, playerTwoPoints)) {
                return rule.getScore(playerOnePoints, playerTwoPoints);
            }
        }
        throw new IllegalArgumentException("No applicable score rule found");
    }
}