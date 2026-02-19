package tenis.Client;

public class TennisGame {
	private int playerOnePoints = 0;
	private int playerTwoPoints = 0;

	public void pointToPlayerOne() {
		playerOnePoints++;
	}

	public void pointToPlayerTwo() {
		playerTwoPoints++;
	}

	public String getScore() {
		if (isWin()) {
			return playerOnePoints > playerTwoPoints ? "Player 1 wins" : "Player 2 wins";
		}

		if (isAdvantage()) {
			return playerOnePoints > playerTwoPoints ? "Advantage Player 1" : "Advantage Player 2";
		}

		if (isDeuce()) {
			return "Deuce";
		}

		if (playerOnePoints == playerTwoPoints && playerOnePoints != 0) {
			return scoreName(playerOnePoints) + "-All";
		}

		return scoreName(playerOnePoints) + "-" + scoreName(playerTwoPoints);
	}

	private boolean isAdvantage() {
		return playerOnePoints >= 3 && playerTwoPoints >= 3 && Math.abs(playerOnePoints - playerTwoPoints) == 1;
	}

	private boolean isWin() {
		return (playerOnePoints >= 4 || playerTwoPoints >= 4) && Math.abs(playerOnePoints - playerTwoPoints) >= 2;
	}

	private boolean isDeuce() {
		return playerOnePoints >= 3 && playerTwoPoints == playerOnePoints;
	}

	private String scoreName(int score) {
		switch (score) {
			case 0:
				return "Love";
			case 1:
				return "Fifteen";
			case 2:
				return "Thirty";
			case 3:
				return "Forty";
			default:
				return "";
		}
	}
}
