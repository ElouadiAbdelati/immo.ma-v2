/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.AnnonceStatus;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author ok
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public String init() {
        return "SELECT item FROM " + entityClass.getSimpleName() + " item WHERE 1=1";
    }

    public String addCriteria(String key, Object value) {
        return addCriteria(key, value, "=");
    }

    public String addCriteria(String key, Object value, String operator) {
        if (value != null && !value.toString().isEmpty()) {
            if (!"LIKE".equals(operator)) {
                return " AND item." + key + " " + operator + " '" + value.toString() + "'";
            } else {
                return " AND item." + key + " " + operator + " '" + value.toString() + "%'";
            }

        }
        return "";
    }

    public String addCriteria(String key, Double valueMin, Double valueMax) {
        String query = addCriteria(key, valueMin, ">=");
        query += addCriteria(key, valueMax, "<=");
        return query;
    }

    public T findBy(String criteria, String value) {
        final String query = "SELECT item FROM " + entityClass.getSimpleName() + " item WHERE item." + criteria + " = '" + value + "'";
        System.out.println("query = " + query);
        return getSingle(query);
    }

    public List<T> findAllBy(String criteria, String value) {
        final String query = "SELECT item FROM " + entityClass.getSimpleName() + " item WHERE item." + criteria + " = '" + value + "'";
        System.out.println("query = " + query);
        return getAll(query);
    }

    private T getSingle(String query) {
        List<T> list = getEntityManager().createQuery(query).getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
    

    private List<T> getAll(String query) {
        List<T> list = getEntityManager().createQuery(query).getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
