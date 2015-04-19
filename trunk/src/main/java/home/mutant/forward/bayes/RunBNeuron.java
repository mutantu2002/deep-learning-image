/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mutant.forward.bayes;

import home.mutant.deep.ui.Image;
import home.mutant.deep.ui.ResultFrame;
import home.mutant.deep.utils.ImageUtils;
import home.mutant.deep.utils.MnistDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cipi
 */
public class RunBNeuron {
	public static final int NO_NEURONS = 49;
	public static void main(String[] args) throws Exception 
	{
		
		MnistDatabase.loadImages();
		BColumn net = new BColumn(NO_NEURONS, 7*7);
		int subImageIndex=9;
		for (int i=0;i<6000;i++) {
			List<byte[]> pixels = MnistDatabase.trainImages.get(i).divideImage(7, 4);
			net.train(pixels.get(subImageIndex));
		}
		ResultFrame frame = new ResultFrame(300, 300);
		frame.showImages(net.generateNeuronsImages(), (int) Math.sqrt(NO_NEURONS));
		
		ResultFrame frame2 = new ResultFrame(1200, 950);
		List<Image> images = new ArrayList<Image>();
		for (int i=0;i<600;i++) {
			final Image generateImage = net.generateImage(MnistDatabase.trainImages.get(i).divideImage(7, 4).get(subImageIndex));
			images.add(generateImage);
		}
		frame2.showImages(images);
	}

}
