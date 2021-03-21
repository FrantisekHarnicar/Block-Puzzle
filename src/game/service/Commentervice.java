package game.service;

import game.entity.Score;

import java.util.List;

public interface Commentervice {
    void addScore(Score score) throws ScoreException;
    List<Score> getTopScores(String game) throws ScoreException;
    void reset() throws ScoreException;
}
