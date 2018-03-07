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
 * A data access object (DAO) providing persistence and search support for Feedback entities. Transaction control of the save(), update() and delete() operations can directly
 * support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.Feedback
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class FeedbackDAO {
	private static final Logger	log					= LoggerFactory.getLogger(FeedbackDAO.class);
	// property constants
	public static final String	USER_ID				= "userId";
	public static final String	TYPE				= "type";
	public static final String	CONTENT				= "content";
	public static final String	ATTACHMENT			= "attachment";
	public static final String	ATTACHMENT_TYPE		= "attachmentType";
	public static final String	STATUS				= "status";
	public static final String	NO_HANDLE_REASON	= "noHandleReason";

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

	public void save(Feedback transientInstance) {
		log.debug("saving Feedback instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Feedback persistentInstance) {
		log.debug("deleting Feedback instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Feedback findById(java.lang.Integer id) {
		log.debug("getting Feedback instance with id: " + id);
		try {
			Feedback instance = (Feedback) getCurrentSession().get("com.xxbs.v1.db.Feedback", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Feedback> findByExample(Feedback instance) {
		log.debug("finding Feedback instance by example");
		try {
			List<Feedback> results = (List<Feedback>) getCurrentSession().createCriteria("com.xxbs.v1.db.Feedback").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Feedback instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Feedback as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Feedback> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List<Feedback> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<Feedback> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List<Feedback> findByAttachment(Object attachment) {
		return findByProperty(ATTACHMENT, attachment);
	}

	public List<Feedback> findByAttachmentType(Object attachmentType) {
		return findByProperty(ATTACHMENT_TYPE, attachmentType);
	}

	public List<Feedback> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<Feedback> findByNoHandleReason(Object noHandleReason) {
		return findByProperty(NO_HANDLE_REASON, noHandleReason);
	}

	public List findAll() {
		log.debug("finding all Feedback instances");
		try {
			String queryString = "from Feedback";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Feedback merge(Feedback detachedInstance) {
		log.debug("merging Feedback instance");
		try {
			Feedback result = (Feedback) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Feedback instance) {
		log.debug("attaching dirty Feedback instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Feedback instance) {
		log.debug("attaching clean Feedback instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static FeedbackDAO getFromApplicationContext(ApplicationContext ctx) {
		return (FeedbackDAO) ctx.getBean("FeedbackDAO");
	}
}