package com.journaldev.dao;

public interface AbstractDAO<T> {
    T findById(int id);
}
