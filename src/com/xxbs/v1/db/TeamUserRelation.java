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
 * TeamUserRelation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "team_user_relation", catalog = "xxbs", uniqueConstraints = @UniqueConstraint(columnNames = { "team_id", "user_id" }))
public class TeamUserRelation implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		teamId;
	private Integer		userId;
	private String		teamUserName;
	private String		teamUserRole;
	private Integer		type;
	private Timestamp	lastModifyTime;
	private Timestamp	recordedTime;

	// Constructors

	/** default constructor */
	public TeamUserRelation() {
	}

	/** minimal constructor */
	public TeamUserRelation(Integer teamId, Integer userId, String teamUserRole, Integer type, Timestamp recordedTime) {
		this.teamId = teamId;
		this.userId = userId;
		this.teamUserRole = teamUserRole;
		this.type = type;
		this.recordedTime = recordedTime;
	}

	/** full constructor */
	public TeamUserRelation(Integer teamId, Integer userId, String teamUserName, String teamUserRole, Integer type, Timestamp lastModifyTime,
			Timestamp recordedTime) {
		this.teamId = teamId;
		this.userId = userId;
		this.teamUserName = teamUserName;
		this.teamUserRole = teamUserRole;
		this.type = type;
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

	@Column(name = "team_id", nullable = false)
	public Integer getTeamId() {
		return this.teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	@Column(name = "user_id", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "team_user_name", length = 30)
	public String getTeamUserName() {
		return this.teamUserName;
	}

	public void setTeamUserName(String teamUserName) {
		this.teamUserName = teamUserName;
	}

	@Column(name = "team_user_role", nullable = false, length = 30)
	public String getTeamUserRole() {
		return this.teamUserRole;
	}

	public void setTeamUserRole(String teamUserRole) {
		this.teamUserRole = teamUserRole;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
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