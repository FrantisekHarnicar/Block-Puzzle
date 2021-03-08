import console.Console;
import core.Field;

public class Main {
    public static void main(String[] args) {
        Field field = new Field(5, 5);
        Console ui = new Console(field);
        ui.play();
    }

}
