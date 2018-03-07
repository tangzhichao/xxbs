package com.xxbs.v1.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Role entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "role", catalog = "xxbs", uniqueConstraints = { @UniqueConstraint(columnNames = { "parent_id", "key" }),
		@UniqueConstraint(columnNames = { "parent_id", "name" }) })
public class Role implements java.io.Serializable {

	// Fields

	private Integer	id;
	private Integer	parentId;
	private String	name;
	private String	key;

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(String name, String key) {
		this.name = name;
		this.key = key;
	}

	/** full constructor */
	public Role(Integer parentId, String name, String key) {
		this.parentId = parentId;
		this.name = name;
		this.key = key;
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

	@Column(name = "name", nullable = false, length = 30)
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

}