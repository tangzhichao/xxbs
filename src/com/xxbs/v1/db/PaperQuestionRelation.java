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
 * PaperQuestionRelation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "paper_question_relation", catalog = "xxbs", uniqueConstraints = @UniqueConstraint(columnNames = { "paper_id", "question_id" }))
public class PaperQuestionRelation implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		paperId;
	private Integer		questionId;
	private String		sortOrder;
	private Timestamp	recordedTime;

	// Constructors

	/** default constructor */
	public PaperQuestionRelation() {
	}

	/** full constructor */
	public PaperQuestionRelation(Integer paperId, Integer questionId, String sortOrder, Timestamp recordedTime) {
		this.paperId = paperId;
		this.questionId = questionId;
		this.sortOrder = sortOrder;
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

	@Column(name = "paper_id", nullable = false)
	public Integer getPaperId() {
		return this.paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	@Column(name = "question_id", nullable = false)
	public Integer getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	@Column(name = "sort_order", nullable = false, length = 5)
	public String getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Column(name = "recorded_time", nullable = false, length = 0)
	public Timestamp getRecordedTime() {
		return this.recordedTime;
	}

	public void setRecordedTime(Timestamp recordedTime) {
		this.recordedTime = recordedTime;
	}

}