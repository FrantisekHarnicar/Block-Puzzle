package game.server.controller;

import game.blockPuzzle.core.Field;
import game.blockPuzzle.core.GameState;
import game.blockPuzzle.core.ObjectTile;
import game.blockPuzzle.core.Tile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

@Controller
@RequestMapping("/blockpuzzle")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class BlockPuzzleController {

    private Field field = new Field(1);
    private Tile[][] object;
    private boolean remove;
    private int level = 1;


    @RequestMapping
    public String blockpuzzle(@RequestParam(required = false) String row,
                              @RequestParam(required = false) String column,
                              @RequestParam(required = false) String object) {
        try{
            if (object != null) {
                switch (object.charAt(0)) {
                    case 'A' -> this.object = field.getObjectA();
                    case 'B' -> this.object = field.getObjectB();
                    case 'C' -> this.object = field.getObjectC();
                    case 'D' -> this.object = field.getObjectD();
                    case 'E' -> this.object = field.getObjectE();
                }
            }

            if (remove) {
                if (this.object != null)
                    field.remove_object(this.object);
            } else {

                if (row != null && column != null && this.object != null) {
                    field.move(Integer.parseInt(row), Integer.parseInt(column), this.object);
                    this.object = null;
                }
            }

        }catch (Exception e){
            // sicko v poradku
        }
        return "blockpuzzle";
    }
    @RequestMapping("/newlevel")
    public String newGame(){
        field = new Field(level);
        return "blockpuzzle";
    }

    @RequestMapping("/remove")
    public String remove() {
        remove = !remove;
        return "blockpuzzle";
    }

    public boolean isRemove() {
        return remove;
    }
    public boolean stateGame(){
        if(GameState.PLAYING == field.getState()){
            return true;
        }else {
            if(level != 5)
            level++;
            return false;
        }
    }

    public String getMainField() {
        StringBuilder sb = new StringBuilder();

        sb.append("<table>\n");

        for (int row = 0; row < field.getRowCount(); row++) {
            sb.append("<tr>\n");
            for (int column = 0; column < field.getColumnCount(); column++) {
                Tile tile = field.getTile(row, column);
                sb.append("<td>\n");
                switch (tile.getState()){
                    case EMPTY -> {
                        sb.append(String.format("<a href='/blockpuzzle?row=%d&column=%d'>\n", row, column));
                        sb.append("<img src='/images/BlockPuzzle/prazdne.png'>");
                        sb.append("</a>\n");
                    }
                    case FULL -> {
                        sb.append(String.format("<a href='/blockpuzzle?object=%s'>\n", ((ObjectTile) tile).getId()));
                        sb.append("<img src='/images/BlockPuzzle/"+ tileColor(((ObjectTile) tile).getId()) +".png'>");
                        sb.append("</a>\n");
                    }
                }

                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }

        sb.append("<table>\n");

        return sb.toString();
    }

    public String printUselessObject() {
        String sb = " ";
        if (field.UselessObject('A')) {
            sb = sb + printObject(field.getObjectA());
        }
        if (field.UselessObject('B')) {
            sb = sb + printObject(field.getObjectB());
        }
        if (field.UselessObject('C')) {
            sb = sb + printObject(field.getObjectC());
        }
        if (field.UselessObject('D')) {
            sb = sb + printObject(field.getObjectD());
        }
        if (field.UselessObject('E')) {
            sb = sb + printObject(field.getObjectE());
        }
        return sb;
    }

    private String printObject(Tile[][] tile) {
        StringBuilder sb = new StringBuilder();

        sb.append("<table>\n");

        for (int row = 0; row < field.getRowCount(); row++) {
            sb.append("<tr>\n");
            for (int column = 0; column < field.getColumnCount(); column++) {
                sb.append("<td>\n");
                switch (tile[row][column].getState()) {
                    case EMPTY -> sb.append("");
                    case FULL -> {
                        sb.append(String.format("<a href='/blockpuzzle?object=%s'>\n", ((ObjectTile) tile[row][column]).getId()));
                        sb.append("<img src='/images/BlockPuzzle/"+ tileColor(((ObjectTile) tile[row][column]).getId()) +".png'>");
                        sb.append("</a>\n");
                    }
                }
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }

        sb.append("<table>\n");
        return sb.toString();

    }

    /*private String getImageName(Tile tile) {
        switch (tile.getState()) {
            case EMPTY -> {
                return "prazdne";
            }
            case FULL -> {
                return tileColor(((ObjectTile) tile).getId());
            }
            default -> throw new IllegalArgumentException("Unsuported tile state " + tile.getState());
        }
    }*/

    private String tileColor(char tileID) {
        return switch (tileID) {
            case 'A' -> "oranzova";
            case 'B' -> "modra";
            case 'C' -> "ruzova";
            case 'D' -> "zelena";
            case 'E' -> "zlta";
            default -> throw new IllegalArgumentException("Unsuported ID " + tileID);
        };
    }


}
