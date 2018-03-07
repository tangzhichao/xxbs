package com.xxbs.v1.util.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.Configurable;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.ShadowFilter;
import com.jhlabs.image.TransformFilter;

public class ValidateCodeGimpy extends Configurable implements GimpyEngine{
	
	
	/*
	 * 验证码样式控制
	 */
	public BufferedImage getDistortedImage(BufferedImage baseImage){
		NoiseProducer noiseProducer = getConfig().getNoiseImpl();
		BufferedImage distortedImage = new BufferedImage(baseImage.getWidth(),
				baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D graph = (Graphics2D) distortedImage.getGraphics();

		ShadowFilter shadowFilter = new ShadowFilter();
		shadowFilter.setRadius(5);
		shadowFilter.setDistance(6);
		shadowFilter.setOpacity(0.5f);

		Random rand = new Random();

		RippleFilter rippleFilter = new RippleFilter();
		
		rippleFilter.setWaveType(RippleFilter.SINE);
		//振幅
		//rippleFilter.setXAmplitude(8.6f);
		//rippleFilter.setYAmplitude(rand.nextFloat() + 8.0f);
		rippleFilter.setXAmplitude(3.6f);
		rippleFilter.setYAmplitude(rand.nextFloat() + 3.0f);
		//波
		rippleFilter.setXWavelength(rand.nextInt(7) + 15);
		rippleFilter.setYWavelength(rand.nextInt(3) + 7);
		
		rippleFilter.setEdgeAction(TransformFilter.RGB_CLAMP);

		BufferedImage effectImage = rippleFilter.filter(baseImage, null);
		effectImage = shadowFilter.filter(effectImage, null);

		graph.drawImage(effectImage, -10, -5, null, null);
		graph.dispose();

		//添加两条线
		noiseProducer.makeNoise(distortedImage, .25f, .8f, .3f, .7f);
		//noiseProducer.makeNoise(distortedImage, .1f, .25f, .5f, .9f);
		
		return distortedImage;
	}
}
