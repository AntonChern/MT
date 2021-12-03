package ru.spbu.mt.chernikov.anton;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

public class DFSBehaviour extends Behaviour {
    private AID waiting;
    private final DFSAgent myAgent;
    private boolean done;

    public DFSBehaviour(DFSAgent agent) {
        this.myAgent = agent;
        this.done = false;
    }

    @Override
    public void action() {
        boolean is_main = myAgent.getLocalName().equals("0");
        if (!is_main) {
            ACLMessage msg = myAgent.receive();
            while (msg == null) {
                msg = myAgent.receive();
            }
            this.waiting = msg.getSender();
        }

        ArrayList<AID> neighbours = myAgent.getNeighbours();
        for (AID neighbour : neighbours) {
            ACLMessage query = new ACLMessage(ACLMessage.CFP);
            query.addReceiver(neighbour);
            query.setContent("query");
            myAgent.send(query);

            String flag = "query";
            String content = "";
            while (flag.equals("query")) {
                ACLMessage question = myAgent.receive();
                while (question == null) {
                    question = myAgent.receive();
                }
                content = question.getContent();
                flag = content.substring(0, 5);

                if (flag.equals("query")) {
                    ACLMessage answer = new ACLMessage(ACLMessage.CFP);
                    answer.addReceiver(question.getSender());
                    answer.setContent("negat");
                    myAgent.send(answer);
                }
            }
            if (flag.equals("posit")) {
                String[] pair = content.substring(5).split(" ");
                int number = Integer.parseInt(pair[0]);
                double value = Double.parseDouble(pair[1]);
                myAgent.getStats().setFirst(myAgent.getStats().getFirst() + number);
                myAgent.getStats().setSecond(myAgent.getStats().getSecond() + value);
            }
        }

        if (!is_main) {
            ACLMessage result = new ACLMessage(ACLMessage.CFP);
            result.addReceiver(waiting);
            result.setContent("posit" + myAgent.getStats().getFirst() + " " + myAgent.getStats().getSecond());
            myAgent.send(result);

            myAgent.addBehaviour(new DoneBehaviour());
        } else {
            System.out.println((double) myAgent.getStats().getSecond() / myAgent.getStats().getFirst());
        }
        done = true;
    }

    @Override
    public boolean done() {
        return done;
    }
}
