package com.my.db.dao;

import java.util.List;

public interface InterfaceDAO<E> {

    boolean insert(E element);
    List<E> getAll();
    E get(int id);
    boolean update(E element);

}
