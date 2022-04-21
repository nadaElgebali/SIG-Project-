
package com.sig.model;

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
    private int num;
    private String customer;
    private Date invDate;
    private ArrayList<MainLineHandler> lines;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    public MainHeaderHandler() {
    }

    public MainHeaderHandler(int num, String customer, Date invDate) {
        this.num = num;
        this.customer = customer;
        this.invDate = invDate;
    }

    public Date getInvDate() {
        return invDate;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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
        return num + "," + df.format(invDate) + "," + customer;
    }
    
}
