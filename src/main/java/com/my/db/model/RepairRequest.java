package com.my.db.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * {@ code RepairRequest} class represents properties and behaviours of repairRequest objects.
 * <br>
 * Each repairRequest has id, user id, user name, status id, status name, master id, cost, date description
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class RepairRequest implements Serializable {

    private static final long serialVersionUID = 8466257860808346236L;

    private int id;
    private int userId;
    private String userName;
    private int statusId;
    private String statusName;
    private Integer masterId;
    private String masterName;
    private BigDecimal cost;
    private Date date;
    private String description;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getUserName() {
        return userName;
    }

    public String getMasterName() {
        return masterName;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepairRequest that = (RepairRequest) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "RepairRequest{" +
                "id=" + id +
                ", userId=" + userId +
                ", statusId=" + statusId +
                ", statusName='" + statusName + '\'' +
                ", masterId=" + masterId +
                ", cost=" + cost +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
