package com.xxbs.v1.db;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user", catalog = "xxbs", uniqueConstraints = @UniqueConstraint(columnNames = "phone_number"))
public class User implements java.io.Serializable {

	// Fields

	private Integer		id;
	private String		phoneNumber;
	private String		email;
	private String		nickName;
	private String		password;
	private Integer		roleId;
	private Integer		sex;
	private Timestamp	brithday;
	private Integer		brithdayCalendar;
	private Integer		age;
	private String		images;
	private Integer		emotionalState;
	private String		statement;
	private Integer		learningPhaseId;
	private Integer		level;
	private Double		gold;
	private Double		lat;
	private Double		lng;
	private String		city;
	private Integer		liveAreaId;
	private Integer		currentAreaId;
	private Integer		learnModeIsOpen;
	private Integer		readyExamId;
	private Integer		answerCount;
	private Integer		answerCorrectCount;
	private Integer		matchCount;
	private Integer		matchWinCount;
	private Timestamp	lastLoginTime;
	private String		lastLoginIp;
	private Timestamp	lastModifyTime;
	private Timestamp	recordedTime;
	private Integer		valid;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String phoneNumber, Integer roleId, Integer learningPhaseId, Integer level, Double gold, Integer learnModeIsOpen, Integer answerCount,
			Integer answerCorrectCount, Integer matchCount, Integer matchWinCount, Timestamp recordedTime, Integer valid) {
		this.phoneNumber = phoneNumber;
		this.roleId = roleId;
		this.learningPhaseId = learningPhaseId;
		this.level = level;
		this.gold = gold;
		this.learnModeIsOpen = learnModeIsOpen;
		this.answerCount = answerCount;
		this.answerCorrectCount = answerCorrectCount;
		this.matchCount = matchCount;
		this.matchWinCount = matchWinCount;
		this.recordedTime = recordedTime;
		this.valid = valid;
	}

	/** full constructor */
	public User(String phoneNumber, String email, String nickName, String password, Integer roleId, Integer sex, Timestamp brithday, Integer brithdayCalendar,
			Integer age, String images, Integer emotionalState, String statement, Integer learningPhaseId, Integer level, Double gold, Double lat, Double lng,
			String city, Integer liveAreaId, Integer currentAreaId, Integer learnModeIsOpen, Integer readyExamId, Integer answerCount,
			Integer answerCorrectCount, Integer matchCount, Integer matchWinCount, Timestamp lastLoginTime, String lastLoginIp, Timestamp lastModifyTime,
			Timestamp recordedTime, Integer valid) {
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.nickName = nickName;
		this.password = password;
		this.roleId = roleId;
		this.sex = sex;
		this.brithday = brithday;
		this.brithdayCalendar = brithdayCalendar;
		this.age = age;
		this.images = images;
		this.emotionalState = emotionalState;
		this.statement = statement;
		this.learningPhaseId = learningPhaseId;
		this.level = level;
		this.gold = gold;
		this.lat = lat;
		this.lng = lng;
		this.city = city;
		this.liveAreaId = liveAreaId;
		this.currentAreaId = currentAreaId;
		this.learnModeIsOpen = learnModeIsOpen;
		this.readyExamId = readyExamId;
		this.answerCount = answerCount;
		this.answerCorrectCount = answerCorrectCount;
		this.matchCount = matchCount;
		this.matchWinCount = matchWinCount;
		this.lastLoginTime = lastLoginTime;
		this.lastLoginIp = lastLoginIp;
		this.lastModifyTime = lastModifyTime;
		this.recordedTime = recordedTime;
		this.valid = valid;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "phone_number", unique = true, nullable = false, length = 50)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "nick_name", length = 20)
	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "role_id", nullable = false)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "sex")
	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Column(name = "brithday", length = 0)
	public Timestamp getBrithday() {
		return this.brithday;
	}

	public void setBrithday(Timestamp brithday) {
		this.brithday = brithday;
	}

	@Column(name = "brithday_calendar")
	public Integer getBrithdayCalendar() {
		return this.brithdayCalendar;
	}

	public void setBrithdayCalendar(Integer brithdayCalendar) {
		this.brithdayCalendar = brithdayCalendar;
	}

	@Column(name = "age")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "images", length = 150)
	public String getImages() {
		return this.images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	@Column(name = "emotional_state")
	public Integer getEmotionalState() {
		return this.emotionalState;
	}

	public void setEmotionalState(Integer emotionalState) {
		this.emotionalState = emotionalState;
	}

	@Column(name = "statement", length = 50)
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	@Column(name = "learning_phase_id", nullable = false)
	public Integer getLearningPhaseId() {
		return this.learningPhaseId;
	}

	public void setLearningPhaseId(Integer learningPhaseId) {
		this.learningPhaseId = learningPhaseId;
	}

	@Column(name = "level", nullable = false)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "gold", nullable = false, precision = 10)
	public Double getGold() {
		return this.gold;
	}

	public void setGold(Double gold) {
		this.gold = gold;
	}

	@Column(name = "lat", precision = 20, scale = 17)
	public Double getLat() {
		return this.lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	@Column(name = "lng", precision = 20, scale = 17)
	public Double getLng() {
		return this.lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	@Column(name = "city", length = 100)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "live_area_id")
	public Integer getLiveAreaId() {
		return this.liveAreaId;
	}

	public void setLiveAreaId(Integer liveAreaId) {
		this.liveAreaId = liveAreaId;
	}

	@Column(name = "current_area_id")
	public Integer getCurrentAreaId() {
		return this.currentAreaId;
	}

	public void setCurrentAreaId(Integer currentAreaId) {
		this.currentAreaId = currentAreaId;
	}

	@Column(name = "learn_mode_is_open", nullable = false)
	public Integer getLearnModeIsOpen() {
		return this.learnModeIsOpen;
	}

	public void setLearnModeIsOpen(Integer learnModeIsOpen) {
		this.learnModeIsOpen = learnModeIsOpen;
	}

	@Column(name = "ready_exam_id")
	public Integer getReadyExamId() {
		return this.readyExamId;
	}

	public void setReadyExamId(Integer readyExamId) {
		this.readyExamId = readyExamId;
	}

	@Column(name = "answer_count", nullable = false)
	public Integer getAnswerCount() {
		return this.answerCount;
	}

	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}

	@Column(name = "answer_correct_count", nullable = false)
	public Integer getAnswerCorrectCount() {
		return this.answerCorrectCount;
	}

	public void setAnswerCorrectCount(Integer answerCorrectCount) {
		this.answerCorrectCount = answerCorrectCount;
	}

	@Column(name = "match_count", nullable = false)
	public Integer getMatchCount() {
		return this.matchCount;
	}

	public void setMatchCount(Integer matchCount) {
		this.matchCount = matchCount;
	}

	@Column(name = "match_win_count", nullable = false)
	public Integer getMatchWinCount() {
		return this.matchWinCount;
	}

	public void setMatchWinCount(Integer matchWinCount) {
		this.matchWinCount = matchWinCount;
	}

	@Column(name = "last_login_time", length = 0)
	public Timestamp getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Column(name = "last_login_ip", length = 50)
	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	@Column(name = "last_modify_time", length = 0)
	public Timestamp getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Column(name = "recorded_time", nullable = false, length = 0)
	public Timestamp getRecordedTime() {
		return this.recordedTime;
	}

	public void setRecordedTime(Timestamp recordedTime) {
		this.recordedTime = recordedTime;
	}

	@Column(name = "valid", nullable = false)
	public Integer getValid() {
		return this.valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

}