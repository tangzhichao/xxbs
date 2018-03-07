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
 * A data access object (DAO) providing persistence and search support for Paper entities. Transaction control of the save(), update() and delete() operations can directly support
 * Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information for how to
 * configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.Paper
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class PaperDAO {
	private static final Logger	log					= LoggerFactory.getLogger(PaperDAO.class);
	// property constants
	public static final String	USER_ID				= "userId";
	public static final String	NAME				= "name";
	public static final String	TYPE				= "type";
	public static final String	LEARNING_PHASE_ID	= "learningPhaseId";
	public static final String	EXAM_ID				= "examId";
	public static final String	POINTS				= "points";
	public static final String	GOLD				= "gold";
	public static final String	AREA_ID				= "areaId";
	public static final String	ANSWER_SECOND		= "answerSecond";

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

	public void save(Paper transientInstance) {
		log.debug("saving Paper instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Paper persistentInstance) {
		log.debug("deleting Paper instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Paper findById(java.lang.Integer id) {
		log.debug("getting Paper instance with id: " + id);
		try {
			Paper instance = (Paper) getCurrentSession().get("com.xxbs.v1.db.Paper", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Paper> findByExample(Paper instance) {
		log.debug("finding Paper instance by example");
		try {
			List<Paper> results = (List<Paper>) getCurrentSession().createCriteria("com.xxbs.v1.db.Paper").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Paper instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Paper as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Paper> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List<Paper> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Paper> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<Paper> findByLearningPhaseId(Object learningPhaseId) {
		return findByProperty(LEARNING_PHASE_ID, learningPhaseId);
	}

	public List<Paper> findByExamId(Object examId) {
		return findByProperty(EXAM_ID, examId);
	}

	public List<Paper> findByPoints(Object points) {
		return findByProperty(POINTS, points);
	}

	public List<Paper> findByGold(Object gold) {
		return findByProperty(GOLD, gold);
	}

	public List<Paper> findByAreaId(Object areaId) {
		return findByProperty(AREA_ID, areaId);
	}

	public List<Paper> findByAnswerSecond(Object answerSecond) {
		return findByProperty(ANSWER_SECOND, answerSecond);
	}

	public List findAll() {
		log.debug("finding all Paper instances");
		try {
			String queryString = "from Paper";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Paper merge(Paper detachedInstance) {
		log.debug("merging Paper instance");
		try {
			Paper result = (Paper) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Paper instance) {
		log.debug("attaching dirty Paper instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Paper instance) {
		log.debug("attaching clean Paper instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PaperDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PaperDAO) ctx.getBean("PaperDAO");
	}
}