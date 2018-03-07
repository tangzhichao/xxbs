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
 * A data access object (DAO) providing persistence and search support for CorrectAnswer entities. Transaction control of the save(), update() and delete() operations can directly
 * support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.CorrectAnswer
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class CorrectAnswerDAO {
	private static final Logger	log					= LoggerFactory.getLogger(CorrectAnswerDAO.class);
	// property constants
	public static final String	QUESTION_ID			= "questionId";
	public static final String	CORRECT_OPTION_ID	= "correctOptionId";

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

	public void save(CorrectAnswer transientInstance) {
		log.debug("saving CorrectAnswer instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CorrectAnswer persistentInstance) {
		log.debug("deleting CorrectAnswer instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CorrectAnswer findById(java.lang.Integer id) {
		log.debug("getting CorrectAnswer instance with id: " + id);
		try {
			CorrectAnswer instance = (CorrectAnswer) getCurrentSession().get("com.xxbs.v1.db.CorrectAnswer", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<CorrectAnswer> findByExample(CorrectAnswer instance) {
		log.debug("finding CorrectAnswer instance by example");
		try {
			List<CorrectAnswer> results = (List<CorrectAnswer>) getCurrentSession().createCriteria("com.xxbs.v1.db.CorrectAnswer").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding CorrectAnswer instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from CorrectAnswer as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<CorrectAnswer> findByQuestionId(Object questionId) {
		return findByProperty(QUESTION_ID, questionId);
	}

	public List<CorrectAnswer> findByCorrectOptionId(Object correctOptionId) {
		return findByProperty(CORRECT_OPTION_ID, correctOptionId);
	}

	public List findAll() {
		log.debug("finding all CorrectAnswer instances");
		try {
			String queryString = "from CorrectAnswer";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CorrectAnswer merge(CorrectAnswer detachedInstance) {
		log.debug("merging CorrectAnswer instance");
		try {
			CorrectAnswer result = (CorrectAnswer) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CorrectAnswer instance) {
		log.debug("attaching dirty CorrectAnswer instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CorrectAnswer instance) {
		log.debug("attaching clean CorrectAnswer instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CorrectAnswerDAO getFromApplicationContext(ApplicationContext ctx) {
		return (CorrectAnswerDAO) ctx.getBean("CorrectAnswerDAO");
	}
}