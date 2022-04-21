
package com.main.model;

/**
 *
 * @author Nada ElGebali
 */

public class MainLineHandler {
    private String Name;
    private double price;
    private int count;
    private MainHeaderHandler header;

    public MainLineHandler() {
    }

    public MainLineHandler(String Name, double price, int count, MainHeaderHandler header) {
        this.Name = Name;
        this.price = price;
        this.count = count;
        this.header = header;
    }

    public MainHeaderHandler getHeader() {
        return header;
    }

    public void setHeader(MainHeaderHandler header) {
        this.header = header;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public double getLineTotal() {
        return price * count;
    }

    @Override
    public String toString() {
        return header.getNumber() + "," + Name + "," + price + "," + count;
    }

    
    
}
