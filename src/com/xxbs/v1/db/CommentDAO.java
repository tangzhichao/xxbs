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
 * A data access object (DAO) providing persistence and search support for Comment entities. Transaction control of the save(), update() and delete() operations can directly
 * support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.Comment
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class CommentDAO {
	private static final Logger	log					= LoggerFactory.getLogger(CommentDAO.class);
	// property constants
	public static final String	USER_ID				= "userId";
	public static final String	TARGET_BLOG_ID		= "targetBlogId";
	public static final String	TARGET_COMMENT_ID	= "targetCommentId";
	public static final String	CONTENT				= "content";
	public static final String	FLOOR_NUMBER		= "floorNumber";
	public static final String	PRAISE_COUNT		= "praiseCount";
	public static final String	TRAMPLE_COUNT		= "trampleCount";
	public static final String	ATTACHMENT			= "attachment";
	public static final String	ATTACHMENT_TYPE		= "attachmentType";
	public static final String	OPEN_TYPE			= "openType";
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

	public void save(Comment transientInstance) {
		log.debug("saving Comment instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Comment persistentInstance) {
		log.debug("deleting Comment instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Comment findById(java.lang.Integer id) {
		log.debug("getting Comment instance with id: " + id);
		try {
			Comment instance = (Comment) getCurrentSession().get("com.xxbs.v1.db.Comment", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Comment> findByExample(Comment instance) {
		log.debug("finding Comment instance by example");
		try {
			List<Comment> results = (List<Comment>) getCurrentSession().createCriteria("com.xxbs.v1.db.Comment").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Comment instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Comment as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Comment> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List<Comment> findByTargetBlogId(Object targetBlogId) {
		return findByProperty(TARGET_BLOG_ID, targetBlogId);
	}

	public List<Comment> findByTargetCommentId(Object targetCommentId) {
		return findByProperty(TARGET_COMMENT_ID, targetCommentId);
	}

	public List<Comment> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List<Comment> findByFloorNumber(Object floorNumber) {
		return findByProperty(FLOOR_NUMBER, floorNumber);
	}

	public List<Comment> findByPraiseCount(Object praiseCount) {
		return findByProperty(PRAISE_COUNT, praiseCount);
	}

	public List<Comment> findByTrampleCount(Object trampleCount) {
		return findByProperty(TRAMPLE_COUNT, trampleCount);
	}

	public List<Comment> findByAttachment(Object attachment) {
		return findByProperty(ATTACHMENT, attachment);
	}

	public List<Comment> findByAttachmentType(Object attachmentType) {
		return findByProperty(ATTACHMENT_TYPE, attachmentType);
	}

	public List<Comment> findByOpenType(Object openType) {
		return findByProperty(OPEN_TYPE, openType);
	}

	public List<Comment> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all Comment instances");
		try {
			String queryString = "from Comment";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Comment merge(Comment detachedInstance) {
		log.debug("merging Comment instance");
		try {
			Comment result = (Comment) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Comment instance) {
		log.debug("attaching dirty Comment instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Comment instance) {
		log.debug("attaching clean Comment instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CommentDAO getFromApplicationContext(ApplicationContext ctx) {
		return (CommentDAO) ctx.getBean("CommentDAO");
	}
}