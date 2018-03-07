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
 * A data access object (DAO) providing persistence and search support for UserAnswerLog entities. Transaction control of the save(), update() and delete() operations can directly
 * support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.UserAnswerLog
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class UserAnswerLogDAO {
	private static final Logger	log				= LoggerFactory.getLogger(UserAnswerLogDAO.class);
	// property constants
	public static final String	USER_ID			= "userId";
	public static final String	PAPER_ID		= "paperId";
	public static final String	QUESTION_ID		= "questionId";
	public static final String	OPTION_ID		= "optionId";
	public static final String	ANSWER_SECOND	= "answerSecond";

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

	public void save(UserAnswerLog transientInstance) {
		log.debug("saving UserAnswerLog instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserAnswerLog persistentInstance) {
		log.debug("deleting UserAnswerLog instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserAnswerLog findById(java.lang.Integer id) {
		log.debug("getting UserAnswerLog instance with id: " + id);
		try {
			UserAnswerLog instance = (UserAnswerLog) getCurrentSession().get("com.xxbs.v1.db.UserAnswerLog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<UserAnswerLog> findByExample(UserAnswerLog instance) {
		log.debug("finding UserAnswerLog instance by example");
		try {
			List<UserAnswerLog> results = (List<UserAnswerLog>) getCurrentSession().createCriteria("com.xxbs.v1.db.UserAnswerLog").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding UserAnswerLog instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from UserAnswerLog as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<UserAnswerLog> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List<UserAnswerLog> findByPaperId(Object paperId) {
		return findByProperty(PAPER_ID, paperId);
	}

	public List<UserAnswerLog> findByQuestionId(Object questionId) {
		return findByProperty(QUESTION_ID, questionId);
	}

	public List<UserAnswerLog> findByOptionId(Object optionId) {
		return findByProperty(OPTION_ID, optionId);
	}

	public List<UserAnswerLog> findByAnswerSecond(Object answerSecond) {
		return findByProperty(ANSWER_SECOND, answerSecond);
	}

	public List findAll() {
		log.debug("finding all UserAnswerLog instances");
		try {
			String queryString = "from UserAnswerLog";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public UserAnswerLog merge(UserAnswerLog detachedInstance) {
		log.debug("merging UserAnswerLog instance");
		try {
			UserAnswerLog result = (UserAnswerLog) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserAnswerLog instance) {
		log.debug("attaching dirty UserAnswerLog instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserAnswerLog instance) {
		log.debug("attaching clean UserAnswerLog instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserAnswerLogDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserAnswerLogDAO) ctx.getBean("UserAnswerLogDAO");
	}
}