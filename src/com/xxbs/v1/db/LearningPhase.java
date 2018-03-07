package com.xxbs.v1.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * LearningPhase entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "learning_phase", catalog = "xxbs", uniqueConstraints = { @UniqueConstraint(columnNames = { "parent_id", "key" }),
		@UniqueConstraint(columnNames = { "parent_id", "name" }) })
public class LearningPhase implements java.io.Serializable {

	// Fields

	private Integer	id;
	private Integer	parentId;
	private String	name;
	private String	key;
	private Integer	level;

	// Constructors

	/** default constructor */
	public LearningPhase() {
	}

	/** minimal constructor */
	public LearningPhase(String name, String key, Integer level) {
		this.name = name;
		this.key = key;
		this.level = level;
	}

	/** full constructor */
	public LearningPhase(Integer parentId, String name, String key, Integer level) {
		this.parentId = parentId;
		this.name = name;
		this.key = key;
		this.level = level;
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

	@Column(name = "key", nullable = false, length = 20)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "level", nullable = false)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}