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
 * A data access object (DAO) providing persistence and search support for QuestionTagRelation entities. Transaction control of the save(), update() and delete() operations can
 * directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information
 * for how to configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.QuestionTagRelation
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class QuestionTagRelationDAO {
	private static final Logger	log			= LoggerFactory.getLogger(QuestionTagRelationDAO.class);
	// property constants
	public static final String	QUESTION_ID	= "questionId";
	public static final String	TAG_ID		= "tagId";

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

	public void save(QuestionTagRelation transientInstance) {
		log.debug("saving QuestionTagRelation instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(QuestionTagRelation persistentInstance) {
		log.debug("deleting QuestionTagRelation instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public QuestionTagRelation findById(java.lang.Integer id) {
		log.debug("getting QuestionTagRelation instance with id: " + id);
		try {
			QuestionTagRelation instance = (QuestionTagRelation) getCurrentSession().get("com.xxbs.v1.db.QuestionTagRelation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<QuestionTagRelation> findByExample(QuestionTagRelation instance) {
		log.debug("finding QuestionTagRelation instance by example");
		try {
			List<QuestionTagRelation> results = (List<QuestionTagRelation>) getCurrentSession().createCriteria("com.xxbs.v1.db.QuestionTagRelation")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding QuestionTagRelation instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from QuestionTagRelation as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<QuestionTagRelation> findByQuestionId(Object questionId) {
		return findByProperty(QUESTION_ID, questionId);
	}

	public List<QuestionTagRelation> findByTagId(Object tagId) {
		return findByProperty(TAG_ID, tagId);
	}

	public List findAll() {
		log.debug("finding all QuestionTagRelation instances");
		try {
			String queryString = "from QuestionTagRelation";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public QuestionTagRelation merge(QuestionTagRelation detachedInstance) {
		log.debug("merging QuestionTagRelation instance");
		try {
			QuestionTagRelation result = (QuestionTagRelation) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(QuestionTagRelation instance) {
		log.debug("attaching dirty QuestionTagRelation instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(QuestionTagRelation instance) {
		log.debug("attaching clean QuestionTagRelation instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static QuestionTagRelationDAO getFromApplicationContext(ApplicationContext ctx) {
		return (QuestionTagRelationDAO) ctx.getBean("QuestionTagRelationDAO");
	}
}