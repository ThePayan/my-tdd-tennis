package tenis;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParameterizedTennisGameTest {

    static Stream<TestCase> testCasesProvider() {
        return Stream.of(
            // Puntuaciones iniciales y simples
            new TestCase(0, 0, "Love-Love"),
            new TestCase(1, 0, "Fifteen-Love"),
            new TestCase(0, 1, "Love-Fifteen"),
            new TestCase(1, 1, "Fifteen-All"),
            new TestCase(2, 0, "Thirty-Love"),
            new TestCase(0, 2, "Love-Thirty"),
            
            // Estados de Deuce
            new TestCase(3, 3, "Deuce"),
            new TestCase(4, 4, "Deuce"),
            
            // Ventajas
            new TestCase(4, 3, "Advantage Player 1"),
            new TestCase(3, 4, "Advantage Player 2"),
            new TestCase(5, 4, "Advantage Player 1"),
            
            // Victorias
            new TestCase(4, 0, "Player 1 wins"),
            new TestCase(0, 4, "Player 2 wins"),
            new TestCase(4, 2, "Player 1 wins"),
            new TestCase(2, 4, "Player 2 wins"),
            new TestCase(5, 3, "Player 1 wins"),
            new TestCase(3, 5, "Player 2 wins")
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    void should_return_expected_score_for_given_points(TestCase testCase) {
        // Arrange
        TennisGame game = new TennisGame();
        // Act
        givePoints(game, testCase.playerOnePoints, testCase.playerTwoPoints);
        String result = game.getScore();
        // Assert
        assertEquals(testCase.expectedScore, result);
    }

    private void givePoints(TennisGame game, int playerOne, int playerTwo) {
        for (int i = 0; i < playerOne; i++) {
            game.pointToPlayerOne();
        }
        for (int i = 0; i < playerTwo; i++) {
            game.pointToPlayerTwo();
        }
    }

    static class TestCase {
        int playerOnePoints;
        int playerTwoPoints;
        String expectedScore;

        TestCase(int playerOnePoints, int playerTwoPoints, String expectedScore) {
            this.playerOnePoints = playerOnePoints;
            this.playerTwoPoints = playerTwoPoints;
            this.expectedScore = expectedScore;
        }

        @Override
        public String toString() {
            return String.format("%d-%d should return \"%s\"", 
                playerOnePoints, playerTwoPoints, expectedScore);
        }
    }
}