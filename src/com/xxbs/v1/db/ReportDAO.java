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
 * A data access object (DAO) providing persistence and search support for Report entities. Transaction control of the save(), update() and delete() operations can directly support
 * Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information for how to
 * configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.Report
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class ReportDAO {
	private static final Logger	log					= LoggerFactory.getLogger(ReportDAO.class);
	// property constants
	public static final String	SOURCE_USER_ID		= "sourceUserId";
	public static final String	TARGET_USER_ID		= "targetUserId";
	public static final String	TARGET_BLOG_ID		= "targetBlogId";
	public static final String	TARGET_COMMENT_ID	= "targetCommentId";
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

	public void save(Report transientInstance) {
		log.debug("saving Report instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Report persistentInstance) {
		log.debug("deleting Report instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Report findById(java.lang.Integer id) {
		log.debug("getting Report instance with id: " + id);
		try {
			Report instance = (Report) getCurrentSession().get("com.xxbs.v1.db.Report", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Report> findByExample(Report instance) {
		log.debug("finding Report instance by example");
		try {
			List<Report> results = (List<Report>) getCurrentSession().createCriteria("com.xxbs.v1.db.Report").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Report instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Report as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Report> findBySourceUserId(Object sourceUserId) {
		return findByProperty(SOURCE_USER_ID, sourceUserId);
	}

	public List<Report> findByTargetUserId(Object targetUserId) {
		return findByProperty(TARGET_USER_ID, targetUserId);
	}

	public List<Report> findByTargetBlogId(Object targetBlogId) {
		return findByProperty(TARGET_BLOG_ID, targetBlogId);
	}

	public List<Report> findByTargetCommentId(Object targetCommentId) {
		return findByProperty(TARGET_COMMENT_ID, targetCommentId);
	}

	public List<Report> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<Report> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List<Report> findByAttachment(Object attachment) {
		return findByProperty(ATTACHMENT, attachment);
	}

	public List<Report> findByAttachmentType(Object attachmentType) {
		return findByProperty(ATTACHMENT_TYPE, attachmentType);
	}

	public List<Report> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<Report> findByNoHandleReason(Object noHandleReason) {
		return findByProperty(NO_HANDLE_REASON, noHandleReason);
	}

	public List findAll() {
		log.debug("finding all Report instances");
		try {
			String queryString = "from Report";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Report merge(Report detachedInstance) {
		log.debug("merging Report instance");
		try {
			Report result = (Report) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Report instance) {
		log.debug("attaching dirty Report instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Report instance) {
		log.debug("attaching clean Report instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ReportDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ReportDAO) ctx.getBean("ReportDAO");
	}
}