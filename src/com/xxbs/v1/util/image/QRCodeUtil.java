package com.xxbs.v1.util.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeUtil {

	public static void main(String[] args) throws WriterException {
		try {
//			批量
			generateQRCode(1000, 1000, "http://www.baidu.com/", "C:\\Users\\tang\\Desktop\\QR1.png", "C:\\Users\\tang\\Desktop\\IMG_20150226_192939.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param qrW
	 * @param qrH
	 * @param qrContext
	 * @param qrPath
	 * @param logoPath
	 */
	public static boolean generateQRCode(int qrW, int qrH, String qrContext, String qrPath, String logoPath) {
		try {
			QRCodeWriter writer = new QRCodeWriter();

			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//选择H档容错度，即使30%的图案被遮挡，也可以被正确扫描，这是保证之后添加LOGO图标的关键
			BitMatrix matrix = writer.encode(qrContext, BarcodeFormat.QR_CODE, qrW, qrH, hints);

			BufferedImage qrImg = MatrixToImageWriter.toBufferedImage(matrix);

			BufferedImage logoImg = ImageIO.read(new File(logoPath));
			int logoW = logoImg.getWidth(null) > qrImg.getWidth() * 2 / 10 ? (qrImg.getWidth() * 2 / 10) : logoImg.getWidth(null);
			int logoH = logoImg.getHeight(null) > qrImg.getHeight() * 2 / 10 ? (qrImg.getHeight() * 2 / 10) : logoImg.getWidth(null);
			int logoX = (qrImg.getWidth() - logoW) / 2;
			int logoY = (qrImg.getHeight() - logoH) / 2;

			Graphics2D g = qrImg.createGraphics();
			g.drawImage(logoImg, logoX, logoY, logoW, logoH, null);
			g.dispose();
			qrImg.flush();

			ImageIO.write(qrImg, qrPath.substring(qrPath.lastIndexOf(".") + 1), new File(qrPath));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
