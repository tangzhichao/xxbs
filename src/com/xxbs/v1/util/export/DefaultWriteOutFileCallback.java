package com.xxbs.v1.util.export;

import java.awt.image.RenderedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * �����쳣��ص�����ķ�������д���ļ� <br>
 * ����ʱ������쳣���ڴ����쳣����ô����д���ļ�����
 * 
 * @author tang
 * 
 */
public class DefaultWriteOutFileCallback implements WriteOutFileCallback {

	protected String path;

	// д���ļ�
	@Override
	public FileOutputStream writeOutFile(Object obj, String path) throws FileNotFoundException, IOException, Exception {
		return writeOutFile(obj, new FileOutputStream(this.path = path));
	}

	// д���ļ�
	@Override
	public FileOutputStream writeOutFile(Object obj, FileOutputStream out) throws FileNotFoundException, IOException, Exception {

		if (obj instanceof HSSFWorkbook) {

			HSSFWorkbook workbook = (HSSFWorkbook) obj;

			workbook.write(out);

		} else if (obj instanceof XWPFDocument) {

			XWPFDocument document = (XWPFDocument) obj;

			document.write(out);

		} else if (obj instanceof RenderedImage) {

			RenderedImage image = (RenderedImage) obj;

			ImageIO.write(image, "png", out);

		} else if (obj instanceof String) {

			out.write(obj.toString().getBytes());
		}
		return out;
	}

}
