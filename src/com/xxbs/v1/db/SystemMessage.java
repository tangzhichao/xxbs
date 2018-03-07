package com.xxbs.v1.db;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SystemMessage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "system_message", catalog = "xxbs")
public class SystemMessage implements java.io.Serializable {

	// Fields

	private Integer		id;
	private String		title;
	private String		content;
	private String		datainfo;
	private Integer		type;
	private Integer		targetUserId;
	private Integer		sourceUserId;
	private Integer		sourceBlogId;
	private Integer		sourceCommentId;
	private Timestamp	lastModifyTime;
	private Timestamp	recordedTime;
	private Integer		status;

	// Constructors

	/** default constructor */
	public SystemMessage() {
	}

	/** minimal constructor */
	public SystemMessage(String content, Integer type, Integer targetUserId, Integer sourceUserId, Timestamp recordedTime, Integer status) {
		this.content = content;
		this.type = type;
		this.targetUserId = targetUserId;
		this.sourceUserId = sourceUserId;
		this.recordedTime = recordedTime;
		this.status = status;
	}

	/** full constructor */
	public SystemMessage(String title, String content, String datainfo, Integer type, Integer targetUserId, Integer sourceUserId, Integer sourceBlogId,
			Integer sourceCommentId, Timestamp lastModifyTime, Timestamp recordedTime, Integer status) {
		this.title = title;
		this.content = content;
		this.datainfo = datainfo;
		this.type = type;
		this.targetUserId = targetUserId;
		this.sourceUserId = sourceUserId;
		this.sourceBlogId = sourceBlogId;
		this.sourceCommentId = sourceCommentId;
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

	@Column(name = "title", length = 20)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", nullable = false, length = 1000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "datainfo", length = 500)
	public String getDatainfo() {
		return this.datainfo;
	}

	public void setDatainfo(String datainfo) {
		this.datainfo = datainfo;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "target_user_id", nullable = false)
	public Integer getTargetUserId() {
		return this.targetUserId;
	}

	public void setTargetUserId(Integer targetUserId) {
		this.targetUserId = targetUserId;
	}

	@Column(name = "source_user_id", nullable = false)
	public Integer getSourceUserId() {
		return this.sourceUserId;
	}

	public void setSourceUserId(Integer sourceUserId) {
		this.sourceUserId = sourceUserId;
	}

	@Column(name = "source_blog_id")
	public Integer getSourceBlogId() {
		return this.sourceBlogId;
	}

	public void setSourceBlogId(Integer sourceBlogId) {
		this.sourceBlogId = sourceBlogId;
	}

	@Column(name = "source_comment_id")
	public Integer getSourceCommentId() {
		return this.sourceCommentId;
	}

	public void setSourceCommentId(Integer sourceCommentId) {
		this.sourceCommentId = sourceCommentId;
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