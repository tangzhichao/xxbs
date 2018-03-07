package com.xxbs.v1.db;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Feedback entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "feedback", catalog = "xxbs")
public class Feedback implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		userId;
	private Integer		type;
	private String		content;
	private String		attachment;
	private Integer		attachmentType;
	private Integer		status;
	private String		noHandleReason;
	private Timestamp	lastModifyTime;
	private Timestamp	recordedTime;

	// Constructors

	/** default constructor */
	public Feedback() {
	}

	/** minimal constructor */
	public Feedback(Integer userId, Integer type, String content, Integer status, Timestamp recordedTime) {
		this.userId = userId;
		this.type = type;
		this.content = content;
		this.status = status;
		this.recordedTime = recordedTime;
	}

	/** full constructor */
	public Feedback(Integer userId, Integer type, String content, String attachment, Integer attachmentType, Integer status, String noHandleReason,
			Timestamp lastModifyTime, Timestamp recordedTime) {
		this.userId = userId;
		this.type = type;
		this.content = content;
		this.attachment = attachment;
		this.attachmentType = attachmentType;
		this.status = status;
		this.noHandleReason = noHandleReason;
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

	@Column(name = "user_id", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "content", nullable = false, length = 300)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "attachment", length = 1000)
	public String getAttachment() {
		return this.attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	@Column(name = "attachment_type")
	public Integer getAttachmentType() {
		return this.attachmentType;
	}

	public void setAttachmentType(Integer attachmentType) {
		this.attachmentType = attachmentType;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "no_handle_reason", length = 300)
	public String getNoHandleReason() {
		return this.noHandleReason;
	}

	public void setNoHandleReason(String noHandleReason) {
		this.noHandleReason = noHandleReason;
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