package UIElements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

/**
 * Created by marcus on 04/04/2017.
 */
public class ConfigurationEditor extends JPanel {

    public static final int SCENARIO_EDITOR_WIDTH = 300;
    public static final int SCENARIO_EDITOR_HEIGHT= 740;
    public ConfigurationEditor(String configFilePath){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        this.setSize(new Dimension(SCENARIO_EDITOR_WIDTH, SCENARIO_EDITOR_HEIGHT));

        final String inputFilePath = configFilePath;
        final JEditorPane edPane = new JEditorPane();
//        edPane.setSize(new Dimension(SCENARIO_EDITOR_WIDTH, SCENARIO_EDITOR_HEIGHT));
        JScrollPane sPne = new JScrollPane(edPane);
        sPne.setSize(new Dimension(SCENARIO_EDITOR_WIDTH, SCENARIO_EDITOR_HEIGHT));
        sPne.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(sPne);

        JPanel jPanel = new JPanel();
//        Action Load = new AbstractAction() {
//
//            @Override
//            public void actionPerformed(ActionEvent event) {
//                try {
//                    load(edPane, inputFilePath);
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//            }
//        };
//
//        Load.putValue(Action.NAME, "Load");
//        JButton loadButton = new JButton(Load);
//        jPanel.add(loadButton);
        Action absActionSave = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    save(edPane, inputFilePath);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        };

        absActionSave.putValue(Action.NAME, "Save");
        JButton jButton = new JButton(absActionSave);
        jPanel.add(jButton);
        Action absActionClear = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent event) {
                edPane.setText("");
            }

        };

        absActionClear.putValue(Action.NAME, "Clear");
        JButton clearButton = new JButton(absActionClear);
        jPanel.add(clearButton);
        try {
            load(edPane, inputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.add(jPanel);
    }

    public static void save(JTextComponent text, String inputFile) throws Exception {
        FileWriter writer = null;
        writer = new FileWriter(inputFile);
        text.write(writer);
        writer.close();
    }

    public static void load(JTextComponent text, String inputFile) throws Exception {
        FileReader inputReader = null;
        inputReader = new FileReader(inputFile);
        text.read(inputReader, inputFile);
        inputReader.close();
    }

}
