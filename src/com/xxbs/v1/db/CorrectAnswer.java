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
 * CorrectAnswer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "correct_answer", catalog = "xxbs", uniqueConstraints = @UniqueConstraint(columnNames = { "question_id", "correct_option_id" }))
public class CorrectAnswer implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		questionId;
	private Integer		correctOptionId;
	private Timestamp	lastModifyTime;

	// Constructors

	/** default constructor */
	public CorrectAnswer() {
	}

	/** minimal constructor */
	public CorrectAnswer(Integer questionId, Integer correctOptionId) {
		this.questionId = questionId;
		this.correctOptionId = correctOptionId;
	}

	/** full constructor */
	public CorrectAnswer(Integer questionId, Integer correctOptionId, Timestamp lastModifyTime) {
		this.questionId = questionId;
		this.correctOptionId = correctOptionId;
		this.lastModifyTime = lastModifyTime;
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

	@Column(name = "question_id", nullable = false)
	public Integer getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	@Column(name = "correct_option_id", nullable = false)
	public Integer getCorrectOptionId() {
		return this.correctOptionId;
	}

	public void setCorrectOptionId(Integer correctOptionId) {
		this.correctOptionId = correctOptionId;
	}

	@Column(name = "last_modify_time", length = 0)
	public Timestamp getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

}