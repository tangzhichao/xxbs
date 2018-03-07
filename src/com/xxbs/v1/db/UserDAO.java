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
 * A data access object (DAO) providing persistence and search support for User entities. Transaction control of the save(), update() and delete() operations can directly support
 * Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides additional information for how to
 * configure it for the desired type of transaction control.
 * 
 * @see com.xxbs.v1.db.User
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class UserDAO {
	private static final Logger	log						= LoggerFactory.getLogger(UserDAO.class);
	// property constants
	public static final String	PHONE_NUMBER			= "phoneNumber";
	public static final String	EMAIL					= "email";
	public static final String	NICK_NAME				= "nickName";
	public static final String	PASSWORD				= "password";
	public static final String	ROLE_ID					= "roleId";
	public static final String	SEX						= "sex";
	public static final String	BRITHDAY_CALENDAR		= "brithdayCalendar";
	public static final String	AGE						= "age";
	public static final String	IMAGES					= "images";
	public static final String	EMOTIONAL_STATE			= "emotionalState";
	public static final String	STATEMENT				= "statement";
	public static final String	LEARNING_PHASE_ID		= "learningPhaseId";
	public static final String	LEVEL					= "level";
	public static final String	GOLD					= "gold";
	public static final String	LAT						= "lat";
	public static final String	LNG						= "lng";
	public static final String	CITY					= "city";
	public static final String	LIVE_AREA_ID			= "liveAreaId";
	public static final String	CURRENT_AREA_ID			= "currentAreaId";
	public static final String	LEARN_MODE_IS_OPEN		= "learnModeIsOpen";
	public static final String	READY_EXAM_ID			= "readyExamId";
	public static final String	ANSWER_COUNT			= "answerCount";
	public static final String	ANSWER_CORRECT_COUNT	= "answerCorrectCount";
	public static final String	MATCH_COUNT				= "matchCount";
	public static final String	MATCH_WIN_COUNT			= "matchWinCount";
	public static final String	LAST_LOGIN_IP			= "lastLoginIp";
	public static final String	VALID					= "valid";

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

	public void save(User transientInstance) {
		log.debug("saving User instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(User persistentInstance) {
		log.debug("deleting User instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public User findById(java.lang.Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getCurrentSession().get("com.xxbs.v1.db.User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<User> findByExample(User instance) {
		log.debug("finding User instance by example");
		try {
			List<User> results = (List<User>) getCurrentSession().createCriteria("com.xxbs.v1.db.User").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding User instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from User as model where model." + propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<User> findByPhoneNumber(Object phoneNumber) {
		return findByProperty(PHONE_NUMBER, phoneNumber);
	}

	public List<User> findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List<User> findByNickName(Object nickName) {
		return findByProperty(NICK_NAME, nickName);
	}

	public List<User> findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List<User> findByRoleId(Object roleId) {
		return findByProperty(ROLE_ID, roleId);
	}

	public List<User> findBySex(Object sex) {
		return findByProperty(SEX, sex);
	}

	public List<User> findByBrithdayCalendar(Object brithdayCalendar) {
		return findByProperty(BRITHDAY_CALENDAR, brithdayCalendar);
	}

	public List<User> findByAge(Object age) {
		return findByProperty(AGE, age);
	}

	public List<User> findByImages(Object images) {
		return findByProperty(IMAGES, images);
	}

	public List<User> findByEmotionalState(Object emotionalState) {
		return findByProperty(EMOTIONAL_STATE, emotionalState);
	}

	public List<User> findByStatement(Object statement) {
		return findByProperty(STATEMENT, statement);
	}

	public List<User> findByLearningPhaseId(Object learningPhaseId) {
		return findByProperty(LEARNING_PHASE_ID, learningPhaseId);
	}

	public List<User> findByLevel(Object level) {
		return findByProperty(LEVEL, level);
	}

	public List<User> findByGold(Object gold) {
		return findByProperty(GOLD, gold);
	}

	public List<User> findByLat(Object lat) {
		return findByProperty(LAT, lat);
	}

	public List<User> findByLng(Object lng) {
		return findByProperty(LNG, lng);
	}

	public List<User> findByCity(Object city) {
		return findByProperty(CITY, city);
	}

	public List<User> findByLiveAreaId(Object liveAreaId) {
		return findByProperty(LIVE_AREA_ID, liveAreaId);
	}

	public List<User> findByCurrentAreaId(Object currentAreaId) {
		return findByProperty(CURRENT_AREA_ID, currentAreaId);
	}

	public List<User> findByLearnModeIsOpen(Object learnModeIsOpen) {
		return findByProperty(LEARN_MODE_IS_OPEN, learnModeIsOpen);
	}

	public List<User> findByReadyExamId(Object readyExamId) {
		return findByProperty(READY_EXAM_ID, readyExamId);
	}

	public List<User> findByAnswerCount(Object answerCount) {
		return findByProperty(ANSWER_COUNT, answerCount);
	}

	public List<User> findByAnswerCorrectCount(Object answerCorrectCount) {
		return findByProperty(ANSWER_CORRECT_COUNT, answerCorrectCount);
	}

	public List<User> findByMatchCount(Object matchCount) {
		return findByProperty(MATCH_COUNT, matchCount);
	}

	public List<User> findByMatchWinCount(Object matchWinCount) {
		return findByProperty(MATCH_WIN_COUNT, matchWinCount);
	}

	public List<User> findByLastLoginIp(Object lastLoginIp) {
		return findByProperty(LAST_LOGIN_IP, lastLoginIp);
	}

	public List<User> findByValid(Object valid) {
		return findByProperty(VALID, valid);
	}

	public List findAll() {
		log.debug("finding all User instances");
		try {
			String queryString = "from User";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public User merge(User detachedInstance) {
		log.debug("merging User instance");
		try {
			User result = (User) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(User instance) {
		log.debug("attaching dirty User instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(User instance) {
		log.debug("attaching clean User instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserDAO) ctx.getBean("UserDAO");
	}
}