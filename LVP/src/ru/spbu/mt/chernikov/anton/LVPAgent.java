package ru.spbu.mt.chernikov.anton;

import jade.core.AID;
import jade.core.Agent;

import java.util.ArrayList;

public class LVPAgent extends Agent {
    private ArrayList<AID> neighbours;
    private double value;

    @Override
    protected void setup() {
        Object[] args = getArguments();

        this.value = (double) args[0];

        this.neighbours = new ArrayList<>();
        String[] id_neighbours = ((String) args[1]).split(" ");
        for (String id_neighbour : id_neighbours) {
            neighbours.add(new AID(id_neighbour, AID.ISLOCALNAME));
        }

        addBehaviour(new SendBehaviour(this, Const.timeout));
        addBehaviour(new WaitBehaviour(this, Const.timeout));
    }

    public ArrayList<AID> getNeighbours() {
        return this.neighbours;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
