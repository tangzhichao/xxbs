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
 * Team entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "team", catalog = "xxbs", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Team implements java.io.Serializable {

	// Fields

	private Integer		id;
	private Integer		createUserId;
	private Integer		leaderUserId;
	private String		name;
	private String		image;
	private Integer		type;
	private String		city;
	private String		description;
	private Timestamp	lastModifyTime;
	private Timestamp	recordedTime;
	private Integer		status;

	// Constructors

	/** default constructor */
	public Team() {
	}

	/** minimal constructor */
	public Team(Integer createUserId, Integer leaderUserId, String name, Integer type, String city, Timestamp recordedTime, Integer status) {
		this.createUserId = createUserId;
		this.leaderUserId = leaderUserId;
		this.name = name;
		this.type = type;
		this.city = city;
		this.recordedTime = recordedTime;
		this.status = status;
	}

	/** full constructor */
	public Team(Integer createUserId, Integer leaderUserId, String name, String image, Integer type, String city, String description, Timestamp lastModifyTime,
			Timestamp recordedTime, Integer status) {
		this.createUserId = createUserId;
		this.leaderUserId = leaderUserId;
		this.name = name;
		this.image = image;
		this.type = type;
		this.city = city;
		this.description = description;
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

	@Column(name = "create_user_id", nullable = false)
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "leader_user_id", nullable = false)
	public Integer getLeaderUserId() {
		return this.leaderUserId;
	}

	public void setLeaderUserId(Integer leaderUserId) {
		this.leaderUserId = leaderUserId;
	}

	@Column(name = "name", unique = true, nullable = false, length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "image", length = 50)
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "city", nullable = false, length = 200)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "description", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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