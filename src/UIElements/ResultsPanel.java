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
                {"VALUE", "DCV", "SCV", "Unit"},
                {"Volume of air vented in", resultsDCV.getVolumeOfAirVentedIn(), resultsSCV.getVolumeOfAirVentedIn(), "M^3"},
                {"Lowest temperature reached", resultsDCV.getLowestTemperatureReached(), resultsSCV.getLowestTemperatureReached(), "Celcius"},
                {"Time spent outside threshold temperature", resultsDCV.getTimeSpentOutsideThresholdTemperature(), resultsSCV.getTimeSpentOutsideThresholdTemperature(), "Celcius"},
                {"Heat energy used", resultsDCV.getHeatEnergyUsed(), resultsSCV.getHeatEnergyUsed(), "Joules"},
                {"Heater run time", resultsDCV.getHeaterRunTime(), resultsSCV.getHeaterRunTime(), "Minutes"},
                {"Cooling energy used", resultsDCV.getCoolingEnergyUsed(), resultsSCV.getCoolingEnergyUsed(), "Joules"},
                {"Cooling run time", resultsDCV.getCoolingRunTime(), resultsSCV.getCoolingRunTime(), "Minutes"},
                {"Time spent above CO threshold", resultsDCV.getTimeSpentAboveCOThreshold(), resultsSCV.getTimeSpentAboveCOThreshold(), "Minutes"},
                {"Time spent above CO2 threshold", resultsDCV.getTimeSpentAboveCO2Threshold(), resultsSCV.getTimeSpentAboveCO2Threshold(), "Minutes"},
                {"Time spent above VOC threshold", resultsDCV.getTimeSpentAboveVOCThreshold(), resultsSCV.getTimeSpentAboveVOCThreshold(), "Minutes"},
                {"Highest CO level", resultsDCV.getHighestCOLevel(), resultsSCV.getHighestCOLevel(), "PPM"},
                {"Highest CO2 level", resultsDCV.getHighestCO2Level(), resultsSCV.getHighestCO2Level(), "PPM"},
                {"Highest VOC level", resultsDCV.getHighestVOCLevel(), resultsSCV.getHighestVOCLevel(), "PPM"},
                {"CO vented", resultsDCV.getCOvented(), resultsSCV.getCOvented(), "PPM"},
                {"CO2 vented", resultsDCV.getCO2vented(), resultsSCV.getCO2vented(), "PPM"},
                {"VOC vented", resultsDCV.getVOCvented(), resultsSCV.getVOCvented(), "PPM"},
                {"Dehumidifier run time", resultsDCV.getDehumidifierRunTime(), resultsSCV.getDehumidifierRunTime(), "Minutes"},
                {"Dehumidifier energy used", resultsDCV.getDehumidifierEnergyUsed(), resultsSCV.getDehumidifierEnergyUsed(), "Joules"},
                {"Total water removed", resultsDCV.getTotalWaterRemoved(), resultsSCV.getTotalWaterRemoved(), "Mililitres"},
                {"Lowest relative humidity level", resultsDCV.getLowestRelativeHumidityLevel(), resultsSCV.getLowestRelativeHumidityLevel(), "Percent"},
                {"Highest relative humidity level", resultsDCV.getHighestRelativeHumidityLevel(), resultsSCV.getHighestRelativeHumidityLevel(), "Percent"},
                {"Time spent outside threshold humidity", resultsDCV.getTimeSpentOutisdeThresholdHumidity(), resultsSCV.getTimeSpentOutisdeThresholdHumidity(), "Minutes"},
                {"Humidifier energy used", resultsDCV.getHumidifierEnergyUsed(), resultsSCV.getHumidifierEnergyUsed(), "Joules"},
                {"Humidifier run time", resultsDCV.getHumidifierRunTime(), resultsSCV.getHumidifierRunTime(), "Minutes"},
                {"Water added", resultsDCV.getWaterAdded(), resultsSCV.getWaterAdded(), "Mililitres"},
                {"Total energy used", resultsDCV.getTotalEnergyUsed(), resultsSCV.getTotalEnergyUsed(), "Joules"}
        };
        return data;
    }
}
