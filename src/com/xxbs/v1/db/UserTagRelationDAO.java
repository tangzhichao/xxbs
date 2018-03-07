package com.xxbs.v1.db;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data access object (DAO) providing persistence and search support for UserTagRelation entities. Transaction control of the save(), update() and delete() operations can
 * directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information
 * for how to configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.UserTagRelation
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class UserTagRelationDAO {
	private static final Logger	log		= LoggerFactory.getLogger(UserTagRelationDAO.class);
	// property constants
	public static final String	USER_ID	= "userId";
	public static final String	TAG_ID	= "tagId";
	public static final String	STATUS	= "status";

	private SessionFactory		sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	protected void initDao() {
		// do nothing
	}

	public void save(UserTagRelation transientInstance) {
		log.debug("saving UserTagRelation instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserTagRelation persistentInstance) {
		log.debug("deleting UserTagRelation instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserTagRelation findById(java.lang.Integer id) {
		log.debug("getting UserTagRelation instance with id: " + id);
		try {
			UserTagRelation instance = (UserTagRelation) getCurrentSession().get("com.xxbs.v1.db.UserTagRelation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<UserTagRelation> findByExample(UserTagRelation instance) {
		log.debug("finding UserTagRelation instance by example");
		try {
			List<UserTagRelation> results = (List<UserTagRelation>) getCurrentSession().createCriteria("com.xxbs.v1.db.UserTagRelation").add(create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding UserTagRelation instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from UserTagRelation as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<UserTagRelation> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List<UserTagRelation> findByTagId(Object tagId) {
		return findByProperty(TAG_ID, tagId);
	}

	public List<UserTagRelation> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all UserTagRelation instances");
		try {
			String queryString = "from UserTagRelation";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public UserTagRelation merge(UserTagRelation detachedInstance) {
		log.debug("merging UserTagRelation instance");
		try {
			UserTagRelation result = (UserTagRelation) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserTagRelation instance) {
		log.debug("attaching dirty UserTagRelation instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserTagRelation instance) {
		log.debug("attaching clean UserTagRelation instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserTagRelationDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserTagRelationDAO) ctx.getBean("UserTagRelationDAO");
	}
}