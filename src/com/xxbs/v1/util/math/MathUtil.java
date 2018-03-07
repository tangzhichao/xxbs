package com.xxbs.v1.util.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.xxbs.v1.util.Utils;
import com.xxbs.v1.util.reflect.ReflectUtil;
import com.xxbs.v1.util.str.ValidUtil;

public class MathUtil {

	/**
	 * @param field
	 *            要排序的字段
	 * @return
	 */
	public static <T> Comparator<T> getSort(final String field, final boolean asc) {
		return new Comparator<T>() {
			public int compare(T o1, T o2) {
				Object obj1 = (Object) ReflectUtil.get(o1, field);
				Object obj2 = (Object) ReflectUtil.get(o2, field);
				if (obj1 instanceof Comparable && obj2 instanceof Comparable) {
					Comparable comp1 = (Comparable) obj1;
					Comparable comp2 = (Comparable) obj2;
					return asc ? comp1.compareTo(comp2) : comp2.compareTo(comp1);
				}
				return asc ? Utils.toString(obj1).compareTo(Utils.toString(obj2)) : Utils.toString(obj2).compareTo(Utils.toString(obj1));
			}
		};
	}

	/**
	 * @param field
	 *            要排序的字段
	 * @return
	 */
	public static <T> Comparator<T> getSort(final String[] field, final boolean[] asc) {
		return new Comparator<T>() {
			public int compare(T o1, T o2) {
				for (int i = 0; i < field.length; i++) {
					Comparator<Object> comparator = getSort(field[i], asc[i]);
					int compare = comparator.compare(o1, o2);
					if (compare != 0) {
						return compare;
					}
				}
				return 0;
			}
		};
	}

	/**
	 * @param set
	 * @param sortField
	 *            要排序的字段
	 * @return
	 */
	public static <T> List<T> toListAndSort(Set<T> set, String sortField, boolean asc) {
		ArrayList<T> list = new ArrayList<>(set);
		Collections.sort(list, getSort(sortField == null ? "id" : sortField, asc));
		return list;
	}

	/**
	 * @param set
	 * @param sortField
	 *            要排序的字段
	 * @return
	 */
	public static <T> List<T> toListAndSort(Set<T> set, String[] sortField, boolean[] asc) {
		ArrayList<T> list = new ArrayList<>(set);
		Collections.sort(list, getSort(sortField, asc));
		return list;
	}

	public static int millisToDay(long ms) {
		return (int) (ms / 1000 / 60 / 60 / 24);
	}
	
	/**
	 * 根据列数与在所有数据当中的索引计算所在的行<br>
	 * 
	 * @注意 返回的行数是从1开始的<br>
	 * 
	 * @param columns
	 *            列数
	 * @param index
	 *            需要计算的索引
	 * @return 所在的行索引
	 */
	public static int computeRowIndex(int columns, int index) {
		int quotient = (index + 1) / columns;
		int remainder = (index + 1) % columns;
		int rowIndex;
		if (remainder == 0) {
			rowIndex = quotient;
		} else {
			rowIndex = quotient + 1;
		}
		return rowIndex;
	}
	
	/**
	 * 将一个大于等于0的整数转换为中文表示形式
	 * <P>
	 * 如：data=123,返回"一百二十三";data=3103,返回"三千一百零三"
	 * 
	 * @param data
	 *            一个大于等于零小于十万的整数
	 * @return 返回整数的中文表示形式的字符串
	 */
	public static String integerToChinese(int data) {
		String[] int_string_maping = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		if (data < 0) {
			return null;
		}
		if (data >= 100000) {
			return null;
		}
		if (data < 10) {
			return int_string_maping[data];
		}
		String[] int_unit_maping = { "", "十", "百", "千", "万" };
		StringBuffer buffer = new StringBuffer();
		int count = 0;
		for (int i = data; i != 0; i = i / 10) {
			int re_number = i % 10;
			if (count > 0) {
				if (i == 1 && re_number == 1 && data >= 0 && data < 20) {
					buffer.append(int_unit_maping[count]);
				} else {
					if (re_number == 0) {
						if (buffer.length() > 0) {
							buffer.append(int_string_maping[re_number]);
						}
					} else {
						buffer.append(int_unit_maping[count] + int_string_maping[re_number]);
					}
				}
			} else {
				if (re_number > 0) {
					buffer.append(int_string_maping[re_number]);
				}
			}
			count++;
		}
		String zerozero = int_string_maping[0] + int_string_maping[0];
		for (int i = buffer.indexOf(zerozero); i >= 0; i = buffer.indexOf(zerozero)) {
			buffer.deleteCharAt(i);
		}
		return buffer.reverse().toString();
	}
}
