/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mutant.forward.bayes.gaussian;
import home.mutant.deep.ui.ResultFrame;
import home.mutant.deep.utils.MnistDatabase;

/**
 *
 * @author cipi
 */
public class MultiListClassifyGBNeuron {
	public static void main(String[] args) throws Exception{
		MnistDatabase.loadImagesCrop(10);
		GBNeuron n= new GBNeuron(100);
		n.trainImage(MnistDatabase.trainImages.get((int) (Math.random()*60000)));
		n.calculateMeanDeviation();
		for (int i = 1; i < 1000; i++) {
			int countSimilar = (int) n.countSimilar(MnistDatabase.trainImages.get(i).getDataOneDimensional());
			System.out.println(countSimilar);
			if (countSimilar>160){
				n.trainImage(MnistDatabase.trainImages.get(i),countSimilar);
				n.calculateMeanDeviation();
				System.out.println(MnistDatabase.trainLabels.get(i));
			}
		}
		ResultFrame frame = new ResultFrame(600, 200);
		frame.showImage(n.generateImage());
	}
	public static void main2(String[] args) throws Exception{
		MnistDatabase.loadImagesCrop(10);
		GBNeuron n= new GBNeuron(100);
		n.trainImage(MnistDatabase.trainImages.get((int) (Math.random()*60000)));
		n.calculateMeanDeviation();
		for (int i = 1; i < 10000; i++) {
			if (n.isSimilar(MnistDatabase.trainImages.get(i).getDataOneDimensional()))
			{
				n.trainImage(MnistDatabase.trainImages.get(i));
				n.calculateMeanDeviation();
				System.out.println(MnistDatabase.trainLabels.get(i));
			}
		}
		ResultFrame frame = new ResultFrame(600, 200);
		frame.showImage(n.generateImage());
	}
}
