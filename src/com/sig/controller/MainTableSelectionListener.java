package com.sig.controller;

//imports
import com.sig.model.MainHeaderHandler;
import com.sig.model.MainLineHandler;
import com.sig.model.LineTableEngine;
import com.sig.view.GUIFrame;
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
        int selectedInvIndex = uiFrame.getInvHTbl().getSelectedRow();
        System.out.println("Invoice loaded successfully: " + selectedInvIndex);
        if (selectedInvIndex != -1) {
            MainHeaderHandler selectedInv = uiFrame.getInvoicesArray().get(selectedInvIndex);
            ArrayList<MainLineHandler> lines = selectedInv.getLines();
            LineTableEngine lineTableModel = new LineTableEngine(lines);
            uiFrame.setLinesArray(lines);
            uiFrame.getInvLTbl().setModel(lineTableModel);
            uiFrame.getCustNameLbl().setText(selectedInv.getCustomer());
            uiFrame.getInvNumLbl().setText("" + selectedInv.getNum());
            uiFrame.getInvTotalIbl().setText("" + selectedInv.getInvoiceTotal());
            uiFrame.getInvDateLbl().setText(GUIFrame.dateFormat.format(selectedInv.getInvDate()));
        }
    }

}
