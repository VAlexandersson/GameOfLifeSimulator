package com.snook.gol;

import com.snook.gol.model.Board;
import com.snook.gol.model.CellState;
import com.snook.gol.model.SimulationRule;

public class Simulation {
    private Board simulationBoard;
    private final SimulationRule simulationRule;

    public Simulation(Board simulationBoard, SimulationRule simulationRule) {
        this.simulationBoard = simulationBoard;
        this.simulationRule = simulationRule;
    }

    public void step() {
        Board nextState = simulationBoard.copy();

        for (int y = 0; y < simulationBoard.getHeight(); y++) {
            for (int x = 0; x < simulationBoard.getWidth(); x++) {
                CellState newState = simulationRule.getNextState(x, y, simulationBoard);
                nextState.setState(x, y, newState);

            }
        }
        this.simulationBoard = nextState;
    }

    public Board getBoard() {
        return simulationBoard;
    }
}