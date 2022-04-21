
package com.main.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Nada ElGebali
 */

//class and constructor
public class MainHeaderHandler {
    private int number;
    private String customer;
    private Date invoiceDate;
    private ArrayList<MainLineHandler> lines;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    public MainHeaderHandler() {
    }

    public MainHeaderHandler(int num, String customer, Date invoiceDate) {
        this.number = num;
        this.customer = customer;
        this.invoiceDate = invoiceDate;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ArrayList<MainLineHandler> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList<MainLineHandler> lines) {
        this.lines = lines;
    }
    
    //function getlinetotals
    public double getInvoiceTotal() {
        double total = 0.0;
        
        for (int i = 0; i < getLines().size(); i++) {
            total += getLines().get(i).getLineTotal();
        }
        
        return total;
    }

    @Override
    public String toString() {
        return number + "," + df.format(invoiceDate) + "," + customer;
    }
    
}
