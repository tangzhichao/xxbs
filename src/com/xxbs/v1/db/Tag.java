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
 * Tag entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tag", catalog = "xxbs", uniqueConstraints = @UniqueConstraint(columnNames = { "parent_id", "name", "type" }))
public class Tag implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		parentId;
	private String		name;
	private Integer		level;
	private Integer		type;
	private Timestamp	recordedTime;

	// Constructors

	/** default constructor */
	public Tag() {
	}

	/** minimal constructor */
	public Tag(String name, Integer level, Integer type, Timestamp recordedTime) {
		this.name = name;
		this.level = level;
		this.type = type;
		this.recordedTime = recordedTime;
	}

	/** full constructor */
	public Tag(Integer parentId, String name, Integer level, Integer type, Timestamp recordedTime) {
		this.parentId = parentId;
		this.name = name;
		this.level = level;
		this.type = type;
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

	@Column(name = "parent_id")
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "level", nullable = false)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "recorded_time", nullable = false, length = 0)
	public Timestamp getRecordedTime() {
		return this.recordedTime;
	}

	public void setRecordedTime(Timestamp recordedTime) {
		this.recordedTime = recordedTime;
	}

}