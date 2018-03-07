package com.xxbs.v1.db;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * StatusData entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "status_data", catalog = "xxbs")
public class StatusData implements java.io.Serializable {

	// Fields

	private Integer		id;
	private String		tableName;
	private String		columnName;
	private Integer		module;
	private String		dataValue;
	private String		showName;
	private String		sortOrder;
	private Timestamp	lastModifyTime;

	// Constructors

	/** default constructor */
	public StatusData() {
	}

	/** minimal constructor */
	public StatusData(String tableName, String columnName, Integer module, String dataValue, String showName, String sortOrder) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.module = module;
		this.dataValue = dataValue;
		this.showName = showName;
		this.sortOrder = sortOrder;
	}

	/** full constructor */
	public StatusData(String tableName, String columnName, Integer module, String dataValue, String showName, String sortOrder, Timestamp lastModifyTime) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.module = module;
		this.dataValue = dataValue;
		this.showName = showName;
		this.sortOrder = sortOrder;
		this.lastModifyTime = lastModifyTime;
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

	@Column(name = "table_name", nullable = false, length = 100)
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "column_name", nullable = false, length = 100)
	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Column(name = "module", nullable = false)
	public Integer getModule() {
		return this.module;
	}

	public void setModule(Integer module) {
		this.module = module;
	}

	@Column(name = "data_value", nullable = false, length = 100)
	public String getDataValue() {
		return this.dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	@Column(name = "show_name", nullable = false, length = 100)
	public String getShowName() {
		return this.showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	@Column(name = "sort_order", nullable = false, length = 5)
	public String getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Column(name = "last_modify_time", length = 0)
	public Timestamp getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

}