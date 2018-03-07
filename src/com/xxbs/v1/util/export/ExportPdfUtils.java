package com.xxbs.v1.util.export;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.xxbs.v1.util.Utils;
import com.xxbs.v1.util.export.IndustryCentralityPdfDataBean.ChildModule;

/**
 * ��ҵ���ĵ���pdf������
 * 
 * @author tang
 * @date 2015/3/25
 */
public class ExportPdfUtils {

	private final int defaultPageWidth = 842;
	private final int defaultPageHeight = 595;
	private final String creator = "tzc";

	private int pageWidth = defaultPageWidth;
	private int pageHeight = defaultPageHeight;
	// pxת��pt�ı����С
	float rate = 3f / 4f;
	private BaseFont chineseBaseFont;

	private final com.itextpdf.text.Font font20 = createFont(20, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
	private final com.itextpdf.text.Font font12 = createFont(12, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
	private final com.itextpdf.text.Font fontBold26 = createFont(26, com.itextpdf.text.Font.BOLD, BaseColor.BLACK);
	private final com.itextpdf.text.Font fontBold20 = createFont(20, com.itextpdf.text.Font.BOLD, BaseColor.BLACK);

	private ExportUtil exportTool;

	private final String expandName = "pdf";

	public ExportPdfUtils(final ExportUtil exportTool) {
		this.exportTool = exportTool;
		exportTool.setWriteOutFileCallback(new DefaultWriteOutFileCallback() {
			public FileOutputStream writeOutFile(Object obj, FileOutputStream out) throws FileNotFoundException, IOException, Exception {
				if (obj instanceof IndustryCentralityPdfDataBean) {
					IndustryCentralityPdfDataBean bean = (IndustryCentralityPdfDataBean) obj;
					return createDocument(bean, path, out);
				}
				return null;
			}
		});
	}

	public int getPageWidth() {
		return pageWidth;
	}

	public void setPageWidth(int pageWidth) {
		this.pageWidth = pageWidth;
	}

	public int getPageHeight() {
		return pageHeight;
	}

	public void setPageHeight(int pageHeight) {
		this.pageHeight =pageHeight;
	}

	public void exportPdf(IndustryCentralityPdfDataBean bean) {

		String filePath = exportTool.getFilePath(bean.getFileName(), expandName);
		if (filePath == null) {
			return;
		}
		if (!filePath.endsWith("." + expandName)) {
			JOptionPane.showMessageDialog(exportTool.getParentComponent(), "����ʧ��!�ļ���ʽ����pdf!");
			return;
		}
		if (!exportTool.isCover(filePath)) {
			exportTool.savePreviouFilePath(filePath);
			return;
		}

		createDocument(bean, filePath, null);
	}

	private FileOutputStream createDocument(IndustryCentralityPdfDataBean bean, String filePath, FileOutputStream out) {
		Document document = null;
		PdfWriter writer = null;
		boolean outIsNull = out == null || !out.getChannel().isOpen();
		boolean isOpenFile = true;
		try {
			if (outIsNull) {
				out = new FileOutputStream(filePath);
			}
			document = createDocument();
			writer = createWriter(document, out);
			setDocumentStyle(document, bean.getCompany(), bean.getFillSplitTitle(), bean.getFillTitle(), bean.getModuleName());// ��������Ҫ���ڴ�����֮��
			setWriterStyle(writer);
			document.open();
			editDocument(bean, document);
			if (outIsNull) {
				exportTool.savePreviouFilePath(filePath);
			}
		} catch (Exception e) {
			out = exportTool.catchException(bean, out, filePath, expandName, e);
			isOpenFile = false;
		} finally {
			closeDocument(document, writer);
			if (outIsNull) {
				exportTool.closeStream(out);
			}
		}

		if (isOpenFile) {
			exportTool.openFile(filePath);
		}

		return out;
	}

	private void editDocument(IndustryCentralityPdfDataBean bean, Document document) throws Exception, DocumentException {

		document.setPageSize(new Rectangle(pageWidth, defaultPageHeight));
		document.newPage();

		Paragraph paragraph = createParagraph("  ", fontBold26);
		setParagraphStyle(paragraph, 0, 0, 0, Paragraph.ALIGN_CENTER, 50);
		addBreakRow(paragraph, 3);
		document.add(paragraph);

		paragraph = createParagraph(bean.getDistrict() + bean.getIndustry(), fontBold26);
		setParagraphStyle(paragraph, 0, 0, 0, Paragraph.ALIGN_CENTER, 50);
		document.add(paragraph);

		paragraph = createParagraph(bean.getModuleName(), fontBold26);
		setParagraphStyle(paragraph, 0, 0, 0, Paragraph.ALIGN_CENTER, 50);
		document.add(paragraph);

		addEmpty(document, 10);

		paragraph = createParagraph(bean.getCompany(), font12);
		setParagraphStyle(paragraph, 0, 0, 0, Paragraph.ALIGN_CENTER, 50);
		document.add(paragraph);

		if (bean.getAllImageComponent() != null) {// ���������ģ��ȫ��һ��
			document.setPageSize(new Rectangle(pageWidth, pageHeight));
			document.newPage();

			com.itextpdf.text.Image image = createImage(bean.getAllImageComponent());
			setImageStyle(bean.getAllImageComponent(), image, com.itextpdf.text.Image.ALIGN_CENTER, false);
			document.add(image);
			return;
		}

		for (int i = 0; i < bean.getChildModules().size(); i++) {

			ChildModule childModule = bean.getChildModules().get(i);

			Dimension ps = childModule.getPageSize();
			int ph = ps.height >= defaultPageHeight ? ps.height : defaultPageHeight;
			document.setPageSize(new Rectangle(pageWidth, ph));
			document.newPage();

			paragraph = createParagraph(childModule.getTitle(), fontBold20);
			setParagraphStyle(paragraph, 50, 50, 0, Paragraph.ALIGN_LEFT, 30);
			addBreakRow(paragraph);

			Chapter chapter = createChapter(paragraph, 0);
			setChapterStyle(chapter, 0, 0, 0);

			paragraph = createParagraph(childModule.getDate(), font20);
			setParagraphStyle(paragraph, 50, 50, 0, Paragraph.ALIGN_LEFT, 30);
			addBreakRow(paragraph, 2);
			chapter.add(paragraph);

			if (childModule.getTableComponent() != null) {// ����б�
				com.itextpdf.text.Image image = createImage(childModule.getTableComponent());
				setImageStyle(childModule.getTableComponent(), image, com.itextpdf.text.Image.ALIGN_LEFT, false);

				paragraph = createParagraph("", font20);
				setParagraphStyle(paragraph, 30, 50, 0, Paragraph.ALIGN_LEFT, 30);
				paragraph.add(image);

				chapter.add(paragraph);
			}

			if (childModule.getChartsContainer() != null) {// �������ͼһ��
				com.itextpdf.text.Image image = createImage(childModule.getChartsContainer());
				setImageStyle(childModule.getChartsContainer(), image, com.itextpdf.text.Image.ALIGN_LEFT, false);
				chapter.add(image);
				document.add(chapter);
				continue;
			}

			LinkedHashMap<String, Component> charts = childModule.getCharts();
			if (Utils.isEmpty(charts)) {
				document.add(chapter);
				continue;
			}

			int columns = childModule.getChartColumns();
			PdfPTable table = new PdfPTable(columns);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			int[] widths = new int[columns];
			Arrays.fill(widths, 1);
			table.setWidths(widths);
			// table.setWidthPercentage(100);
			table.setTotalWidth(childModule.getChartSize().width * columns);

			chapter.add(table);

			for (Entry<String, Component> entry : childModule.getCharts().entrySet()) {

				com.itextpdf.text.Image image = createImage(entry.getValue());
				setImageStyle(entry.getValue(), image, com.itextpdf.text.Image.ALIGN_CENTER, false);

				table.addCell(image);
			}
			int size = childModule.getCharts().size();
			int remainder = size % columns;
			addEmpty(table, columns - remainder);// ���ͼ�θ��������������������ֻᱻITextȥ�����������Ϊ�˷�ֹITextȥ��
			document.add(chapter);
		}
	}

	public void closeDocument(Document document, PdfWriter writer) {
		try {
			if (document != null) {
				document.close();
			}
		} catch (Exception ex) {
		}
		try {
			if (writer != null) {
				writer.close();
			}
		} catch (Exception ex) {
		}
	}

	public BaseFont getBaseFont() {
		if (chineseBaseFont == null) {
			chineseBaseFont = createChineseBaseFont();
		}
		return chineseBaseFont;
	}

	public BaseFont createChineseBaseFont() {
		try {
			return BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public com.itextpdf.text.Font createFont(int size, int style, BaseColor color) {
		return new com.itextpdf.text.Font(getBaseFont(), size, style, color);
	}

	public com.itextpdf.text.Image createImage(Component component) throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();// �ڴ��������Բ��ر�
		RenderedImage renderedImage = exportTool.getRenderedImageByComponent(component);
		ImageIO.write(renderedImage, "png", arrayOutputStream);
		com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(arrayOutputStream.toByteArray());
		arrayOutputStream.close();
		return image;
	}

	public void setImageStyle(Component imageComponent, com.itextpdf.text.Image image, int alignment, boolean isFillWidth) {
		image.setAlignment(alignment);
		// image.scaleToFit(imageComponent.getWidth() * rate, imageComponent.getHeight() * rate);// ��Ҫ��pxת��pt
		image.scaleAbsolute(imageComponent.getWidth() * rate-1, imageComponent.getHeight() * rate-1);// ��Ҫ��pxת��pt
		if (isFillWidth) {
			float originalW = image.getWidth();
			float originalH = image.getHeight();
			float originalRate = originalW / originalH;
			image.scaleAbsolute(pageWidth, pageWidth / originalRate);
		}
	}

	public PdfWriter createWriter(Document document, FileOutputStream out) throws Exception {
		PdfWriter writer = PdfWriter.getInstance(document, out);
		return writer;
	}

	public void setWriterStyle(PdfWriter writer) {
		writer.setStrictImageSequence(true);
	}

	public Document createDocument() {
		Document document = new Document(new Rectangle(defaultPageWidth, defaultPageHeight), 50, 50, 50, 50);
		return document;
	}

	public void setDocumentStyle(Document document, String author, String keyword, String title, String subject) {
		document.setPageSize(new Rectangle(defaultPageWidth, defaultPageHeight));
		document.addCreationDate();
		document.addProducer();
		document.addAuthor(author);
		document.addKeywords(keyword);
		document.addCreator(creator);
		document.addTitle(title);
		document.addSubject(subject);
		// document.setMargins(0f, 0f, 10f, 10f);
	}

	public Paragraph createParagraph(String text, com.itextpdf.text.Font font) throws Exception {
		Paragraph paragraph = new Paragraph(text, font);
		return paragraph;
	}

	public void setParagraphStyle(Paragraph paragraph, int leftGap, int rightGap, int firstLineIndent, int alignment, int leading) {
		paragraph.setIndentationLeft(leftGap);
		paragraph.setIndentationRight(rightGap);
		paragraph.setFirstLineIndent(firstLineIndent);
		paragraph.setAlignment(alignment);
		paragraph.setLeading(leading);// �м��
	}

	public static Chapter createChapter(Paragraph paragraph, int number) {
		Chapter chapter = new Chapter(paragraph, number);
		return chapter;
	}

	public static void setChapterStyle(Chapter chapter, int numberDepth, int leftGap, int rightGap) {
		chapter.setIndentationLeft(leftGap);
		chapter.setIndentationRight(rightGap);
		chapter.setNumberDepth(numberDepth);
	}

	public static Section createSection(Chapter chapter, Paragraph paragraph, int leftGap, int rightGap) {
		Section section = chapter.addSection(paragraph);
		return section;
	}

	public static void setSectionStyle(Section section, int leftGap, int rightGap) {
		section.setIndentationLeft(leftGap);
		section.setIndentationRight(rightGap);
	}

	public static void addEmpty(Section section, int len) {
		for (int i = 0; i < len; i++) {
			section.add(new Paragraph(" "));
		}
	}

	public static void addEmpty(PdfPTable table, int len) {
		for (int i = 0; i < len; i++) {
			table.addCell(" ");
		}
	}

	public static void addEmpty(Document document, int len) throws Exception {
		for (int i = 0; i < len; i++) {
			document.add(new Paragraph(" "));
		}
	}

	public static void addBreakRow(List<Element> elements) {
		elements.add(Chunk.NEWLINE);
	}

	public static void addBreakRow(List<Element> element, int len) {
		for (int i = 0; i < len; i++) {
			addBreakRow(element);
		}
	}
}
