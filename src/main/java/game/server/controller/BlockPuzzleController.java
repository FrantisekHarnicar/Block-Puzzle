package game.server.controller;

import game.blockPuzzle.core.Field;
import game.blockPuzzle.core.GameState;
import game.blockPuzzle.core.ObjectTile;
import game.blockPuzzle.core.Tile;
import game.entity.Score;
import game.service.CommentService;
import game.service.RatingService;
import game.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static game.blockPuzzle.core.TileState.FULL;

@Controller
@RequestMapping("/blockpuzzle")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class BlockPuzzleController {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserController userController;

    private Field field = new Field(1);
    private static long startTime = System.currentTimeMillis();
    private Tile[][] object;
    private boolean remove;
    private boolean endGame;
    private boolean gameOver = true;
    private int level = 1;
    public int score = 0;


    @RequestMapping
    public String blockpuzzle(@RequestParam(required = false) String row,
                              @RequestParam(required = false) String column,
                              @RequestParam(required = false) String object,
                              Model model) {
        try{
            if(field.getState() == GameState.PLAYING) {
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
                if(field.getState() == GameState.SOLVED)
                    score += field.getScore();
            }
            if(level == 5 || (endGame && userController.getLoggedUser() != null)) {
                endGame = false;
                field.setGameState(GameState.SOLVED);
                scoreService.addScore(new Score("BlockPuzzle", userController.getLoggedUser(), score, new Date()));
            }
        }catch (Exception e){
            System.out.println("Daco si posral v blockpuzzle metode");
        }

        model.addAttribute("scores", scoreService.getTopScores("BlockPuzzle"));
        model.addAttribute("comments", commentService.getComments("BlockPuzzle"));
        model.addAttribute("avgRating", ratingService.getAverageRating("BlockPuzzle"));

        return "blockpuzzle";
    }

    public boolean isEndGame() {
        return endGame;
    }

    @RequestMapping("/newlevel")
    public String newGame(){
        if(level != 5)
            level++;
        field = new Field(level);
        startTime = System.currentTimeMillis();
        return "redirect:/blockpuzzle";
    }
    public static long getTime(){
        return startTime;
    }

    @RequestMapping("/remove")
    public String remove() {
        remove = !remove;
        return "redirect:/blockpuzzle";
    }

    @RequestMapping("/endgame")
    public String endGame() {
        endGame = true;
        gameOver = !gameOver;
        return "redirect:/blockpuzzle";
    }

    public boolean getViewButtons() {
        if(stateGame() == false && gameOver == true){
            return false;
        }else{
            return true;
        }
    }
    public boolean getGameOver(){
        return gameOver;
    }

    public boolean isRemove() {
        return remove;
    }
    public boolean stateGame(){
        if(GameState.PLAYING == field.getState()){
            return true;
        }else {
            return false;
        }
    }

    public String getMainField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='main_field'>");
        sb.append("<table class='main_table'>\n");

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

        sb.append("</table>\n");
        sb.append("</div>");

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
        sb.append(String.format("<div class='object%s'>", objectID(tile)));
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

        sb.append("</table>\n");
        sb.append("</div>");
        return sb.toString();

    }
    private char objectID(Tile[][] tile){
        for (int row = 0; row < field.getRowCount(); row++) {
            for (int column = 0; column < field.getColumnCount(); column++) {
                if(tile[row][column].getState() == FULL){
                    return ((ObjectTile) tile[row][column]).getId();
                }
                }
            }
        return '?';
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
