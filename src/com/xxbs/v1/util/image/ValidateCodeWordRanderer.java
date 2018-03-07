package com.xxbs.v1.util.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.google.code.kaptcha.text.WordRenderer;
import com.google.code.kaptcha.util.Configurable;

public class ValidateCodeWordRanderer extends Configurable implements WordRenderer {
	
	/**
	 * 验证码自定义颜色
	 */
	
	//默认配置com.google.code.kaptcha.text.impl.DefaultWordRenderer
	/**
	 * 字体颜色数组
	 */
	private Color [] colors = {
			Color.decode("0x1116f1"),
			Color.decode("0xff3000"),
			Color.decode("0xD15D3C"),
			Color.decode("0x7C679F"),
			Color.decode("0x3275A3"),
			Color.decode("0x8080FF"),
			Color.decode("0xff0000"),
			Color.decode("0x5B8738"),
			Color.decode("0x96B36E"),
			Color.decode("0x1D5EA3")
	};
	
	public BufferedImage renderWord(String word, int width, int height){
		
		int fontSize = getConfig().getTextProducerFontSize();
		Font[] fonts = getConfig().getTextProducerFonts(fontSize);
		Color color = getConfig().getTextProducerFontColor();
		int charSpace = getConfig().getTextProducerCharSpace();
		BufferedImage image = new BufferedImage(width, height, 2);

		Graphics2D g2D = image.createGraphics();
		g2D.setColor(color);

		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));

		g2D.setRenderingHints(hints);

		FontRenderContext frc = g2D.getFontRenderContext();
		Random random = new Random();

		int startPosY = (height - fontSize) / 5 + fontSize;
    
		char[] wordChars = word.toCharArray();
		Font[] chosenFonts = new Font[wordChars.length];
    	int[] charWidths = new int[wordChars.length];
    	int widthNeeded = 0;
    	
    	for (int i = 0; i < wordChars.length; i++){
    		chosenFonts[i] = fonts[random.nextInt(fonts.length)];

    		char[] charToDraw = { wordChars[i] };

    		GlyphVector gv = chosenFonts[i].createGlyphVector(frc, charToDraw);
    		charWidths[i] = (int)gv.getVisualBounds().getWidth();
    		if (i > 0){
    			widthNeeded += 2;
    		}
    		widthNeeded += charWidths[i];
    	}

    	int startPosX = (width - widthNeeded) / 2;
    	for (int i = 0; i < wordChars.length; i++){
    		g2D.setColor(colors[random.nextInt(colors.length)]);
    		g2D.setFont(chosenFonts[i]);
    		char[] charToDraw = { wordChars[i] };

    		g2D.drawChars(charToDraw, 0, charToDraw.length, startPosX, startPosY);
    		startPosX = startPosX + charWidths[i] + charSpace;
    	}

    	return image;
	}
}