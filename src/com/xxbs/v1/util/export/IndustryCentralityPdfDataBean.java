package com.xxbs.v1.util.export;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * ��ҵ���ĵ���pdfʱ��Ҫ��bean
 * 
 * @author tang
 * @date 2015/3/25
 */
public class IndustryCentralityPdfDataBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public static class ChildModule implements Serializable {

		private static final long serialVersionUID = 1L;

		protected String title;
		protected String date;
		protected int chartColumns;
		protected Component tableComponent;
		protected Dimension chartSize;
		protected Container chartsContainer;
		protected Dimension pageSize;
		protected LinkedHashMap<String, Component> charts;

		public ChildModule() {
		}

		public ChildModule(String title, String date) {
			this(title, date, null);
		}

		public ChildModule(String title, String date, int chartColumns) {
			this(title, date, chartColumns, null);
		}

		public ChildModule(String title, String date, LinkedHashMap<String, Component> images) {
			this(title, date, 2, images);
		}

		public ChildModule(String title, String date, int chartColumns, LinkedHashMap<String, Component> charts) {
			this.title = title;
			this.date = date;
			this.chartColumns = chartColumns;
			this.charts = charts;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public LinkedHashMap<String, Component> getCharts() {
			return charts;
		}

		public void setCharts(LinkedHashMap<String, Component> charts) {
			this.charts = charts;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public int getChartColumns() {
			return chartColumns;
		}

		public void setChartColumns(int chartColumns) {
			this.chartColumns = chartColumns;
		}

		public Component getTableComponent() {
			return tableComponent;
		}

		public void setTableComponent(Component tableComponent) {
			this.tableComponent = tableComponent;
		}

		public Dimension getChartSize() {
			return chartSize;
		}

		public void setChartSize(Dimension chartSize) {
			this.chartSize = chartSize;
		}

		public Container getChartsContainer() {
			return chartsContainer;
		}

		public void setChartsContainer(Container chartsContainer) {
			this.chartsContainer = chartsContainer;
		}

		public Dimension getPageSize() {
			return pageSize;
		}

		public void setPageSize(Dimension pageSize) {
			this.pageSize = pageSize;
		}
	}

	protected String fileName;
	protected String company;
	protected String moduleName;
	protected String district;
	protected String industry;
	protected List<ChildModule> childModules;
	protected Component allImageComponent;

	public IndustryCentralityPdfDataBean() {
	}

	public IndustryCentralityPdfDataBean(String fileName, String company, String moduleName, String district, String industry) {
		this(fileName, company, moduleName, district, industry, null);
	}

	public IndustryCentralityPdfDataBean(String fileName, String company, String moduleName, String district, String industry, List<ChildModule> childModules) {
		this.fileName = fileName;
		this.company = company;
		this.moduleName = moduleName;
		this.district = district;
		this.industry = industry;
		this.childModules = childModules;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<ChildModule> getChildModules() {
		return childModules;
	}

	public void setChildModules(List<ChildModule> childModules) {
		this.childModules = childModules;
	}

	public String getFillTitle() {
		return district + industry + moduleName;
	}

	public String getFillSplitTitle() {
		return district + "," + industry + "," + moduleName;
	}

	public Component getAllImageComponent() {
		return allImageComponent;
	}

	public void setAllImageComponent(Component allImageComponent) {
		this.allImageComponent = allImageComponent;
	}
}
