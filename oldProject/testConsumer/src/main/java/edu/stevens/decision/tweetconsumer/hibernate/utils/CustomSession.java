/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stevens.decision.tweetconsumer.hibernate.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.Filter;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.stat.SessionStatistics;
import org.hibernate.type.Type;
import org.hibernate.classic.Session;
/**
 *
 * @author michaelpereira
 */
public class CustomSession implements Session {

    Session session1;
    Session session2;

    public CustomSession(Session session1, Session session2) {
        this.session1 = session1;
        this.session2 = session2;
    }



    public Object saveOrUpdateCopy(Object o) throws HibernateException {
        session2.saveOrUpdateCopy(o);
        return session1.saveOrUpdateCopy(o);
    }

    public Object saveOrUpdateCopy(Object o, Serializable srlzbl) throws HibernateException {
         session2.saveOrUpdateCopy(o, srlzbl);
        return session1.saveOrUpdateCopy(o, srlzbl);
    }

    public Object saveOrUpdateCopy(String string, Object o) throws HibernateException {
        session2.saveOrUpdateCopy(string, o);
        return session1.saveOrUpdateCopy(string, o);
    }

    public Object saveOrUpdateCopy(String string, Object o, Serializable srlzbl) throws HibernateException {
       session2.saveOrUpdateCopy(string, o, srlzbl);
        return session1.saveOrUpdateCopy(string,o, srlzbl);
    }

    public List find(String string) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List find(String string, Object o, Type type) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List find(String string, Object[] os, Type[] types) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Iterator iterate(String string) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Iterator iterate(String string, Object o, Type type) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Iterator iterate(String string, Object[] os, Type[] types) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection filter(Object o, String string) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection filter(Object o, String string, Object o1, Type type) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection filter(Object o, String string, Object[] os, Type[] types) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int delete(String string) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int delete(String string, Object o, Type type) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int delete(String string, Object[] os, Type[] types) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query createSQLQuery(String string, String string1, Class type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query createSQLQuery(String string, String[] strings, Class[] types) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void save(Object o, Serializable srlzbl) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void save(String string, Object o, Serializable srlzbl) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(Object o, Serializable srlzbl) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(String string, Object o, Serializable srlzbl) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EntityMode getEntityMode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public org.hibernate.Session getSession(EntityMode em) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void flush() throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFlushMode(FlushMode fm) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public FlushMode getFlushMode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCacheMode(CacheMode cm) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public CacheMode getCacheMode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SessionFactory getSessionFactory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Connection connection() throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Connection close() throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cancelQuery() throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isOpen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isConnected() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isDirty() throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Serializable getIdentifier(Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void evict(Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object load(Class type, Serializable srlzbl, LockMode lm) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object load(String string, Serializable srlzbl, LockMode lm) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object load(Class type, Serializable srlzbl) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object load(String string, Serializable srlzbl) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load(Object o, Serializable srlzbl) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void replicate(Object o, ReplicationMode rm) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void replicate(String string, Object o, ReplicationMode rm) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Serializable save(Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Serializable save(String string, Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void saveOrUpdate(Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void saveOrUpdate(String string, Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(String string, Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object merge(Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object merge(String string, Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void persist(Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void persist(String string, Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void delete(Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void delete(String string, Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void lock(Object o, LockMode lm) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void lock(String string, Object o, LockMode lm) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void refresh(Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void refresh(Object o, LockMode lm) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LockMode getCurrentLockMode(Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Transaction beginTransaction() throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Transaction getTransaction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Criteria createCriteria(Class type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Criteria createCriteria(Class type, String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Criteria createCriteria(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Criteria createCriteria(String string, String string1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query createQuery(String string) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SQLQuery createSQLQuery(String string) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query createFilter(Object o, String string) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query getNamedQuery(String string) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object get(Class type, Serializable srlzbl) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object get(Class type, Serializable srlzbl, LockMode lm) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object get(String string, Serializable srlzbl) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object get(String string, Serializable srlzbl, LockMode lm) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getEntityName(Object o) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Filter enableFilter(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Filter getEnabledFilter(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void disableFilter(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SessionStatistics getStatistics() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setReadOnly(Object o, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Connection disconnect() throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void reconnect() throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void reconnect(Connection cnctn) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
