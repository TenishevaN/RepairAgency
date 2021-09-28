package com.my.db.model;

import java.util.Objects;

/**
 * {@ code AccountLocalization} class uses to localize user.
 * <br>
 * Each account localization has id, account id, language id
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class AccountLocalization {

    private int id;
    private int account_id;
    private int language_id;
    private String name;

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

    public int getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountLocalization that = (AccountLocalization) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }
}
