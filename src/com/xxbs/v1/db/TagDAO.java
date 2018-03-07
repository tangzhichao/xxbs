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
 * A data access object (DAO) providing persistence and search support for Tag entities. Transaction control of the save(), update() and delete() operations can directly support
 * Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information for how to
 * configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.Tag
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class TagDAO {
	private static final Logger	log			= LoggerFactory.getLogger(TagDAO.class);
	// property constants
	public static final String	PARENT_ID	= "parentId";
	public static final String	NAME		= "name";
	public static final String	LEVEL		= "level";
	public static final String	TYPE		= "type";

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

	public void save(Tag transientInstance) {
		log.debug("saving Tag instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Tag persistentInstance) {
		log.debug("deleting Tag instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tag findById(java.lang.Integer id) {
		log.debug("getting Tag instance with id: " + id);
		try {
			Tag instance = (Tag) getCurrentSession().get("com.xxbs.v1.db.Tag", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Tag> findByExample(Tag instance) {
		log.debug("finding Tag instance by example");
		try {
			List<Tag> results = (List<Tag>) getCurrentSession().createCriteria("com.xxbs.v1.db.Tag").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Tag instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Tag as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Tag> findByParentId(Object parentId) {
		return findByProperty(PARENT_ID, parentId);
	}

	public List<Tag> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Tag> findByLevel(Object level) {
		return findByProperty(LEVEL, level);
	}

	public List<Tag> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findAll() {
		log.debug("finding all Tag instances");
		try {
			String queryString = "from Tag";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Tag merge(Tag detachedInstance) {
		log.debug("merging Tag instance");
		try {
			Tag result = (Tag) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tag instance) {
		log.debug("attaching dirty Tag instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tag instance) {
		log.debug("attaching clean Tag instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TagDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TagDAO) ctx.getBean("TagDAO");
	}
}