package UIElements;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import metrics.Results;

import java.awt.*;


/**
 * Created by marcus on 04/04/2017.
 */
public class ResultsPanel extends JPanel{
    String[] headings = {"Result", "Value (DCV)", "Value (SCV)"};
    Results resultsDCV, resultsSCV;
    Object[][] data;
    JTable table;

    public ResultsPanel(Results resultsDCV, Results resultsSCV){
        this.resultsDCV = resultsDCV;
        this.resultsSCV = resultsSCV;
        Object[][] data = getUpdatedTable();
        JTable table = new JTable(new MyTableModel());
//        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setPreferredWidth(250);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        this.table = table;
        this.add(table);
        this.data = data;
    }

    public void updateTable(Results r1, Results r2){
        resultsDCV = r1;
        resultsSCV = r2;
        this.data = getUpdatedTable();
        System.out.println("Result from table update is: " + data[5][1]);

        table.repaint();
        ((AbstractTableModel) table.getModel()).fireTableDataChanged();
    }

    private class MyTableModel extends AbstractTableModel {

        private Object[][] data = getUpdatedTable();

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return headings.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return headings[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return getValueAt(0, columnIndex).getClass();
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
//            System.out.println("Callings");
            return getUpdatedTable()[rowIndex][columnIndex];
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//            data[rowIndex][columnIndex] = aValue;
//            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    private Object[][] getUpdatedTable(){
        Object[][] data = {
                {"VALUE", "DCV", "SCV"},
                {"Volume of air vented in", resultsDCV.getVolumeOfAirVentedIn(), resultsSCV.getVolumeOfAirVentedIn()},
                {"Lowest temperature reached", resultsDCV.getLowestTemperatureReached(), resultsSCV.getLowestTemperatureReached()},
                {"Time spent outside threshold temperature", resultsDCV.getTimeSpentOutsideThresholdTemperature(), resultsSCV.getTimeSpentOutsideThresholdTemperature()},
                {"Heat energy used", resultsDCV.getHeatEnergyUsed(), resultsSCV.getHeatEnergyUsed()},
                {"Heater run time", resultsDCV.getHeaterRunTime(), resultsSCV.getHeaterRunTime()},
                {"Cooling energy used", resultsDCV.getCoolingEnergyUsed(), resultsSCV.getCoolingEnergyUsed()},
                {"Cooling run time", resultsDCV.getCoolingRunTime(), resultsSCV.getCoolingRunTime()},
                {"Time spent above CO threshold", resultsDCV.getTimeSpentAboveCOThreshold(), resultsSCV.getTimeSpentAboveCOThreshold()},
                {"Time spent above CO2 threshold", resultsDCV.getTimeSpentAboveCO2Threshold(), resultsSCV.getTimeSpentAboveCO2Threshold()},
                {"Time spent above VOC threshold", resultsDCV.getTimeSpentAboveVOCThreshold(), resultsSCV.getTimeSpentAboveVOCThreshold()},
                {"Highest CO level", resultsDCV.getHighestCOLevel(), resultsSCV.getHighestCOLevel()},
                {"Highest CO2 level", resultsDCV.getHighestCO2Level(), resultsSCV.getHighestCO2Level()},
                {"Highest VOC level", resultsDCV.getHighestVOCLevel(), resultsSCV.getHighestVOCLevel()},
                {"CO vented", resultsDCV.getCOvented(), resultsSCV.getCOvented()},
                {"CO2 vented", resultsDCV.getCO2vented(), resultsSCV.getCO2vented()},
                {"VOC vented", resultsDCV.getVOCvented(), resultsSCV.getVOCvented()},
                {"Dehumidifier run time", resultsDCV.getDehumidifierRunTime(), resultsSCV.getDehumidifierRunTime()},
                {"Dehumidifier energy used", resultsDCV.getDehumidifierEnergyUsed(), resultsSCV.getDehumidifierEnergyUsed()},
                {"Total water removed", resultsDCV.getTotalWaterRemoved(), resultsSCV.getTotalWaterRemoved()},
                {"Lowest relative humidity level", resultsDCV.getLowestRelativeHumidityLevel(), resultsSCV.getLowestRelativeHumidityLevel()},
                {"Highest relative humidity level", resultsDCV.getHighestRelativeHumidityLevel(), resultsSCV.getHighestRelativeHumidityLevel()},
                {"Time spent outside threshold humidity", resultsDCV.getTimeSpentOutisdeThresholdHumidity(), resultsSCV.getTimeSpentOutisdeThresholdHumidity()},
                {"Humidifier energy used", resultsDCV.getHumidifierEnergyUsed(), resultsSCV.getHumidifierEnergyUsed()},
                {"Humidifier run time", resultsDCV.getHumidifierRunTime(), resultsSCV.getHumidifierRunTime()},
                {"Water added", resultsDCV.getWaterAdded(), resultsSCV.getWaterAdded()},
                {"Total energy used", resultsDCV.getTotalEnergyUsed(), resultsSCV.getTotalEnergyUsed()}
        };
        return data;
    }
}
