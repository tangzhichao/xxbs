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
 * A data access object (DAO) providing persistence and search support for SystemMessage entities. Transaction control of the save(), update() and delete() operations can directly
 * support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.SystemMessage
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class SystemMessageDAO {
	private static final Logger	log					= LoggerFactory.getLogger(SystemMessageDAO.class);
	// property constants
	public static final String	TITLE				= "title";
	public static final String	CONTENT				= "content";
	public static final String	DATAINFO			= "datainfo";
	public static final String	TYPE				= "type";
	public static final String	TARGET_USER_ID		= "targetUserId";
	public static final String	SOURCE_USER_ID		= "sourceUserId";
	public static final String	SOURCE_BLOG_ID		= "sourceBlogId";
	public static final String	SOURCE_COMMENT_ID	= "sourceCommentId";
	public static final String	STATUS				= "status";

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

	public void save(SystemMessage transientInstance) {
		log.debug("saving SystemMessage instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SystemMessage persistentInstance) {
		log.debug("deleting SystemMessage instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SystemMessage findById(java.lang.Integer id) {
		log.debug("getting SystemMessage instance with id: " + id);
		try {
			SystemMessage instance = (SystemMessage) getCurrentSession().get("com.xxbs.v1.db.SystemMessage", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SystemMessage> findByExample(SystemMessage instance) {
		log.debug("finding SystemMessage instance by example");
		try {
			List<SystemMessage> results = (List<SystemMessage>) getCurrentSession().createCriteria("com.xxbs.v1.db.SystemMessage").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding SystemMessage instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from SystemMessage as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SystemMessage> findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List<SystemMessage> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List<SystemMessage> findByDatainfo(Object datainfo) {
		return findByProperty(DATAINFO, datainfo);
	}

	public List<SystemMessage> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<SystemMessage> findByTargetUserId(Object targetUserId) {
		return findByProperty(TARGET_USER_ID, targetUserId);
	}

	public List<SystemMessage> findBySourceUserId(Object sourceUserId) {
		return findByProperty(SOURCE_USER_ID, sourceUserId);
	}

	public List<SystemMessage> findBySourceBlogId(Object sourceBlogId) {
		return findByProperty(SOURCE_BLOG_ID, sourceBlogId);
	}

	public List<SystemMessage> findBySourceCommentId(Object sourceCommentId) {
		return findByProperty(SOURCE_COMMENT_ID, sourceCommentId);
	}

	public List<SystemMessage> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all SystemMessage instances");
		try {
			String queryString = "from SystemMessage";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SystemMessage merge(SystemMessage detachedInstance) {
		log.debug("merging SystemMessage instance");
		try {
			SystemMessage result = (SystemMessage) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SystemMessage instance) {
		log.debug("attaching dirty SystemMessage instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SystemMessage instance) {
		log.debug("attaching clean SystemMessage instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static SystemMessageDAO getFromApplicationContext(ApplicationContext ctx) {
		return (SystemMessageDAO) ctx.getBean("SystemMessageDAO");
	}
}