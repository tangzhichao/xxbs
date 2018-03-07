package com.xxbs.v1.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Exam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "exam", catalog = "xxbs", uniqueConstraints = @UniqueConstraint(columnNames = { "type_name", "name", "area_id", "learning_phase_id" }))
public class Exam implements java.io.Serializable {

	// Fields

	private Integer	id;
	private String	typeName;
	private String	name;
	private Integer	areaId;
	private Integer	learningPhaseId;

	// Constructors

	/** default constructor */
	public Exam() {
	}

	/** minimal constructor */
	public Exam(String typeName, String name, Integer learningPhaseId) {
		this.typeName = typeName;
		this.name = name;
		this.learningPhaseId = learningPhaseId;
	}

	/** full constructor */
	public Exam(String typeName, String name, Integer areaId, Integer learningPhaseId) {
		this.typeName = typeName;
		this.name = name;
		this.areaId = areaId;
		this.learningPhaseId = learningPhaseId;
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

	@Column(name = "type_name", nullable = false, length = 20)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "area_id")
	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	@Column(name = "learning_phase_id", nullable = false)
	public Integer getLearningPhaseId() {
		return this.learningPhaseId;
	}

	public void setLearningPhaseId(Integer learningPhaseId) {
		this.learningPhaseId = learningPhaseId;
	}

}