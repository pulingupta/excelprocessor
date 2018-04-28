package com.nortal.expenditure.model;

import java.util.Date;

public class Expenditure {
    private Date date;
    private String supplier;
    private String type;
    private String product;
    private double units;
    private double unitPrice;
    private double taxPercentage;
    private double totalExpense;
    private double totalExpenseWithFee;

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public double getTotalExpenseWithFee() {
        return totalExpenseWithFee;
    }

    public void setTotalExpenseWithFee(double totalExpenseWithFee) {
        this.totalExpenseWithFee = totalExpenseWithFee;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
