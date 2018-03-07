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
 * A data access object (DAO) providing persistence and search support for Blog entities. Transaction control of the save(), update() and delete() operations can directly support
 * Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information for how to
 * configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.Blog
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class BlogDAO {
	private static final Logger	log				= LoggerFactory.getLogger(BlogDAO.class);
	// property constants
	public static final String	USER_ID			= "userId";
	public static final String	BLOG_TYPE		= "blogType";
	public static final String	OPEN_TYPE		= "openType";
	public static final String	SOURCE_BLOG_ID	= "sourceBlogId";
	public static final String	TITLE			= "title";
	public static final String	ATTACHMENT		= "attachment";
	public static final String	ATTACHMENT_TYPE	= "attachmentType";
	public static final String	CITY			= "city";
	public static final String	CONTENT			= "content";
	public static final String	IS_ANONYMOUS	= "isAnonymous";
	public static final String	COMMENT_SETTING	= "commentSetting";
	public static final String	PRAISE_COUNT	= "praiseCount";
	public static final String	TRAMPLE_COUNT	= "trampleCount";
	public static final String	COMMENT_COUNT	= "commentCount";
	public static final String	STATUS			= "status";

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

	public void save(Blog transientInstance) {
		log.debug("saving Blog instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Blog persistentInstance) {
		log.debug("deleting Blog instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Blog findById(java.lang.Integer id) {
		log.debug("getting Blog instance with id: " + id);
		try {
			Blog instance = (Blog) getCurrentSession().get("com.xxbs.v1.db.Blog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Blog> findByExample(Blog instance) {
		log.debug("finding Blog instance by example");
		try {
			List<Blog> results = (List<Blog>) getCurrentSession().createCriteria("com.xxbs.v1.db.Blog").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Blog instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Blog as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Blog> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List<Blog> findByBlogType(Object blogType) {
		return findByProperty(BLOG_TYPE, blogType);
	}

	public List<Blog> findByOpenType(Object openType) {
		return findByProperty(OPEN_TYPE, openType);
	}

	public List<Blog> findBySourceBlogId(Object sourceBlogId) {
		return findByProperty(SOURCE_BLOG_ID, sourceBlogId);
	}

	public List<Blog> findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List<Blog> findByAttachment(Object attachment) {
		return findByProperty(ATTACHMENT, attachment);
	}

	public List<Blog> findByAttachmentType(Object attachmentType) {
		return findByProperty(ATTACHMENT_TYPE, attachmentType);
	}

	public List<Blog> findByCity(Object city) {
		return findByProperty(CITY, city);
	}

	public List<Blog> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List<Blog> findByIsAnonymous(Object isAnonymous) {
		return findByProperty(IS_ANONYMOUS, isAnonymous);
	}

	public List<Blog> findByCommentSetting(Object commentSetting) {
		return findByProperty(COMMENT_SETTING, commentSetting);
	}

	public List<Blog> findByPraiseCount(Object praiseCount) {
		return findByProperty(PRAISE_COUNT, praiseCount);
	}

	public List<Blog> findByTrampleCount(Object trampleCount) {
		return findByProperty(TRAMPLE_COUNT, trampleCount);
	}

	public List<Blog> findByCommentCount(Object commentCount) {
		return findByProperty(COMMENT_COUNT, commentCount);
	}

	public List<Blog> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all Blog instances");
		try {
			String queryString = "from Blog";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Blog merge(Blog detachedInstance) {
		log.debug("merging Blog instance");
		try {
			Blog result = (Blog) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Blog instance) {
		log.debug("attaching dirty Blog instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Blog instance) {
		log.debug("attaching clean Blog instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BlogDAO getFromApplicationContext(ApplicationContext ctx) {
		return (BlogDAO) ctx.getBean("BlogDAO");
	}
}