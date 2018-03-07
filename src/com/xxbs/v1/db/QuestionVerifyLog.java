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
 * QuestionVerifyLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "question_verify_log", catalog = "xxbs", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "question_id", "verify_type" }))
public class QuestionVerifyLog implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		userId;
	private Integer		questionId;
	private Integer		verifyType;
	private Timestamp	recordedTime;
	private Integer		status;

	// Constructors

	/** default constructor */
	public QuestionVerifyLog() {
	}

	/** full constructor */
	public QuestionVerifyLog(Integer userId, Integer questionId, Integer verifyType, Timestamp recordedTime, Integer status) {
		this.userId = userId;
		this.questionId = questionId;
		this.verifyType = verifyType;
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

	@Column(name = "question_id", nullable = false)
	public Integer getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	@Column(name = "verify_type", nullable = false)
	public Integer getVerifyType() {
		return this.verifyType;
	}

	public void setVerifyType(Integer verifyType) {
		this.verifyType = verifyType;
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