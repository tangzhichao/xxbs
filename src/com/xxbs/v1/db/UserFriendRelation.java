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
 * UserFriendRelation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_friend_relation", catalog = "xxbs", uniqueConstraints = @UniqueConstraint(columnNames = { "source_user_id", "target_user_id" }))
public class UserFriendRelation implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		sourceUserId;
	private Integer		targetUserId;
	private Timestamp	lastModifyTime;
	private Timestamp	recordedTime;
	private Integer		status;

	// Constructors

	/** default constructor */
	public UserFriendRelation() {
	}

	/** minimal constructor */
	public UserFriendRelation(Integer sourceUserId, Integer targetUserId, Timestamp recordedTime, Integer status) {
		this.sourceUserId = sourceUserId;
		this.targetUserId = targetUserId;
		this.recordedTime = recordedTime;
		this.status = status;
	}

	/** full constructor */
	public UserFriendRelation(Integer sourceUserId, Integer targetUserId, Timestamp lastModifyTime, Timestamp recordedTime, Integer status) {
		this.sourceUserId = sourceUserId;
		this.targetUserId = targetUserId;
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

	@Column(name = "source_user_id", nullable = false)
	public Integer getSourceUserId() {
		return this.sourceUserId;
	}

	public void setSourceUserId(Integer sourceUserId) {
		this.sourceUserId = sourceUserId;
	}

	@Column(name = "target_user_id", nullable = false)
	public Integer getTargetUserId() {
		return this.targetUserId;
	}

	public void setTargetUserId(Integer targetUserId) {
		this.targetUserId = targetUserId;
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