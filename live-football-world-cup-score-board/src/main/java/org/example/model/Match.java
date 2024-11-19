package org.example.model;

/**
 * Define Match properties
 */
public class Match {
    /**
     * id for a match
     */
    private Integer id;
    /**
     * Participating home team information
     */
    private Team homeTeam;
    /**
     * Participating away team information
     */
    private Team awayTeam;

    /**
     * Create a new match for the game
     * @param id - id of the match
     * @param homeTeam - Participating home team information
     * @param awayTeam - Participating away team information
     */
    public Match(Integer id, Team homeTeam, Team awayTeam) {
        setId(id);
        sethomeTeam(homeTeam);
        setawayTeam(awayTeam);
    }

    /**
     * Return id of the match
     * @return id - id of the match
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set match id
     * @param id -id of the match
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Return homeTeam information of match
     * @return homeTeam - Participating home team information
     */
    public Team gethomeTeam() {
        return homeTeam;
    }

    /**
     * Set homeTeam information of match
     * @param homeTeam - - Participating home team information
     */
    public void sethomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    /**
     * Return awayTeam information of match
     * @return awayTeam - Participating away team information
     */
    public Team getawayTeam() {
        return awayTeam;
    }

    /**
     * Set awayTeam information of match
     * @param awayTeam - Participating away team information
     */
    public void setawayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }


}
