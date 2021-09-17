package com.my.db.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Payment implements Serializable {

    private static final long serialVersionUID = 8466257860808346236L;
    private int id;
    private int invoiceId;
    private int repairRequestId;
    private BigDecimal ammount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getRepairRequestId() {
        return repairRequestId;
    }

    public void setRepairRequestId(int repairRequestId) {
        this.repairRequestId = repairRequestId;
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
        Payment payment = (Payment) o;
        return id == payment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", invoiceId=" + invoiceId +
                ", repairRequestId=" + repairRequestId +
                ", ammount=" + ammount +
                '}';
    }
}
