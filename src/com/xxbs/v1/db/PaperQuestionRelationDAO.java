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
 * A data access object (DAO) providing persistence and search support for PaperQuestionRelation entities. Transaction control of the save(), update() and delete() operations can
 * directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information
 * for how to configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.PaperQuestionRelation
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class PaperQuestionRelationDAO {
	private static final Logger	log			= LoggerFactory.getLogger(PaperQuestionRelationDAO.class);
	// property constants
	public static final String	PAPER_ID	= "paperId";
	public static final String	QUESTION_ID	= "questionId";
	public static final String	SORT_ORDER	= "sortOrder";

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

	public void save(PaperQuestionRelation transientInstance) {
		log.debug("saving PaperQuestionRelation instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PaperQuestionRelation persistentInstance) {
		log.debug("deleting PaperQuestionRelation instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PaperQuestionRelation findById(java.lang.Integer id) {
		log.debug("getting PaperQuestionRelation instance with id: " + id);
		try {
			PaperQuestionRelation instance = (PaperQuestionRelation) getCurrentSession().get("com.xxbs.v1.db.PaperQuestionRelation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<PaperQuestionRelation> findByExample(PaperQuestionRelation instance) {
		log.debug("finding PaperQuestionRelation instance by example");
		try {
			List<PaperQuestionRelation> results = (List<PaperQuestionRelation>) getCurrentSession().createCriteria("com.xxbs.v1.db.PaperQuestionRelation")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding PaperQuestionRelation instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from PaperQuestionRelation as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<PaperQuestionRelation> findByPaperId(Object paperId) {
		return findByProperty(PAPER_ID, paperId);
	}

	public List<PaperQuestionRelation> findByQuestionId(Object questionId) {
		return findByProperty(QUESTION_ID, questionId);
	}

	public List<PaperQuestionRelation> findBySortOrder(Object sortOrder) {
		return findByProperty(SORT_ORDER, sortOrder);
	}

	public List findAll() {
		log.debug("finding all PaperQuestionRelation instances");
		try {
			String queryString = "from PaperQuestionRelation";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PaperQuestionRelation merge(PaperQuestionRelation detachedInstance) {
		log.debug("merging PaperQuestionRelation instance");
		try {
			PaperQuestionRelation result = (PaperQuestionRelation) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PaperQuestionRelation instance) {
		log.debug("attaching dirty PaperQuestionRelation instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PaperQuestionRelation instance) {
		log.debug("attaching clean PaperQuestionRelation instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PaperQuestionRelationDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PaperQuestionRelationDAO) ctx.getBean("PaperQuestionRelationDAO");
	}
}