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
 * A data access object (DAO) providing persistence and search support for QuestionVerifyLog entities. Transaction control of the save(), update() and delete() operations can
 * directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information
 * for how to configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.QuestionVerifyLog
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class QuestionVerifyLogDAO {
	private static final Logger	log			= LoggerFactory.getLogger(QuestionVerifyLogDAO.class);
	// property constants
	public static final String	USER_ID		= "userId";
	public static final String	QUESTION_ID	= "questionId";
	public static final String	VERIFY_TYPE	= "verifyType";
	public static final String	STATUS		= "status";

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

	public void save(QuestionVerifyLog transientInstance) {
		log.debug("saving QuestionVerifyLog instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(QuestionVerifyLog persistentInstance) {
		log.debug("deleting QuestionVerifyLog instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public QuestionVerifyLog findById(java.lang.Integer id) {
		log.debug("getting QuestionVerifyLog instance with id: " + id);
		try {
			QuestionVerifyLog instance = (QuestionVerifyLog) getCurrentSession().get("com.xxbs.v1.db.QuestionVerifyLog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<QuestionVerifyLog> findByExample(QuestionVerifyLog instance) {
		log.debug("finding QuestionVerifyLog instance by example");
		try {
			List<QuestionVerifyLog> results = (List<QuestionVerifyLog>) getCurrentSession().createCriteria("com.xxbs.v1.db.QuestionVerifyLog")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding QuestionVerifyLog instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from QuestionVerifyLog as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<QuestionVerifyLog> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List<QuestionVerifyLog> findByQuestionId(Object questionId) {
		return findByProperty(QUESTION_ID, questionId);
	}

	public List<QuestionVerifyLog> findByVerifyType(Object verifyType) {
		return findByProperty(VERIFY_TYPE, verifyType);
	}

	public List<QuestionVerifyLog> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all QuestionVerifyLog instances");
		try {
			String queryString = "from QuestionVerifyLog";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public QuestionVerifyLog merge(QuestionVerifyLog detachedInstance) {
		log.debug("merging QuestionVerifyLog instance");
		try {
			QuestionVerifyLog result = (QuestionVerifyLog) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(QuestionVerifyLog instance) {
		log.debug("attaching dirty QuestionVerifyLog instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(QuestionVerifyLog instance) {
		log.debug("attaching clean QuestionVerifyLog instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static QuestionVerifyLogDAO getFromApplicationContext(ApplicationContext ctx) {
		return (QuestionVerifyLogDAO) ctx.getBean("QuestionVerifyLogDAO");
	}
}