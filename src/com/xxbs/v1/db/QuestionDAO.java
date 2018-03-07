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
 * A data access object (DAO) providing persistence and search support for Question entities. Transaction control of the save(), update() and delete() operations can directly
 * support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.Question
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class QuestionDAO {
	private static final Logger	log						= LoggerFactory.getLogger(QuestionDAO.class);
	// property constants
	public static final String	USER_ID					= "userId";
	public static final String	TITLE					= "title";
	public static final String	REMARKS					= "remarks";
	public static final String	ANALYSIS				= "analysis";
	public static final String	CREATE_FROM				= "createFrom";
	public static final String	TYPE					= "type";
	public static final String	ANSWER_COUNT			= "answerCount";
	public static final String	POINTS					= "points";
	public static final String	ANSWER_SECOND			= "answerSecond";
	public static final String	LEARNING_PHASE_ID		= "learningPhaseId";
	public static final String	KNOWLEDGE_POINT_TAG_ID	= "knowledgePointTagId";
	public static final String	STATUS					= "status";

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

	public void save(Question transientInstance) {
		log.debug("saving Question instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Question persistentInstance) {
		log.debug("deleting Question instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Question findById(java.lang.Integer id) {
		log.debug("getting Question instance with id: " + id);
		try {
			Question instance = (Question) getCurrentSession().get("com.xxbs.v1.db.Question", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Question> findByExample(Question instance) {
		log.debug("finding Question instance by example");
		try {
			List<Question> results = (List<Question>) getCurrentSession().createCriteria("com.xxbs.v1.db.Question").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Question instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Question as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Question> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List<Question> findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List<Question> findByRemarks(Object remarks) {
		return findByProperty(REMARKS, remarks);
	}

	public List<Question> findByAnalysis(Object analysis) {
		return findByProperty(ANALYSIS, analysis);
	}

	public List<Question> findByCreateFrom(Object createFrom) {
		return findByProperty(CREATE_FROM, createFrom);
	}

	public List<Question> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<Question> findByAnswerCount(Object answerCount) {
		return findByProperty(ANSWER_COUNT, answerCount);
	}

	public List<Question> findByPoints(Object points) {
		return findByProperty(POINTS, points);
	}

	public List<Question> findByAnswerSecond(Object answerSecond) {
		return findByProperty(ANSWER_SECOND, answerSecond);
	}

	public List<Question> findByLearningPhaseId(Object learningPhaseId) {
		return findByProperty(LEARNING_PHASE_ID, learningPhaseId);
	}

	public List<Question> findByKnowledgePointTagId(Object knowledgePointTagId) {
		return findByProperty(KNOWLEDGE_POINT_TAG_ID, knowledgePointTagId);
	}

	public List<Question> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all Question instances");
		try {
			String queryString = "from Question";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Question merge(Question detachedInstance) {
		log.debug("merging Question instance");
		try {
			Question result = (Question) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Question instance) {
		log.debug("attaching dirty Question instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Question instance) {
		log.debug("attaching clean Question instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static QuestionDAO getFromApplicationContext(ApplicationContext ctx) {
		return (QuestionDAO) ctx.getBean("QuestionDAO");
	}
}