package ru.spbu.mt.chernikov.anton;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class MeanController {
    private static final String MAIN_HOST = "127.0.0.1";
    private static final String MAIN_PORT = "49001";
    private static final String CLASS_NAME = "ru.spbu.mt.chernikov.anton.LVPAgent";
    private static final String GUI = "true";

    private ContainerController buildController() {
        Runtime runtime = Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, MAIN_HOST);
        profile.setParameter(Profile.MAIN_PORT, MAIN_PORT);
        profile.setParameter(Profile.GUI, GUI);

        return runtime.createMainContainer(profile);
    }

    public void setAgents(double[] values, boolean[][] adjacencyMatrix) {
        ContainerController containerController = buildController();

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            StringBuilder neighbours = new StringBuilder();
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j]) {
                    neighbours.append(j).append(" ");
                }
            }
            Object[] args = new Object[] {values[i], neighbours.toString()};
            try {
                AgentController agentController = containerController.createNewAgent(String.valueOf(i), CLASS_NAME, args);
                agentController.start();
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }
    }
}
