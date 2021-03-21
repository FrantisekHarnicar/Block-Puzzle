package Test;

import game.service.Commentervice;
import game.service.ScoreServiceJDBC;
import org.junit.Test;
import game.entity.Score;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScoreServiceTest {

    private Commentervice createService() {
        return new ScoreServiceJDBC();
    }

    @Test
    public void testReset() {
        Commentervice service = createService();
        service.reset();
        assertEquals(0, service.getTopScores("mines").size());
    }

    @Test
    public void testAddScore() {
        Commentervice service = createService();
        service.reset();
        Date date = new Date();
        service.addScore(new Score("mines", "Jaro", 200, date));

        List<Score> scores = service.getTopScores("mines");

        assertEquals(1, scores.size());

        assertEquals("mines", scores.get(0).getGame());
        assertEquals("Jaro", scores.get(0).getPlayer());
        assertEquals(200, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedOn());
    }

    @Test
    public void testAddScore3() {
        Commentervice service = createService();
        service.reset();
        Date date = new Date();
        service.addScore(new Score("BlockPuzzle", "Jaro", 200, date));
        service.addScore(new Score("BlockPuzzle", "Fero", 400, date));
        service.addScore(new Score("BlockPuzzle", "Jozo", 100, date));
        List<Score> scores = service.getTopScores("BlockPuzzle");

        assertEquals(3, scores.size());

        assertEquals("BlockPuzzle", scores.get(0).getGame());
        assertEquals("Fero", scores.get(0).getPlayer());
        assertEquals(400, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedOn());

        assertEquals("BlockPuzzle", scores.get(1).getGame());
        assertEquals("Jaro", scores.get(1).getPlayer());
        assertEquals(200, scores.get(1).getPoints());
        assertEquals(date, scores.get(1).getPlayedOn());

        assertEquals("BlockPuzzle", scores.get(2).getGame());
        assertEquals("Jozo", scores.get(2).getPlayer());
        assertEquals(100, scores.get(2).getPoints());
        assertEquals(date, scores.get(2).getPlayedOn());
    }

    @Test
    public void testAddScore10() {
        Commentervice service = createService();
        for (int i = 0; i < 20; i++)
            service.addScore(new Score("mines", "Jaro", 200, new Date()));
        assertEquals(10, service.getTopScores("mines").size());
    }

    @Test
    public void testPersistance() {
        Commentervice service = createService();
        service.reset();
        service.addScore(new Score("mines", "Jaro", 200, new Date()));

        service = createService();
        assertEquals(1, service.getTopScores("mines").size());
    }
}

