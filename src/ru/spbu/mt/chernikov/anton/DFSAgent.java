package ru.spbu.mt.chernikov.anton;

import jade.core.AID;
import jade.core.Agent;

import java.util.ArrayList;

/**
 * Describes the behavior of an agent for calculating the sum of the conceived values and
 * the number of agents in a current connected component
 * */
public class DFSAgent extends Agent {
    private ArrayList<AID> neighbours;
    private Pair<Integer, Double> stats = new Pair<>(1, 0.0);

    @Override
    protected void setup() {
        Object[] args = getArguments();

        this.stats = new Pair<>(1, (Double) args[0]);

        this.neighbours = new ArrayList<>();
        String[] id_neighbours = ((String) args[1]).split(" ");
        for (String id_neighbour : id_neighbours) {
            neighbours.add(new AID(id_neighbour, AID.ISLOCALNAME));
        }

        addBehaviour(new DFSBehaviour(this));
    }

    public ArrayList<AID> getNeighbours() {
        return this.neighbours;
    }

    public Pair<Integer, Double> getStats() {
        return this.stats;
    }
}