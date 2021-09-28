package com.my.db.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code Invoice} class represents invoice object.
 * <br>
 * Each invoice has id, account id, ammount
 * <br>
 */
public class Invoice implements Serializable {

    private static final long serialVersionUID = 8466257860808346236L;
    private int id;
    private int account_id;
    private BigDecimal ammount;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public BigDecimal getAmmount() {
        return ammount;
    }

    public void setAmmount(BigDecimal ammount) {
        this.ammount = ammount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return id == invoice.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", account_id=" + account_id +
                '}';
    }
}
