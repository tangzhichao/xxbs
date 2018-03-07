package com.xxbs.v1.util.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {
	
	/**
	 * 改变图片宽度高度
	 * @param file
	 * @param savePath
	 * @param saveWidth
	 * @param saveHeight
	 */
	public static void changeSize(File file, String savePath, int saveWidth, int saveHeight){
		try{
			//输出图片
			File picFile = new File(savePath);
			FileOutputStream out = new FileOutputStream(picFile);
			
			Image src = javax.imageio.ImageIO.read(file);
			
			BufferedImage bo = new BufferedImage(saveWidth, saveHeight,  BufferedImage.TYPE_INT_RGB);
			bo.getGraphics().drawImage(src.getScaledInstance(saveWidth, saveHeight, Image.SCALE_SMOOTH), 0, 0, null);
			
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	        encoder.encode(bo);
	        
			bo.flush();
			out.flush();
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 在图片上自动添加水印
	 * @param waterImage
	 * @param targetImage
	 * @param out
	 */
	public static void creatMark(String waterImage,String targetImage,OutputStream out){
		try {
			//目标文件
			File targetFile=new File(targetImage);
			Image target=ImageIO.read(targetFile);
			int tw=target.getWidth(null);
			int th=target.getHeight(null);
			BufferedImage image=new BufferedImage(tw,th,BufferedImage.TYPE_INT_RGB);
			Graphics g=image.createGraphics();
			g.drawImage(target, 0, 0, tw, th,null);
			
			//水印文件
			File waterFile=new File(waterImage);
			Image water=ImageIO.read(waterFile);
			int ww=water.getWidth(null);
			int wh=water.getHeight(null);
			g.drawImage(water, tw-ww, th-wh, ww, wh, null);
			g.dispose();
			JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.flush() ;
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
