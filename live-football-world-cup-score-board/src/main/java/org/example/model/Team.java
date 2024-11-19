package org.example.model;

/**
 * Define team properties to participate in a game
 */
public class Team {
    /**
     * Name of the participating team
     */
    private String name;
    /**
     * Score of the participating team
     */
    private int score;

    /**
     * Create a team to participate game with default score 0
     * @param name - the name of the team
     */
    public Team(String name) {
        setName(name);
        //set 0 as default value
        setScore(0);
    }

    /**
     * Return name of the team
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of the team
     * @param name - the name of the team
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return score of the team
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Set score of the team, score must be positive value
     * @param score - the score of the team
     */
    public void setScore(int score) {
        //score must be grater then zero
        if(score < 0) throw new IllegalArgumentException("Score can't be negative.");
        this.score = score;
    }
}
