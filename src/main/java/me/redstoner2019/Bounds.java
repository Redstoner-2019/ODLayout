package me.redstoner2019;

public class Bounds {
    private Position pos1;
    private Position pos2;

    public Position getPos1() {
        return pos1;
    }

    public void setPos1(Position pos1) {
        this.pos1 = pos1;
    }

    public Position getPos2() {
        return pos2;
    }

    public void setPos2(Position pos2) {
        this.pos2 = pos2;
    }

    public Bounds(Position pos1, Position pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }
}
