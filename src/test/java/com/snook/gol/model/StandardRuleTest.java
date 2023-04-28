package com.snook.gol.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandardRuleTest {

    private Board board;
    private SimulationRule simulationRule;
    @BeforeEach
    void setUp() {
        board = new BoundedBoard(3, 3);
        simulationRule = new StandardRule();
    }

    @Test
    void getNextState_Alive_noNeighbour() {
        board.setState(1, 1, CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1, 1, board);

        assertEquals(CellState.DEAD, nextState);
    }
    @Test
    void getNextState_Alive_oneNeighbour() {
         board.setState(1, 1, CellState.ALIVE);
         board.setState(0, 0, CellState.ALIVE);


        CellState nextState = simulationRule.getNextState(1, 1, board);

        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_Alive_twoNeighbour() {
        board.setState(1, 1, CellState.ALIVE);
        board.setState(2, 2, CellState.ALIVE);
        board.setState(1, 2, CellState.ALIVE);


        CellState nextState = simulationRule.getNextState(1, 1, board);

        assertEquals(CellState.ALIVE, nextState);
    }

    @Test
    void getNextState_Alive_threeNeighbour() {
        board.setState(1, 1, CellState.ALIVE);

        board.setState(2, 2, CellState.ALIVE);
        board.setState(1, 2, CellState.ALIVE);
        board.setState(0, 0, CellState.ALIVE);


        CellState nextState = simulationRule.getNextState(1, 1, board);

        assertEquals(CellState.ALIVE, nextState);
    }


    @Test
    void getNextState_Alive_fourNeighbour() {
        board.setState(1, 1, CellState.ALIVE);

        board.setState(2, 2, CellState.ALIVE);
        board.setState(1, 2, CellState.ALIVE);
        board.setState(0, 0, CellState.ALIVE);
        board.setState(0, 2, CellState.ALIVE);


        CellState nextState = simulationRule.getNextState(1, 1, board);

        assertEquals(CellState.DEAD, nextState);
    }
    @Test
    void getNextState_Alive_eightNeighbour() {

        board.setState(0, 0, CellState.ALIVE);
        board.setState(0, 1, CellState.ALIVE);
        board.setState(0, 2, CellState.ALIVE);
        board.setState(1, 0, CellState.ALIVE);
        board.setState(1, 1, CellState.ALIVE);
        board.setState(1, 2, CellState.ALIVE);
        board.setState(2, 0, CellState.ALIVE);
        board.setState(2, 1, CellState.ALIVE);
        board.setState(2, 2, CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1, 1, board);

        assertEquals(CellState.DEAD, nextState);
    }


    @Test
    void getNextState_Dead_noNeighbour() {
        board.setState(1, 1, CellState.DEAD);

        CellState nextState = simulationRule.getNextState(1, 1, board);

        assertEquals(CellState.DEAD, nextState);
    }


    @Test
    void getNextState_Dead_oneNeighbour() {

        board.setState(1, 1, CellState.DEAD);
        board.setState(0, 0, CellState.ALIVE);


        CellState nextState = simulationRule.getNextState(1, 1, board);

        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_Dead_twoNeighbour() {

        board.setState(1, 1, CellState.DEAD);
        board.setState(0, 1, CellState.ALIVE);
        board.setState(0, 2, CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1, 1, board);

        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_Dead_threeNeighbour() {

        board.setState(1, 1, CellState.DEAD);
        board.setState(0, 0, CellState.ALIVE);
        board.setState(0, 1, CellState.ALIVE);
        board.setState(0, 2, CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1, 1, board);

        assertEquals(CellState.ALIVE, nextState);
    }

    @Test
    void getNextState_Dead_fourNeighbour() {

        board.setState(1, 1, CellState.DEAD);
        board.setState(0, 0, CellState.ALIVE);
        board.setState(0, 1, CellState.ALIVE);
        board.setState(0, 2, CellState.ALIVE);
        board.setState(1, 0, CellState.ALIVE);


        CellState nextState = simulationRule.getNextState(1, 1, board);

        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_Dead_eightNeighbour() {

        board.setState(1, 1, CellState.DEAD);
        board.setState(0, 0, CellState.ALIVE);
        board.setState(0, 1, CellState.ALIVE);
        board.setState(0, 2, CellState.ALIVE);
        board.setState(1, 0, CellState.ALIVE);
        board.setState(1, 2, CellState.ALIVE);
        board.setState(2, 0, CellState.ALIVE);
        board.setState(2, 1, CellState.ALIVE);
        board.setState(2, 2, CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1, 1, board);

        assertEquals(CellState.DEAD, nextState);
    }

}