package com.xxbs.v1.db;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Question entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "question", catalog = "xxbs")
public class Question implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		userId;
	private String		title;
	private String		remarks;
	private String		analysis;
	private Integer		createFrom;
	private Integer		type;
	private Integer		answerCount;
	private Double		points;
	private Integer		answerSecond;
	private Integer		learningPhaseId;
	private Integer		knowledgePointTagId;
	private Timestamp	endTime;
	private Timestamp	lastModifyTime;
	private Timestamp	recordedTime;
	private Integer		status;

	// Constructors

	/** default constructor */
	public Question() {
	}

	/** minimal constructor */
	public Question(Integer userId, String title, String remarks, Integer createFrom, Integer type, Integer answerCount, Double points, Integer answerSecond,
			Integer learningPhaseId, Timestamp recordedTime, Integer status) {
		this.userId = userId;
		this.title = title;
		this.remarks = remarks;
		this.createFrom = createFrom;
		this.type = type;
		this.answerCount = answerCount;
		this.points = points;
		this.answerSecond = answerSecond;
		this.learningPhaseId = learningPhaseId;
		this.recordedTime = recordedTime;
		this.status = status;
	}

	/** full constructor */
	public Question(Integer userId, String title, String remarks, String analysis, Integer createFrom, Integer type, Integer answerCount, Double points,
			Integer answerSecond, Integer learningPhaseId, Integer knowledgePointTagId, Timestamp endTime, Timestamp lastModifyTime, Timestamp recordedTime,
			Integer status) {
		this.userId = userId;
		this.title = title;
		this.remarks = remarks;
		this.analysis = analysis;
		this.createFrom = createFrom;
		this.type = type;
		this.answerCount = answerCount;
		this.points = points;
		this.answerSecond = answerSecond;
		this.learningPhaseId = learningPhaseId;
		this.knowledgePointTagId = knowledgePointTagId;
		this.endTime = endTime;
		this.lastModifyTime = lastModifyTime;
		this.recordedTime = recordedTime;
		this.status = status;
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

	@Column(name = "title", nullable = false, length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "remarks", nullable = false, length = 300)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "analysis", length = 500)
	public String getAnalysis() {
		return this.analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	@Column(name = "create_from", nullable = false)
	public Integer getCreateFrom() {
		return this.createFrom;
	}

	public void setCreateFrom(Integer createFrom) {
		this.createFrom = createFrom;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "answer_count", nullable = false)
	public Integer getAnswerCount() {
		return this.answerCount;
	}

	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}

	@Column(name = "points", nullable = false, precision = 10)
	public Double getPoints() {
		return this.points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	@Column(name = "answer_second", nullable = false)
	public Integer getAnswerSecond() {
		return this.answerSecond;
	}

	public void setAnswerSecond(Integer answerSecond) {
		this.answerSecond = answerSecond;
	}

	@Column(name = "learning_phase_id", nullable = false)
	public Integer getLearningPhaseId() {
		return this.learningPhaseId;
	}

	public void setLearningPhaseId(Integer learningPhaseId) {
		this.learningPhaseId = learningPhaseId;
	}

	@Column(name = "knowledge_point_tag_id")
	public Integer getKnowledgePointTagId() {
		return this.knowledgePointTagId;
	}

	public void setKnowledgePointTagId(Integer knowledgePointTagId) {
		this.knowledgePointTagId = knowledgePointTagId;
	}

	@Column(name = "end_time", length = 0)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
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

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}