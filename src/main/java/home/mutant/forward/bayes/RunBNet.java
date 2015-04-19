/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mutant.forward.bayes;

import home.mutant.deep.ui.ResultFrame;
import home.mutant.deep.utils.MnistDatabase;
import home.mutant.forward.bayes.gaussian.ClassifyGBNeuron;

/**
 *
 * @author cipi
 */
public class RunBNet {
	public static final int NO_NEURONS = 400;
	public static final int NO_TRAIN = 6000;
	public static final int NO_TEST = 1200;
	public static final double SCALE =0.1;
	public static void main(String[] args) throws Exception 
	{
		MnistDatabase.loadImages();
		BNet net = new BNet(28, 7, 4, NO_NEURONS);
		net.trainImages(MnistDatabase.trainImages);
		System.out.println("OK training");
		new TransformImagesBnet(MnistDatabase.trainImages.subList(0, NO_TRAIN), net).transform(SCALE);
		System.out.println("OK train images");
		new TransformImagesBnet(MnistDatabase.testImages.subList(0, NO_TEST), net).transform(SCALE);
		System.out.println("OK test images");
		ClassifyGBNeuron bn = new ClassifyGBNeuron(MnistDatabase.trainImages.subList(0, NO_TRAIN), MnistDatabase.trainLabels.subList(0, NO_TRAIN));
		System.out.println(bn.getRate(MnistDatabase.testImages.subList(0, NO_TEST), MnistDatabase.testLabels.subList(0, NO_TEST)));
		ResultFrame frame = new ResultFrame(1200, 950);
		frame.showImages(MnistDatabase.trainImages.subList(1000, 1100));
	}

}
