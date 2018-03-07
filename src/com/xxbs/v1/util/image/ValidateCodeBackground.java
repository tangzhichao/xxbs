package com.xxbs.v1.util.image;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import com.google.code.kaptcha.BackgroundProducer;
import com.google.code.kaptcha.util.Configurable;

public class ValidateCodeBackground extends Configurable implements BackgroundProducer {

	/**
	 * 默认背景
	 */
	public BufferedImage addBackground(BufferedImage baseImage) {
		Color colorFrom = getConfig().getBackgroundColorFrom();
	    Color colorTo = getConfig().getBackgroundColorTo();
	    //colorFrom = Color.WHITE;
	    //colorTo = Color.RED;
	    
	    int width = baseImage.getWidth();
	    int height = baseImage.getHeight();
	    
	    BufferedImage imageWithBackground = new BufferedImage(width, height, 1);
	    
	    Graphics2D graph = (Graphics2D)imageWithBackground.getGraphics();
	    
	    RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	    
	    hints.add(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY));
	    hints.add(new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY));
	    hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
	    
	    graph.setRenderingHints(hints);
	    
	    //int horizontalGaps = height / (height / 20 + 1);
	    //int verticalGaps = width / (width / 15 + 1);
	    
	    GradientPaint paint = new GradientPaint(0.0F, 0.0F, colorFrom, width, height, colorTo);
	    
	    graph.setPaint(paint);
	    graph.fill(new Rectangle2D.Double(0.0D, 0.0D, width, height));
	    /*
	    for (int i = 10; i < height; i += horizontalGaps){
	    	graph.setColor(Color.decode("0xA1C8F0"));
	    	graph.drawLine(0, i, width, i);
	    }
	    for (int i = verticalGaps; i < width; i += verticalGaps){
	    	graph.setColor(Color.decode("0xA1C8F0"));
	    	graph.drawLine(i, 0, i, height);
	    }
	    */
	    graph.drawImage(baseImage, 0, 0, null);
	    
	    return imageWithBackground;
		
//	    int imageHeight = baseImage.getHeight();
//	    int imageWidth = baseImage.getWidth();
//	    
//		Graphics2D graph = (Graphics2D)baseImage.getGraphics();
//		
//	    int horizontalLines = imageHeight / 7;
//	    int verticalLines = imageWidth / 7;
//	    
//	    int horizontalGaps = imageHeight / (horizontalLines + 1);
//	    int verticalGaps = imageWidth / (verticalLines + 1);
//	    
//	   
//	    RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
//	    hints.add(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY));
//	    hints.add(new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY));
//	    hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
//	    graph.setRenderingHints(hints);
//	    
//	    for (int i = horizontalGaps; i < imageHeight; i += horizontalGaps){
//	    	graph.setColor(Color.BLUE);
//	    	graph.drawLine(0, i, imageWidth, i);
//	    }
//	    
//	    for (int i = verticalGaps; i < imageWidth; i += verticalGaps){
//	    	graph.setColor(Color.RED);
//	    	graph.drawLine(i, 0, i, imageHeight);
//	    }
	    
//	    return baseImage;
	}
	
}
