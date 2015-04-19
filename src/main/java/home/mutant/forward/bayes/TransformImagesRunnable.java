/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mutant.forward.bayes;

import home.mutant.deep.ui.Image;
import home.mutant.deep.utils.ImageUtils;
import java.util.List;

/**
 *
 * @author cipi
 */
public class TransformImagesRunnable implements Runnable{
	List<Image> images;
	BNet net;
	double scale = -1;
	public TransformImagesRunnable(List<Image> images, BNet net, double scale) {
		this.images = images;
		this.net = net;
		this.scale = scale;
	}
	
	@Override
	public void run() {
		for (int i=0;i<images.size();i++) {
			final Image generateImage = net.generateImage(images.get(i));
			if (scale<0)
				images.set(i, generateImage);
			else
				images.set(i, ImageUtils.scaleImage(generateImage, scale));
		}
	}
	
}
