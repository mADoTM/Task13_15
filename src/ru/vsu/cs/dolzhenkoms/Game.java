package ru.vsu.cs.dolzhenkoms;

import java.util.Random;

public class Game {
    private final Random random = new Random();

    private GameCell[][] field;
    private GameStatus status;

    private int maxScore = 0;
    private int score = 0;
    private int closedBoxesCount;
    private int playerX;
    private int playerY;

    public Game(int rowCount, int colCount, int bombCount) {
        playerX = random.nextInt(colCount);
        playerY = random.nextInt(rowCount);

        field = initializeField(rowCount, colCount, bombCount);
        status = GameStatus.PLAYING;
        closedBoxesCount = rowCount * colCount - bombCount - 1;
    }

    public Game(int rowCount, int colCount) {
        playerX = random.nextInt(colCount);
        playerY = random.nextInt(rowCount);

        field = initializeField(rowCount, colCount, rowCount * colCount * 20 / 100);
        status = GameStatus.PLAYING;
        closedBoxesCount = rowCount * colCount * 80 / 100 - 1;
    }

    public Game() {
        int rowCount = 7;
        int colCount = 7;

        playerX = random.nextInt(colCount);
        playerY = random.nextInt(rowCount);

        field = initializeField(rowCount, colCount, rowCount * colCount * 20 / 100);
        status = GameStatus.PLAYING;

        closedBoxesCount = rowCount * colCount * 80 / 100 - 1;
    }

    public GameCell[][] getField() {
        return field;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public GameStatus getStatus() {
        return status;
    }

    public int getMaxScore() { return maxScore; }

    public int getScore() {
        return score;
    }

    public void restartGame() {
        field = initializeField();
        score = 0;
        this.status = GameStatus.PLAYING;
    }

    public void leftMouseClick(int x, int y) {
        if(status == GameStatus.PLAYING) {
            if (isCellValidate(x, y)) {
                System.out.println(field[y][x].getValue());
                int countSteps = field[y][x].getValue();
                makeStep(countSteps, getStepVector(x, y));
                if (isGameWon())
                    status = GameStatus.WIN;
            }
        }
    }

    public void customInitializeField(int height, int width) {
        initializeField(height, width, height * width * 20 / 100 - 1);
    }

    private void makeStep(int countSteps, StepVector vector) {
        for(int i = 0; i < countSteps; i++) {
            int tempX = playerX, tempY = playerY;
            if(vector == StepVector.LEFT) {
                if(playerX - 1 < 0) {
                    status = GameStatus.LOSE;
                    field[playerY][playerX].setStatus(GameCellStatus.DEAD);
                    return;
                }

                field[tempY][tempX].setStatus(GameCellStatus.OPENED);
                if((field[playerY][playerX - 1].getStatus() == GameCellStatus.BOMB) || (field[playerY][playerX - 1].getStatus() == GameCellStatus.OPENED)) {
                    status = GameStatus.LOSE;
                    field[playerY][playerX - 1].setStatus(GameCellStatus.DEAD);
                    return;
                }
                field[tempY][tempX - 1].setStatus(GameCellStatus.PLAYER);

                playerX--;
                score += field[tempY][tempX - 1].getValue();
            }
            if(vector == StepVector.UP) {
                if(playerY - 1 < 0) {
                    field[tempY][tempX].setStatus(GameCellStatus.DEAD);
                    status = GameStatus.LOSE;
                    return;
                }

                field[tempY][tempX].setStatus(GameCellStatus.OPENED);

                if((field[playerY - 1][playerX].getStatus() == GameCellStatus.BOMB) || (field[playerY - 1][playerX].getStatus() == GameCellStatus.OPENED)) {
                    status = GameStatus.LOSE;
                    field[playerY - 1][playerX].setStatus(GameCellStatus.DEAD);
                    return;
                }

                field[tempY - 1][tempX].setStatus(GameCellStatus.PLAYER);

                playerY--;
                score += field[tempY - 1][tempX].getValue();
            }
            if(vector == StepVector.RIGHT) {
                if(playerX + 1 > field[playerY].length - 1) {
                    status = GameStatus.LOSE;
                    field[playerY][playerX].setStatus(GameCellStatus.DEAD);
                    return;
                }

                field[tempY][tempX].setStatus(GameCellStatus.OPENED);

                if((field[playerY][playerX + 1].getStatus() == GameCellStatus.BOMB) || (field[playerY][playerX + 1].getStatus() == GameCellStatus.OPENED)) {
                    status = GameStatus.LOSE;
                    field[playerY][playerX + 1].setStatus(GameCellStatus.DEAD);
                    return;
                }
                field[tempY][tempX + 1].setStatus(GameCellStatus.PLAYER);

                playerX++;
                score += field[tempY][tempX + 1].getValue();
            }
            if(vector == StepVector.DOWN) {
                if(playerY + 1 > field.length - 1) {
                    status = GameStatus.LOSE;
                    field[playerY][playerX].setStatus(GameCellStatus.DEAD);
                    return;
                }

                field[tempY][tempX].setStatus(GameCellStatus.OPENED);

                if((field[playerY + 1][playerX].getStatus() == GameCellStatus.BOMB) || (field[playerY + 1][playerX].getStatus() == GameCellStatus.OPENED)) {
                    status = GameStatus.LOSE;
                    field[playerY + 1][playerX].setStatus(GameCellStatus.DEAD);
                    return;
                }
                field[tempY + 1][tempX].setStatus(GameCellStatus.PLAYER);

                playerY++;
                score += field[tempY + 1][tempX].getValue();
            }
        }
    }

    private GameCell[][] initializeField() {
        int rowCount = field.length;
        int colCount = field[0].length;

        return initializeField(rowCount, colCount, rowCount * colCount * 20 / 100 - 1);
    }

    private GameCell[][] initializeField(int rowCount, int colCount, int bombCount) {
        field = new GameCell[rowCount][colCount];

        playerX = random.nextInt(colCount);
        playerY = random.nextInt(rowCount);
        maxScore = 0;

        for(int i = 0; i < rowCount; i++) {
            for(int j = 0; j < colCount; j++) {
                int cellValue = random.nextInt(6) + 1;
                maxScore += cellValue;
                field[i][j] = new GameCell(cellValue, GameCellStatus.DEFAULT);
            }
        }


        field[playerY][playerX].setStatus(GameCellStatus.PLAYER);
        maxScore -= field[playerY][playerX].getValue();

        // Взял рандомайзер у Дмитрия Ивановича
        for (int i = 0; i < bombCount; i++) {
            int pos = random.nextInt(rowCount * colCount - i);
            one:
            for (int r = 0; r < rowCount; r++) {
                for (int c = 0; c < colCount; c++) {
                    if (field[r][c].getStatus() != GameCellStatus.BOMB && field[r][c].getStatus() != GameCellStatus.PLAYER) {
                        if (pos == 0) {
                            maxScore -= field[r][c].getValue();
                            field[r][c].setStatus(GameCellStatus.BOMB);
                            break one;
                        }
                        pos--;
                    }
                }
            }
        }

        return field;
    }

    private boolean isGameWon() {
        return score >= maxScore;
    }

    private boolean isCellValidate(int x, int y) {
        if(x < 0 || x > field[playerY].length - 1) {
            return false;
        }
        if(y < 0 || y > field.length - 1) {
            return false;
        }
        if(x > playerX + 1 || x < playerX - 1) {
            return false;
        }
        if(y > playerY + 1 || y < playerY - 1) {
            return false;
        }

        return true;
    }

    private StepVector getStepVector(int x, int y) {
        if(x == playerX + 1) {
            return StepVector.RIGHT;
        }
        if(x == playerX - 1) {
            return StepVector.LEFT;
        }
        if(y == playerY + 1) {
            return StepVector.DOWN;
        }
        return StepVector.UP;
    }
}
