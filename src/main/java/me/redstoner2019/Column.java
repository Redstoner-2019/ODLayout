package me.redstoner2019;

public class Column {
    public static final double VARIABLE_LENGTH = -1;
    public LengthType lengthType;
    public double length;

    private double start;
    private double end;

    public Column(double length, LengthType lengthType) {
        this.lengthType = lengthType;
        this.length = length;
    }
    public Column(Lengths l) {
        switch (l){
            case VARIABLE -> {
                this.length = VARIABLE_LENGTH;
                this.lengthType = LengthType.CUSTOM;
            }
        }
    }

    public double getLength() {
        return length;
    }
    public int getLengthInt(){
        return (int) length;
    }

    public LengthType getLengthType() {
        return lengthType;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }
}
