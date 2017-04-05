import UIElements.*;
import metrics.Results;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by marcus on 04/04/2017.
 */
public class UI {
    public static int WINDOW_WIDTH = 1700;
    public static int WINDOW_HEIGHT = 600;
    public static final String CONFIG_FILE_NAME = "config.json";
    public static final String ENVIRONMENT_CONFIG_FILE_NAME = "data2016.csv";
    public static final String SCENARIO_CONFIG_FILE_NAME = "scenarios.json";

    public static void main(String args[]){
        final Results r1 = new Results();
        final Results r2 = new Results();

        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Ventilation Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //A panel that allows you to edit and change any configuration options
        ConfigurationEditor configEditor = new ConfigurationEditor(CONFIG_FILE_NAME);

        //A panel that allows you to edit and save any scenarios
        ScenarioEditor scenarioEditor = new ScenarioEditor(SCENARIO_CONFIG_FILE_NAME);

        //A panel that will act as the clients requested command prompt
        SimulationCommandPrompt commandPrompt = new SimulationCommandPrompt();

        //A results panel that will clearly explain results.
        ResultsPanel resultsPanel = new ResultsPanel(r1, r2);

        //Add the necessary JFrames
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        JPanel resultsAndComamndContainer = new JPanel();
        resultsAndComamndContainer.setLayout(new BoxLayout(resultsAndComamndContainer, BoxLayout.Y_AXIS));
        resultsAndComamndContainer.add(resultsPanel);
        Action absActionRun = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent event) {
                Simulation sim = new Simulation(CONFIG_FILE_NAME, ENVIRONMENT_CONFIG_FILE_NAME, SCENARIO_CONFIG_FILE_NAME);
                while(sim.hasSimulated != true){
                }
                resultsPanel.updateTable(sim.getDcv().results, sim.getScv().results);
            }
        };
        absActionRun.putValue(Action.NAME, "Run Sim");
        JButton jButton = new JButton(absActionRun);
        resultsAndComamndContainer.add(jButton);

        container.add(resultsAndComamndContainer);
        container.add(configEditor);
        container.add(scenarioEditor);
//        container.add(commandPrompt);


        //Display the window.
        frame.setContentPane(container);
        frame.pack();
        frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        frame.setVisible(true);
    }
}
