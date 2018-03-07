package com.xxbs.v1.util.orm;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxbs.v1.util.Utils;
import com.xxbs.v1.util.str.ValidUtil;

/**
 * 提供一些常用方法的DAO类
 * 
 * @author god
 * @date 2015-11-18上午11:04:10
 * 
 */
@SuppressWarnings("unchecked")
public class BasicDAO {

	private static final Logger log = LoggerFactory.getLogger(BasicDAO.class);

	private SessionFactory sessionFactory;
	private boolean openSession;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	public Session openSession() {
		return sessionFactory.openSession();
	}

	public Session getSession() {
		if (openSession) {
			return openSession();
		}
		return getCurrentSession();
	}
	
	public boolean isOpenSession() {
		return openSession;
	}

	public void setOpenSession(boolean openSession) {
		this.openSession = openSession;
	}
	public boolean closeSession() {
		getCurrentSession().close();
		return sessionFactory.isClosed();
	}

	public <T> Serializable save(T transientInstance) {
		if (transientInstance == null) {
			return null;
		}
		log.debug("saving " + transientInstance.getClass().getSimpleName() + " instance");
		try {
			Serializable id = getSession().save(transientInstance);
			log.debug("save successful");
			return id;
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("save failed", re);
			return null;
		}
	}

	public <T> boolean delete(T persistentInstance) {
		if (persistentInstance == null) {
			return false;
		}
		log.debug("deleting " + persistentInstance.getClass().getSimpleName() + " instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
			return true;
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("delete failed", re);
			return false;
		}
	}

	public <T> T findById(Class<T> entityClass, Serializable id) {
		if (entityClass == null || id == null) {
			return null;
		}
		log.debug("getting " + entityClass.getSimpleName() + " instance with id: " + id);
		try {
			T instance = (T) getSession().get(entityClass, id);
			return instance;
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("get failed", re);
			return null;
		}
	}

	public <T> List<T> findByExample(T instance) {
		if (instance == null) {
			return null;
		}
		log.debug("finding " + instance.getClass().getSimpleName() + " instance by example");
		try {
			List<T> results = (List<T>) getSession().createCriteria(instance.getClass()).add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by example failed", re);
			return null;
		}
	}

	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) {
		if (entityClass == null || propertyName == null) {
			return null;
		}
		log.debug("finding " + entityClass.getSimpleName() + " instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from " + entityClass.getSimpleName() + " as model where model." + propertyName
					+ (value == null ? " is null or model." + propertyName + "= ?" : "= ?");
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by property name failed", re);
			return null;
		}
	}
	
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value,LinkedHashMap<String, String> orderMap) {
		if (entityClass == null || propertyName == null) {
			return null;
		}
		log.debug("finding " + entityClass.getSimpleName() + " instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from " + entityClass.getSimpleName() + " as model where model." + propertyName
					+ (value == null ? " is null or model." + propertyName + "= ?" : "= ?");
			
			if (!Utils.isEmpty(orderMap)) {
				queryString += " order by ";
				int i = 0;
				for (Entry<String, String> entry : orderMap.entrySet()) {
					if (entry.getValue() != null) {
						queryString += (" " + entry.getKey() + " " + entry.getValue() + (i < orderMap.size() - 1 ? "," : ""));
						i++;
					}
				}
			}
			
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by property name failed", re);
			return null;
		}
	}

	public <T> List<T> findByPropertys(Class<T> entityClass, LinkedHashMap<String, Object> whereMap, String whereEndStr,
			LinkedHashMap<String, String> orderMap, Integer pageSize, Integer pageIndex) {
		if (entityClass == null || whereMap == null) {
			return null;
		}
		Set<Entry<String, Object>> entrySet = whereMap.entrySet();
		if (entrySet == null) {
			return null;
		}
		log.debug("finding " + entityClass.getSimpleName() + " instance with entrys: " + entrySet);
		try {
			String queryString = "from " + entityClass.getSimpleName() + " " + (entrySet.size() > 0 ? "where " : "");
			for (Entry<String, Object> entry : entrySet) {
				queryString += ("" + entry.getKey() + (entry.getValue() == null ? " is null " : "=? "));
			}
			if (whereEndStr != null) {
				queryString += whereEndStr;
			}
			if (!Utils.isEmpty(orderMap)) {
				queryString += " order by ";
				int i = 0;
				for (Entry<String, String> entry : orderMap.entrySet()) {
					if (entry.getValue() != null) {
						queryString += (" " + entry.getKey() + " " + entry.getValue() + (i < orderMap.size() - 1 ? "," : ""));
						i++;
					}
				}
			}
			Query queryObject = getSession().createQuery(queryString);
			int i = 0;
			for (Entry<String, Object> entry : entrySet) {
				if (entry.getValue() != null) {
					queryObject.setParameter(i, entry.getValue());
					i++;
				}
			}
			if (pageSize != null && pageIndex != null) {
				queryObject.setFirstResult((pageIndex - 1) * pageSize);
				queryObject.setMaxResults(pageSize);
			}
			return queryObject.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by propertys name failed", re);
			return null;
		}
	}
	
	public <T> Integer findCountByPropertys(Class<T> entityClass, LinkedHashMap<String, Object> whereMap, String whereEndStr) {
		if (entityClass == null || whereMap == null) {
			return null;
		}
		Set<Entry<String, Object>> entrySet = whereMap.entrySet();
		if (entrySet == null) {
			return null;
		}
		log.debug("finding " + entityClass.getSimpleName() + " instance with entrys: " + entrySet);
		try {
			String queryString = "select count(*) from " + entityClass.getSimpleName() + " " + (entrySet.size() > 0 ? "where " : "");
			for (Entry<String, Object> entry : entrySet) {
				queryString += ("" + entry.getKey() + (entry.getValue() == null ? " is null " : "=? "));
			}
			if (whereEndStr != null) {
				queryString += whereEndStr;
			}
			Query queryObject = getSession().createQuery(queryString);
			int i = 0;
			for (Entry<String, Object> entry : entrySet) {
				if (entry.getValue() != null) {
					queryObject.setParameter(i, entry.getValue());
					i++;
				}
			}
			return ValidUtil.getInteger(queryObject.uniqueResult(), 0);
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by propertys name failed", re);
			return null;
		}
	}
	
	public <T> List<T> findByHQL(Class<T> entityClass,String hql, List<Object> values, Integer pageSize, Integer pageIndex) {
		try {
			System.out.println(">>>>>>>>>>Query HQL:"+hql);
			Query queryObject = getSession().createQuery(hql);
			if (!Utils.isEmpty(values)) {
				for (int i = 0; i < values.size(); i++) {
					queryObject.setParameter(i, values.get(i));
				}
			}
			if (pageSize != null && pageIndex != null) {
				queryObject.setFirstResult((pageIndex - 1) * pageSize);
				queryObject.setMaxResults(pageSize);
			}
			return queryObject.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by hql name failed", re);
			return null;
		}
	}
	
	public <T> Integer findCountByHQL(Class<T> entityClass,String hql, List<Object> values) {
		try {
			System.out.println(">>>>>>>>>>Query HQL:"+hql);
			Query queryObject = getSession().createQuery(hql);
			if (!Utils.isEmpty(values)) {
				for (int i = 0; i < values.size(); i++) {
					queryObject.setParameter(i, values.get(i));
				}
			}
			return ValidUtil.getInteger(queryObject.uniqueResult(), 0);
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by hql name failed", re);
			return null;
		}
	}
	public List<Map<String,Object>> findBySQL(String sql, List<Object> values, Integer pageSize, Integer pageIndex) {
		try {
			System.out.println(">>>>>>>>>>Query SQL:"+sql);
			Query queryObject = getSession().createSQLQuery(sql);
			if (!Utils.isEmpty(values)) {
				for (int i = 0; i < values.size(); i++) {
					queryObject.setParameter(i, values.get(i));
				}
			}
			if (pageSize != null && pageIndex != null) {
				queryObject.setFirstResult((pageIndex - 1) * pageSize);
				queryObject.setMaxResults(pageSize);
			}
			queryObject.setResultTransformer(new MyResultTransformer());
			return queryObject.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by hql name failed", re);
			return null;
		}
	}
	
	public Integer findCountBySQL(String sql, List<Object> values) {
		try {
			System.out.println(">>>>>>>>>>Query HQL:"+sql);
			Query queryObject = getSession().createSQLQuery(sql);
			if (!Utils.isEmpty(values)) {
				for (int i = 0; i < values.size(); i++) {
					queryObject.setParameter(i, values.get(i));
				}
			}
			return ValidUtil.getInteger(queryObject.uniqueResult(), 0);
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by hql name failed", re);
			return null;
		}
	}
	
	public List<Map<String,Object>> findBySQL(String sql, Map<String,Object> values, Integer pageSize, Integer pageIndex) {
		try {
			System.out.println(">>>>>>>>>>Query SQL:"+sql);
			Query queryObject = getSession().createSQLQuery(sql);
			if (!Utils.isEmpty(values)) {
				Set<String> keySet = values.keySet();  
	            for (String string : keySet) {  
	                Object obj = values.get(string);  
	                //这里考虑传入的参数是什么类型，不同类型使用的方法不同  
	                if(obj instanceof Collection<?>){  
	                	queryObject.setParameterList(string, (Collection<?>)obj);  
	                }else if(obj instanceof Object[]){  
	                	queryObject.setParameterList(string, (Object[])obj);  
	                }else{  
	                	queryObject.setParameter(string, obj);  
	                }  
	            }
			}
			if (pageSize != null && pageIndex != null) {
				queryObject.setFirstResult((pageIndex - 1) * pageSize);
				queryObject.setMaxResults(pageSize);
			}
			queryObject.setResultTransformer(new MyResultTransformer());
			return queryObject.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by hql name failed", re);
			return null;
		}
	}
	
	public Integer findCountBySQL(String sql, Map<String,Object> values) {
		try {
			System.out.println(">>>>>>>>>>Query HQL:"+sql);
			Query queryObject = getSession().createSQLQuery(sql);
			if (!Utils.isEmpty(values)) {
				Set<String> keySet = values.keySet();  
	            for (String string : keySet) {  
	                Object obj = values.get(string);  
	                //这里考虑传入的参数是什么类型，不同类型使用的方法不同  
	                if(obj instanceof Collection){  
	                	queryObject.setParameterList(string, (Collection<?>)obj);  
	                }else if(obj instanceof Object[]){  
	                	queryObject.setParameterList(string, (Object[])obj);  
	                }else{  
	                	queryObject.setParameter(string, obj);  
	                }  
	            }
			}
			return ValidUtil.getInteger(queryObject.uniqueResult(), 0);
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by hql name failed", re);
			return null;
		}
	}

	public <T> List<T> findByCriteria( Class<T> entityClass,DetachedCriteria criteria, Integer pageSize, Integer pageIndex) {
		if (criteria == null) {
			return null;
		}
		try {
			Criteria c = criteria.getExecutableCriteria(getCurrentSession());
			if (pageSize != null && pageIndex != null) {
				c.setFirstResult((pageIndex - 1) * pageSize);
				c.setMaxResults(pageSize);
			}
			return c.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by criteria name failed", re);
			return null;
		}
	}
	
	public <T> Integer findCountByCriteria(Class<T> entityClass,DetachedCriteria criteria) {
		try {
			Criteria c = criteria.getExecutableCriteria(getCurrentSession());
			return (int)(long)(Long)c.setProjection(Projections.rowCount()).uniqueResult();
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by criteria name failed", re);
			return null;
		}
	}

	public <T> List<T> findAll(Class<T> entityClass) {
		if (entityClass == null) {
			return null;
		}
		log.debug("finding all " + entityClass.getSimpleName() + "");
		try {
			String queryString = "from " + entityClass.getSimpleName() + "";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find all failed", re);
			return null;
		}
	}

	public <T> T merge(T detachedInstance) {
		if (detachedInstance == null) {
			return null;
		}
		log.debug("merging " + detachedInstance.getClass().getSimpleName() + " instance");
		try {
			T result = (T) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("merge failed", re);
			return null;
		}
	}

	public <T> boolean attachDirty(T instance) {
		if (instance == null) {
			return false;
		}
		log.debug("attaching dirty " + instance.getClass().getSimpleName() + " instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
			return true;
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("attach failed", re);
			return false;
		}
	}

	public <T> boolean attachClean(T instance) {
		if (instance == null) {
			return false;
		}
		log.debug("attaching clean " + instance.getClass().getSimpleName() + " instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
			return true;
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("attach failed", re);
			return false;
		}
	}
	
	public <T> boolean executeByHQL(Class<T> entityClass,String hql, List<Object> values){
		try {
			log.error(">>>>>>>>>>Query HQL:"+hql);
			Query queryObject = getSession().createQuery(hql);
			if (!Utils.isEmpty(values)) {
				for(int i = 0; i < values.size(); i++){
					queryObject.setParameter(i, values.get(i));  
				}
			}
			return queryObject.executeUpdate() > 0;
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by hql name failed", re);
			return false;
		}
	}
	
	public <T> boolean executeByHQL(Class<T> entityClass,String hql, Map<String,Object> values){
		try {
			log.error(">>>>>>>>>>Query HQL:"+hql);
			Query queryObject = getSession().createQuery(hql);
			if (!Utils.isEmpty(values)) {
				Set<String> keySet = values.keySet();  
	            for (String string : keySet) {  
	                Object obj = values.get(string);  
	                //这里考虑传入的参数是什么类型，不同类型使用的方法不同  
	                if(obj instanceof Collection<?>){  
	                	queryObject.setParameterList(string, (Collection<?>)obj);  
	                }else if(obj instanceof Object[]){  
	                	queryObject.setParameterList(string, (Object[])obj);  
	                }else{  
	                	queryObject.setParameter(string, obj);  
	                }  
	            }  
			}
			return queryObject.executeUpdate() > 0;
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by hql name failed", re);
			return false;
		}
	}
}
