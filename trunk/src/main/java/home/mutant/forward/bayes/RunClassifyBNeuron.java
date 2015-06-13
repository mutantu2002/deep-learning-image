/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mutant.forward.bayes;

import home.mutant.deep.utils.MnistDatabase;

/**
 *
 * @author cipi
 */
public class RunClassifyBNeuron {
	public static void main(String[] args) throws Exception 
	{
		MnistDatabase.loadImages();
//		ClassifyBNeuron bn = new ClassifyBNeuron(MnistDatabase.trainImages, MnistDatabase.trainLabels);
//		System.out.println(bn.getRate(MnistDatabase.testImages, MnistDatabase.testLabels));
		ClassifyBNeuron bn = new ClassifyBNeuron(MnistDatabase.trainImages.subList(0, 600), MnistDatabase.trainLabels.subList(0, 600));
		System.out.println(bn.getRate(MnistDatabase.testImages.subList(0, 100), MnistDatabase.testLabels.subList(0, 100)));
	}
}
