package com.snook.gol.model;

public class StandardRule implements SimulationRule{
    @Override
    public CellState getNextState(int x, int y, Board board) {
        int aliveNeighbors = countAliveNeighbours(x, y, board);

        if(board.getState(x, y) == CellState.ALIVE) {
            if(aliveNeighbors < 2) {
                return CellState.DEAD;
            } else if( aliveNeighbors == 2 || aliveNeighbors == 3){
                return CellState.ALIVE;
            } else if (aliveNeighbors > 3) {
                return CellState.DEAD;
            }
        } else {
            if (aliveNeighbors == 3) {
                return CellState.ALIVE;
            }
        }
        return CellState.DEAD;
    }
    public int countAliveNeighbours(int x, int y, Board board) {
        int count = 0;

        count += countCell((x-1), (y-1), board);
        count += countCell(x, (y-1), board);
        count += countCell((x+1), (y-1), board);

        count += countCell((x-1), y, board);
        count += countCell((x+1), y, board);

        count += countCell((x-1), (y+1), board);
        count += countCell(x, (y+1), board);
        count += countCell((x+1), (y+1), board);

        return count;
    }

    private int countCell(int x, int y, Board board) {
        if(board.getState(x, y) == CellState.ALIVE) {
            return 1;
        } else{
            return 0;
        }
    }
}
