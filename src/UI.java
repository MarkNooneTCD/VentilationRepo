import UIElements.ConfigurationPanel;
import UIElements.ResultsPanel;
import UIElements.ScenarioEditor;
import UIElements.SimulationCommandPrompt;
import metrics.Results;

import javax.swing.*;
import java.awt.*;

/**
 * Created by marcus on 04/04/2017.
 */
public class UI {
    public static int WINDOW_WIDTH = 900;
    public static int WINDOW_HEIGHT = 600;

    public static void main(String args[]){
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Ventilation Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //A panel that allows you to edit and change any configuration options
        ConfigurationPanel configPanel = new ConfigurationPanel();

        //A panel that allows you to edit and save any scenarios
        ScenarioEditor scenarioEditor = new ScenarioEditor(Simulation.SCENARIO_CONFIG_FILE_NAME);

        //A panel that will act as the clients requested command prompt
        SimulationCommandPrompt commandPrompt = new SimulationCommandPrompt();

        //A results panel that will clearly explain results.
//        ResultsPanel resultsPanel = new ResultsPanel(new Results());

        //Add the necessary JFrames
//        JPanel container = new JPanel();
//        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
//        container.add(configPanel);
//        container.add(scenarioEditor);
//        container.add(commandPrompt);
//        container.add(resultsPanel);

        //Display the window.
//        frame.setContentPane(resultsPanel);
        frame.pack();
        frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        frame.setVisible(true);
    }
}
