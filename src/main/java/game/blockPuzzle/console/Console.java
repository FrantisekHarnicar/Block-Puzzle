package game.blockPuzzle.console;


import game.entity.Comment;
import game.entity.Rating;
import game.entity.Score;
import game.blockPuzzle.core.Field;
import game.blockPuzzle.core.GameState;
import game.blockPuzzle.core.ObjectTile;
import game.blockPuzzle.core.Tile;
import game.service.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console {
    private static final Pattern COMMAND_PATTERN = Pattern.compile("([RM])([ABCDE])([A-E])([1-5])");
    private static final Pattern NAME_PATTERN = Pattern.compile("([a-zA-Z0-9]{1,10})");
    private static final Pattern YES_OR_NO_PATTERN = Pattern.compile("([YN])");
    private static final Pattern RATING_PATTERN = Pattern.compile("([1-5])");

    private Field field;

    private final Scanner scanner = new Scanner(System.in);
    private final ScoreServiceJDBC scoreService = new ScoreServiceJDBC();
    private final CommentServiceJDBC commentService = new CommentServiceJDBC();
    public static final String GAME_NAME = "BlockPuzzle";
    private String userName;
    private String userComment;
    private static long startTime;
    Date date = new Date();
    private int nextLevel = 1;
    private final int LEVELS = 5;
    private int score = 0;


    public Console(Field field) {
        this.field = field;
    }

    public void play() {
        System.out.println("Top score.");
        printTopScores();
        System.out.println("---------------------------------");
        userName();
        System.out.println("---------------------------------");


        do {
            startTime = System.currentTimeMillis();

            do {
                System.out.println(score);
                System.out.println("LEVEL: " + nextLevel);
                printField();
                System.out.println("---------------------------------");
                printUselessObject();
                userInput();
            } while (field.getState() == GameState.PLAYING);
            System.out.println("YOU SOLVED THIS LEVEL");
            printField();
            System.out.println("---------------------------------");
            nextLevel++;
        } while (nextLevel());
        writeScore();
        System.out.println("---------------------------------");
        printTopScores();
        System.out.println("---------------------------------");
        printComment();
        System.out.println("---------------------------------");
        userChoiceComment();
        System.out.println("---------------------------------");
        userChoiceRating();
        System.out.println("---------------------------------");
    }

    private boolean nextLevel() {
        if (nextLevel == LEVELS + 1) {
            System.out.println("You WIN this game!!! Congratulate.");
            return false;
        }
        score += field.getScore();
        System.out.println("Your score: " + score);
        System.out.print("Next level(Y/N): ");
        String line = scanner.nextLine().toUpperCase();
        Matcher matcher = YES_OR_NO_PATTERN.matcher(line);
        if (matcher.matches()) {
            if ("Y".equals(line)) {
                field = new Field(nextLevel);
                return true;

            } else {
                System.out.println("OK. As you want.");
            }
        }

        return false;
    }

    public static long getTime() {
        return startTime;
    }

    private void printUselessObject() {
        if (field.UselessObject('A')) {
            printObject(field.getObjectA());
        }
        if (field.UselessObject('B')) {
            printObject(field.getObjectB());
        }
        if (field.UselessObject('C')) {
            printObject(field.getObjectC());
        }
        if (field.UselessObject('D')) {
            printObject(field.getObjectD());
        }
        if (field.UselessObject('E')) {
            printObject(field.getObjectE());
        }
    }

    private void printObject(Tile[][] tile) {
        for (int row = 0; row < field.getRowCount(); row++) {
            for (int column = 0; column < field.getColumnCount(); column++) {
                System.out.print(" ");
                switch (tile[row][column].getState()) {
                    case EMPTY -> System.out.print(" ");
                    case FULL -> System.out.print(((ObjectTile) tile[row][column]).getId());
                }
            }
            System.out.println();
        }
    }

    private void printField() {
        System.out.print(" ");
        for (int column = 0; column < field.getColumnCount(); column++) {
            System.out.print(" ");
            System.out.print(column + 1);
        }
        System.out.println();

        for (int row = 0; row < field.getRowCount(); row++) {
            System.out.print((char) (row + 'A'));
            for (int column = 0; column < field.getColumnCount(); column++) {
                Tile tile = field.getTile(row, column);
                System.out.print(" ");
                switch (tile.getState()) {
                    case EMPTY -> System.out.print("-");
                    case FULL -> System.out.print(((ObjectTile) tile).getId());
                }
            }
            System.out.println();
        }
    }

    private void userInput() {
        System.out.print("Enter command (X - exit, RBA1 - remove B from A1, MAA1 - move A to A1: ");
        String line = scanner.nextLine().toUpperCase();
        if ("X".equals(line))
            System.exit(0);

        Matcher matcher = COMMAND_PATTERN.matcher(line);

        if (matcher.matches()) {
            int row = line.charAt(2) - 'A';
            int column = Integer.parseInt(matcher.group(4)) - 1;

            if (matcher.group(2).equals("A")) {
                if (matcher.group(1).equals("R")) {
                    System.out.println("Remove object A");
                    field.remove_object(field.getObjectA());
                } else
                    field.move(row, column, field.getObjectA());
            }
            if (matcher.group(2).equals("B")) {
                if (matcher.group(1).equals("R")) {
                    System.out.println("Remove object B");
                    field.remove_object(field.getObjectB());
                } else
                    field.move(row, column, field.getObjectB());
            }
            if (matcher.group(2).equals("C")) {
                if (matcher.group(1).equals("R")) {
                    System.out.println("Remove object C");
                    field.remove_object(field.getObjectC());
                } else
                    field.move(row, column, field.getObjectC());
            }
            if (matcher.group(2).equals("D")) {
                if (matcher.group(1).equals("R")) {
                    System.out.println("Remove object D");
                    field.remove_object(field.getObjectD());
                } else
                    field.move(row, column, field.getObjectD());
            }
            if (matcher.group(2).equals("E")) {
                if (matcher.group(1).equals("R")) {
                    System.out.println("Remove object E");
                    field.remove_object(field.getObjectE());
                } else
                    field.move(row, column, field.getObjectE());
            }
        } else {
            System.err.println("Wrong input " + line);
        }


    }

    private void printTopScores() {
        List<Score> scores = scoreService.getTopScores(GAME_NAME);
        for (Score score : scores) {
            System.out.printf("%s %d\n", score.getPlayer(), score.getPoints());
        }
    }

    private void userName() {
        System.out.print("Enter name(1-10 number or later): ");
        String line = scanner.nextLine();
        Matcher matcher = NAME_PATTERN.matcher(line);
        if (matcher.matches()) {
            userName = line;
        } else {
            System.err.println("Wrong name " + line);
            userName();
        }
    }

    private void writeScore() {
        ScoreService service = new ScoreServiceJDBC();
        service.addScore(new Score(GAME_NAME, userName, score, date));
    }

    private void userChoiceComment() {
        System.out.print("Would you want write some comment?(Y/N): ");
        String orLine = scanner.nextLine().toUpperCase();
        Matcher orMatcher = YES_OR_NO_PATTERN.matcher(orLine);
        if (orMatcher.matches()) {
            if ("Y".equals(orLine)) {
                userComment();
            } else {
                return;
            }
        } else {
            System.err.println("Wrong chance " + orLine);
            userChoiceComment();
        }
    }

    private void userComment() {
        System.out.print("Write some comment: ");
        userComment = scanner.nextLine();
        writeComment();
    }

    private void writeComment() {
        CommentService service = new CommentServiceJDBC();
        service.addComment(new Comment(GAME_NAME, userName, userComment, date));
        System.out.println("Thanks for your comment");
    }

    private void printComment() {
        List<Comment> comments = commentService.getComments(GAME_NAME);
        for (Comment comment : comments) {
            System.out.printf("%s %s\n", comment.getPlayer(), comment.getComment());
        }
    }

    private void userChoiceRating() {
        RatingService ratingService = new RatingServiceJDBC();
        System.out.println("Average rating is " + ratingService.getAverageRating(GAME_NAME));
        System.out.print("Would you want take some rating?(Y/N): ");
        String orLine = scanner.nextLine().toUpperCase();
        Matcher orMatcher = YES_OR_NO_PATTERN.matcher(orLine);
        if (orMatcher.matches()) {
            if ("Y".equals(orLine)) {
                userRating();
            } else {
                return;
            }
        } else {
            System.err.println("Wrong chance " + orLine);
            userChoiceRating();
        }
    }

    private void userRating() {
        System.out.print("Chose number 1-bad/5-really good: ");
        String rating = scanner.nextLine();
        Matcher matcher = RATING_PATTERN.matcher(rating);
        if (matcher.matches()) {
            int ratingInt = Integer.parseInt(matcher.group(1));
            RatingService ratingService = new RatingServiceJDBC();
            ratingService.setRating(new Rating(GAME_NAME, userName, ratingInt, date));
            System.out.println("Thanks for your rating.");
        } else {
            System.err.println("Wrong rating " + rating);
            userRating();
        }

    }

}
