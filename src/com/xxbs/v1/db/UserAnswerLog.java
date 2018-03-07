package com.xxbs.v1.db;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserAnswerLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_answer_log", catalog = "xxbs")
public class UserAnswerLog implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		userId;
	private Integer		paperId;
	private Integer		questionId;
	private Integer		optionId;
	private Integer		answerSecond;
	private Timestamp	recordedTime;

	// Constructors

	/** default constructor */
	public UserAnswerLog() {
	}

	/** minimal constructor */
	public UserAnswerLog(Integer userId, Integer answerSecond, Timestamp recordedTime) {
		this.userId = userId;
		this.answerSecond = answerSecond;
		this.recordedTime = recordedTime;
	}

	/** full constructor */
	public UserAnswerLog(Integer userId, Integer paperId, Integer questionId, Integer optionId, Integer answerSecond, Timestamp recordedTime) {
		this.userId = userId;
		this.paperId = paperId;
		this.questionId = questionId;
		this.optionId = optionId;
		this.answerSecond = answerSecond;
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

	@Column(name = "paper_id")
	public Integer getPaperId() {
		return this.paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	@Column(name = "question_id")
	public Integer getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	@Column(name = "option_id")
	public Integer getOptionId() {
		return this.optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	@Column(name = "answer_second", nullable = false)
	public Integer getAnswerSecond() {
		return this.answerSecond;
	}

	public void setAnswerSecond(Integer answerSecond) {
		this.answerSecond = answerSecond;
	}

	@Column(name = "recorded_time", nullable = false, length = 0)
	public Timestamp getRecordedTime() {
		return this.recordedTime;
	}

	public void setRecordedTime(Timestamp recordedTime) {
		this.recordedTime = recordedTime;
	}

}