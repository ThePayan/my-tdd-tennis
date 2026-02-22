package tenis;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    static Stream<RuleCase> regularRuleCases() {
        return Stream.of(
            new RuleCase(new RegularScoreRule(), 0, 0, true),   // ambos <3 y empate
            new RuleCase(new RegularScoreRule(), 3, 0, true),   // p1 >=3 pero p2 <3 -> sigue siendo regular
            new RuleCase(new RegularScoreRule(), 0, 4, false),  // p2 >=4 rompe la regla
            new RuleCase(new RegularScoreRule(), 4, 0, false),  // p1 >=4 rompe la regla
            new RuleCase(new RegularScoreRule(), 3, 3, false)   // ambos >=3 -> se va a Deuce
        );
    }

    static Stream<RuleCase> deuceRuleCases() {
        return Stream.of(
            new RuleCase(new DeuceRule(), 3, 3, true),   // clásico deuce
            new RuleCase(new DeuceRule(), 4, 3, false),  // ventaja, no deuce
            new RuleCase(new DeuceRule(), 2, 2, false)   // menor que 3, no deuce
        );
    }

    static Stream<RuleCase> advantageRuleCases() {
        return Stream.of(
            new RuleCase(new AdvantageRule(), 4, 3, true),   // ventaja p1
            new RuleCase(new AdvantageRule(), 3, 4, true),   // ventaja p2
            new RuleCase(new AdvantageRule(), 3, 3, false),  // deuce, no ventaja
            new RuleCase(new AdvantageRule(), 5, 3, false),  // win, no ventaja
            new RuleCase(new AdvantageRule(), 2, 3, false),   // p1 <3 no puede ser ventaja
            new RuleCase(new AdvantageRule(), 3, 2, false)
        );
    }

    static Stream<RuleCase> ruleCaseForThrow() {
        return Stream.of(new RuleCase(new RegularScoreRule(), 0, 0, false));
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

    @ParameterizedTest
    @MethodSource("regularRuleCases")
    void regular_rule_applies_only_in_expected_states(RuleCase ruleCase) {
        assertEquals(ruleCase.expectedApplies,
            ruleCase.rule.applies(ruleCase.playerOnePoints, ruleCase.playerTwoPoints),
            ruleCase.toString());
    }

    
    @ParameterizedTest
    @MethodSource("deuceRuleCases")
    void deuce_rule_applies_only_in_expected_states(RuleCase ruleCase) {
        if (ruleCase.expectedApplies) {
            assertTrue(ruleCase.rule.applies(ruleCase.playerOnePoints, ruleCase.playerTwoPoints), ruleCase.toString());
        } else {
            assertFalse(ruleCase.rule.applies(ruleCase.playerOnePoints, ruleCase.playerTwoPoints), ruleCase.toString());
        }
    }

    

    @ParameterizedTest
    @MethodSource("advantageRuleCases")
    void advantage_rule_applies_only_in_expected_states(RuleCase ruleCase) {
        if (ruleCase.expectedApplies) {
            assertTrue(ruleCase.rule.applies(ruleCase.playerOnePoints, ruleCase.playerTwoPoints), ruleCase.toString());
        } else {
            assertFalse(ruleCase.rule.applies(ruleCase.playerOnePoints, ruleCase.playerTwoPoints), ruleCase.toString());
        }
    }

    @ParameterizedTest
    @MethodSource("ruleCaseForThrow")
    void get_score_throws_when_no_rule_applies(RuleCase ruleCase) throws Exception {
        TennisGame game = new TennisGame();
        Field rulesField = TennisGame.class.getDeclaredField("rules");
        rulesField.setAccessible(true);
        rulesField.set(game, new IScoreRule[]{}); // deja el array vacío para forzar la excepción

        assertThrows(IllegalArgumentException.class, game::getScore, ruleCase.toString());
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

    static class RuleCase {
        final IScoreRule rule;
        final int playerOnePoints;
        final int playerTwoPoints;
        final boolean expectedApplies;

        RuleCase(IScoreRule rule, int playerOnePoints, int playerTwoPoints, boolean expectedApplies) {
            this.rule = rule;
            this.playerOnePoints = playerOnePoints;
            this.playerTwoPoints = playerTwoPoints;
            this.expectedApplies = expectedApplies;
        }

        @Override
        public String toString() {
            return String.format("%s applies(%d,%d) -> %s", 
                rule.getClass().getSimpleName(), playerOnePoints, playerTwoPoints, expectedApplies);
        }
    }
}