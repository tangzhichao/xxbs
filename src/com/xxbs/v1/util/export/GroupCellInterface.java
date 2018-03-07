package com.xxbs.v1.util.export;

/**
 * ���Ժϲ�Excel��Ԫ��Ľӿ�
 * 
 * @author tang
 * 
 */
public interface GroupCellInterface extends Comparable<GroupCellInterface> {

	int getStartRow();

	int getEndRow();

	int getStartCol();

	int getEndCol();

	Object getValue();

	/**
	 * ʵ�ֹ���:���ÿ�ʼ����,����ȣ����ÿ�ʼ����
	 */
	@Override
	int compareTo(GroupCellInterface o);
}
