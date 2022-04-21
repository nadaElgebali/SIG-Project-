package com.main.control;

//imports
import com.main.model.MainHeaderHandler;
import com.main.model.MainLineHandler;
import com.main.model.LineTableEngine;
import com.main.view.GUIFrame;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Nada ElGebali
 */

//class and constructor
public class MainTableSelectionListener implements ListSelectionListener {

    private GUIFrame uiFrame;

    public MainTableSelectionListener(GUIFrame uiFrame) {
        this.uiFrame = uiFrame;
    }

    @Override
    //correlating invoice to uiframe
    public void valueChanged(ListSelectionEvent e) {
        int selectedInvIndex = uiFrame.getInvoiceHTable().getSelectedRow();
        System.out.println("Invoice loaded successfully: " + selectedInvIndex);
        if (selectedInvIndex != -1) {
            MainHeaderHandler selectedInv = uiFrame.getInvoicesArray().get(selectedInvIndex);
            ArrayList<MainLineHandler> lines = selectedInv.getLines();
            LineTableEngine lineTableModel = new LineTableEngine(lines);
            uiFrame.setLinesArray(lines);
            uiFrame.getInvoiceLTable().setModel(lineTableModel);
            uiFrame.getCstNameLbl().setText(selectedInv.getCustomer());
            uiFrame.getInvoiceNumberLabel().setText("" + selectedInv.getNumber());
            uiFrame.getInvoiceTotalIabel().setText("" + selectedInv.getInvoiceTotal());
            uiFrame.getInvoiceDateLabel().setText(GUIFrame.dateFormat.format(selectedInv.getInvoiceDate()));
        }
    }

}
