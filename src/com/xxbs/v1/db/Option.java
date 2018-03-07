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
 * Option entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "option", catalog = "xxbs", uniqueConstraints = { @UniqueConstraint(columnNames = { "question_id", "sort_order" }),
		@UniqueConstraint(columnNames = { "question_id", "content" }) })
public class Option implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		questionId;
	private String		sortOrder;
	private String		content;
	private Timestamp	lastModifyTime;
	private Timestamp	recordedTime;

	// Constructors

	/** default constructor */
	public Option() {
	}

	/** minimal constructor */
	public Option(Integer questionId, String sortOrder, String content, Timestamp recordedTime) {
		this.questionId = questionId;
		this.sortOrder = sortOrder;
		this.content = content;
		this.recordedTime = recordedTime;
	}

	/** full constructor */
	public Option(Integer questionId, String sortOrder, String content, Timestamp lastModifyTime, Timestamp recordedTime) {
		this.questionId = questionId;
		this.sortOrder = sortOrder;
		this.content = content;
		this.lastModifyTime = lastModifyTime;
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

	@Column(name = "content", nullable = false, length = 50)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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

}