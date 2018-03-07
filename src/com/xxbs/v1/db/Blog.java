package com.xxbs.v1.db;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Blog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "blog", catalog = "xxbs")
public class Blog implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		userId;
	private Integer		blogType;
	private Integer		openType;
	private Integer		sourceBlogId;
	private String		title;
	private String		attachment;
	private Integer		attachmentType;
	private String		city;
	private String		content;
	private Integer		isAnonymous;
	private Integer		commentSetting;
	private Integer		praiseCount;
	private Integer		trampleCount;
	private Integer		commentCount;
	private Timestamp	publishTime;
	private Timestamp	lastModifyTime;
	private Timestamp	recordedTime;
	private Integer		status;

	// Constructors

	/** default constructor */
	public Blog() {
	}

	/** minimal constructor */
	public Blog(Integer userId, Integer blogType, Integer openType, String title, Integer isAnonymous, Integer commentSetting, Integer praiseCount,
			Integer trampleCount, Integer commentCount, Timestamp recordedTime, Integer status) {
		this.userId = userId;
		this.blogType = blogType;
		this.openType = openType;
		this.title = title;
		this.isAnonymous = isAnonymous;
		this.commentSetting = commentSetting;
		this.praiseCount = praiseCount;
		this.trampleCount = trampleCount;
		this.commentCount = commentCount;
		this.recordedTime = recordedTime;
		this.status = status;
	}

	/** full constructor */
	public Blog(Integer userId, Integer blogType, Integer openType, Integer sourceBlogId, String title, String attachment, Integer attachmentType, String city,
			String content, Integer isAnonymous, Integer commentSetting, Integer praiseCount, Integer trampleCount, Integer commentCount,
			Timestamp publishTime, Timestamp lastModifyTime, Timestamp recordedTime, Integer status) {
		this.userId = userId;
		this.blogType = blogType;
		this.openType = openType;
		this.sourceBlogId = sourceBlogId;
		this.title = title;
		this.attachment = attachment;
		this.attachmentType = attachmentType;
		this.city = city;
		this.content = content;
		this.isAnonymous = isAnonymous;
		this.commentSetting = commentSetting;
		this.praiseCount = praiseCount;
		this.trampleCount = trampleCount;
		this.commentCount = commentCount;
		this.publishTime = publishTime;
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

	@Column(name = "blog_type", nullable = false)
	public Integer getBlogType() {
		return this.blogType;
	}

	public void setBlogType(Integer blogType) {
		this.blogType = blogType;
	}

	@Column(name = "open_type", nullable = false)
	public Integer getOpenType() {
		return this.openType;
	}

	public void setOpenType(Integer openType) {
		this.openType = openType;
	}

	@Column(name = "source_blog_id")
	public Integer getSourceBlogId() {
		return this.sourceBlogId;
	}

	public void setSourceBlogId(Integer sourceBlogId) {
		this.sourceBlogId = sourceBlogId;
	}

	@Column(name = "title", nullable = false, length = 500)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	@Column(name = "city", length = 200)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "content", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "is_anonymous", nullable = false)
	public Integer getIsAnonymous() {
		return this.isAnonymous;
	}

	public void setIsAnonymous(Integer isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	@Column(name = "comment_setting", nullable = false)
	public Integer getCommentSetting() {
		return this.commentSetting;
	}

	public void setCommentSetting(Integer commentSetting) {
		this.commentSetting = commentSetting;
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

	@Column(name = "comment_count", nullable = false)
	public Integer getCommentCount() {
		return this.commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	@Column(name = "publish_time", length = 0)
	public Timestamp getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
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