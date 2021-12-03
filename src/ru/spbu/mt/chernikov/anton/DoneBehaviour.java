package ru.spbu.mt.chernikov.anton;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class DoneBehaviour extends CyclicBehaviour {

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        while (msg == null) {
            msg = myAgent.receive();
        }
        ACLMessage answer = new ACLMessage(ACLMessage.CFP);
        answer.addReceiver(msg.getSender());
        answer.setContent("negat");
        myAgent.send(answer);
    }
}
