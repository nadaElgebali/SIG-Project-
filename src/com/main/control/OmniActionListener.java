package com.main.control;

//importing
import com.main.model.MainHeaderHandler;
import com.main.model.HeaderTableEngine;
import com.main.model.MainLineHandler;
import com.main.model.LineTableEngine;
import com.main.view.GUIFrame;
import com.main.view.HDialogGen;
import com.main.view.LDialogGen;
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
    private HDialogGen mainHeaderDialog;
    private LDialogGen mainLineDialog;

    public OmniActionListener(GUIFrame uiFrame) {
        this.uiFrame = uiFrame;
    }

// switch to handle speific actions per button 
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Save Files":
                saveFile();
                break;

            case "Load Files":
                loadFiles();
                break;

            case "New Invoice":
                createInvoice();
                break;

            case "Delete Invoice":
                delInvoice();
                break;

            case "New Line":
                createNL();
                break;

            case "Delete Line":
                deleteL();
                break;

            case "newInvoiceOK":
                newInvDok();
                break;

            case "newInvoiceCancel":
                newInvDCancel();
                break;

            case "newLineCancel":
                newLDCancel();
                break;

            case "newLineOK":
                newLiDOk();
                break;
        }
    }

    

    //create invoice
    private void createInvoice() {
        mainHeaderDialog = new HDialogGen(uiFrame);
        mainHeaderDialog.setVisible(true);
    }
    
    //delete invoice
    private void delInvoice() {
        int selectedInvoiceIndex = uiFrame.getInvoiceHTable().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            uiFrame.getInvoicesArray().remove(selectedInvoiceIndex);
            uiFrame.getHeaderTableModel().fireTableDataChanged();

            uiFrame.getInvoiceLTable().setModel(new LineTableEngine(null));
            uiFrame.setLinesArray(null);
            uiFrame.getCstNameLbl().setText("");
            uiFrame.getInvoiceNumberLabel().setText("");
            uiFrame.getInvoiceTotalIabel().setText("");
            uiFrame.getInvoiceDateLabel().setText("");
        }
    }
    
    
    //create new line 
    private void createNL() {
        mainLineDialog = new LDialogGen(uiFrame);
        mainLineDialog.setVisible(true);
    }

    //delete line 
    private void deleteL() {
        int selectedLineIndex = uiFrame.getInvoiceLTable().getSelectedRow();
        int selectedInvoiceIndex = uiFrame.getInvoiceHTable().getSelectedRow();
        if (selectedLineIndex != -1) {
            uiFrame.getLinesArray().remove(selectedLineIndex);
            LineTableEngine lineTableModel = (LineTableEngine) uiFrame.getInvoiceLTable().getModel();
            lineTableModel.fireTableDataChanged();
            uiFrame.getInvoiceTotalIabel().setText("" + uiFrame.getInvoicesArray().get(selectedInvoiceIndex).getInvoiceTotal());
            uiFrame.getHeaderTableModel().fireTableDataChanged();
            uiFrame.getInvoiceHTable().setRowSelectionInterval(selectedInvoiceIndex, selectedInvoiceIndex);
        }
    }

    //save
    private void saveFile() {
        ArrayList<MainHeaderHandler> invoicesArray = uiFrame.getInvoicesArray();
        JFileChooser fc = new JFileChooser();
        //error handline try/catch
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

  
    //invoice dialog ok
    private void newInvDok() {
        mainHeaderDialog.setVisible(false);

        String custName = mainHeaderDialog.getCstNameField().getText();
        String str = mainHeaderDialog.getInvoiceDateField().getText();
        Date d = new Date();
        try {
            d = GUIFrame.dateFormat.parse(str);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(uiFrame, "Cannot parse date, resetting to today.", "Invalid date format", JOptionPane.ERROR_MESSAGE);
        }

        int invNum = 0;
        for (MainHeaderHandler inv : uiFrame.getInvoicesArray()) {
            if (inv.getNumber() > invNum) {
                invNum = inv.getNumber();
            }
        }
        invNum++;
        MainHeaderHandler newInv = new MainHeaderHandler(invNum, custName, d);
        uiFrame.getInvoicesArray().add(newInv);
        uiFrame.getHeaderTableModel().fireTableDataChanged();
        mainHeaderDialog.dispose();
        mainHeaderDialog = null;
    }

    //invoice dialog cancel
      private void newInvDCancel() {
        mainHeaderDialog.setVisible(false);
        mainHeaderDialog.dispose();
        mainHeaderDialog = null;
    }
    //line dialog ok 
    private void newLiDOk() {
        mainLineDialog.setVisible(false);

        String name = mainLineDialog.getItemNF().getText();
        String str1 = mainLineDialog.getItemCF().getText();
        String str2 = mainLineDialog.getItemPF().getText();
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
        int selectedInvHeader = uiFrame.getInvoiceHTable().getSelectedRow();
        if (selectedInvHeader != -1) {
            MainHeaderHandler invHeader = uiFrame.getInvoicesArray().get(selectedInvHeader);
            MainLineHandler line = new MainLineHandler(name, price, count, invHeader);
            uiFrame.getLinesArray().add(line);
            LineTableEngine lineTableModel = (LineTableEngine) uiFrame.getInvoiceLTable().getModel();
            lineTableModel.fireTableDataChanged();
            uiFrame.getHeaderTableModel().fireTableDataChanged();
        }
        uiFrame.getInvoiceHTable().setRowSelectionInterval(selectedInvHeader, selectedInvHeader);
        mainLineDialog.dispose();
        mainLineDialog = null;
    }
    
    //line dialog cancel 
    private void newLDCancel() {
        mainLineDialog.setVisible(false);
        mainLineDialog.dispose();
        mainLineDialog = null;
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
                uiFrame.getInvoiceHTable().setModel(headerTableModel);
                System.out.println("loading files");
            }
          //catch 
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(uiFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(uiFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
      