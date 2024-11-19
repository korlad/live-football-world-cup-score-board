package org.example.service;


import jdk.jfr.Description;
import org.example.model.Match;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LiveFootballWorldCupScoreBoardTest {
    private LiveFootballWorldCupScoreBoard footballGame = null;

    @BeforeEach
    public void init() {
        footballGame = new LiveFootballWorldCupScoreBoard();
    }

    @Test
    @Description("New match should start with different team name")
    public void newMatchShouldStart() {
        Integer id = footballGame.newMatch("Mexico", "Canada");
        Assertions.assertNotNull(id);
    }

    @Test
    @Description("New match should start with initial score 0-0")
    public void newMatchShouldStartWithInitialScore0() {
        footballGame.newMatch("Mexico", "Canada");
        Match match = footballGame.summary().get(0);
        Assertions.assertEquals(0, match.getHomeTeam().getScore());
        Assertions.assertEquals(0, match.getAwayTeam().getScore());
    }

    @Test
    @Description("New match should not start with team name which is currently playing a game")
    public void newMatchShouldNotStartWithOngoingTeamName() {
        footballGame.newMatch("Mexico", "Canada");
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            footballGame.newMatch("Mexico", "Spain");
        });
        Assertions.assertEquals("A team game is still ongoing", exception.getMessage());
    }

    @Test
    @Description("New match should not start where home and away team name are same")
    public void newMatchShouldNotStartWithSameTeamName() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            footballGame.newMatch("Mexico", "Mexico");
        });
        Assertions.assertEquals("Home and away team name should be different", exception.getMessage());
    }

    @Test
    @Description("New match should not start where home and away team name are empty")
    public void newMatchShouldNotStartWithEmptyTeamName() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            footballGame.newMatch(null, "");
        });
        Assertions.assertEquals("homeTeamName and awayTeamName can not be empty", exception.getMessage());
    }

    @Test
    @Description("Update Point should game score properly")
    public void updatePointShouldUpdateGameScore() {
        Integer id = footballGame.newMatch("Mexico", "Canada");
        footballGame.updatePoint(id, 1,2);
        Match match = footballGame.summary().get(0);
        Assertions.assertEquals(1, match.getHomeTeam().getScore());
        Assertions.assertEquals(2, match.getAwayTeam().getScore());
    }

    @Test
    @Description("Update Point should throw exception for unknown match id")
    public void updatePointShouldNotUpdateGameScoreForInvalidMatchId() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            footballGame.updatePoint(1, 1,2);
        });
        Assertions.assertEquals("Match id 1 not found", exception.getMessage());
    }

    @Test
    @Description("Update Point should change live board position based on total score")
    public void updatePointShouldUpdateGameScoreAndMoveHigherBasedOnTotalScore() {
        Integer id = footballGame.newMatch("Mexico", "Canada");
        Integer id2 = footballGame.newMatch("Spain", "Brazil");
        footballGame.updatePoint(id, 1,2);
        Match match = footballGame.summary().get(0);
        Assertions.assertEquals("Mexico", match.getHomeTeam().getName());
        Assertions.assertEquals("Canada", match.getAwayTeam().getName());
        Assertions.assertEquals(1, match.getHomeTeam().getScore());
        Assertions.assertEquals(2, match.getAwayTeam().getScore());
    }

    @Test
    @Description("Update Point should change live board position based on most recent start when total score are same")
    public void updatePointShouldUpdateGameScoreAndMoveHigherBasedOnRecentStartWhenTotalScoreSame() {
        Integer id = footballGame.newMatch("Mexico", "Canada");
        Integer id2 = footballGame.newMatch("Spain", "Brazil");
        footballGame.updatePoint(id, 1,2);
        footballGame.updatePoint(id2, 1,2);
        Match match = footballGame.summary().get(0);
        Assertions.assertEquals("Spain", match.getHomeTeam().getName());
        Assertions.assertEquals("Brazil", match.getAwayTeam().getName());
    }

}
