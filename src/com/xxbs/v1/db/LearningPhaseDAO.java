package com.xxbs.v1.db;

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
 * A data access object (DAO) providing persistence and search support for LearningPhase entities. Transaction control of the save(), update() and delete() operations can directly
 * support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.LearningPhase
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class LearningPhaseDAO {
	private static final Logger	log			= LoggerFactory.getLogger(LearningPhaseDAO.class);
	// property constants
	public static final String	PARENT_ID	= "parentId";
	public static final String	NAME		= "name";
	public static final String	KEY			= "key";
	public static final String	LEVEL		= "level";

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

	public void save(LearningPhase transientInstance) {
		log.debug("saving LearningPhase instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(LearningPhase persistentInstance) {
		log.debug("deleting LearningPhase instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public LearningPhase findById(java.lang.Integer id) {
		log.debug("getting LearningPhase instance with id: " + id);
		try {
			LearningPhase instance = (LearningPhase) getCurrentSession().get("com.xxbs.v1.db.LearningPhase", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<LearningPhase> findByExample(LearningPhase instance) {
		log.debug("finding LearningPhase instance by example");
		try {
			List<LearningPhase> results = (List<LearningPhase>) getCurrentSession().createCriteria("com.xxbs.v1.db.LearningPhase").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding LearningPhase instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from LearningPhase as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<LearningPhase> findByParentId(Object parentId) {
		return findByProperty(PARENT_ID, parentId);
	}

	public List<LearningPhase> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<LearningPhase> findByKey(Object key) {
		return findByProperty(KEY, key);
	}

	public List<LearningPhase> findByLevel(Object level) {
		return findByProperty(LEVEL, level);
	}

	public List findAll() {
		log.debug("finding all LearningPhase instances");
		try {
			String queryString = "from LearningPhase";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public LearningPhase merge(LearningPhase detachedInstance) {
		log.debug("merging LearningPhase instance");
		try {
			LearningPhase result = (LearningPhase) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(LearningPhase instance) {
		log.debug("attaching dirty LearningPhase instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(LearningPhase instance) {
		log.debug("attaching clean LearningPhase instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static LearningPhaseDAO getFromApplicationContext(ApplicationContext ctx) {
		return (LearningPhaseDAO) ctx.getBean("LearningPhaseDAO");
	}
}