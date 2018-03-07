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
 * A data access object (DAO) providing persistence and search support for UserFriendRelation entities. Transaction control of the save(), update() and delete() operations can
 * directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information
 * for how to configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.UserFriendRelation
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class UserFriendRelationDAO {
	private static final Logger	log				= LoggerFactory.getLogger(UserFriendRelationDAO.class);
	// property constants
	public static final String	SOURCE_USER_ID	= "sourceUserId";
	public static final String	TARGET_USER_ID	= "targetUserId";
	public static final String	STATUS			= "status";

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

	public void save(UserFriendRelation transientInstance) {
		log.debug("saving UserFriendRelation instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserFriendRelation persistentInstance) {
		log.debug("deleting UserFriendRelation instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserFriendRelation findById(java.lang.Integer id) {
		log.debug("getting UserFriendRelation instance with id: " + id);
		try {
			UserFriendRelation instance = (UserFriendRelation) getCurrentSession().get("com.xxbs.v1.db.UserFriendRelation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<UserFriendRelation> findByExample(UserFriendRelation instance) {
		log.debug("finding UserFriendRelation instance by example");
		try {
			List<UserFriendRelation> results = (List<UserFriendRelation>) getCurrentSession().createCriteria("com.xxbs.v1.db.UserFriendRelation")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding UserFriendRelation instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from UserFriendRelation as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<UserFriendRelation> findBySourceUserId(Object sourceUserId) {
		return findByProperty(SOURCE_USER_ID, sourceUserId);
	}

	public List<UserFriendRelation> findByTargetUserId(Object targetUserId) {
		return findByProperty(TARGET_USER_ID, targetUserId);
	}

	public List<UserFriendRelation> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all UserFriendRelation instances");
		try {
			String queryString = "from UserFriendRelation";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public UserFriendRelation merge(UserFriendRelation detachedInstance) {
		log.debug("merging UserFriendRelation instance");
		try {
			UserFriendRelation result = (UserFriendRelation) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserFriendRelation instance) {
		log.debug("attaching dirty UserFriendRelation instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserFriendRelation instance) {
		log.debug("attaching clean UserFriendRelation instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserFriendRelationDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserFriendRelationDAO) ctx.getBean("UserFriendRelationDAO");
	}
}