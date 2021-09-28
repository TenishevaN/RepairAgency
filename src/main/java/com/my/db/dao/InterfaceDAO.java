package com.my.db.dao;

import java.util.List;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code InterfaceDAO} class represents DAO API.
 */
public interface InterfaceDAO<E> {

    boolean insert(E element);
    List<E> getAll();
    E get(int id);
    boolean update(E element);

}
