package game;

import game.blockPuzzle.console.Console;
import game.blockPuzzle.core.Field;
import game.service.ScoreServiceJDBC;

public class Main {
    public static void main(String[] args) {
        Field field = new Field(1);
        Console ui = new Console(field);
        ui.play();
    }

}
