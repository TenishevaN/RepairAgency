package com.my.db.model;

/**
 * {@ code Language} enum represents laguage object.
 * <br>
 * Each invoice has id
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public enum Language {
    EN(1), UK(2), RU(3);

    private int id;

    Language(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static int getId(String name) {
        if ("uk".equals(name)) {
            return UK.getId();
        }
        if ("ru".equals(name)) {
            return RU.getId();
        }
        return EN.getId();
    }

    public String getName() {
        return name().toLowerCase();
    }
}
