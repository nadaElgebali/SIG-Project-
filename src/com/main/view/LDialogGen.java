
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

//creating the line dialog 
public class LDialogGen extends JDialog{
    private JTextField itemNF;
    private JTextField itemCF;
    private JTextField itemPF;
    private JLabel itemNameLabel;
    private JLabel itemCountLabel;
    private JLabel itemPriceLabel;
    private JButton okButton;
    private JButton cancelButton;
    
    public LDialogGen(GUIFrame frame) {
        itemNF = new JTextField(20);
        itemNameLabel = new JLabel("Item Name");
        
        itemCF = new JTextField(20);
        itemCountLabel = new JLabel("Item Count");
        
        itemPF = new JTextField(20);
        itemPriceLabel = new JLabel("Item Price");
        
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        
        okButton.setActionCommand("newLineOK");
        cancelButton.setActionCommand("newLineCancel");
        
        okButton.addActionListener(frame.getActionListener());
        cancelButton.addActionListener(frame.getActionListener());
        setLayout(new GridLayout(4, 2));
        
        add(itemNameLabel);
        add(itemNF);
        add(itemCountLabel);
        add(itemCF);
        add(itemPriceLabel);
        add(itemPF);
        add(okButton);
        add(cancelButton);
        
        pack();
    }

    public JTextField getItemNF() {
        return itemNF;
    }

    public JTextField getItemCF() {
        return itemCF;
    }

    public JTextField getItemPF() {
        return itemPF;
    }
}
