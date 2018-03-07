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
 * A data access object (DAO) providing persistence and search support for Exam entities. Transaction control of the save(), update() and delete() operations can directly support
 * Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information for how to
 * configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.Exam
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class ExamDAO {
	private static final Logger	log					= LoggerFactory.getLogger(ExamDAO.class);
	// property constants
	public static final String	TYPE_NAME			= "typeName";
	public static final String	NAME				= "name";
	public static final String	AREA_ID				= "areaId";
	public static final String	LEARNING_PHASE_ID	= "learningPhaseId";

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

	public void save(Exam transientInstance) {
		log.debug("saving Exam instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Exam persistentInstance) {
		log.debug("deleting Exam instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Exam findById(java.lang.Integer id) {
		log.debug("getting Exam instance with id: " + id);
		try {
			Exam instance = (Exam) getCurrentSession().get("com.xxbs.v1.db.Exam", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Exam> findByExample(Exam instance) {
		log.debug("finding Exam instance by example");
		try {
			List<Exam> results = (List<Exam>) getCurrentSession().createCriteria("com.xxbs.v1.db.Exam").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Exam instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Exam as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Exam> findByTypeName(Object typeName) {
		return findByProperty(TYPE_NAME, typeName);
	}

	public List<Exam> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Exam> findByAreaId(Object areaId) {
		return findByProperty(AREA_ID, areaId);
	}

	public List<Exam> findByLearningPhaseId(Object learningPhaseId) {
		return findByProperty(LEARNING_PHASE_ID, learningPhaseId);
	}

	public List findAll() {
		log.debug("finding all Exam instances");
		try {
			String queryString = "from Exam";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Exam merge(Exam detachedInstance) {
		log.debug("merging Exam instance");
		try {
			Exam result = (Exam) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Exam instance) {
		log.debug("attaching dirty Exam instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Exam instance) {
		log.debug("attaching clean Exam instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ExamDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ExamDAO) ctx.getBean("ExamDAO");
	}
}