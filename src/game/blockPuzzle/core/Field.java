package game.blockPuzzle.core;


import game.blockPuzzle.console.Console;
import game.service.GamestudioException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static game.blockPuzzle.core.TileState.FULL;

public class Field {
    private GameState state = GameState.PLAYING;
    private static final String FIELD_FILE = "level.bin";

    private int rowCount;
    private int columnCount;
    private final Tile[][] tiles;
    private final Tile[][] objectTileA;
    private final Tile[][] objectTileB;
    private final Tile[][] objectTileC;
    private final Tile[][] objectTileD;
    private final Tile[][] objectTileE;


    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        tiles = new Tile[rowCount][columnCount];
        objectTileA = new Tile[rowCount][columnCount];
        objectTileB = new Tile[rowCount][columnCount];
        objectTileC = new Tile[rowCount][columnCount];
        objectTileD = new Tile[rowCount][columnCount];
        objectTileE = new Tile[rowCount][columnCount];
        generate();
    }

    public int getScore() {
        return rowCount * columnCount * 4 - (int) (System.currentTimeMillis() - Console.getTime()) / 1000;
    }


    private void generate() {
        generateEmptyField(tiles);
        generateObjectTile();
    }

    private void generateEmptyField(Tile[][] tile) {
        for (int x = 0; x < rowCount; x++) {
            for(int y = 0; y < columnCount; y++) {
                tile[x][y] = new EmptyTile();
            }
        }

    }

    private void generateObjectTile() {
        generateEmptyField(objectTileA);
        generateEmptyField(objectTileB);
        generateEmptyField(objectTileC);
        generateEmptyField(objectTileD);
        generateEmptyField(objectTileE);
        /*int count = 0;

        int solved1 = 0;
        int solved2 = 0;
        int solved3 = 0;
        int solved4 = 0;
        int solved5 = 0;

        int object1 = 0;
        int object2 = 0;
        int object3 = 0;
        int object4 = 0;
        int object5 = 0;

        Random random = new Random();

        while(count != (row * column)){
            object1 = random.nextInt(6);
            object2 = random.nextInt(6);
            object3 = random.nextInt(6);
            object4 = random.nextInt(6);
            object5 = random.nextInt(6);
            count = object1 + object2 +object3 +object4 + object5;
        }
        System.out.println(count);
        object1 = 15;
        object2 = 10;
        while(!isSolved()){
            while(solved1 != object1){
                int rowRandom = random.nextInt(row);
                int columnRandom = random.nextInt(column);
                if(solved1 == 0 && tiles[rowRandom][columnRandom] instanceof EmptyTile){
                    tiles[rowRandom][columnRandom] = new ObjectTile('A');
                    solved1++;
                }else{
                    if(isNeighbor( rowRandom, columnRandom, 'A') && solved1 != 0 && tiles[rowRandom][columnRandom] instanceof EmptyTile){
                        tiles[rowRandom][columnRandom] = new ObjectTile('A');
                        solved1++;
                    }
                }
            }
            while(solved2 != object2){
                int rowRandom = random.nextInt(row);
                int columnRandom = random.nextInt(column);
                if(solved2 == 0 && tiles[rowRandom][columnRandom] instanceof EmptyTile){
                    tiles[rowRandom][columnRandom] = new ObjectTile('B');
                    solved2++;
                }else{
                    if(isNeighbor( rowRandom, columnRandom, 'B') && solved2 != 0 && tiles[rowRandom][columnRandom] instanceof EmptyTile){
                        tiles[rowRandom][columnRandom] = new ObjectTile('B');
                        solved2++;
                    }
                }
            }
            generateEmptyField(tiles);
        }*/
        // object A
        objectTileA[1][2] = new ObjectTile('A');
        objectTileA[1][3] = new ObjectTile('A');
        objectTileA[1][1] = new ObjectTile('A');
        objectTileA[2][1] = new ObjectTile('A');
        objectTileA[3][1] = new ObjectTile('A');
        objectTileA[3][2] = new ObjectTile('A');
        // object A

        //object B
        objectTileB[2][2] = new ObjectTile('B');
        objectTileB[2][1] = new ObjectTile('B');
        objectTileB[2][3] = new ObjectTile('B');
        //object B

        //object C
        objectTileC[2][2] = new ObjectTile('C');
        objectTileC[1][2] = new ObjectTile('C');
        objectTileC[0][2] = new ObjectTile('C');
        objectTileC[0][1] = new ObjectTile('C');
        objectTileC[3][2] = new ObjectTile('C');
        objectTileC[4][2] = new ObjectTile('C');
        objectTileC[3][1] = new ObjectTile('C');
        //object C

        //object D
        objectTileD[2][2] = new ObjectTile('D');
        objectTileD[2][1] = new ObjectTile('D');
        objectTileD[2][3] = new ObjectTile('D');
        objectTileD[2][4] = new ObjectTile('D');
        //object D

        //object E
        objectTileE[2][2] = new ObjectTile('E');
        objectTileE[2][1] = new ObjectTile('E');
        objectTileE[2][0] = new ObjectTile('E');
        objectTileE[1][2] = new ObjectTile('E');
        objectTileE[1][3] = new ObjectTile('E');
        //object E

        //objectTile1[2][4] = new ObjectTile('A');
    }

    private boolean isNeighbor(int rowNeighbor, int columnNeighbor, char id){
        if(rowNeighbor != 0) {
            Tile tileUP = tiles[rowNeighbor - 1][columnNeighbor];
            if (tileUP != null && !(tileUP instanceof EmptyTile) && ((ObjectTile) tileUP).getId() == id) {
                return true;
            }
        }
        if(rowNeighbor != rowCount -1) {
            Tile tileDOWN = tiles[rowNeighbor + 1][columnNeighbor];
            if (tileDOWN != null && !(tileDOWN instanceof EmptyTile) && ((ObjectTile) tileDOWN).getId() == id) {
                return true;
            }
        }
        if(columnNeighbor != columnCount -1) {
            Tile tileRIGHT = tiles[rowNeighbor][columnNeighbor + 1];
            if (tileRIGHT != null && !(tileRIGHT instanceof EmptyTile) && ((ObjectTile) tileRIGHT).getId() == id) {
                return true;
            }
        }
        if(columnNeighbor != 0) {
            Tile tileLEFT = tiles[rowNeighbor][columnNeighbor - 1];
            if (tileLEFT != null && !(tileLEFT instanceof EmptyTile) && ((ObjectTile) tileLEFT).getId() == id) {
                return true;
            }
        }
        return false;
    }

    private boolean isSolved() {
        for(int x = 0; x < rowCount; x++){
            for(int y = 0; y < columnCount; y++){
                if(tiles[x][y] instanceof EmptyTile){
                    return true;
                }
            }
        }
        System.out.println("YOU WIN, I KNOW IT");
        state = GameState.SOLVED;
        return false;
    }

    public void move(int plantX, int plantY, Tile[][] fullTile){
        for(int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                if(!(fullTile[x][y] instanceof EmptyTile)){
                    clean(tiles, ((ObjectTile)fullTile[x][y]).getId());
                }
            }
        }
        if(plantX <= -1 || plantX >= rowCount) return;
        if(plantY <= -1 || plantY >= columnCount) return;
        int count = 0;
        int rememberX = 0;
        int rememberY = 0;
        for(int x = 0; x < rowCount; x++){
            for(int y = 0; y < columnCount; y++){
                if(fullTile[x][y].getState() == FULL){
                    if(count == 0) {
                        if(tiles[plantX][plantY]  instanceof ObjectTile){            // osetrenie ineho id
                            clean(tiles, ((ObjectTile) fullTile[x][y]).getId());
                            return;
                        }
                        tiles[plantX][plantY] = fullTile[x][y];                         // kopirovanie prvej pozicie
                        rememberX = x;
                        rememberY = y;
                        count++;
                    }else{
                        if(plantX + (x - rememberX) <= -1 || plantX + (x - rememberX) >= rowCount){                          // osetrenie mimo pola
                            clean(tiles, ((ObjectTile) fullTile[x][y]).getId());
                            return;
                        }
                        if(plantY + (y - rememberY) <= -1 || plantY + (y - rememberY) >= columnCount){                       // osetrenie mimo pola
                            clean(tiles, ((ObjectTile) fullTile[x][y]).getId());
                            return;
                        }
                        if(tiles[plantX + (x - rememberX)][plantY + (y - rememberY)] instanceof ObjectTile){            // osetrenie ineho id
                            clean(tiles, ((ObjectTile) fullTile[x][y]).getId());
                            return;
                        }
                        tiles[plantX + (x - rememberX)][plantY + (y - rememberY)] = fullTile[x][y];                     // kopirovanie ostatnych pozicii
                    }
                }
            }
        }
    }

    private void clean(Tile tile[][], char id){
        for(int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                if (!(tile[x][y] instanceof EmptyTile) && id == ((ObjectTile) tile[x][y]).getId()) {
                    tile[x][y] = new EmptyTile();
                }
            }
        }
    }
    public boolean UselessObject(char id) {
        for(int row = 0; row < getRowCount(); row++){
            for(int column = 0; column < getColumnCount(); column++){
                if (!(tiles[row][column] instanceof EmptyTile) && id == ((ObjectTile) tiles[row][column]).getId()) {
                    return false;
                }
            }
        }
        return true;
    }
    public void remove_object(Tile[][] tile){
        move(rowCount, columnCount,tile);
    }

    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FIELD_FILE))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new GamestudioException("Error saving field", e);
        }
    }


    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
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
