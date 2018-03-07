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
 * UserTagRelation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_tag_relation", catalog = "xxbs", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "tag_id" }))
public class UserTagRelation implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		userId;
	private Integer		tagId;
	private Timestamp	lastModifyTime;
	private Timestamp	recordedTime;
	private Integer		status;

	// Constructors

	/** default constructor */
	public UserTagRelation() {
	}

	/** minimal constructor */
	public UserTagRelation(Integer userId, Integer tagId, Timestamp recordedTime, Integer status) {
		this.userId = userId;
		this.tagId = tagId;
		this.recordedTime = recordedTime;
		this.status = status;
	}

	/** full constructor */
	public UserTagRelation(Integer userId, Integer tagId, Timestamp lastModifyTime, Timestamp recordedTime, Integer status) {
		this.userId = userId;
		this.tagId = tagId;
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

	@Column(name = "tag_id", nullable = false)
	public Integer getTagId() {
		return this.tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
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