package com.xxbs.v1.db;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Comment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "comment", catalog = "xxbs")
public class Comment implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		userId;
	private Integer		targetBlogId;
	private Integer		targetCommentId;
	private String		content;
	private Integer		floorNumber;
	private Integer		praiseCount;
	private Integer		trampleCount;
	private String		attachment;
	private Integer		attachmentType;
	private Integer		openType;
	private Timestamp	lastModifyTime;
	private Timestamp	recordedTime;
	private Integer		status;

	// Constructors

	/** default constructor */
	public Comment() {
	}

	/** minimal constructor */
	public Comment(Integer userId, String content, Integer praiseCount, Integer trampleCount, Integer openType, Timestamp recordedTime, Integer status) {
		this.userId = userId;
		this.content = content;
		this.praiseCount = praiseCount;
		this.trampleCount = trampleCount;
		this.openType = openType;
		this.recordedTime = recordedTime;
		this.status = status;
	}

	/** full constructor */
	public Comment(Integer userId, Integer targetBlogId, Integer targetCommentId, String content, Integer floorNumber, Integer praiseCount,
			Integer trampleCount, String attachment, Integer attachmentType, Integer openType, Timestamp lastModifyTime, Timestamp recordedTime, Integer status) {
		this.userId = userId;
		this.targetBlogId = targetBlogId;
		this.targetCommentId = targetCommentId;
		this.content = content;
		this.floorNumber = floorNumber;
		this.praiseCount = praiseCount;
		this.trampleCount = trampleCount;
		this.attachment = attachment;
		this.attachmentType = attachmentType;
		this.openType = openType;
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

	@Column(name = "content", nullable = false, length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "floor_number")
	public Integer getFloorNumber() {
		return this.floorNumber;
	}

	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}

	@Column(name = "praise_count", nullable = false)
	public Integer getPraiseCount() {
		return this.praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	@Column(name = "trample_count", nullable = false)
	public Integer getTrampleCount() {
		return this.trampleCount;
	}

	public void setTrampleCount(Integer trampleCount) {
		this.trampleCount = trampleCount;
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

	@Column(name = "open_type", nullable = false)
	public Integer getOpenType() {
		return this.openType;
	}

	public void setOpenType(Integer openType) {
		this.openType = openType;
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