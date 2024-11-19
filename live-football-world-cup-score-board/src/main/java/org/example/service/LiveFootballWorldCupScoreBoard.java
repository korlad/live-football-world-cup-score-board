package org.example.service;

import org.example.model.Match;
import org.example.model.Team;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Football game library able to create, update match
 * and show score in live board
 */
public class LiveFootballWorldCupScoreBoard {

    /**
     * Represent live score board for football games
     */
    private final List<Integer> scoreBoard = Collections.synchronizedList(new LinkedList<>());

    /**
     * Store match information
     */
    private final Map<Integer, Match> matches = Collections.synchronizedMap(new HashMap<>());

    /**
     * Add a new match to the system, generate id for the match and synchronize score board
     * @param homeTeamName - The name of the participating home team
     * @param awayTeamName - The name of the participating away team
     * @return matchId - The system generated id for the newly added match
     */
    public Integer newMatch(String homeTeamName, String awayTeamName) {
        if(homeTeamName == null || awayTeamName == null || homeTeamName.isBlank() || awayTeamName.isBlank()) {
            throw new IllegalArgumentException("homeTeamName and awayTeamName can not be empty");
        }
        if(homeTeamName.equals(awayTeamName)) {
            throw new IllegalArgumentException("Home and away team name should be different");
        }
        if(isTeamInOngoingMatch(homeTeamName)) {
            throw new IllegalArgumentException(homeTeamName+" team game is still ongoing");
        }
        if(isTeamInOngoingMatch(awayTeamName)) {
            throw new IllegalArgumentException(awayTeamName+" team game is still ongoing");
        }
        //generate match id for the new match
        Integer matchId = matches.size() + 1 ;
        matches.put(matchId, new Match(matchId, new Team(homeTeamName), new Team(awayTeamName)));
        //synchronize live broad for newly added match
        syncScoreBoard(matchId);
        return matchId;
    }

    public void updatePoint(Integer matchId, Integer homeTeamScore, Integer awayTeamScore) {
        if(! matches.containsKey(matchId)) {
            throw new IllegalArgumentException("Match id "+matchId+" not found");
        }
        //update score for the participating team
        matches.get(matchId).getHomeTeam().setScore(homeTeamScore);
        matches.get(matchId).getAwayTeam().setScore(awayTeamScore);
        //synchronize live broad for score update
        syncScoreBoard(matchId);
    }

    /**
     * Remove game from live board
     * @param matchId - The id of the match
     */
    public void finishMatch(Integer matchId) {
        if(! matches.containsKey(matchId)) {
            throw new IllegalArgumentException("Match id "+matchId+" not found");
        }
        scoreBoard.remove(matchId);
    }

    /**
     * Get live board summary with valid order
     */
    public List<Match> summary() {
        return scoreBoard.stream()
                .map(matches::get)
                .collect(Collectors.toList());
    }

    /**
     * Check given team name are in ongoing match or not
     * @param teamName - name of the team
     * @return found - true when team name found in ongoing match
     */
    private boolean isTeamInOngoingMatch(String teamName) {
        for (Integer id : scoreBoard) {
            if(teamName.equals(matches.get(id).getHomeTeam().getName()) ||
                    teamName.equals(matches.get(id).getAwayTeam().getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Synchronize live board for any game update
     * @param matchId - the id of the match
     */
    private void syncScoreBoard(Integer matchId) {
        //find correct position the match
        int position = getPositionOnScoreBoard(matchId);
        //update match position on the live board
        scoreBoard.add(position, matchId);
    }

    /**
     * Find the perfect position in live board for a match
     * @param matchId - the id of the match
     * @return position - the appropriate position for the match on live board
     */
    private Integer getPositionOnScoreBoard(Integer matchId) {
        //remove if it already in live, because we need to reposition it
        if(scoreBoard.contains(matchId))
            scoreBoard.remove(matchId);
        int position = 0;
        for(Integer id : scoreBoard) {
            //Check total score for the match
            if(matches.get(matchId).getHomeTeam().getScore() + matches.get(matchId).getAwayTeam().getScore() >
                    matches.get(id).getHomeTeam().getScore() + matches.get(id).getAwayTeam().getScore() ) {
                return position;
            }
            //Check recently started match when total score are same
            else if(matches.get(matchId).getHomeTeam().getScore() + matches.get(matchId).getAwayTeam().getScore() ==
                    matches.get(id).getHomeTeam().getScore() + matches.get(id).getAwayTeam().getScore() ) {
                return matchId >id ? position : position +1;
            }
            position++;
        }
        return position;    
    }

}
