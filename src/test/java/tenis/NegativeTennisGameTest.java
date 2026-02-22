package tenis;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class NegativeTennisGameTest {

	static Stream<NegativeCase> negativeCases() {
		return Stream.of(
			new NegativeCase(1, 0, "Love-Love", "After Player 1 scores, score must change"),
			new NegativeCase(0, 1, "Love-Love", "After Player 2 scores, score must change"),
			new NegativeCase(3, 3, "Advantage Player 1", "Tied at three should be deuce, not advantage"),
			new NegativeCase(4, 3, "Player 1 wins", "Leading by one at four should be advantage, not win"),
			new NegativeCase(4, 2, "Forty-Thirty", "Win by two must not report running score"),
			new NegativeCase(5, 5, "Player 1 wins", "Equal five should still be deuce")
		);
	}

	@ParameterizedTest
	@MethodSource("negativeCases")
	void should_not_return_unexpected_scores(NegativeCase negativeCase) {
		TennisGame game = new TennisGame();
		givePoints(game, negativeCase.playerOnePoints, negativeCase.playerTwoPoints);
		String score = game.getScore();

		assertNotEquals(negativeCase.unexpectedScore, score, negativeCase.description);
	}

	private void givePoints(TennisGame game, int playerOne, int playerTwo) {
		for (int i = 0; i < playerOne; i++) {
			game.pointToPlayerOne();
		}
		for (int i = 0; i < playerTwo; i++) {
			game.pointToPlayerTwo();
		}
	}

	static class NegativeCase {
		final int playerOnePoints;
		final int playerTwoPoints;
		final String unexpectedScore;
		final String description;

		NegativeCase(int playerOnePoints, int playerTwoPoints, String unexpectedScore, String description) {
			this.playerOnePoints = playerOnePoints;
			this.playerTwoPoints = playerTwoPoints;
			this.unexpectedScore = unexpectedScore;
			this.description = description;
		}

		@Override
		public String toString() {
			return String.format("%d-%d not %s", playerOnePoints, playerTwoPoints, unexpectedScore);
		}
	}
}
