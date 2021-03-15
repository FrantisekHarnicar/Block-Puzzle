package console;

import core.Field;
import core.ObjectTile;
import core.Tile;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console {
    private static final Pattern COMMAND_PATTERN = Pattern.compile("([RM])([ABCDE])([A-E])([1-5])");

    private final Field field;

    private final Scanner scanner = new Scanner(System.in);


    public Console (Field field){
        this.field = field;
    }

    public void play(){
        do{
            printField();
            printUselessObject();
            userInput();
        }while (field.isSolved());

        printField();
    }

    private void printUselessObject(){
        if(field.UselessObject('A')){
            printObject(field.getObjectA());
        }
        if(field.UselessObject('B')){
            printObject(field.getObjectB());
        }
        if(field.UselessObject('C')){
            printObject(field.getObjectC());
        }
        if(field.UselessObject('D')){
            printObject(field.getObjectD());
        }
        if(field.UselessObject('E')){
            printObject(field.getObjectE());
        }
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
        System.out.print("Enter command (X - exit, RBA1 - remove B from A1, MAA1 - move A to A1: ");
        String line = scanner.nextLine().toUpperCase();
        if ("X".equals(line))
            System.exit(0);

        Matcher matcher = COMMAND_PATTERN.matcher(line);
        if (matcher.matches()) {
            int row = line.charAt(1) - 'A';
            int column = Integer.parseInt(matcher.group(4)) - 1;


            if (matcher.group(2).equals("A")) {
                if(matcher.group(1).equals("R")){
                    field.remove_object(field.getObjectA());
                }else
                field.move(row, column, field.getObjectA());
            }
            if (matcher.group(2).equals("B")) {
                if(matcher.group(1).equals("R")){
                    field.remove_object(field.getObjectB());
                }else
                field.move(row, column, field.getObjectB());
            }
            if (matcher.group(2).equals("C")) {
                if(matcher.group(1).equals("R")){
                    field.remove_object(field.getObjectC());
                }else
                field.move(row, column, field.getObjectC());
            }
            if (matcher.group(2).equals("D")) {
                if(matcher.group(1).equals("R")){
                    field.remove_object(field.getObjectD());
                }else
                field.move(row, column, field.getObjectD());
            }
            if (matcher.group(2).equals("E")) {
                if(matcher.group(1).equals("R")){
                    field.remove_object(field.getObjectE());
                }else
                field.move(row, column, field.getObjectE());
            }
        }else {
            System.err.println("Wrong input " + line);
        }


    }

}
