package com.snook;

public class Simulation {

    public static int DEAD = 0;
    public static int ALIVE = 1;

    int width;
    int height;
    int[][] board;

    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
    }

    public static Simulation copy(Simulation simulation) {
        Simulation simCopy = new Simulation(simulation.width, simulation.height);
        for (int y = 0; y < simulation.height; y++) {
            for (int x = 0; x < simulation.width; x++) {
                simCopy.setState(x, y, simulation.getState(x, y));
            }
        }
        return simCopy;
    }


    public void printBoard() {
        System.out.println("---");
        for (int y = 0; y < height; y++) {
            StringBuilder line = new StringBuilder("|");
            for (int x = 0; x < width; x++) {
                if(this.board[x][y] == DEAD) {
                    line.append(".");
                } else {
                    line.append("*");
                }
            }
            line.append("|");
            System.out.println(line);
        }
        System.out.println("---\n");
    }

    public void setAlive(int x, int y) {
        this.setState(x, y, ALIVE);
    }

    public void setDead(int x, int y) {
        this.setState(x, y, DEAD);
    }

    public void setState(int x, int y, int state) {
        if(x < 0 || x >= width) {
            return;
        }
        if(y < 0 || y >= height) {
            return;
        }
        this.board[x][y] = state;
    }



    public int countAliveNeighbours(int x, int y) {
        int count = 0;

        count += getState(x-1, y-1);
        count += getState(x, y-1);
        count += getState(x+1, y-1);

        count += getState(x-1, y);
        count += getState(x+1, y);

        count += getState(x-1, y+1);
        count += getState(x, y+1);
        count += getState(x+1, y+1);

        return count;
    }

    public int getState(int x, int y) {
        if(x < 0 || x >= width) {
            return DEAD;
        }
        if(y < 0 || y >= height) {
            return DEAD;
        }
        return this.board[x][y];
    }


    public void step() {
        int[][] newBoard = new int[width][height];


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int aliveNeighbors = countAliveNeighbours(x, y);

                if(getState(x, y) == ALIVE) {
                    if(aliveNeighbors < 2) {
                        newBoard[x][y] = DEAD;
                    } else if( aliveNeighbors == 2 || aliveNeighbors == 3){
                        newBoard[x][y] = ALIVE;
                    } else if (aliveNeighbors > 3) {
                        newBoard[x][y] = DEAD;
                    }
                } else {
                    if (aliveNeighbors == 3) {
                        newBoard[x][y] = ALIVE;
                    }
                }
            }
        }
        this.board = newBoard;
    }
}