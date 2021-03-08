package console;

import core.Field;
import core.ObjectTile;
import core.Tile;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console {
    private static final Pattern COMMAND_PATTERN = Pattern.compile("([ABCDEX]) to ([A-E])([1-5])");

    private final Field field;

    private final Scanner scanner = new Scanner(System.in);


    public Console (Field field){
        this.field = field;
    }

    public void play(){
        printField();
        printObject(field.getObject1());
        printObject(field.getObject2());
        printObject(field.getObject3());
        printObject(field.getObject4());
        printObject(field.getObject5());
        field.move(0,1,field.getObject1());
        printField();
    }

    private void printObject(Tile tile[][]) {
        for (int row = 0; row < field.getRow(); row++) {
            for (int column = 0; column < field.getColumn(); column++) {
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
        for (int column = 0; column < field.getColumn(); column++) {
            System.out.print(" ");
            System.out.print(column + 1);
        }
        System.out.println();

        for (int row = 0; row < field.getRow(); row++) {
            System.out.print((char) (row + 'A'));
            for (int column = 0; column < field.getColumn(); column++) {
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

    private void userInput(){
       /* System.out.print("Enter command (X - exit, A to A1 - move: ");
        String line = scanner.nextLine().toUpperCase();
        if ("X".equals(line))
            System.exit(0);

        Matcher matcher = COMMAND_PATTERN.matcher(line);
        if (matcher.matches()) {
            int row = line.charAt(1) - 'A';
            int column = Integer.parseInt(matcher.group(3)) - 1;

            if (matcher.group(1).equals("O")) {
                field.openTile(row, column);
            } else if (line.startsWith("M")) {
                field.markTile(row, column);
            }
        } else {
            System.err.println("Wrong input " + line);
        }*/


    }

}
