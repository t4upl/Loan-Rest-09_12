package com.journaldev.dao;

import java.util.List;

public interface AbstractDAO<T> {
    T findById(int id);
    List<T> findByExample(T t);
}
