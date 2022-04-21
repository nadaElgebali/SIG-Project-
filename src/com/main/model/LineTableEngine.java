
package com.main.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Nada ElGebali
 */
public class LineTableEngine extends AbstractTableModel {

    private ArrayList<MainLineHandler> linesArray;
    private String[] columns = {"Name", "Price", "Count", "Total"};

    public LineTableEngine(ArrayList<MainLineHandler> linesArray) {
        this.linesArray = linesArray;
    }

    @Override
    public int getRowCount() {
        return linesArray == null ? 0 : linesArray.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (linesArray == null) {
            return "";
        } else {
            MainLineHandler line = linesArray.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return line.getName();
                case 1:
                    return line.getPrice();
                case 2:
                    return line.getCount();
                case 3:
                    return line.getLineTotal();
                default:
                    return "";
            }
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

}
