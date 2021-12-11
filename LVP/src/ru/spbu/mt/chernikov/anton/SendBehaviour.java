package ru.spbu.mt.chernikov.anton;

import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Random;

public class SendBehaviour extends TickerBehaviour {
    private final LVPAgent myAgent;

    public SendBehaviour(LVPAgent a, long period) {
        super(a, period);
        myAgent = a;
    }

    @Override
    protected void onTick() {
        ArrayList<AID> neighbours = myAgent.getNeighbours();

        Random rand = new Random();
        for (AID neighbour : neighbours) {
            ACLMessage message = new ACLMessage(ACLMessage.CFP);
            message.addReceiver(neighbour);
            double noise = 2 * Const.bound * rand.nextDouble() - Const.bound;
            message.setContent(String.valueOf(myAgent.getValue() + noise));
            if (rand.nextDouble() < Const.sended) {
                if (rand.nextDouble() < Const.delayed) {
                    myAgent.doWait(Const.timeout);
                }
                myAgent.send(message);
            }
        }
    }
}
