package com.journaldev.dao;

public interface GenericInsertableDAO  <T>{
    T insert (T t);
    Long getCount();
}
