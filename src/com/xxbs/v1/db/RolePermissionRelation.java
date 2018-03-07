package com.xxbs.v1.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * RolePermissionRelation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "role_permission_relation", catalog = "xxbs", uniqueConstraints = @UniqueConstraint(columnNames = { "role_id", "permission_id" }))
public class RolePermissionRelation implements java.io.Serializable {

	// Fields

	private Integer	id;
	private Integer	roleId;
	private Integer	permissionId;

	// Constructors

	/** default constructor */
	public RolePermissionRelation() {
	}

	/** full constructor */
	public RolePermissionRelation(Integer roleId, Integer permissionId) {
		this.roleId = roleId;
		this.permissionId = permissionId;
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

	@Column(name = "role_id", nullable = false)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "permission_id", nullable = false)
	public Integer getPermissionId() {
		return this.permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

}