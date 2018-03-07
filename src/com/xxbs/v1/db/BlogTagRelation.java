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
 * BlogTagRelation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "blog_tag_relation", catalog = "xxbs", uniqueConstraints = @UniqueConstraint(columnNames = { "blog_id", "tag_id" }))
public class BlogTagRelation implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		blogId;
	private Integer		tagId;
	private Timestamp	recordedTime;

	// Constructors

	/** default constructor */
	public BlogTagRelation() {
	}

	/** full constructor */
	public BlogTagRelation(Integer blogId, Integer tagId, Timestamp recordedTime) {
		this.blogId = blogId;
		this.tagId = tagId;
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

	@Column(name = "blog_id", nullable = false)
	public Integer getBlogId() {
		return this.blogId;
	}

	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
	}

	@Column(name = "tag_id", nullable = false)
	public Integer getTagId() {
		return this.tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	@Column(name = "recorded_time", nullable = false, length = 0)
	public Timestamp getRecordedTime() {
		return this.recordedTime;
	}

	public void setRecordedTime(Timestamp recordedTime) {
		this.recordedTime = recordedTime;
	}

}