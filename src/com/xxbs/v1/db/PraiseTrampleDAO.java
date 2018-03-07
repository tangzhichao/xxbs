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
 * A data access object (DAO) providing persistence and search support for PraiseTrample entities. Transaction control of the save(), update() and delete() operations can directly
 * support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.PraiseTrample
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class PraiseTrampleDAO {
	private static final Logger	log			= LoggerFactory.getLogger(PraiseTrampleDAO.class);
	// property constants
	public static final String	USER_ID		= "userId";
	public static final String	BLOG_ID		= "blogId";
	public static final String	COMMENT_ID	= "commentId";
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

	public void save(PraiseTrample transientInstance) {
		log.debug("saving PraiseTrample instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PraiseTrample persistentInstance) {
		log.debug("deleting PraiseTrample instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PraiseTrample findById(java.lang.Integer id) {
		log.debug("getting PraiseTrample instance with id: " + id);
		try {
			PraiseTrample instance = (PraiseTrample) getCurrentSession().get("com.xxbs.v1.db.PraiseTrample", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<PraiseTrample> findByExample(PraiseTrample instance) {
		log.debug("finding PraiseTrample instance by example");
		try {
			List<PraiseTrample> results = (List<PraiseTrample>) getCurrentSession().createCriteria("com.xxbs.v1.db.PraiseTrample").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding PraiseTrample instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from PraiseTrample as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<PraiseTrample> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List<PraiseTrample> findByBlogId(Object blogId) {
		return findByProperty(BLOG_ID, blogId);
	}

	public List<PraiseTrample> findByCommentId(Object commentId) {
		return findByProperty(COMMENT_ID, commentId);
	}

	public List<PraiseTrample> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all PraiseTrample instances");
		try {
			String queryString = "from PraiseTrample";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PraiseTrample merge(PraiseTrample detachedInstance) {
		log.debug("merging PraiseTrample instance");
		try {
			PraiseTrample result = (PraiseTrample) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PraiseTrample instance) {
		log.debug("attaching dirty PraiseTrample instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PraiseTrample instance) {
		log.debug("attaching clean PraiseTrample instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PraiseTrampleDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PraiseTrampleDAO) ctx.getBean("PraiseTrampleDAO");
	}
}