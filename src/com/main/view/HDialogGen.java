
package com.main.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Nada ElGebali
 */

//Creating the Header dialog 
public class HDialogGen extends JDialog {
    private JTextField cstNameField;
    private JTextField invoiceDateField;
    private JLabel cstNameLbl;
    private JLabel invoiceDateLbl;
    private JButton okButton;
    private JButton cancelButton;

    public HDialogGen(GUIFrame frame) {
        cstNameLbl = new JLabel("Customer Name:");
        cstNameField = new JTextField(20);
        invoiceDateLbl = new JLabel("Invoice Date:");
        invoiceDateField = new JTextField(20);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        
        okButton.setActionCommand("newInvoiceOK");
        cancelButton.setActionCommand("newInvoiceCancel");
        
        okButton.addActionListener(frame.getActionListener());
        cancelButton.addActionListener(frame.getActionListener());
        setLayout(new GridLayout(3, 2));
        
        add(invoiceDateLbl);
        add(invoiceDateField);
        add(cstNameLbl);
        add(cstNameField);
        add(okButton);
        add(cancelButton);
        
        pack();
        
    }

    public JTextField getCstNameField() {
        return cstNameField;
    }

    public JTextField getInvoiceDateField() {
        return invoiceDateField;
    }
    
}
