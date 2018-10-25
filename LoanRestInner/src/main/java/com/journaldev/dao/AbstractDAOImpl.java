package com.journaldev.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDAOImpl<T> {

    protected Class entityClass;

    @Autowired
    protected SessionFactory sessionFactory;

    public AbstractDAOImpl(Class entityClass) {
        this.entityClass = entityClass;
    }

    public List<T> findByExample(T t) {
        SessionObject sessionObject = getSesionTransactionObject();
        Criteria criteria = sessionObject.getSession().createCriteria(t.getClass()).add(Example.create(t));
        List<T> list = criteria.list();
        sessionObject.commitTransactionAndCloseSession();
        return list;
    }

    public T findById(int id) {
        SessionObject sessionObject = getSesionTransactionObject();
        T t = (T) sessionObject.getSession().get(entityClass, id);
        sessionObject.commitTransactionAndCloseSession();
        return t;
    }

    public SessionObject  getSesionTransactionObject() {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        return new SessionObject(session, transaction);
    }

    public Long getCount() {
        SessionObject sessionObject = getSesionTransactionObject();
        Criteria criteria = sessionObject.getSession().createCriteria(entityClass)
                .setProjection(Projections.rowCount());

        List list = criteria.list();
        if (list != null) {
            Long rowCount = (Long) list.get(0);
            sessionObject.commitTransactionAndCloseSession();
            return rowCount;
        }

        throw new RuntimeException(this.getClass().getCanonicalName() + " getCount()");
    }

    @Getter
    @Setter
    @AllArgsConstructor
    protected static class SessionObject {
        protected Session session;
        protected Transaction transaction;

        public void commitTransactionAndCloseSession() {
            this.transaction.commit();
            this.session.close();
        }
    }
}
