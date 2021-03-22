package game.blockPuzzle.core;


public class ObjectTile extends Tile{
    private char id;
    public ObjectTile(char id){
        this.id = id;
        setState(TileState.FULL);
    }

    public char getId() {
        return id;
    }
}
