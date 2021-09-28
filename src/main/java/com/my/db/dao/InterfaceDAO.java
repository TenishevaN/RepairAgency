package com.my.db.dao;

import java.util.List;

/**
 * {@ code InterfaceDAO} class represents DAO API.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public interface InterfaceDAO<E> {

    boolean insert(E element);

    List<E> getAll();

    E get(int id);

    boolean update(E element);

}
