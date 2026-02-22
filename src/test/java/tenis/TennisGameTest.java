package tenis;

import org.junit.jupiter.api.Test;

import tenis.TennisGame;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TennisGameTest {

    @Test
    void shouldReturnLoveLove_OnStart() {
        TennisGame game = new TennisGame();
        assertEquals("Love-Love", game.getScore());
    }

    @Test
    void testPlayerOneScoresOnce_ShouldBeFifteenLove() {
        TennisGame game = new TennisGame();
        game.pointToPlayerOne();
        assertEquals("Fifteen-Love", game.getScore());
    }

    @Test
    void testPlayerTwoScoresOnce_ShouldBeLoveFifteen() {
        TennisGame game = new TennisGame();
        game.pointToPlayerTwo();
        assertEquals("Love-Fifteen", game.getScore());
    }

    @Test
    void testBothPlayersScoreOnce_ShouldBeFifteenAll() {
        TennisGame game = new TennisGame();
        game.pointToPlayerOne();
        game.pointToPlayerTwo();
        assertEquals("Fifteen-All", game.getScore());
    }

    @Test
    void testPlayerOneScoresTwice_ShouldBeThirtyLove() {
        TennisGame game = new TennisGame();
        givePoints(game, 2, 0);
        assertEquals("Thirty-Love", game.getScore());
    }

    @Test
    void testPlayerTwoScoresTwice_ShouldBeLoveThirty() {
        TennisGame game = new TennisGame();
        givePoints(game, 0, 2);
        assertEquals("Love-Thirty", game.getScore());
    }

    @Test
    void testScoreIsFortyAll_ShouldBeDeuce() {
        TennisGame game = new TennisGame();
        givePoints(game, 3, 3);
        assertEquals("Deuce", game.getScore());
    }

    @Test
    void testPlayerOneAdvantageAfterDeuce_ShouldBeAdvantagePlayerOne() {
        TennisGame game = new TennisGame();
        givePoints(game, 3, 3);
        game.pointToPlayerOne();
        assertEquals("Advantage Player 1", game.getScore());
    }

    @Test
    void testPlayerTwoAdvantageAfterDeuce_ShouldBeAdvantagePlayerTwo() {
        TennisGame game = new TennisGame();
        givePoints(game, 3, 3);
        game.pointToPlayerTwo();
        assertEquals("Advantage Player 2", game.getScore());
    }

    @Test
    void testPlayerOneWinsAfterAdvantage_ShouldBeWinPlayerOne() {
        TennisGame game = new TennisGame();
        givePoints(game, 3, 3);
        game.pointToPlayerOne();
        game.pointToPlayerOne();
        assertEquals("Player 1 wins", game.getScore());
    }

    @Test
    void testPlayerTwoWinsAfterAdvantage_ShouldBeWinPlayerTwo() {
        TennisGame game = new TennisGame();
        givePoints(game, 3, 3);
        game.pointToPlayerTwo();
        game.pointToPlayerTwo();
        assertEquals("Player 2 wins", game.getScore());
    }

    @Test
    void testAdvantageLost_BackToDeuce() {
        TennisGame game = new TennisGame();
        givePoints(game, 4, 3); // Player 1 has advantage
        game.pointToPlayerTwo();
        assertEquals("Deuce", game.getScore());
    }

    @Test
    void testPlayerOneWinsByTwoPoints_ShouldBeWinPlayerOne() {
        TennisGame game = new TennisGame();
        givePoints(game, 4, 0);
        assertEquals("Player 1 wins", game.getScore());
    }

    @Test
    void testPlayerTwoWinsByTwoPoints_ShouldBeWinPlayerTwo() {
        TennisGame game = new TennisGame();
        givePoints(game, 0, 4);
        assertEquals("Player 2 wins", game.getScore());
    }

    private void givePoints(TennisGame game, int playerOne, int playerTwo) {
        for (int i = 0; i < playerOne; i++) {
            game.pointToPlayerOne();
        }
        for (int i = 0; i < playerTwo; i++) {
            game.pointToPlayerTwo();
        }
    }
}
