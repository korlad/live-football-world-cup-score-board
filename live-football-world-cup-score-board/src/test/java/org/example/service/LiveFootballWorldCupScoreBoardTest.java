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
    
}