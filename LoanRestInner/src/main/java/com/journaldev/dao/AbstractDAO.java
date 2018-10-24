package com.journaldev.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractDAO{

    protected Class entityClass;

    @Autowired
    protected SessionFactory sessionFactory;

//    protected Session session;
//    protected Transaction transaction;

    public AbstractDAO(Class entityClass) {
        this.entityClass = entityClass;
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

//    protected void openSessionAndBeginTransaction () {
//        this.session = this.sessionFactory.openSession();
//        this.transaction = session.beginTransaction();
//    }
//
//    private void commitTransactionAndCloseSession ()  {
//        this.transaction.commit();
//        this.session.close();
//    }

    protected Long processCriteria(Criteria criteria){
        List list = criteria.list();
        if (list != null) {
            Long rowCount = (Long) list.get(0);
            return rowCount;
        }

        throw new RuntimeException(this.getClass().getCanonicalName());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class SessionObject {
        protected Session session;
        protected Transaction transaction;

        public void commitTransactionAndCloseSession() {
            this.transaction.commit();
            this.session.close();
        }
    }




}
