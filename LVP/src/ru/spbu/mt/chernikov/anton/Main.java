package ru.spbu.mt.chernikov.anton;

public class Main {
    public static void main(String[] args) {
        MeanController meanController = new MeanController();
        boolean[][] adjacencyMatrix = {{false, true,  false, true},
                                       {true,  false, true,  false},
                                       {false, true,  false, true},
                                       {true,  false, true,  false}};
        double[] values = {1.0, 2.0, 3.0, 4.0};

        meanController.setAgents(values, adjacencyMatrix);
    }
}
