package com.xxbs.v1.db;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Paper entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "paper", catalog = "xxbs")
public class Paper implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		userId;
	private String		name;
	private Integer		type;
	private Integer		learningPhaseId;
	private Integer		examId;
	private Double		points;
	private Double		gold;
	private Integer		areaId;
	private Integer		answerSecond;
	private Timestamp	paperYear;
	private Timestamp	paperMonth;
	private Timestamp	recordedTime;

	// Constructors

	/** default constructor */
	public Paper() {
	}

	/** minimal constructor */
	public Paper(Integer userId, String name, Integer type, Integer learningPhaseId, Integer examId, Double points, Double gold, Integer answerSecond,
			Timestamp paperYear, Timestamp paperMonth, Timestamp recordedTime) {
		this.userId = userId;
		this.name = name;
		this.type = type;
		this.learningPhaseId = learningPhaseId;
		this.examId = examId;
		this.points = points;
		this.gold = gold;
		this.answerSecond = answerSecond;
		this.paperYear = paperYear;
		this.paperMonth = paperMonth;
		this.recordedTime = recordedTime;
	}

	/** full constructor */
	public Paper(Integer userId, String name, Integer type, Integer learningPhaseId, Integer examId, Double points, Double gold, Integer areaId,
			Integer answerSecond, Timestamp paperYear, Timestamp paperMonth, Timestamp recordedTime) {
		this.userId = userId;
		this.name = name;
		this.type = type;
		this.learningPhaseId = learningPhaseId;
		this.examId = examId;
		this.points = points;
		this.gold = gold;
		this.areaId = areaId;
		this.answerSecond = answerSecond;
		this.paperYear = paperYear;
		this.paperMonth = paperMonth;
		this.recordedTime = recordedTime;
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

	@Column(name = "user_id", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "learning_phase_id", nullable = false)
	public Integer getLearningPhaseId() {
		return this.learningPhaseId;
	}

	public void setLearningPhaseId(Integer learningPhaseId) {
		this.learningPhaseId = learningPhaseId;
	}

	@Column(name = "exam_id", nullable = false)
	public Integer getExamId() {
		return this.examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	@Column(name = "points", nullable = false, precision = 10)
	public Double getPoints() {
		return this.points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	@Column(name = "gold", nullable = false, precision = 10)
	public Double getGold() {
		return this.gold;
	}

	public void setGold(Double gold) {
		this.gold = gold;
	}

	@Column(name = "area_id")
	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	@Column(name = "answer_second", nullable = false)
	public Integer getAnswerSecond() {
		return this.answerSecond;
	}

	public void setAnswerSecond(Integer answerSecond) {
		this.answerSecond = answerSecond;
	}

	@Column(name = "paper_year", nullable = false, length = 0)
	public Timestamp getPaperYear() {
		return this.paperYear;
	}

	public void setPaperYear(Timestamp paperYear) {
		this.paperYear = paperYear;
	}

	@Column(name = "paper_month", nullable = false, length = 0)
	public Timestamp getPaperMonth() {
		return this.paperMonth;
	}

	public void setPaperMonth(Timestamp paperMonth) {
		this.paperMonth = paperMonth;
	}

	@Column(name = "recorded_time", nullable = false, length = 0)
	public Timestamp getRecordedTime() {
		return this.recordedTime;
	}

	public void setRecordedTime(Timestamp recordedTime) {
		this.recordedTime = recordedTime;
	}

}