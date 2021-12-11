package ru.spbu.mt.chernikov.anton;

import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

public class WaitBehaviour extends TickerBehaviour {
    private final LVPAgent myAgent;
    private int iteration = 0;

    public WaitBehaviour(LVPAgent a, long period) {
        super(a, period);
        myAgent = a;
    }

    @Override
    protected void onTick() {
        ArrayList<AID> neighbours = myAgent.getNeighbours();

        double value = myAgent.getValue();
        double res = 0.0;
        for (AID neighbour : neighbours) {
            ACLMessage msg = null;
            long cur_time = System.currentTimeMillis();
            long time = 0;
            while (msg == null && time < Const.timeout) {
                msg = myAgent.receive();
                time = System.currentTimeMillis() - cur_time;
            }
            if (msg != null) {
                res += Double.parseDouble(msg.getContent()) - value;
            }
        }
        double new_value = value + Const.alpha * res;
        myAgent.setValue(new_value);

//        if (Math.abs(new_value - value) < Const.epsilon) {
//            this.stop();
//            System.out.println(myAgent.getLocalName() + ": " + new_value);
//        }
        iteration++;
        if (iteration == Const.iterations) {
            this.stop();
            System.out.println(myAgent.getLocalName() + ": " + new_value);
        }
    }
}