package com.xxbs.v1.util.collection;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.xxbs.v1.util.Utils;

public class CollectionUtil {

	public static <T> T get(Set<T> set, int index) {
		if (!Utils.isEmpty(set)) {
			int i = 0;
			for (T t : set) {
				if (i == index) {
					return t;
				}
				i++;
			}
		}
		return null;
	}

	public static <T> T getFirst(Set<T> set) {
		return get(set, 0);
	}
	
	
	/**
	 * 用于copy出表格数据中的某几列数据
	 * 
	 * @param data
	 *            这是一个表格数据,并且数据结构是Vector<Vector<String>>
	 * @param start
	 *            开始列下标
	 * @param end
	 *            结束列下标(包前不包后)
	 * 
	 * @return
	 */
	public static Vector<Vector> copyOfColumn(Vector<Vector> data, int start, int end) {
		if (Utils.isEmpty(data)) {
			return data;
		}
		Vector<Vector> vector = new Vector<Vector>();
		for (int i = 0; i < data.size(); i++) {
			Vector<String> row = data.get(i);
			vector.add(copyOfRange(row, start, end));
		}
		return vector;
	}

	/**
	 * 用于copy出表格数据中的某几列数据
	 * 
	 * @param data
	 *            这是一个表格数据,并且数据结构是Vector<Vector<String>>
	 * @param start
	 *            开始列下标
	 * @param endIndexOffsetLength
	 *            结束下标与总下标的差值
	 * 
	 * @return
	 */
	public static Vector<Vector> copyOfColumnByEndIndexOffsetLength(Vector<Vector> data, int start, int endIndexOffsetLength) {
		if (Utils.isEmpty(data)) {
			return data;
		}
		Vector<Vector> vector = new Vector<Vector>();
		for (int i = 0; i < data.size(); i++) {
			Vector<String> row = data.get(i);
			vector.add(copyOfRangeByEndIndexOffsetLength(row, 0, endIndexOffsetLength));
		}
		return vector;
	}

	/**
	 * 用于copy出行数据或列数据中的某一段数据
	 * 
	 * @param data
	 *            行数据或列数据
	 * @param start
	 *            开始下标
	 * @param end
	 *            结束列下标(包前不包后)
	 * @return
	 */
	public static Vector copyOfRange(Vector data, int start, int end) {
		if (Utils.isEmpty(data)) {
			return data;
		}
		String[] array = new String[data.size()];
		data.toArray(array);
		String[] range = Arrays.copyOfRange(array, start, end);
		return new Vector(Arrays.asList(range));
	}

	/**
	 * 用于copy出行数据或列数据中的某一段数据
	 * 
	 * @param data
	 *            行数据或列数据
	 * @param start
	 *            开始下标
	 * @param endIndexOffsetLength
	 *            结束下标与总下标的差值
	 * @return
	 */
	public static Vector copyOfRangeByEndIndexOffsetLength(Vector data, int start, int endIndexOffsetLength) {
		if (Utils.isEmpty(data)) {
			return data;
		}
		String[] array = new String[data.size()];
		data.toArray(array);
		String[] range = Arrays.copyOfRange(array, start, array.length - endIndexOffsetLength);
		return new Vector(Arrays.asList(range));
	}

	/**
	 * 用于为表格数据添加一列数据
	 * 
	 * @param data
	 *            这是一个表格数据,并且数据结构是Vector<Vector<String>>
	 * @param col
	 *            这是一列数据
	 * @param index
	 *            添加到哪一列
	 * @return 请注意接收返回值，因为原data并没有添加任何数据
	 */
	public static Vector<Vector> addOfColumn(Vector<Vector> data, String[] col, int index) {

		if (data == null) {
			return data;
		}

		Vector<Vector> tempVector = new Vector<Vector>(data.size());

		for (int i = 0; i < data.size(); i++) {
			Vector row = (Vector) data.get(i).clone();
			String newValue;
			if (col == null || i >= col.length) {
				newValue = "";
			} else {
				newValue = col[i];
			}
			row.add(index, newValue);
			tempVector.add(row);
		}

		return tempVector;
	}

	/**
	 * 将二维数据进行x轴与y轴互换
	 * 
	 * @param list
	 * @return
	 */
	public static Vector<Vector> rowToCol(Vector<Vector> list) {

		if (Utils.isEmpty(list)) {
			return list;
		}

		List<List<Object>> temp = (List) list;
		List toList = rowToCol(temp);
		return (Vector<Vector>) toList;
	}

	/**
	 * 将二维数据进行x轴与y轴互换
	 * 
	 * @param list
	 * @return
	 */
	public static <T> List<List<T>> rowToCol(List<List<T>> list) {

		if (Utils.isEmpty(list)) {
			return list;
		}

		List<List<T>> toList = new Vector<List<T>>();

		Map<Integer, List<T>> map = new HashMap<Integer, List<T>>();

		for (int i = 0; i < list.size(); i++) {
			List<T> row = list.get(i);

			for (int j = 0; j < row.size(); j++) {
				T cell = row.get(j);

				List<T> col = map.get(j);
				if (col == null) {
					col = new Vector<T>();
				}
				col.add(cell);
				map.put(j, col);
			}
		}

		Vector<Integer> sort = new Vector<Integer>(map.keySet());

		Collections.sort(sort);

		for (Integer key : sort) {
			toList.add(map.get(key));
		}

		return toList;
	}

	/**
	 * 将一个字符串添加到字符串数组指定位置
	 * 
	 * @param source
	 * @param ele
	 * @param index
	 * @return
	 */
	public static String[] addElement(String[] source, String ele, int index) {

		if (source == null) {
			return source;
		}

		String[] ss = new String[source.length + 1];

		for (int i = 0, j = 0; i < ss.length; i++, j++) {
			if (i == index) {
				ss[i] = ele;
				j--;
			} else {
				ss[i] = source[j];
			}
		}
		return ss;
	}
	
	/**
	 * 删除一个元素然后返回一个新的数组
	 * 
	 * @param source
	 * @param index
	 * @return
	 */
	public static String[] removeElement(String[] source, int index) {

		if (source == null) {
			return source;
		}

		String[] ss = new String[source.length - 1];

		for (int i = 0, j = 0; i < source.length; i++, j++) {
			if (i == index) {
				j--;
			} else {
				ss[j] = source[i];
			}
		}
		return ss;
	}

	/**
	 * 将多行一列的Vector<Vector<String>>数据转成数组String[]
	 * 
	 * @param vector
	 *            注意，此vector应该是多行一列的数据
	 * @return
	 */
	public static String[] convertToArray(Vector<Vector> vector) {
		if (vector == null) {
			return null;
		}
		String[] strings = new String[vector.size()];
		for (int i = 0; i < strings.length; i++) {
			strings[i] = (String) vector.get(i).get(0);
		}
		return strings;
	}

	/**
	 * 由于Vector的clone方法只对"行"这一层面进行克隆，所以此方法的目的是对Vector<Vector>的每一列都进行克隆
	 * 
	 * @param source
	 * @return 返回一个新的Vector<Vector>
	 */
	public static Vector<Vector> cloneVector(Vector<Vector> source) {
		if (Utils.isEmpty(source)) {
			return source;
		}
		Vector<Vector> clone = new Vector<Vector>(source.size());
		for (Vector vector : source) {
			Vector row = (Vector) vector.clone();
			clone.add(row);
		}
		return clone;
	}

	/**
	 * 一个int数组是否包含指定的int，如果存在返回true
	 * 
	 * @param ints
	 * @param curr
	 * @return
	 */
	public static boolean isExistInt(int[] ints, int curr) {
		if (ints != null) {
			for (int i = 0; i < ints.length; i++) {
				if (ints[i] == curr) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 一个数组当中是否存在指定的值
	 * 
	 * @param s
	 * @param curr
	 * @return
	 */
	public static <T> boolean isExist(T[] s, T curr) {
		return indexOf(s, curr) > -1;
	}

	/**
	 * 用指定字符串将一个字符串数组拼接成一个字符串
	 */
	public static String spliceArrayValue(Object[] strs, String regex) {
		if (strs == null || strs.length == 0) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		for (Object temp : strs) {
			if (temp == null) {
				temp = "";
			}
			buffer.append(temp);
			buffer.append(regex);
		}
		int lastRegexIndex = buffer.lastIndexOf(regex);
		if (lastRegexIndex >= 0) {
			buffer.delete(lastRegexIndex, buffer.length());// 删除最后一个","
		}
		return buffer.toString();
	}

	/**
	 * 用指定字符串将一个字符串集合拼接成一个字符串
	 */
	public static String spliceCollectionValue(Collection strs, String regex) {
		if (Utils.isEmpty(strs)) {
			return "";
		}
		return spliceArrayValue(strs.toArray(new Object[strs.size()]), regex);
	}

	/**
	 * 如果目标数组长度超过原始数组长度，则将原始数组循环填充目标数组
	 */
	private static <T> T[] copy(T[] source, T[] target) {
		if (target.length <= source.length) {
			return Arrays.copyOf(source, target.length);
		}
		source = Arrays.copyOf(source, source.length);
		for (int i = 0, j = 0; i < target.length; i++, j++) {
			if (i >= source.length) {
				if (j >= source.length) {
					j = 0;
				}
				target[i] = source[j];
			} else {
				target[i] = source[i];
			}
		}
		return target;
	}

	/**
	 * 如果目标长度超过原始数组长度，则将原始数组循环填充
	 */
	public static <T> T[] copy(T[] source, int len) {
		T[] target;
		Class<T[]> arrayType = (Class<T[]>) source.getClass();
		if ((Object) arrayType == (Object) Object[].class) {
			target = (T[]) new Object[len];
		} else {
			target = (T[]) Array.newInstance(arrayType.getComponentType(), len);
		}
		return copy(source, target);
	}

	/**
	 * 寻找下标,没找到返回-1
	 */
	public static <T> int indexOf(T[] source, T obj) {
		int index = -1;
		if (source != null) {
			for (int i = 0; i < source.length; i++) {
				if (obj == null) {
					if (source[i] == null) {
						index = i;
						break;
					}
				} else {
					if (obj == source[i] || obj.equals(source[i])) {
						index = i;
						break;
					}
				}
			}
		}
		return index;
	}
	
	/**
	 * 字符串数组转整形数组
	 */
	public static Integer[] strArray2intArray(String[] arr) {
		if (arr == null || arr.length <= 0) {
			return null;
		}
		Integer[] intArr = new Integer[arr.length];
		for (int i = 0; i < arr.length; i++) {
			intArr[i] = Integer.parseInt(arr[i]);
		}
		return intArr;
	}
	
	public static List<String> getUrlList(String urlsStr) {
		String[] split = urlsStr.split(";");
		List<String> list = Arrays.asList(split);
		return list;
	}
}
