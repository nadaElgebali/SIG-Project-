package com.sig.controller;

//importing
import com.sig.model.MainHeaderHandler;
import com.sig.model.HeaderTableEngine;
import com.sig.model.MainLineHandler;
import com.sig.model.LineTableEngine;
import com.sig.view.GUIFrame;
import com.sig.view.HDialogGen;
import com.sig.view.LDialogGen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Nada ElGebali
 */

//class and constructor 
public class OmniActionListener implements ActionListener {

    private GUIFrame uiFrame;
    private HDialogGen headerDialog;
    private LDialogGen lineDialog;

    public OmniActionListener(GUIFrame uiFrame) {
        this.uiFrame = uiFrame;
    }

// switch to handle speific actions per button 
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Save Files":
                saveFiles();
                break;

            case "Load Files":
                loadFiles();
                break;

            case "New Invoice":
                createNewInvoice();
                break;

            case "Delete Invoice":
                deleteInvoice();
                break;

            case "New Line":
                createNewLine();
                break;

            case "Delete Line":
                deleteLine();
                break;

            case "newInvoiceOK":
                newInvoiceDialogOK();
                break;

            case "newInvoiceCancel":
                newInvoiceDialogCancel();
                break;

            case "newLineCancel":
                newLineDialogCancel();
                break;

            case "newLineOK":
                newLineDialogOK();
                break;
        }
    }

    //load files 
    private void loadFiles() {
        JFileChooser fileChooser = new JFileChooser();
        //try and catch to avoid IO ex List<String> and ParseException
        try {
            int container = fileChooser.showOpenDialog(uiFrame);
            //separating ok from cancel  
            if (container == JFileChooser.APPROVE_OPTION) {
                File headerFile = fileChooser.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                ArrayList<MainHeaderHandler> invoiceHeaders = new ArrayList<>();
                //looping and splitting 
                for (String headerLine : headerLines) {
                    String[] raw = headerLine.split(",");
                    String sp1 = raw[0];
                    String sp2 = raw[1];
                    String sp3 = raw[2];
                    int code = Integer.parseInt(sp1);
                    Date invoiceDate = GUIFrame.dateFormat.parse(sp2);
                    MainHeaderHandler header = new MainHeaderHandler(code, sp3, invoiceDate);
                    invoiceHeaders.add(header);
                }
                uiFrame.setInvoicesArray(invoiceHeaders);
                //looping and splitting
                container = fileChooser.showOpenDialog(uiFrame);
                if (container == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fileChooser.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    ArrayList<MainLineHandler> invoiceLines = new ArrayList<>();
                    for (String lineLine : lineLines) {
                        String[] raw = lineLine.split(",");
                        String sp1 = raw[0];    
                        String sp2 = raw[1];    
                        String sp3 = raw[2];    
                        String sp4 = raw[3];    
                        int invCode = Integer.parseInt(sp1);
                        double price = Double.parseDouble(sp3);
                        int count = Integer.parseInt(sp4);
                        MainHeaderHandler inv = uiFrame.getInvObject(invCode);
                        MainLineHandler line = new MainLineHandler(sp2, price, count, inv);
                        inv.getLines().add(line);
                    }
                }
                HeaderTableEngine headerTableModel = new HeaderTableEngine(invoiceHeaders);
                uiFrame.setHeaderTableModel(headerTableModel);
                uiFrame.getInvHTbl().setModel(headerTableModel);
                System.out.println("loading files");
            }
          //catch 
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(uiFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(uiFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createNewInvoice() {
        headerDialog = new HDialogGen(uiFrame);
        headerDialog.setVisible(true);
    }

    private void deleteInvoice() {
        int selectedInvoiceIndex = uiFrame.getInvHTbl().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            uiFrame.getInvoicesArray().remove(selectedInvoiceIndex);
            uiFrame.getHeaderTableModel().fireTableDataChanged();

            uiFrame.getInvLTbl().setModel(new LineTableEngine(null));
            uiFrame.setLinesArray(null);
            uiFrame.getCustNameLbl().setText("");
            uiFrame.getInvNumLbl().setText("");
            uiFrame.getInvTotalIbl().setText("");
            uiFrame.getInvDateLbl().setText("");
        }
    }

    private void createNewLine() {
        lineDialog = new LDialogGen(uiFrame);
        lineDialog.setVisible(true);
    }

    private void deleteLine() {
        int selectedLineIndex = uiFrame.getInvLTbl().getSelectedRow();
        int selectedInvoiceIndex = uiFrame.getInvHTbl().getSelectedRow();
        if (selectedLineIndex != -1) {
            uiFrame.getLinesArray().remove(selectedLineIndex);
            LineTableEngine lineTableModel = (LineTableEngine) uiFrame.getInvLTbl().getModel();
            lineTableModel.fireTableDataChanged();
            uiFrame.getInvTotalIbl().setText("" + uiFrame.getInvoicesArray().get(selectedInvoiceIndex).getInvoiceTotal());
            uiFrame.getHeaderTableModel().fireTableDataChanged();
            uiFrame.getInvHTbl().setRowSelectionInterval(selectedInvoiceIndex, selectedInvoiceIndex);
        }
    }

    private void saveFiles() {
        ArrayList<MainHeaderHandler> invoicesArray = uiFrame.getInvoicesArray();
        JFileChooser fc = new JFileChooser();
        try {
            int result = fc.showSaveDialog(uiFrame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                String headers = "";
                String lines = "";
                for (MainHeaderHandler invoice : invoicesArray) {
                    headers += invoice.toString();
                    headers += "\n";
                    for (MainLineHandler line : invoice.getLines()) {
                        lines += line.toString();
                        lines += "\n";
                    }
                }
                //  welcome 0-6 1-7 gen
                headers = headers.substring(0, headers.length()-1);
                lines = lines.substring(0, lines.length()-1);
                result = fc.showSaveDialog(uiFrame);
                File lineFile = fc.getSelectedFile();
                FileWriter lfw = new FileWriter(lineFile);
                hfw.write(headers);
                lfw.write(lines);
                hfw.close();
                lfw.close();
            }
            // try catch 
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(uiFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void newInvoiceDialogCancel() {
        headerDialog.setVisible(false);
        headerDialog.dispose();
        headerDialog = null;
    }

    private void newInvoiceDialogOK() {
        headerDialog.setVisible(false);

        String custName = headerDialog.getCustNameField().getText();
        String str = headerDialog.getInvDateField().getText();
        Date d = new Date();
        try {
            d = GUIFrame.dateFormat.parse(str);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(uiFrame, "Cannot parse date, resetting to today.", "Invalid date format", JOptionPane.ERROR_MESSAGE);
        }

        int invNum = 0;
        for (MainHeaderHandler inv : uiFrame.getInvoicesArray()) {
            if (inv.getNum() > invNum) {
                invNum = inv.getNum();
            }
        }
        invNum++;
        MainHeaderHandler newInv = new MainHeaderHandler(invNum, custName, d);
        uiFrame.getInvoicesArray().add(newInv);
        uiFrame.getHeaderTableModel().fireTableDataChanged();
        headerDialog.dispose();
        headerDialog = null;
    }

    private void newLineDialogCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void newLineDialogOK() {
        lineDialog.setVisible(false);

        String name = lineDialog.getItemNameField().getText();
        String str1 = lineDialog.getItemCountField().getText();
        String str2 = lineDialog.getItemPriceField().getText();
        int count = 1;
        double price = 1;
        try {
            count = Integer.parseInt(str1);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(uiFrame, "Cannot convert number", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }

        try {
            price = Double.parseDouble(str2);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(uiFrame, "Cannot convert price", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }
        int selectedInvHeader = uiFrame.getInvHTbl().getSelectedRow();
        if (selectedInvHeader != -1) {
            MainHeaderHandler invHeader = uiFrame.getInvoicesArray().get(selectedInvHeader);
            MainLineHandler line = new MainLineHandler(name, price, count, invHeader);
            uiFrame.getLinesArray().add(line);
            LineTableEngine lineTableModel = (LineTableEngine) uiFrame.getInvLTbl().getModel();
            lineTableModel.fireTableDataChanged();
            uiFrame.getHeaderTableModel().fireTableDataChanged();
        }
        uiFrame.getInvHTbl().setRowSelectionInterval(selectedInvHeader, selectedInvHeader);
        lineDialog.dispose();
        lineDialog = null;
    }

}
