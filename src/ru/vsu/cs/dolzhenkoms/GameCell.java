package ru.vsu.cs.dolzhenkoms;

public class GameCell {
    private int value;
    private GameCellStatus status;

    public GameCell(int value, GameCellStatus status) {
        this.value = value;
        this.status = status;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public GameCellStatus getStatus() {
        return status;
    }

    public void setStatus(GameCellStatus status) {
        this.status = status;
    }
}
