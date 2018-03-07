package com.xxbs.v1.util.chart;

import java.text.NumberFormat;
import java.util.Locale;

import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.general.PieDataset;

import com.xxbs.v1.util.collection.CollectionUtil;

/**
 * �ܺ��Բ���key��PieSectionLabelGenerator
 * 
 * @author tang
 * 
 */
@SuppressWarnings("rawtypes")
public class IgnoreKeyPieSectionLabelGenerator extends StandardPieSectionLabelGenerator {

	private static final long serialVersionUID = 1L;

	protected Comparable[] ignoreKeys;

	public IgnoreKeyPieSectionLabelGenerator() {
	}

	public IgnoreKeyPieSectionLabelGenerator(Comparable[] ignoreKeys) {
		this.ignoreKeys = ignoreKeys;
	}

	public IgnoreKeyPieSectionLabelGenerator(Locale locale, Comparable[] ignoreKeys) {
		super(locale);
		this.ignoreKeys = ignoreKeys;
	}

	public IgnoreKeyPieSectionLabelGenerator(String labelFormat, Locale locale, Comparable[] ignoreKeys) {
		super(labelFormat, locale);
		this.ignoreKeys = ignoreKeys;
	}

	public IgnoreKeyPieSectionLabelGenerator(String labelFormat, NumberFormat numberFormat, NumberFormat percentFormat, Comparable[] ignoreKeys) {
		super(labelFormat, numberFormat, percentFormat);
		this.ignoreKeys = ignoreKeys;
	}

	public IgnoreKeyPieSectionLabelGenerator(String labelFormat, Comparable[] ignoreKeys) {
		super(labelFormat);
		this.ignoreKeys = ignoreKeys;
	}

	@Override
	public String generateSectionLabel(PieDataset dataset, Comparable key) {
		if (!CollectionUtil.isExist(ignoreKeys, key)) {
			return super.generateSectionLabel(dataset, key);
		} else {
			return null;
		}
	}

	@Override
	protected Object[] createItemArray(PieDataset dataset, Comparable key) {
		if (!CollectionUtil.isExist(ignoreKeys, key)) {
			return super.createItemArray(dataset, key);
		} else {
			return null;
		}
	}

	public Comparable[] getIgnoreKeys() {
		return ignoreKeys;
	}

	public void setIgnoreKeys(Comparable[] ignoreKeys) {
		this.ignoreKeys = ignoreKeys;
	}
}
