package core;

public abstract class Tile {
    private TileState state = TileState.EMPTY;

    public TileState getState() {
        return state;
    }

    void setState(TileState state) {
        this.state = state;
    }
}

