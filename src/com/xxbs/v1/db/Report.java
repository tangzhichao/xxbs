package com.xxbs.v1.db;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Report entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "report", catalog = "xxbs")
public class Report implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		sourceUserId;
	private Integer		targetUserId;
	private Integer		targetBlogId;
	private Integer		targetCommentId;
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
	public Report() {
	}

	/** minimal constructor */
	public Report(Integer sourceUserId, Integer targetUserId, Integer type, String content, Integer status, Timestamp recordedTime) {
		this.sourceUserId = sourceUserId;
		this.targetUserId = targetUserId;
		this.type = type;
		this.content = content;
		this.status = status;
		this.recordedTime = recordedTime;
	}

	/** full constructor */
	public Report(Integer sourceUserId, Integer targetUserId, Integer targetBlogId, Integer targetCommentId, Integer type, String content, String attachment,
			Integer attachmentType, Integer status, String noHandleReason, Timestamp lastModifyTime, Timestamp recordedTime) {
		this.sourceUserId = sourceUserId;
		this.targetUserId = targetUserId;
		this.targetBlogId = targetBlogId;
		this.targetCommentId = targetCommentId;
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

	@Column(name = "target_blog_id")
	public Integer getTargetBlogId() {
		return this.targetBlogId;
	}

	public void setTargetBlogId(Integer targetBlogId) {
		this.targetBlogId = targetBlogId;
	}

	@Column(name = "target_comment_id")
	public Integer getTargetCommentId() {
		return this.targetCommentId;
	}

	public void setTargetCommentId(Integer targetCommentId) {
		this.targetCommentId = targetCommentId;
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