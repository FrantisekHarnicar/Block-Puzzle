package game;

import game.blockPuzzle.console.Console;
import game.blockPuzzle.core.Field;

public class Main {
    public static void main(String[] args) {
        Field field = new Field(5, 5);
        Console ui = new Console(field);
        ui.play();
    }

}
