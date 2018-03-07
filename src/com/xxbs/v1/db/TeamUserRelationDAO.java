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
 * A data access object (DAO) providing persistence and search support for TeamUserRelation entities. Transaction control of the save(), update() and delete() operations can
 * directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information
 * for how to configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.TeamUserRelation
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class TeamUserRelationDAO {
	private static final Logger	log				= LoggerFactory.getLogger(TeamUserRelationDAO.class);
	// property constants
	public static final String	TEAM_ID			= "teamId";
	public static final String	USER_ID			= "userId";
	public static final String	TEAM_USER_NAME	= "teamUserName";
	public static final String	TEAM_USER_ROLE	= "teamUserRole";
	public static final String	TYPE			= "type";

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

	public void save(TeamUserRelation transientInstance) {
		log.debug("saving TeamUserRelation instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TeamUserRelation persistentInstance) {
		log.debug("deleting TeamUserRelation instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TeamUserRelation findById(java.lang.Integer id) {
		log.debug("getting TeamUserRelation instance with id: " + id);
		try {
			TeamUserRelation instance = (TeamUserRelation) getCurrentSession().get("com.xxbs.v1.db.TeamUserRelation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TeamUserRelation> findByExample(TeamUserRelation instance) {
		log.debug("finding TeamUserRelation instance by example");
		try {
			List<TeamUserRelation> results = (List<TeamUserRelation>) getCurrentSession().createCriteria("com.xxbs.v1.db.TeamUserRelation")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TeamUserRelation instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from TeamUserRelation as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TeamUserRelation> findByTeamId(Object teamId) {
		return findByProperty(TEAM_ID, teamId);
	}

	public List<TeamUserRelation> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List<TeamUserRelation> findByTeamUserName(Object teamUserName) {
		return findByProperty(TEAM_USER_NAME, teamUserName);
	}

	public List<TeamUserRelation> findByTeamUserRole(Object teamUserRole) {
		return findByProperty(TEAM_USER_ROLE, teamUserRole);
	}

	public List<TeamUserRelation> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findAll() {
		log.debug("finding all TeamUserRelation instances");
		try {
			String queryString = "from TeamUserRelation";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TeamUserRelation merge(TeamUserRelation detachedInstance) {
		log.debug("merging TeamUserRelation instance");
		try {
			TeamUserRelation result = (TeamUserRelation) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TeamUserRelation instance) {
		log.debug("attaching dirty TeamUserRelation instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TeamUserRelation instance) {
		log.debug("attaching clean TeamUserRelation instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TeamUserRelationDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TeamUserRelationDAO) ctx.getBean("TeamUserRelationDAO");
	}
}