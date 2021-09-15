package com.my.db.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Review implements Serializable {

    private static final long serialVersionUID = 8466257860808346236L;

    private int id;
    private int repair_request_id;
    private Date date;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRepairRequestId() {
        return repair_request_id;
    }

    public void setRepairRequestId(int repair_request_id) {
        this.repair_request_id = repair_request_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", repair_request_id=" + repair_request_id +
                ", date=" + date +
                ", comment='" + comment + '\'' +
                '}';
    }
}
