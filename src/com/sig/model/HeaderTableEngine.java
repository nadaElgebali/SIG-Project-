
package com.sig.model;

import com.sig.view.GUIFrame;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Nada ElGebali
 */
public class HeaderTableEngine extends AbstractTableModel {

    private ArrayList<MainHeaderHandler> invoicesArray;
    private String[] columns = {"Invoice Number", "Invoice Date", "Customer Name", "Grand Total"};
    
    public HeaderTableEngine(ArrayList<MainHeaderHandler> invoicesArray) {
        this.invoicesArray = invoicesArray;
    }

    @Override
    public int getRowCount() {
        return invoicesArray.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MainHeaderHandler inv = invoicesArray.get(rowIndex);
        switch (columnIndex) {
            case 0: return inv.getNum();
            case 1: return GUIFrame.dateFormat.format(inv.getInvDate());
            case 2: return inv.getCustomer();
            case 3: return inv.getInvoiceTotal();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
