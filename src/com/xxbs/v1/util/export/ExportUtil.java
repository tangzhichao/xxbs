package com.xxbs.v1.util.export;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;

import com.xxbs.v1.util.Utils;
import com.xxbs.v1.util.collection.CollectionUtil;
import com.xxbs.v1.util.io.PropertiesFileUtil;
import com.xxbs.v1.util.str.ValidUtil;

/**
 * ExportTool 是一个操作例如excel/word/之类文档导出的工具
 * 
 * 如果数据量过大，并且在乎导出的效率和时间,那么请在导出之前将ExcelConfig对象设置为null,这样导出时既不处理单元格宽高, 单元格宽高由POI自动处理,也不处理单元格的文字字体,单元格边框;
 * 
 * 如果既在乎效率和时间又在乎导出的样式, 那么请在导出之前将ExcelConfig对象的isAutoWidth属性和isAutoHeight属性全部置为false ,这样导出时单元格宽高固定为ExcelConfig对象的height和width的属性值;
 * 
 * ExcelConfig是一个很重要的导出excel时使用的类,可以使用此ExportTool(Component parent, ExcelConfig excelConfig)构造方法和setExcelConfig(ExcelConfig excelConfig)设置ExcelConfig对象,另请查看ExcelConfig类的说明
 * 
 * @author tang
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ExportUtil {

	private static PropertiesFileUtil CONFIG_FILE_TOOL = new PropertiesFileUtil();

	public static Map<String, Integer> preferredWidthMap = new HashMap<>();

	private final int multiTableGapNum = 3;
	private int retryCount = 0;
	private final int maxRetryCount = 5;

	/**
	 * 记录上次的目录
	 */
	private String previouFilePath;

	/**
	 * excelConfig excel单元格样式配置
	 */
	private ExcelConfig excelConfig = new ExcelConfig();

	/**
	 * 弹出的对话框的父组件
	 */
	private Component parentComponent;

	/**
	 * 导出之后是否打开
	 */
	private boolean isExportAfterOpen = true;

	private WriteOutFileCallback writeOutFileCallback;

	public ExportUtil(Component parent, ExcelConfig excelConfig) {
		this.excelConfig = excelConfig;
		this.parentComponent = parent;
		writeOutFileCallback = new DefaultWriteOutFileCallback();
		initPreviouFilePath();
	}

	/**
	 * 为parent赋值
	 * 
	 */
	public ExportUtil(Component parent) {
		this(parent, new ExcelConfig());
	}

	/*-----------------------begin static method-----------------------*/
	/**
	 * 打开一个文件
	 * 
	 * @param path
	 *            文件的完整路径
	 * @return
	 */
	public void openFile(final String path) {
		if (isExportAfterOpen) {
			// 开启一个线程，避免打开文件时由于未知原因卡死
			new Thread() {
				public void run() {
					try {
						Desktop.getDesktop().open(new File(path));
					} catch (Exception e) {
						e.printStackTrace();
						showErrorMessage(e);
					}
				}
			}.start();
		}
	}

	/**
	 * 替换字符串数组中的html字符
	 * 
	 * @param tags
	 *            替换特殊标签，如<span>,一定要带上括号
	 */
	public static String[] replaceHtml(String[] souces, String[] tags) {

		String[] strings2 = new String[souces.length];

		for (int i = 0; i < souces.length; i++) {

			strings2[i] = replaceHtml(souces[i], tags);
		}

		return strings2;
	}

	/**
	 * 替换字符串中的html字符
	 * 
	 * @param tags
	 *            替换特殊标签，如<span>,一定要带上括号
	 */
	public static String replaceHtml(String souce, String[] tags) {

		souce = replaceHtml(souce);

		if (tags != null) {

			for (int j = 0; j < tags.length; j++) {

				souce = souce.replace(tags[j], "");
			}
		}

		return souce;
	}

	/**
	 * 替换字符串中的html字符
	 */
	public static String replaceHtml(String souce) {

		String item = souce;

		item = item.replace("<html>", "");
		item = item.replace("</html>", "");

		item = item.replace("<head>", "");
		item = item.replace("</head>", "");

		item = item.replace("<body>", "");
		item = item.replace("</body>", "");

		item = item.replace("<center>", "");
		item = item.replace("</center>", "");

		item = item.replace("<br>", "");
		item = item.replace("<br/>", "");

		item = item.replace("<b>", "");
		item = item.replace("</b>", "");

		return item;
	}

	/*-----------------------end static method-----------------------*/

	/*-----------------------begin get set method-----------------------*/
	public String getPreviouFilePath() {
		return previouFilePath;
	}

	public Component getParentComponent() {
		return parentComponent;
	}

	public void setParentComponent(Component parent) {
		this.parentComponent = parent;
	}

	public void setExcelConfig(ExcelConfig excelConfig) {
		this.excelConfig = excelConfig;
	}

	public ExcelConfig getExcelConfig() {
		return excelConfig;
	}

	public boolean isExportAfterOpen() {
		return isExportAfterOpen;
	}

	public void setExportAfterOpen(boolean isExportAfterOpen) {
		this.isExportAfterOpen = isExportAfterOpen;
	}

	public void setWriteOutFileCallback(WriteOutFileCallback writeOutFileCallback) {
		this.writeOutFileCallback = writeOutFileCallback;
	}

	public WriteOutFileCallback getWriteOutFileCallback() {
		return writeOutFileCallback;
	}

	/*-----------------------end get set method-----------------------*/

	/*-----------------------begin public method-----------------------*/
	/**
	 * 获得选择的文件路径
	 * 
	 * @param frame
	 * @param fileName
	 *            不需要"."，更不需要扩展名
	 * @param expandName
	 *            不需要"." 只需要后缀名即可，如"pdf"
	 * @return String 是一个文件物理路径，如果为null则表示用户取消或者其他原因，调用此方法必须判断返回的是不是null
	 */
	public String getFilePath(String fileName, String expandName) {

		if (expandName.startsWith(".")) {
			expandName = expandName.substring(1, expandName.length());
		}

		// 文件保存消息框
		JFileChooser fileChooser = (previouFilePath == null || previouFilePath.length() < 1) ? new JFileChooser() : new JFileChooser(previouFilePath);

		FileFilter filter = getFileFilter(expandName);
		fileChooser.addChoosableFileFilter(filter);
		if (fileName != null) {
			fileChooser.setSelectedFile(new File(fileName));// 设置初始文件名字
		}
		fileChooser.setFileFilter(filter);// 调用定义好的文件类型

		int rVal = fileChooser.showSaveDialog(parentComponent);// frame在哪个父容器显示

		String filePath = null;
		if (rVal == JFileChooser.APPROVE_OPTION) {

			String path = fileChooser.getSelectedFile().getAbsolutePath();// 读取文本的路径

			if (!path.endsWith("." + expandName)) {
				filePath = path + "." + expandName;
			} else {
				filePath = path;
			}
		}
		return filePath;

	}

	/**
	 * 保存上此选择的目录
	 * 
	 * @param path
	 *            此参数必须是带上文件名的路径，有没有文件后缀都可以，但是必须要文件名，此外文件分隔符必须与本地操作系统相符
	 */
	public void savePreviouFilePath(String path) {
		previouFilePath = path.substring(0, path.lastIndexOf(File.separator) + 1);
		CONFIG_FILE_TOOL.setValue(previouFilePath);
	}

	/**
	 * 文件过滤器
	 * 
	 * @param expandName
	 * @return
	 */
	public FileFilter getFileFilter(final String expandName) {

		// 文件过滤器,设置文件保存类型
		FileFilter xlsFilter = new FileFilter() {
			// 提示
			@Override
			public String getDescription() {

				return "*." + expandName + "(" + expandName + "文件)";
			}

			// 过滤
			@Override
			public boolean accept(File file) {

				String name = file.getName();

				return (name.toLowerCase().endsWith("." + expandName) || file.isDirectory()) && !file.isHidden();
			}
		};

		return xlsFilter;
	}

	/**
	 * 是否覆盖
	 * 
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean isCover(String filePath) {

		if (new File(filePath).exists()) {

			int showConfirmDialog = JOptionPane.showConfirmDialog(parentComponent, "文件已存在!是否覆盖?", "请选择", JOptionPane.YES_NO_OPTION);

			return showConfirmDialog != JOptionPane.YES_OPTION ? false : true;
		}

		return true;
	}

	/*-----------------------end public method-----------------------*/

	/*-----------------------begin excel/word jointly method-----------------------*/
	private void initPreviouFilePath() {

		previouFilePath = CONFIG_FILE_TOOL.getValue();

		if (previouFilePath == null || previouFilePath.equals("")) {

			previouFilePath = System.getProperty("user.dir") + File.separator + "export";

			File f = new File(previouFilePath);

			if (!f.exists()) {
				f.mkdir();
			}

			// FileSystemView fileView = FileSystemView.getFileSystemView();

			// 桌面文件目录
			// File file = fileView.getHomeDirectory();

			// 我的文档目录如：‘C:\Users\PC\Documents’,此方法可能由于系统默认文件夹被修改而打开的不是‘我的文档’目录，所以不建议使用
			// File file = fileView.getDefaultDirectory();

			// previouFilePath = file.getPath();

			// 用户文件夹目录
			// previouFilePath = System.getProperty("user.home");
		}
	}

	// 捕获异常并处理异常
	public FileOutputStream catchException(Object obj, FileOutputStream out, String filePath, String expandName, Exception e) {
		try {
			if (e != null && e.getMessage() != null && e.getMessage().indexOf("目录名或卷标语法不正确") > -1) {

				int rerult = showRetryMessage("文件名、目录名或卷标语法不正确!");
				if (rerult != JOptionPane.OK_OPTION) {
					return out;
				}

				String fileName = filePath.substring(filePath.indexOf(File.separator) + 1);
				filePath = getFilePath(fileName, expandName);
				if (filePath == null) {
					return out;
				}
				if (!isCover(filePath)) {
					savePreviouFilePath(filePath);
					return out;
				}

				closeStream(out);
				out = writeOutFileCallback.writeOutFile(obj, filePath);
				savePreviouFilePath(filePath);

			} else if (e != null && e.getMessage() != null && e.getMessage().indexOf("另一个程序正在使用此文件") > -1) {

				int rerult = showRetryMessage("另一个程序正在使用此文件，进程无法访问!");
				if (rerult != JOptionPane.OK_OPTION) {
					return out;
				}

				closeStream(out);
				out = writeOutFileCallback.writeOutFile(obj, filePath);
				savePreviouFilePath(filePath);

			} else {
				showErrorMessage(e);
			}
		} catch (Exception e2) {
			// 此时如果又发生异常，很可能是和刚报错时是一样的错误
			if (retryCount < maxRetryCount) {
				retryCount++;
				catchException(obj, out, filePath, expandName, e2);
			} else {
				retryCount = 0;
				showErrorMessage(e);
			}
		}
		return out;
	}

	// 关闭流
	public void closeStream(AutoCloseable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (Exception e) {
			}
		}
	}

	// 用对话框显示错误信息以此通知用户
	private void showErrorMessage(Exception e) {
		//TODO
	}
	
	/**
	 * 文件不能写时的提示框
	 * 
	 * @param window
	 * @param filePath
	 */
	public static void showCannotWriteMessage(Window window, String filePath, Object[] ops, int selectIndex) {
		//TODO
	}

	/**
	 * 文件格式不能打开时的提示框
	 * 
	 * @param window
	 * @param postfix
	 */
	public static void showCannotOpenMessage(Window window, String postfix) {
		//TODO
	}

	// 弹出重试对话框让用户选择
	private int showRetryMessage(String message) {
		String[] option = new String[] { "重试", "取消" };
		JTextArea textArea = new JTextArea(message);
		textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		textArea.setBackground(new Color(0xEEEEEE));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFont(new Font("宋体", Font.PLAIN, 14));
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0xdadada)));
		scrollPane.setPreferredSize(new Dimension(260, 80));
		return JOptionPane.showOptionDialog(parentComponent, scrollPane, "提示信息", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);
	}

	/*-----------------------end excel/word jointly method-----------------------*/

	/*-----------------------begin excel-----------------------*/
	/**
	 * 表头一共有几行
	 * 
	 * @param tableHeadValue
	 * @return
	 */
	private int getTableHeaderRowCount(List<GroupCellInterface> tableHeadValue) {

		GroupCellInterface[] groups = new GroupCellInterface[tableHeadValue.size()];

		tableHeadValue.toArray(groups);// this is a copy operate

		Arrays.sort(groups, new Comparator<GroupCellInterface>() {

			public int compare(GroupCellInterface o1, GroupCellInterface o2) {

				return o1.getEndRow() - o2.getEndRow();
			}
		});

		return groups[groups.length - 1].getEndRow() + 1;
	}

	/**
	 * 转换居中样式字符串为居中样式short数组
	 */
	private short[] conveterAlignment(String colsAlignment) {

		short[] shorts = null;

		if (colsAlignment != null) {

			String[] colStr = colsAlignment.split(";");

			shorts = new short[colStr.length];

			for (int i = 0; i < colStr.length; i++) {

				String col = colStr[i];

				if ("-1".equals(col)) {
					shorts[i] = HSSFCellStyle.ALIGN_LEFT;
				} else if ("1".equals(col)) {
					shorts[i] = HSSFCellStyle.ALIGN_RIGHT;
				} else if ("0".equals(col)) {
					shorts[i] = HSSFCellStyle.ALIGN_CENTER;
				} else {
					shorts[i] = HSSFCellStyle.ALIGN_LEFT;
				}
			}
		}
		return shorts;
	}

	/**
	 * 输出excel文件
	 * 
	 * @param fileName
	 * @param expandName
	 * @param wb
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void outExcelFile(String fileName, String expandName, HSSFWorkbook wb) throws FileNotFoundException, IOException {

		String filePath = getFilePath(fileName, expandName);
		if (filePath == null) {
			return;
		}
		if (!filePath.endsWith(".xls") && !filePath.endsWith(".xlsx")) {
			JOptionPane.showMessageDialog(parentComponent, "保存失败!文件格式不是xls或xlsx!");
			return;
		}
		if (!isCover(filePath)) {
			savePreviouFilePath(filePath);
			return;
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			wb.write(out);
			savePreviouFilePath(filePath);
		} catch (Exception e) {

			out = catchException(wb, out, filePath, expandName, e);

		} finally {
			closeStream(out);
		}

		preferredWidthMap.clear();// 避免占用内存过大

		openFile(filePath);
	}

	/**
	 * 解决合并单元格之后边框不能设置问题
	 * 
	 * @param sheet
	 * @param startRow
	 * @param endRow
	 * @param startCol
	 * @param endCol
	 */
	public void createCellAddBorder(HSSFSheet sheet, int startRow, int endRow, int startCol, int endCol) {

		HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		setStyleDefaultValue(style);

		for (int i = startRow; i <= endRow; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				row = sheet.createRow(i);
			}

			for (int j = startCol; j <= endCol; j++) {
				HSSFCell cell = row.getCell(j);
				if (cell == null) {
					cell = row.createCell(j);
				}
				cell.setCellStyle(style);
			}
		}
	}

	/**
	 * 设置一个sheet所有列自动列宽和自动行高,解决sheet.autoSizeColumn(j)中文bug,此方法很消耗内存,并且耗时过长, 数据量过大时请勿调用
	 * 
	 * @param sheet
	 */
	public void setAutoWidthHeight(HSSFSheet sheet) {

		List<List<HSSFCell>> allList = new ArrayList<List<HSSFCell>>();

		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {

			List<HSSFCell> rowList = new ArrayList<HSSFCell>();
			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				row = sheet.createRow(i);
			}

			for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {

				HSSFCell cell = row.getCell(j);
				if (cell == null || getCellValue(cell) == null && j == row.getLastCellNum()) {
					continue;
				}
				rowList.add(cell);
			}

			allList.add(rowList);
		}

		if (excelConfig.isAutoWidth) {

			List<List<HSSFCell>> rowToCol = CollectionUtil.rowToCol(allList);

			for (int i = 0; i < rowToCol.size(); i++) {

				List<HSSFCell> col = rowToCol.get(i);

				String maxString = getCellValueMaxString(col);
				int width = getPreferredWidth(maxString);

				if (width > excelConfig.maxWidth) {
					width = excelConfig.maxWidth;
				}
				if (width < ExcelConfig.MinWidth) {
					width = ExcelConfig.MinWidth;
				} else if (width > ExcelConfig.MaxWidth) {
					width = ExcelConfig.MaxWidth;
				}

				try {
					sheet.setColumnWidth(i, width);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			sheet.setDefaultColumnWidth(excelConfig.widthByCharCount);
		}

		if (excelConfig.isAutoHeight) {

			for (int i = 0; i < allList.size(); i++) {

				HSSFCell maxStringCell = getMaxStringCell(allList.get(i));
				if (maxStringCell != null) {

					String maxString = Utils.toString(getCellValue(maxStringCell));

					int columnIndex = maxStringCell.getColumnIndex();
					int rowIndex = i;

					int columnWidth = sheet.getColumnWidth(columnIndex);
					int preferredHeight = getPreferredHeight(maxString, columnWidth, getPreferredWidth(maxString));

					if (preferredHeight > excelConfig.maxHeight) {
						preferredHeight = excelConfig.maxHeight;
					}
					if (preferredHeight < ExcelConfig.MinHeight) {
						preferredHeight = ExcelConfig.MinHeight;
					}

					sheet.getRow(rowIndex).setHeight((short) preferredHeight);
				}
			}
		} else {
			for (int i = 0; i < allList.size(); i++) {
				sheet.getRow(i).setHeight((short) excelConfig.height);
			}
		}
	}

	/**
	 * 字符串转换成excel宽度
	 * 
	 * @param str
	 * @return
	 */
	public static int getPreferredWidth(String str) {

		if (str == null || str.isEmpty()) {
			return ExcelConfig.MinWidth;
		}

		Integer integer = preferredWidthMap.get(str);
		if (integer != null) {
			return integer;
		}

		int width = 0;

		try {
			char[] charArray = str.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {// 中文
					width += ExcelConfig.ChineseCharWidth;
				} else if (Character.isUpperCase(charArray[i])) {// 大写字母
					width += ExcelConfig.UpperCharWidth;
				} else {
					width += ExcelConfig.NumberAndLowerCharWidth;
				}
			}
		} catch (Exception e) {
			// ignore
		}
		if (width < ExcelConfig.MinWidth) {
			width = ExcelConfig.MinWidth;
		}

		preferredWidthMap.put(str, width);

		return width;
	}

	/**
	 * 字符串转换成excel高度
	 * 
	 * @param str
	 * @return
	 */
	public static int getPreferredHeight(String str, int colWidth, int preferredWidth) {

		if (str == null || str.isEmpty()) {
			return ExcelConfig.MinHeight;
		}

		int height = 0;
		if (preferredWidth > colWidth) {
			height = ((int) (preferredWidth * 1.0 / colWidth + 1)) * ExcelConfig.MinHeight;
		} else {
			height = ExcelConfig.MinHeight;
		}
		if (height < ExcelConfig.MinHeight) {
			height = ExcelConfig.MinHeight;
		}
		return height;
	}

	/**
	 * 获得某一列或某一行最大的文本
	 * 
	 * @param cells
	 * @return
	 */
	public String getCellValueMaxString(List<HSSFCell> cells) {
		HSSFCell lastCell = getMaxStringCell(cells);
		return lastCell != null ? (Utils.toString(getCellValue(lastCell))) : "";
	}

	/**
	 * 获得某一列或某一行最大的文本的单元格
	 * 
	 * @param cells
	 * @return
	 */
	public HSSFCell getMaxStringCell(List<HSSFCell> cells) {
		if (cells == null || cells.size() < 1) {
			return null;
		}
		try {
			HSSFCell[] hssfCells = new HSSFCell[cells.size()];
			cells.toArray(hssfCells);// this is a copy operation

			Arrays.sort(hssfCells, new Comparator<HSSFCell>() {
				public int compare(HSSFCell o1, HSSFCell o2) {

					Object cellValue1 = getCellValue(o1);
					Object cellValue2 = getCellValue(o2);

					if ("".equals(cellValue1) && "".equals(cellValue2) || cellValue1 == cellValue2) {
						return 0;// 不返回0报错
					}
					if (cellValue1 == null) {
						return -1;
					}
					if (cellValue2 == null) {
						return 1;
					}

					int preferredWidth1 = getPreferredWidth(cellValue1.toString());
					int preferredWidth2 = getPreferredWidth(cellValue2.toString());
					return preferredWidth1 - preferredWidth2;
				}
			});

			HSSFCell lastCell = hssfCells[hssfCells.length - 1];
			return lastCell;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cells.get(0);
	}

	/**
	 * 获取单元格值
	 * 
	 * @param cell
	 * @return
	 */
	public Object getCellValue(HSSFCell cell) {
		if (cell == null) {
			return null;
		}
		int cellType = cell.getCellType();
		if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
			return cell.getNumericCellValue();
		} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
			return cell.getStringCellValue();
		} else if (cellType == HSSFCell.CELL_TYPE_ERROR) {
			return cell.getErrorCellValue();
		} else if (cellType == HSSFCell.CELL_TYPE_FORMULA) {
			return cell.getStringCellValue();
		} else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
			return cell.getBooleanCellValue();
		} else {
			return null;
		}
	}

	/**
	 * 获得表单元格默认样式
	 * 
	 * @param wb
	 * @return
	 */
	public HSSFCellStyle getDefaultStyle(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		setStyleDefaultValue(style);
		HSSFFont font = getDefaultFont(wb);
		style.setFont(font);
		return style;
	}

	/**
	 * 默认字体
	 * 
	 * @param wb
	 * @return
	 */
	public HSSFFont getDefaultFont(HSSFWorkbook wb) {
		HSSFFont font = wb.createFont();
		if (excelConfig == null) {
			font.setFontHeightInPoints((short) 10);
			font.setFontName("宋体");
		} else {
			font.setFontHeightInPoints(excelConfig.fontSize);
			font.setFontName(excelConfig.fontName);
		}
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		return font;
	}

	/**
	 * 获得表头的默认样式
	 * 
	 * @param wb
	 * @return
	 */
	public HSSFCellStyle getHeadStyle(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		setStyleDefaultValue(style);
		HSSFFont font = getDefaultFont(wb);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		return style;
	}

	/**
	 * 给样式设置一些默认值
	 * 
	 * @param style
	 */
	public void setStyleDefaultValue(HSSFCellStyle style) {

		if (excelConfig != null && excelConfig.isBorder) {

			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		}
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setWrapText(true);
	}

	public HSSFSheet createSheet(HSSFWorkbook wb, String sheetName) {
		HSSFSheet sheet = wb.getSheet(sheetName);
		if (sheet == null) {
			sheet = wb.createSheet(sheetName);
		}
		return sheet;
	}

	public HSSFRow createRow(HSSFSheet sheet, int rowIndex) {
		HSSFRow row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}
		return row;
	}

	public HSSFCell createCell(HSSFRow row, int colIndex) {
		HSSFCell cell = row.getCell(colIndex);
		if (cell == null) {
			cell = row.createCell(colIndex);
		}
		return cell;
	}

	/**
	 * 创建复杂表头
	 * 
	 * @param tableHeadValue
	 * @param wb
	 * @param sheet
	 */
	public void createExcelCellForGroup(List<GroupCellInterface> tableHeadValue, HSSFWorkbook wb, HSSFSheet sheet, HSSFCellStyle style) {

		Collections.sort(tableHeadValue);
		if (style == null) {
			style = getDefaultStyle(wb);
		}
		for (int i = 0; i < tableHeadValue.size(); i++) {

			GroupCellInterface execl = tableHeadValue.get(i);

			if (execl.getEndRow() > execl.getStartRow() || execl.getEndCol() > execl.getStartCol()) {

				createCellAddBorder(sheet, execl.getStartRow(), execl.getEndRow(), execl.getStartCol(), execl.getEndCol());
			}
		}
		for (int i = 0; i < tableHeadValue.size(); i++) {

			GroupCellInterface execl = tableHeadValue.get(i);

			if (execl.getEndRow() > execl.getStartRow() || execl.getEndCol() > execl.getStartCol()) {

				CellRangeAddress cellRangeAddress = new CellRangeAddress(execl.getStartRow(), execl.getEndRow(), execl.getStartCol(), execl.getEndCol());

				sheet.addMergedRegion(cellRangeAddress);
			}
			HSSFRow row = createRow(sheet, execl.getStartRow());

			HSSFCell cell = createCell(row, execl.getStartCol());
			cell.setCellValue(execl.getValue().toString());
			cell.setCellStyle(style);
		}
	}

	/**
	 * 创建一张sheet所有单元格，无标题
	 */
	public void createCellForSimple(String[] rowHeadValue, String[] tableHeadValue, Vector<Vector> tableBodyValue, String alignment, HSSFWorkbook wb, HSSFSheet sheet,
			int colHeadLenght, boolean isAlignmentByColunm) {
		createCellForMulti(null, rowHeadValue, tableHeadValue, tableBodyValue, alignment, wb, sheet, colHeadLenght, isAlignmentByColunm);
	}

	/**
	 * 创建一张sheet所有单元格,有标题
	 */
	public void createCellForMulti(String title, String[] rowHeadValue, String[] tableHeadValue, Vector<Vector> tableBodyValue, String alignment, HSSFWorkbook wb,
			HSSFSheet sheet, int colHeadLenght, boolean isAlignmentByColunm) {

		if (tableBodyValue == null || tableBodyValue.size() < 1) {
			return;
		}

		short[] align_HSSFCellStyle = conveterAlignment(alignment);

		Vector<Vector> tableBodyValueClone = (Vector<Vector>) tableBodyValue.clone();

		boolean isRowHeader = rowHeadValue != null && rowHeadValue.length > 0;

		if (isRowHeader) {
			tableBodyValueClone = CollectionUtil.addOfColumn(tableBodyValueClone, rowHeadValue, 0);
		}

		HSSFCellStyle headStyle = getHeadStyle(wb);

		HSSFCellStyle leftStyle = getDefaultStyle(wb);
		leftStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		HSSFCellStyle centerStyle = getDefaultStyle(wb);

		HSSFCellStyle rightStyle = getDefaultStyle(wb);
		rightStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

		boolean isColHeader = tableHeadValue != null && tableHeadValue.length > 0;

		int contentRowNum = 0;

		if (title != null) {

			int lastRowNum = sheet.getLastRowNum();
			int titleRowNum = lastRowNum + (lastRowNum == 0 ? 0 : multiTableGapNum);
			contentRowNum = lastRowNum + (lastRowNum == 0 ? 1 : multiTableGapNum + 1);

			createCellAddBorder(sheet, titleRowNum, titleRowNum, 0, colHeadLenght - 1);

			CellRangeAddress cellRangeAddress = new CellRangeAddress(titleRowNum, titleRowNum, 0, colHeadLenght - 1);
			sheet.addMergedRegion(cellRangeAddress);

			HSSFRow titleRow = createRow(sheet, titleRowNum);
			titleRow.setHeight((short) 350);
			HSSFCell titleCell = createCell(titleRow, 0);
			titleCell.setCellValue(title);

			HSSFCellStyle titleStyle = getHeadStyle(wb);
			titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

			HSSFFont titleFont = getDefaultFont(wb);
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			titleStyle.setFont(titleFont);

			titleCell.setCellStyle(titleStyle);
		}
		// 如果没有创建列头就创建列头
		if (isColHeader) {

			HSSFRow row = sheet.createRow(contentRowNum);

			for (int j = 0; j < tableHeadValue.length; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(tableHeadValue[j]);
				cell.setCellStyle(headStyle);
			}
		}

		int headerRowCount = 0;

		// 设置行
		for (int i = 0; i < tableBodyValueClone.size(); i++) {

			Vector<String> rowData = tableBodyValueClone.get(i);

			if (rowData == null || rowData.size() < 1) {
				headerRowCount++;
				continue;
			}
			// 在sheet中创建行
			HSSFRow row = sheet.createRow(contentRowNum + i + (isColHeader ? 1 : 0));

			// 设置列
			for (int j = 0; j < colHeadLenght; j++) {

				String valueStr = rowData.get(j);

				// 将文本转为double主要原因在于，如果不转他就有警告
				// double valueDou = 0.0;

				boolean isNum = ValidUtil.isNumber(valueStr);

				if (valueStr == null) {
					valueStr = "";

					// } else if (BairuiUtil.isNumber(valueStr) &&
					// !BairuiUtil.isOperator(valueStr)) {
					// try {
					// // 这里转为double会进行四舍五入，所以这样不符合金额计算，
					// //
					// 比如你有1.9999999999元但不能买2元的物品，这是必须的要求，但是如果你转为double他就变成了2元
					//
					// //
					// 但是，我还是转为double了，原因在于，你即使不进行四舍五入，excel也会进行四舍五入，并且精度更低,excel保留14位，java保留15位
					// valueDou = Double.parseDouble(valueStr);
					// isNum = true;
					// // isNum = BairuiUtil.isNumber(valueDou + "");//后来发现没必要
					// } catch (NumberFormatException e) {
					// }
				} else if (valueStr.trim().equals("-")) {
					// 如果只是单纯的减号，我们不需要excel把减号认为是公式
					valueStr = "—";
				} else if (valueStr.trim().equals("--")) {
					// 如果只是单纯的符号，我们不需要excel把此符号认为是公式
					valueStr = "——";
				}

				HSSFCell cell = row.createCell(j);

				// if (isNum) {
				// cell.setCellValue(valueDou);
				// } else {
				// // cell.setCellType(Cell.CELL_TYPE_STRING);
				// cell.setCellValue(valueStr);
				// }
				cell.setCellValue(valueStr);

				if (isRowHeader && j == 0) {

					cell.setCellStyle(leftStyle);

				} else {

					int temp = isAlignmentByColunm ? j : i;

					if (!isColHeader && !isAlignmentByColunm) {
						temp = temp - headerRowCount;
					} else if (isRowHeader && isAlignmentByColunm) {
						temp = temp - 1;
					}

					if (align_HSSFCellStyle != null && align_HSSFCellStyle.length > 0 && temp < align_HSSFCellStyle.length) {

						if (align_HSSFCellStyle[temp] == HSSFCellStyle.ALIGN_LEFT) {
							cell.setCellStyle(leftStyle);
						} else if (align_HSSFCellStyle[temp] == HSSFCellStyle.ALIGN_RIGHT) {
							cell.setCellStyle(rightStyle);
						} else {
							cell.setCellStyle(centerStyle);
						}

					} else {

						if (isNum || ValidUtil.isPercent(valueStr)) {
							cell.setCellStyle(rightStyle);
						} else {
							cell.setCellStyle(leftStyle);
						}
					}
				}
			}
		}
		// excelConfig如果为空，宽高由poi自身处理(poi会自动调整行高，但是列宽固定)
		if (excelConfig != null) {
			if (excelConfig.isAutoWidth || excelConfig.isAutoHeight) {
				try {
					// 如果设置自动宽高(行高或列宽会根据内容自动适应)，此设置比较消耗内存，因为牵涉很多遍历和比较计算
					setAutoWidthHeight(sheet);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// 如果不自动宽高，宽高会被设置成固定的值，即excelConfig.width和excelConfig.height
				sheet.setDefaultColumnWidth(excelConfig.widthByCharCount);
				for (Row row : sheet) {
					row.setHeight((short) excelConfig.height);
				}
			}
		}
	}

	/**
	 * 为excel添加一张图片
	 */
	public void addExcelPicture(HSSFWorkbook wb, HSSFSheet sheet, RenderedImage image, int beginRow, double rate) {
		addExcelPicture(wb, sheet, image, beginRow, sheet.getRow(beginRow).getLastCellNum() + 1, rate);
	}

	/**
	 * 为excel添加一张图片
	 * 
	 * @param beginRow
	 *            这张图片的位置 -开始行
	 * @param beginCol
	 *            这张图片的位置 -开始列
	 * @param rate
	 *            生成到excel的图片大小与原图片大小的比例
	 */
	public void addExcelPicture(HSSFWorkbook wb, HSSFSheet sheet, RenderedImage image, int beginRow, int beginCol, double rate) {
		if (image == null) {
			return;
		}
		try {
			// 内存流，可以不关闭
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

			ImageIO.write(image, "png", arrayOutputStream);

			// 一个sheet不管插入多少图片，只需要创建一个HSSFPatriarch对象
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

			// 原英文注释：http://poi.apache.org/apidocs/org/apache/poi/hssf/usermodel/HSSFClientAnchor.html
			// dx1 - the x coordinate within the first cell.dx1 - x coordinate
			// of the left up corner
			// dy1 - the y coordinate within the first cell.dy1 - y coordinate
			// of the left up corner
			// dx2 - the x coordinate within the second cell.dx2 - x coordinate
			// of the right down corner
			// dy2 - the y coordinate within the second cell.dy2 - y coordinate
			// of the right down corner
			// col1 - the column (0 based) of the first cell.
			// row1 - the row (0 based) of the first cell.
			// col2 - the column (0 based) of the second cell.
			// row2 - the row (0 based) of the second cell.
			// new HSSFClientAnchor(dx1, dy1, dx2, dy2, col1, row1, col2, row2)

			// 设置图片大小和位置
			// dx2最大值 1023,dy2最大值255
			// short beginCol = (short)
			// (sheet.getRow(beginRow).getLastCellNum()+ 1);

			// 八个参数还没搞懂,但只需要知道anchor对象就是用来确定图片位置的
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) beginCol, beginRow, (short) beginCol, beginRow);

			// setAnchorType只能设置为这三个值：ClientAnchor.MOVE_AND_RESIZE=0,
			// ClientAnchor.MOVE_DONT_RESIZE=2,
			// ClientAnchor.DONT_MOVE_AND_RESIZE=3
			// 0 = Move and size with Cells,
			// 2 = Move but don't size with cells,
			// 3 = Don't move or size with cells.

			// 三个值都试过了，没看出有任何差别
			anchor.setAnchorType(3);

			// POI只支持DIB,EMF,JPEG,PICT,PNG,WMF格式
			HSSFPicture picture = patriarch.createPicture(anchor, wb.addPicture(arrayOutputStream.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

			// resize(参数是与原图片大小的比例),1.0也就是保持图片原来大小
			picture.resize(rate);

			arrayOutputStream.close();

		} catch (IOException e) {
			showErrorMessage(e);
		}
	}

	/**
	 * 导出excel,不合并表头不合并表单元格，用于一个sheet一个表格数据，不需要行头，不需要表格标题 详细注释请参见exportExcelForTableMultiRowHeader
	 */
	public void exportExcelForTableSimple(String fileName, HSSFWorkbook wb, String sheetName, String[] tableHeadValue, Vector<Vector> tableBodyValue, String alignment,
			boolean isOut, boolean isAlignmentByColunm) {
		exportExcelForTableSimpleRowHeader(fileName, wb, sheetName, null, tableHeadValue, tableBodyValue, alignment, isOut, isAlignmentByColunm);
	}

	/**
	 * 导出excel,不合并表头不合并表单元格，用于一个sheet一个表格数据，需要行头，不需要表格标题 详细注释请参见exportExcelForTableMultiRowHeader
	 */
	public void exportExcelForTableSimpleRowHeader(String fileName, HSSFWorkbook wb, String sheetName, String[] rowHeadValue, String[] tableHeadValue,
			Vector<Vector> tableBodyValue, String alignment, boolean isOut, boolean isAlignmentByColunm) {

		exportExcelForTableMultiRowHeader(fileName, wb, sheetName, null, rowHeadValue, tableHeadValue, tableBodyValue, alignment, isOut, isAlignmentByColunm);
	}

	/**
	 * 导出excel,不合并表头不合并表单元格，用于一个sheet一个表格数据，需要行头，需要表格标题
	 * 
	 * @注意 ：如果wb为null，则isOut必须为true，反之亦然isOut如果为false，则wb必须不为null
	 * @param fileName
	 *            输出文件的默认文件名，无需文件拓展名
	 * @param wb
	 *            表示一个excel文件，可以为null，如果为null，会新建Workbook，否则调用传入的Workbook
	 * @param sheetName
	 *            这张sheet名字
	 * @param title
	 *            给这表格起一个标题，这个标题会在表格的最上方占据一行
	 * @param rowHeadValue
	 *            数据的行头，不包括列头的第一列，即不包括行头和列头交叉部分，交叉部分由列头处理
	 * @param tableHeadValue
	 *            列头
	 * @param tableBodyValue
	 *            数据
	 * @param alignment
	 *            列或行水平对齐方式(-1：左对齐, 0:居中对齐, 1右对齐),用分号";"间隔,如alignment="0;1;1-1;-1;1"
	 * @param isOut
	 *            是否输出文件，如果传入false则不输出文件，还可以继续为wb添加sheet，也就是说你可以反复调用此方法， 只要你传入一个wb，并在最后一次调用时将isOut传入true,或者自己调用outExcelFile
	 * @param isAlignmentByColunm
	 *            表示参数alignment是不是列样式，如果为true则参数alignment表示列的对齐方式， 如果为false则参数alignment表示行的对齐方式
	 */
	public void exportExcelForTableMultiRowHeader(String fileName, HSSFWorkbook wb, String sheetName, String title, String[] rowHeadValue, String[] tableHeadValue,
			Vector<Vector> tableBodyValue, String alignment, boolean isOut, boolean isAlignmentByColunm) {

		if (wb == null) {
			wb = new HSSFWorkbook();
		}

		HSSFSheet sheet = createSheet(wb, sheetName);

		createCellForMulti(title, rowHeadValue, tableHeadValue, tableBodyValue, alignment, wb, sheet, tableHeadValue.length, isAlignmentByColunm);

		if (!isOut) {
			return;
		}
		try {
			outExcelFile(fileName, "xls", wb);
		} catch (Exception e) {
			showErrorMessage(e);
		}
	}

	/**
	 * 导出excel,不合并表头不合并表单元格，用于一张sheet带多张表格数据，需要行头，需要表格标题 详细注释请参见exportExcelForTableMultiRowHeader
	 */
	public void exportExcelForTableMultisRowHeader(String fileName, HSSFWorkbook wb, String sheetName, String[] titles, String[][] rowHeadValues, String[][] tableHeadValues,
			Vector<Vector>[] tableBodyValues, String[] alignments, boolean isOut, boolean[] isAlignmentByColunms) {

		if (wb == null) {
			wb = new HSSFWorkbook();
		}

		HSSFSheet sheet = createSheet(wb, sheetName);
		

		for (int i = 0; i < titles.length; i++) {
			
			int headerLength=tableHeadValues[i]!=null?tableHeadValues[i].length:(tableBodyValues[i].get(0)!=null?tableBodyValues[i].get(0).size():0);
			
			createCellForMulti(titles[i], rowHeadValues != null ? rowHeadValues[i] : null, tableHeadValues[i], tableBodyValues[i], alignments != null ? alignments[i] : null, wb,
					sheet, headerLength, isAlignmentByColunms != null ? isAlignmentByColunms[i] : true);
		}

		if (!isOut) {
			return;
		}
		try {
			outExcelFile(fileName, "xls", wb);
		} catch (Exception e) {
			showErrorMessage(e);
		}
	}

	/**
	 * 导出excel,不合并表头不合并表单元格的，用于导出多张sheet 详细注释请参见exportExcelForTableMultiRowHeader
	 */
	public void exportExcelForTablesSimple(String fileName, String[] sheetNames, String[][] tableHeadValues, Vector<Vector>[] tableBodyValues, String[] alignments,
			boolean[] isAlignmentByColunm) {

		exportExcelForTablesSimpleRowHeader(fileName, sheetNames, null, tableHeadValues, tableBodyValues, alignments, isAlignmentByColunm);
	}

	/**
	 * 导出excel,不合并表头不合并表单元格的，用于导出多张sheet 详细注释请参见exportExcelForTableMultiRowHeader
	 */
	public void exportExcelForTablesSimpleRowHeader(String fileName, String[] sheetNames, String[][] rowHeadValues, String[][] tableHeadValues, Vector<Vector>[] tableBodyValues,
			String[] alignments, boolean[] isAlignmentByColunms) {

		HSSFWorkbook wb = new HSSFWorkbook();

		for (int i = 0; i < sheetNames.length; i++) {

			exportExcelForTableSimpleRowHeader(fileName, wb, sheetNames[i],

			rowHeadValues != null ? rowHeadValues[i] : null,

			tableHeadValues[i], tableBodyValues[i],

			alignments != null ? alignments[i] : null,

			i == sheetNames.length - 1 ? true : false, isAlignmentByColunms != null ? isAlignmentByColunms[i] : true);
		}
	}

	/**
	 * 导出excel,合并表头不合并表单元格的，只用于导出一个excel一张表， 详细注释请参见exportExcelForTableMultiRowHeader
	 */
	public void exportExcelForTableGroup(String fileName, HSSFWorkbook wb, String sheetName, List<GroupCellInterface> tableHeadValue, Vector<Vector> tableBodyValue,
			String alignment, boolean isOut, boolean isAlignmentByColunm) {

		if (wb == null) {
			wb = new HSSFWorkbook();
		}

		HSSFSheet sheet = createSheet(wb, sheetName);

		createExcelCellForGroup(tableHeadValue, wb, sheet, getHeadStyle(wb));

		Vector<Vector> tableBodyValueClone = (Vector<Vector>) tableBodyValue.clone();

		int headerRowCount = getTableHeaderRowCount(tableHeadValue);

		for (int i = 0; i < headerRowCount; i++) {
			tableBodyValueClone.add(0, null);
		}

		// sheet.getRow(headerRowCount-1).getLastCellNum()还可以这样写sheet.getRow(0).getLastCellNum()没区别
		createCellForSimple(null, null, tableBodyValueClone, alignment, wb, sheet, sheet.getRow(headerRowCount - 1).getLastCellNum(), isAlignmentByColunm);

		if (!isOut) {
			return;
		}
		try {
			outExcelFile(fileName, "xls", wb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出excel,合并表头不合并表单元格的，用于导出一个多张sheet 详细注释请参见exportExcelForTableMultiRowHeader
	 */
	public void exportExcelForTablesGroup(String fileName, String[] sheetNames, List<GroupCellInterface>[] tableHeadValues, Vector<Vector>[] tableBodyValues, String[] alignments,
			boolean[] isAlignmentByColunms) {

		HSSFWorkbook wb = new HSSFWorkbook();

		for (int i = 0; i < sheetNames.length; i++) {

			HSSFSheet sheet = createSheet(wb, sheetNames[i]);

			createExcelCellForGroup(tableHeadValues[i], wb, sheet, getHeadStyle(wb));

			Vector<Vector> tableBodyValueClone = (Vector<Vector>) tableBodyValues[i].clone();

			int headerRowCount = getTableHeaderRowCount(tableHeadValues[i]);

			for (int j = 0; j < headerRowCount; j++) {
				tableBodyValueClone.add(0, null);
			}

			createCellForSimple(null, null, tableBodyValueClone, alignments != null ? alignments[i] : null, wb, sheet, sheet.getRow(headerRowCount - 1).getLastCellNum(),
					isAlignmentByColunms != null ? isAlignmentByColunms[i] : true);
		}

		try {
			outExcelFile(fileName, "xls", wb);
		} catch (Exception e) {
			showErrorMessage(e);
		}
	}

	/**
	 * 同exportExcelForTableSimple，不过多了一张图片
	 * 
	 * @param image
	 *            可以调用getRenderedImageByComponent(Component component)根据一个组件得到一张图片
	 * @param rate
	 *            生成到excel的图片大小与原图片大小的比例
	 */
	public void exportExcelForImageSimple(String fileName, HSSFWorkbook wb, String sheetName, String[] tableHeadValue, Vector<Vector> tableBodyValue, String alignment,
			boolean isOut, boolean isAlignmentByColunm, RenderedImage image, double rate) {

		if (wb == null) {
			wb = new HSSFWorkbook();
		}

		exportExcelForTableSimple(null, wb, sheetName, tableHeadValue, tableBodyValue, alignment, false, isAlignmentByColunm);

		addExcelPicture(wb, wb.getSheet(sheetName), image, 1, rate);

		if (!isOut) {
			return;
		}
		try {
			outExcelFile(fileName, "xls", wb);
		} catch (Exception e) {
			showErrorMessage(e);
		}

	}

	/**
	 * 同exportExcelForTableGroup，不过多了一张图片
	 * 
	 * @param image
	 *            可以调用getRenderedImageByComponent(Component component)根据一个组件得到一张图片
	 * @param rate
	 *            生成到excel的图片大小与原图片大小的比例
	 */
	public void exportExcelForImageGroup(String fileName, HSSFWorkbook wb, String sheetName, List<GroupCellInterface> tableHeadValue, Vector<Vector> tableBodyValue,
			String alignment, boolean isOut, boolean isAlignmentByColunm, RenderedImage image, double rate) {

		if (wb == null) {
			wb = new HSSFWorkbook();
		}

		exportExcelForTableGroup(null, wb, sheetName, tableHeadValue, tableBodyValue, alignment, false, isAlignmentByColunm);
		addExcelPicture(wb, wb.getSheet(sheetName), image, 1, rate);

		if (!isOut) {
			return;
		}
		try {
			outExcelFile(fileName, "xls", wb);
		} catch (Exception e) {
			showErrorMessage(e);
		}
	}

	/**
	 * 导出一张sheet带多张表格并且带多张图片的excel
	 * 
	 * @param titles
	 *            每个表格的标题
	 * @param rowHeadValues
	 *            如果不需要设置行头可以设置为null
	 * @param tableHeadValues
	 * @param tableBodyValues
	 * @param alignments
	 * @param isOut
	 *            是否输出excel
	 * @param isAlignmentByColunms
	 * @param images
	 *            每个表格的图片
	 * @param rates
	 *            图片大小比例
	 * @详细注释 请参见exportExcelForTableMultiRowHeader
	 */
	public void exportExcelForImageMultisRowHeader(String fileName, HSSFWorkbook wb, String sheetName, String[] titles, String[][] rowHeadValues, String[][] tableHeadValues,
			Vector<Vector>[] tableBodyValues, String[] alignments, boolean isOut, boolean[] isAlignmentByColunms, RenderedImage[] images, double[] rates) {

		if (wb == null) {
			wb = new HSSFWorkbook();
		}

		HSSFSheet sheet = createSheet(wb, sheetName);
		
		int maxHeaderLength=0;
		for (int i = 0; i < titles.length; i++) {
			int headerLength=tableHeadValues[i]!=null?tableHeadValues[i].length:(tableBodyValues[i].get(0)!=null?tableBodyValues[i].get(0).size():0);
			maxHeaderLength=Math.max(maxHeaderLength, headerLength);
		}
		
		for (int i = 0; i < titles.length; i++) {

			RenderedImage image = images[i];
			if (image != null) {
				int beginRow = sheet.getLastRowNum() + (i == 0 ? 0 : multiTableGapNum);
				addExcelPicture(wb, sheet, image, beginRow, maxHeaderLength + 1, rates != null ? rates[i] : 1.0);
			}
			
			int headerLength=tableHeadValues[i]!=null?tableHeadValues[i].length:(tableBodyValues[i].get(0)!=null?tableBodyValues[i].get(0).size():0);
			
			createCellForMulti(titles[i], rowHeadValues != null ? rowHeadValues[i] : null, tableHeadValues[i], tableBodyValues[i], alignments != null ? alignments[i] : null, wb,
					sheet, headerLength, isAlignmentByColunms != null ? isAlignmentByColunms[i] : true);
		}

		if (!isOut) {
			return;
		}
		try {
			outExcelFile(fileName, "xls", wb);
		} catch (Exception e) {
			showErrorMessage(e);
		}
	}

	/*-----------------------end excel-----------------------*/

	/*-----------------------begin word-----------------------*/
	/**
	 * 输出word
	 */
	private void outDocFile(String fileName, String expandName, XWPFDocument document) throws FileNotFoundException, IOException {

		String filePath = getFilePath(fileName, expandName);
		if (filePath == null) {
			return;
		}
		if (!filePath.endsWith(".doc") && !filePath.endsWith(".docx")) {
			JOptionPane.showMessageDialog(parentComponent, "保存失败!文件格式不是doc或docx!");
			return;
		}
		if (!isCover(filePath)) {
			savePreviouFilePath(filePath);
			return;
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			document.write(out);
			savePreviouFilePath(filePath);
		} catch (Exception e) {

			out = catchException(document, out, filePath, expandName, e);

		} finally {
			closeStream(out);
		}

		openFile(filePath);
	}

	/**
	 * word 创建表单元格
	 * 
	 * @param tableHeadValue
	 * @param tableBodyValue
	 * @param table
	 */
	private void createCell(String[] tableHeadValue, Vector<Vector> tableBodyValue, XWPFTable table) {

		if (tableBodyValue == null || tableBodyValue.size() < 1) {
			return;
		}

		table.setCellMargins(0, 0, 0, (9000 / tableHeadValue.length)); // YES

		Vector<Vector> tableBodyValueClone = (Vector<Vector>) tableBodyValue.clone();

		if (tableHeadValue != null && tableHeadValue.length > 0) {

			tableBodyValueClone.add(0, new Vector<String>(Arrays.asList(tableHeadValue)));

		}
		for (int i = 0; i < tableBodyValueClone.size(); i++) {

			Vector<String> rowData = tableBodyValueClone.get(i);

			if (rowData == null || rowData.size() < 1) {
				continue;
			}

			XWPFTableRow row = table.createRow();

			row.setHeight(500);// YES

			for (int j = 1; j < rowData.size(); j++) {

				String value = rowData.get(j);

				if (value == null || value.length() < 1) {
					continue;
				}

				XWPFTableCell cell = row.createCell();

				cell.setText(value);

				cell.setVerticalAlignment(XWPFVertAlign.CENTER);

				if (i == 0) {
					// 如果是表头
				} else {
					// 如果不是表头
				}
			}

			if (row.getTableCells().size() >= tableHeadValue.length) {

				XWPFTableCell cell = row.getCell(0);

				cell.setText(rowData.get(0));

				cell.setVerticalAlignment(XWPFVertAlign.CENTER);
			}
		}

		if (table.getRows().size() > tableBodyValueClone.size()) {

			table.removeRow(0);
		}
	}

	/**
	 * 为doc文档添加一张图片,来源于网络,@link http://www.it165.net/pro/html/201108/451.html
	 * 
	 * @注意：其中需要改动的是addPicture换成addPictureData,createPicture的第一个参数换成doc.getAllPictures().size()-1
	 * 
	 * @param doc
	 * @param id
	 * @param width
	 * @param height
	 */
	public void addDocPicture(XWPFDocument doc, int id, int width, int height) {
		final int EMU = 9525;
		width *= EMU;
		height *= EMU;

		String blipId = doc.getAllPictures().get(id).getPackageRelationship().getId();

		CTInline inline = doc.createParagraph().createRun().getCTR().addNewDrawing().addNewInline();

		String picXml = "" + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
				+ "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
				+ "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" + "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""
				+ id
				+ "\" name=\"Generated\"/>"
				+ "            <pic:cNvPicPr/>"
				+ "         </pic:nvPicPr>"
				+ "         <pic:blipFill>"
				+ "            <a:blip r:embed=\""
				+ blipId
				+ "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
				+ "            <a:stretch>"
				+ "               <a:fillRect/>"
				+ "            </a:stretch>"
				+ "         </pic:blipFill>"
				+ "         <pic:spPr>"
				+ "            <a:xfrm>"
				+ "               <a:off x=\"0\" y=\"0\"/>"
				+ "               <a:ext cx=\""
				+ width
				+ "\" cy=\""
				+ height
				+ "\"/>"
				+ "            </a:xfrm>"
				+ "            <a:prstGeom prst=\"rect\">"
				+ "               <a:avLst/>" + "            </a:prstGeom>" + "         </pic:spPr>" + "      </pic:pic>" + "   </a:graphicData>" + "</a:graphic>";

		// CTGraphicalObjectData graphicData =
		inline.addNewGraphic().addNewGraphicData();
		XmlToken xmlToken = null;
		try {
			xmlToken = XmlToken.Factory.parse(picXml);
		} catch (XmlException xe) {
			xe.printStackTrace();
		}
		inline.set(xmlToken);
		// graphicData.set(xmlToken);

		inline.setDistT(0);
		inline.setDistB(0);
		inline.setDistL(0);
		inline.setDistR(0);

		CTPositiveSize2D extent = inline.addNewExtent();
		extent.setCx(width);
		extent.setCy(height);

		CTNonVisualDrawingProps docPr = inline.addNewDocPr();
		docPr.setId(id);
		docPr.setName("图片" + id);
		docPr.setDescr("");
	}

	private void setPageTitleValue(XWPFParagraph page) {
		setPageDefaultValue(page);
		page.setAlignment(ParagraphAlignment.CENTER);
	}

	private void setPageDefaultValue(XWPFParagraph page) {
		page.setAlignment(ParagraphAlignment.LEFT);
		page.setVerticalAlignment(TextAlignment.TOP);
		page.setBorderBottom(Borders.NONE);
		page.setBorderTop(Borders.NONE);
		page.setBorderRight(Borders.NONE);
		page.setBorderLeft(Borders.NONE);
		page.setBorderBetween(Borders.NONE);
		page.setWordWrap(true);
		page.setPageBreak(false);
		page.setIndentationFirstLine(100);
	}

	private void setRunTitleValue(XWPFRun run, String text) {
		run.setText(text);
		run.setBold(true);
		run.setFontFamily("Courier");
		run.setFontSize(18);
		run.setUnderline(UnderlinePatterns.NONE);
		run.setTextPosition(50);
		// run.addBreak(BreakType.TEXT_WRAPPING);
	}

	private void setRunDefaultValue(XWPFRun run, String text) {
		run.setText(text);
		run.setBold(false);
		run.setFontFamily("宋体");
		run.setFontSize(12);
		run.setUnderline(UnderlinePatterns.NONE);
		run.setTextPosition(0);
		// run.setItalic(true);
	}

	private XWPFRun setDocumentTextValues(XWPFDocument doc, String text, boolean isTitle) {
		if (text != null) {
			XWPFParagraph page = doc.createParagraph();
			setPageDefaultValue(page);
			XWPFRun run = page.createRun();

			setRunDefaultValue(run, text);
			if (isTitle && text.length() < 40) {
				run.setBold(true);
			}
			return run;
		}
		return null;
	}

	/**
	 * 创建XWPFDocument
	 * 
	 * @param title
	 *            标题
	 * @param pageStrings
	 *            所有页的文本数组
	 * @return
	 */
	private XWPFDocument createDocForTextSimple(String title, String... pageStrings) {

		XWPFDocument doc = new XWPFDocument();
		XWPFParagraph page = doc.createParagraph();
		setPageTitleValue(page);

		XWPFRun titleRun = page.createRun();
		setRunTitleValue(titleRun, title);

		page = doc.createParagraph();
		setPageDefaultValue(page);
		// page.setSpacingLineRule(LineSpacingRule.EXACT);
		// page.setIndentationFirstLine(100);

		if (pageStrings != null && pageStrings.length > 0) {

			for (int i = 0; i < pageStrings.length; i++) {// 这个循环是为了分页

				XWPFRun run = setDocumentTextValues(doc, pageStrings[i], true);

				if (run != null) {
					run.addBreak(BreakType.PAGE);
				}
			}
		}

		return doc;
	}

	/**
	 * 创建XWPFDocument
	 * 
	 * @param title
	 *            标题
	 * @param tableHeadValue
	 *            表头
	 * @param tableBodyValue
	 *            表数据
	 * @return
	 */
	private XWPFDocument createDocForTableSimple(String title, String[] tableHeadValue, Vector<Vector> tableBodyValue) {
		XWPFDocument doc = new XWPFDocument();
		XWPFParagraph page = doc.createParagraph();

		setPageTitleValue(page);

		XWPFRun titleRun = page.createRun();
		setRunTitleValue(titleRun, title);

		XWPFTable table = doc.createTable();
		createCell(tableHeadValue, tableBodyValue, table);
		return doc;
	}

	/**
	 * 创建XWPFDocument
	 * 
	 * @param component
	 *            根据此控件导出图片
	 * @param title
	 * @return
	 */
	private XWPFDocument createDocForImageSimple(Component component, String title) {
		XWPFDocument doc = new XWPFDocument();
		XWPFParagraph page = doc.createParagraph();
		setPageTitleValue(page);
		XWPFRun titleRun = page.createRun();
		setRunTitleValue(titleRun, title);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedImage image = (BufferedImage) getImageByComponent(component);
		try {

			ImageIO.write(image, "png", out);
			// String id = doc.addPictureData(out.toByteArray(), XWPFDocument.PICTURE_TYPE_PNG);
			doc.addPictureData(out.toByteArray(), XWPFDocument.PICTURE_TYPE_PNG);
			addDocPicture(doc, doc.getAllPictures().size() - 1, 604, 444);

		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStream(out);
		}

		return doc;
	}

	/**
	 * 导出简单文本word
	 * 
	 * 
	 * @param title
	 * @param content
	 *            所有页的文本数组
	 */
	public void exportWordForTextSimple(String title, String... content) {
		try {
			outDocFile(title, "doc", createDocForTextSimple(title, content));
		} catch (Exception e) {
			showErrorMessage(e);
		}
	}

	/**
	 * 导出简单表格word
	 * 
	 * @param title
	 * @param tableHeadValue
	 * @param tableBodyValue
	 */
	public void exportWordForTable(String title, String[] tableHeadValue, Vector<Vector> tableBodyValue) {
		try {
			outDocFile(title, "doc", createDocForTableSimple(title, tableHeadValue, tableBodyValue));
		} catch (Exception e) {
			showErrorMessage(e);
		}
	}

	/**
	 * 根据一个swing组件导出word，这个组件生成的图片的word
	 * 
	 * 
	 * @param component
	 *            这个组件会被刻录成一张图片，然后添加至word中
	 * @param title
	 */
	public void exportWordForComponentImage(Component component, String title) {
		try {
			XWPFDocument doc = createDocForImageSimple(component, title);
			outDocFile(title, "doc", doc);
		} catch (Exception e) {
			showErrorMessage(e);
		}
	}

	/*-----------------------end word-----------------------*/

	/*-----------------------begin image-----------------------*/
	/**
	 * 输出图片
	 * 
	 * 
	 * @param fileName
	 * @param expandName
	 * @param image
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void outImage(String fileName, String expandName, RenderedImage image) throws FileNotFoundException, IOException {
		String filePath = getFilePath(fileName, expandName);
		if (filePath == null) {
			return;
		}
		if (!isCover(filePath)) {
			savePreviouFilePath(filePath);
			return;
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			ImageIO.write(image, expandName, out);
			savePreviouFilePath(filePath);
		} catch (Exception e) {

			catchException(image, out, filePath, expandName, e);

		} finally {
			closeStream(out);
		}

		openFile(filePath);
	}

	/**
	 * 根据一个组件，导出一张图片
	 * 
	 * @param component
	 * @param title
	 *            图片标题和导出的文件名
	 */
	public void exportImageForComponent(Component component, String title, boolean isTitle) {

		boolean isNoneTitle = title == null || title.length() < 1 || !isTitle;

		int titleHeight = isNoneTitle ? 0 : 50;

		BufferedImage image = createImage(component, 0, titleHeight);
		Graphics g = image.getGraphics();

		if (!isNoneTitle) {

			Color oldColor = g.getColor();
			g.setColor(Color.WHITE);

			// 填充一个空的区域,用于画标题
			g.fillRect(0, 0, component.getWidth(), titleHeight);
			g.setColor(oldColor);

			Font oldFont = g.getFont();
			g.setFont(new Font("宋体", Font.BOLD, 24));
			// 画标题
			g.drawString(title, 50, 30);
			g.setFont(oldFont);

			g.translate(0, titleHeight);

		}

		component.paint(g);

		try {
			outImage(title, "png", image);
		} catch (Exception e) {
			showErrorMessage(e);
		}
	}

	public BufferedImage createImage(Component component, int offsetWidth, int offsetHeight) {
		// 使用component.createImage方法创建Image，如果组件的父组件为null,component.createImage方法会返回null
		BufferedImage image = (BufferedImage) component.createImage(component.getWidth() + offsetWidth, component.getHeight() + offsetHeight);
		if (image == null) {
			// 直接new BufferedImage可能会导致图像模糊
			image = new BufferedImage(component.getWidth() + offsetWidth, component.getHeight() + offsetHeight, BufferedImage.TYPE_3BYTE_BGR);
		}
		return image;
	}

	/**
	 * 根据一个组件，得到一张图片(Image)<br>
	 * 不能使用component.createImage方法创建Image，因为如果组件没有显示在屏幕上时，component.createImage方法会返回null
	 * 
	 * @param component
	 * @return Image
	 */
	public Image getImageByComponent(Component component) {
		if (component == null) {
			return null;
		}
		Image image = createImage(component, 0, 0);
		Graphics g = image.getGraphics();
		component.paint(g);
		return image;
	}

	/**
	 * 根据一个组件，得到一张图片(RenderedImage)
	 * 
	 * @param component
	 * @return Image
	 */
	public RenderedImage getRenderedImageByComponent(Component component) {
		return imageToRenderedImage(getImageByComponent(component));
	}

	/**
	 * 进行图片转换，将Image类型转成RenderedImage类型
	 * 
	 * @param image
	 * @return
	 */
	private RenderedImage imageToRenderedImage(Image image) {
		if (image == null) {
			return null;
		}
		if (image instanceof RenderedImage) {
			return (RenderedImage) image;
		}
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(image, null, null);
		return bufferedImage;
	}

	/*-----------------------end image-----------------------*/

	/*-----------------------begin text-----------------------*/
	private void outText(String fileName, String expandName, String text) throws FileNotFoundException, IOException {
		String filePath = getFilePath(fileName, expandName);
		if (filePath == null) {
			return;
		}
		if (!isCover(filePath)) {
			savePreviouFilePath(filePath);
			return;
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			out.write(text.getBytes());
			savePreviouFilePath(filePath);
		} catch (Exception e) {

			catchException(text, out, filePath, expandName, e);

		} finally {
			closeStream(out);
		}

		openFile(filePath);
	}

	public void exportTextForSimple(String fileName, String context) {
		try {
			outText(fileName, "txt", context);
		} catch (Exception e) {
			showErrorMessage(e);
		}
	}

	/*-----------------------end text-----------------------*/

	/*-----------------------begin test-----------------------*/

	public static Vector<Vector> getTestData(int row, int col) {
		Vector<Vector> all = new Vector<Vector>();
		for (int i = 0; i < row; i++) {
			Vector<String> rowData = new Vector<String>();
			rowData.add("201" + i + "年");
			for (int j = 1; j < col; j++) {
				rowData.add(new Random().nextInt(100) + "");
			}
			all.add(rowData);
		}
		return all;
	}

	/**
	 * 2行7列合并表头测试数据
	 * 
	 * @return
	 */
	public static List<GroupCellInterface> getGroups() {

		List<GroupCellInterface> all = new ArrayList<GroupCellInterface>();
		Random random = new Random();
		ExcelGroupCell cell1 = new ExcelGroupCell(0, 1, 0, 0, random.nextInt(100) + "");
		ExcelGroupCell cell2 = new ExcelGroupCell(0, 0, 1, 2, random.nextInt(100) + "");
		ExcelGroupCell cell3 = new ExcelGroupCell(0, 0, 3, 5, random.nextInt(100) + "");
		ExcelGroupCell cell4 = new ExcelGroupCell(0, 1, 6, 6, random.nextInt(100) + "");

		ExcelGroupCell cell5 = new ExcelGroupCell(1, 1, 1, 1, random.nextInt(100) + "");
		ExcelGroupCell cell6 = new ExcelGroupCell(1, 1, 2, 2, random.nextInt(100) + "");
		ExcelGroupCell cell7 = new ExcelGroupCell(1, 1, 3, 3, random.nextInt(100) + "");
		ExcelGroupCell cell8 = new ExcelGroupCell(1, 1, 4, 4, random.nextInt(100) + "");
		ExcelGroupCell cell9 = new ExcelGroupCell(1, 1, 5, 5, random.nextInt(100) + "");

		all.add(cell1);
		all.add(cell2);
		all.add(cell3);
		all.add(cell4);
		all.add(cell5);
		all.add(cell6);
		all.add(cell7);
		all.add(cell8);
		all.add(cell9);

		return all;
	}

	/**
	 * 3行7列合并表头测试数据
	 * 
	 * @return
	 */
	public static List<GroupCellInterface> getGroups2() {
		List<GroupCellInterface> all = new ArrayList<GroupCellInterface>();
		Random random = new Random();
		ExcelGroupCell cell1 = new ExcelGroupCell(0, 2, 0, 0, random.nextInt(100) + "");
		ExcelGroupCell cell2 = new ExcelGroupCell(0, 0, 1, 2, random.nextInt(100) + "");
		ExcelGroupCell cell3 = new ExcelGroupCell(0, 0, 3, 5, random.nextInt(100) + "");
		ExcelGroupCell cell4 = new ExcelGroupCell(0, 2, 6, 6, random.nextInt(100) + "");

		ExcelGroupCell cell5 = new ExcelGroupCell(1, 2, 1, 1, random.nextInt(100) + "");
		ExcelGroupCell cell6 = new ExcelGroupCell(1, 2, 2, 2, random.nextInt(100) + "");
		ExcelGroupCell cell7 = new ExcelGroupCell(1, 2, 3, 3, random.nextInt(100) + "");

		ExcelGroupCell cell8 = new ExcelGroupCell(1, 1, 4, 5, random.nextInt(100) + "");
		ExcelGroupCell cell9 = new ExcelGroupCell(2, 2, 4, 4, random.nextInt(100) + "");
		ExcelGroupCell cell10 = new ExcelGroupCell(2, 2, 5, 5, random.nextInt(100) + "");

		all.add(cell1);
		all.add(cell2);
		all.add(cell3);
		all.add(cell4);
		all.add(cell5);
		all.add(cell6);
		all.add(cell7);
		all.add(cell8);
		all.add(cell9);
		all.add(cell10);

		return all;
	}

	// /*
	// /**
	// * excel 测试
	// *
	// * @param args
	// * @throws Exception
	// */
	// public static void main2(String[] args) throws Exception {
	//
	// ExportTool exportTool = new ExportTool(null);
	// HSSFWorkbook wb = new HSSFWorkbook();
	//
	// exportTool.exportExcelForTableSimple("123", wb, "1-0", new String[] { "col1", "col2" }, getTestData(3, 2), "1;0;-1", false, false);
	// exportTool.exportExcelForTableSimple("123", wb, "1-1", new String[] { "col1", "col2" }, getTestData(3, 2), "1;-1", false, true);
	//
	// exportTool.exportExcelForTableSimpleRowHeader("123", wb, "2-0", new String[] { "row1", "row2", "row3" }, new String[] { "col1", "col2", "col3",
	// "col4", "col5" }, getTestData(3, 4), "1;0;-1", false, false);
	// exportTool.exportExcelForTableSimpleRowHeader("123", wb, "2-1", new String[] { "row1", "row2", "row3" }, new String[] { "col1", "col2", "col3",
	// "col4", "col5" }, getTestData(3, 4), "1;0;-1;-1", false, true);
	//
	// exportTool.exportExcelForTableGroup("123", wb, "3-0", getGroups(), getTestData(3, 7), "-1;0;1;0;-1;0;1", false, true);
	// exportTool.exportExcelForTableGroup("123", wb, "3-1", getGroups(), getTestData(3, 7), "-1;0;1", false, false);
	//
	// exportTool.exportExcelForTableGroup("123", wb, "4-0", getGroups2(), getTestData(3, 7), "-1;0;1;0;-1;0;1", false, true);
	// exportTool.exportExcelForTableGroup("123", wb, "4-1", getGroups2(), getTestData(3, 7), "-1;0;1", true, false);
	//
	// }
	//
	// public static void main(String[] args) {
	//
	// JFrame frame = ComponentFactory.getDefaultFrame(500, 50);
	// frame.setBackground(Color.YELLOW);
	// frame.setVisible(true);
	//
	// ExportTool exportTool = new ExportTool(null);
	// RenderedImage image = exportTool.getRenderedImageByComponent(frame);
	//
	// HSSFWorkbook wb = new HSSFWorkbook();
	// // HSSFSheet sheet = wb.createSheet("1");
	// //
	// // exportTool.addExcelPicture(wb, sheet, image, sheet.getLastRowNum(), 2
	// // + 1, 1.0);
	// // exportTool.createCellForMulti("123", null, new String[] { "col11",
	// // "col21" }, getTestData(3, 2), "1;-1", wb, sheet, 2, true);
	// //
	// // exportTool.addExcelPicture(wb, sheet, image, sheet.getLastRowNum() +
	// // 3, 4 + 1, 1.0);
	// // exportTool.createCellForMulti("234", null, new String[] { "col12",
	// // "col22", "col32", "col42" }, getTestData(3, 4), "1;-1;1;-1", wb,
	// // sheet, 4, true);
	// //
	// // sheet = wb.createSheet("2");
	// //
	// // exportTool.addExcelPicture(wb, sheet, image, sheet.getLastRowNum(), 2
	// // + 1, 1.0);
	// // exportTool.createCellForMulti("345", null, new String[] { "col13",
	// // "col23" }, getTestData(1, 2), "1;-1", wb, sheet, 2, true);
	// //
	// // exportTool.addExcelPicture(wb, sheet, image, sheet.getLastRowNum() +
	// // 3, 3 + 1, 1.0);
	// // exportTool.createCellForMulti("456", null, new String[] { "col14",
	// // "col24", "col34" }, getTestData(2, 3), "1;-1;1", wb, sheet, 3, true);
	// // try {
	// // exportTool.outExcelFile("1234", "xls", wb);
	// // } catch (Exception e) {
	// // e.printStackTrace();
	// // }
	//
	// wb = new HSSFWorkbook();
	// String[] titles = { "123", "234" };
	// String[][] tableHeadValues = { new String[] { "col11", "col21" }, new String[] { "col12", "col22", "col32", "col42" } };
	// Vector[] tableBodyValues = { getTestData(3, 2), getTestData(3, 4) };
	// String[] alignments = { "1;-1", "1;-1;1;-1" };
	// RenderedImage[] images = { image, image };
	// double[] rates = { 1.0, 1.0 };
	// exportTool.exportExcelForImageMultisRowHeader(null, wb, "1", titles, null, tableHeadValues, tableBodyValues, alignments, false, null, images, rates);
	//
	// String[] titles2 = { "345", "456" };
	// String[][] tableHeadValues2 = { new String[] { "col13", "col23" }, new String[] { "col14", "col24", "col34" } };
	// Vector[] tableBodyValues2 = { getTestData(1, 2), getTestData(2, 3) };
	// String[] alignments2 = { "1;-1", "1;-1;1" };
	// exportTool.exportExcelForImageMultisRowHeader("2345", wb, "2", titles2, null, tableHeadValues2, tableBodyValues2, alignments2, true, null, images,
	// rates);
	// }

	/*-----------------------end test-----------------------*/
	// // 导出ppt
	// public void exportPpt() {
	//
	// }
	//
	// // 导出pdf
	// public void exportPdfForTableSimple() {
	//
	// }
	//
	// // 导出pdf
	// public void exportPdfForTextSimple() {
	//
	// }
	//
	// // 导出pdf
	// public void exportPdfForImageSimple() {
	//
	// }
}
