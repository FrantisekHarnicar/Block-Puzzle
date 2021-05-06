package game.blockPuzzle.core;


import game.blockPuzzle.console.Console;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static game.blockPuzzle.core.TileState.FULL;

public class Field {
    private GameState state = GameState.PLAYING;


    private int level;
    private final int rowCount;
    private final int columnCount;
    private final Tile[][] tiles;
    private final Tile[][] objectTileA;
    private final Tile[][] objectTileB;
    private final Tile[][] objectTileC;
    private final Tile[][] objectTileD;
    private final Tile[][] objectTileE;


    public Field(int level) {
        this.level = level;
        this.rowCount = 5;
        this.columnCount = 5;
        tiles = new Tile[rowCount][columnCount];
        objectTileA = new Tile[rowCount][columnCount];
        objectTileB = new Tile[rowCount][columnCount];
        objectTileC = new Tile[rowCount][columnCount];
        objectTileD = new Tile[rowCount][columnCount];
        objectTileE = new Tile[rowCount][columnCount];
        generate();
    }

    public int getScore() {
        int score = rowCount * columnCount * 50 - (int) (System.currentTimeMillis() - Console.getTime()) / 1000;
        return Math.max(score, 0);

    }


    private void generate() {
        generateEmptyField(tiles);
        loadObjectTile();
    }

    private void generateEmptyField(Tile[][] tile) {
        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                tile[x][y] = new EmptyTile();
            }
        }

    }

    private void loadObjectTile() {
        generateEmptyField(objectTileA);
        generateEmptyField(objectTileB);
        generateEmptyField(objectTileC);
        generateEmptyField(objectTileD);
        generateEmptyField(objectTileE);

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/main/java/game/levels/Levels.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject lvl;
            switch (level) {
                case 2 -> lvl = (JSONObject) jsonObject.get("lvl2");
                case 3 -> lvl = (JSONObject) jsonObject.get("lvl3");
                case 4 -> lvl = (JSONObject) jsonObject.get("lvl4");
                case 5 -> lvl = (JSONObject) jsonObject.get("lvl5");
                default -> lvl = (JSONObject) jsonObject.get("lvl1");
            }

            String stringIterator;
            int objCount = Integer.parseInt(lvl.get("objectCount").toString());

            for (int i = 1; i < objCount + 1; i++) {
                stringIterator = String.valueOf(i);
                JSONObject jsonObject1 = (JSONObject) lvl.get(stringIterator);
                int objCount1 = Integer.parseInt(jsonObject1.get("objectCount").toString());
                JSONArray arrayRow = (JSONArray) jsonObject1.get("row");
                JSONArray arrayColumn = (JSONArray) jsonObject1.get("column");
                int row;
                int column;
                for (int x = 0; x < objCount1; x++) {
                    row = Integer.parseInt(arrayRow.get(x).toString());
                    column = Integer.parseInt(arrayColumn.get(x).toString());
                    switch (i) {
                        case 1 -> objectTileA[row][column] = new ObjectTile('A');
                        case 2 -> objectTileB[row][column] = new ObjectTile('B');
                        case 3 -> objectTileC[row][column] = new ObjectTile('C');
                        case 4 -> objectTileD[row][column] = new ObjectTile('D');
                        case 5 -> objectTileE[row][column] = new ObjectTile('E');
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void isSolved() {
        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                if (tiles[x][y] instanceof EmptyTile) {
                    return;
                }
            }
        }
        state = GameState.SOLVED;
    }

    public void move(int plantX, int plantY, Tile[][] fullTile) {
        if(GameState.PLAYING == state) {
            for (int x = 0; x < rowCount; x++) {
                for (int y = 0; y < columnCount; y++) {
                    if (!(fullTile[x][y] instanceof EmptyTile)) {
                        clean(tiles, ((ObjectTile) fullTile[x][y]).getId());
                    }
                }
            }
            if (plantX <= -1 || plantX >= rowCount) return;
            if (plantY <= -1 || plantY >= columnCount) return;
            int count = 0;
            int rememberX = 0;
            int rememberY = 0;
            for (int x = 0; x < rowCount; x++) {
                for (int y = 0; y < columnCount; y++) {
                    if (fullTile[x][y].getState() == FULL) {
                        if (count == 0) {
                            if (tiles[plantX][plantY] instanceof ObjectTile) {            // osetrenie ineho id
                                clean(tiles, ((ObjectTile) fullTile[x][y]).getId());
                                return;
                            }
                            tiles[plantX][plantY] = fullTile[x][y];                         // kopirovanie prvej pozicie
                            rememberX = x;
                            rememberY = y;
                            count++;
                        } else {
                            if (plantX + (x - rememberX) <= -1 || plantX + (x - rememberX) >= rowCount) {                          // osetrenie mimo pola
                                clean(tiles, ((ObjectTile) fullTile[x][y]).getId());
                                return;
                            }
                            if (plantY + (y - rememberY) <= -1 || plantY + (y - rememberY) >= columnCount) {                       // osetrenie mimo pola
                                clean(tiles, ((ObjectTile) fullTile[x][y]).getId());
                                return;
                            }
                            if (tiles[plantX + (x - rememberX)][plantY + (y - rememberY)] instanceof ObjectTile) {            // osetrenie ineho id
                                clean(tiles, ((ObjectTile) fullTile[x][y]).getId());
                                return;
                            }
                            tiles[plantX + (x - rememberX)][plantY + (y - rememberY)] = fullTile[x][y];                     // kopirovanie ostatnych pozicii
                        }
                    }
                }
            }
            isSolved();
        }
    }

    private void clean(Tile[][] tile, char id) {
        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                if (!(tile[x][y] instanceof EmptyTile) && id == ((ObjectTile) tile[x][y]).getId()) {
                    tile[x][y] = new EmptyTile();
                }
            }
        }
    }

    public boolean UselessObject(char id) {
        for (int row = 0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                if (!(tiles[row][column] instanceof EmptyTile) && id == ((ObjectTile) tiles[row][column]).getId()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void remove_object(Tile[][] tile) {
        move(rowCount, columnCount, tile);
    }


    public int getColumnCount() {
        return columnCount;
    }


    public int getRowCount() {
        return rowCount;
    }


    public Tile getTile(int row, int column) {
        return tiles[row][column];
    }

    public Tile[][] getObjectA() {
        return objectTileA;
    }

    public Tile[][] getObjectB() {
        return objectTileB;
    }

    public Tile[][] getObjectC() {
        return objectTileC;
    }

    public Tile[][] getObjectD() {
        return objectTileD;
    }

    public Tile[][] getObjectE() {
        return objectTileE;
    }

    public GameState getState() {
        isSolved();
        return state;
    }
}
