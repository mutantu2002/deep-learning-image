/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mutant.forward.bayes;

import home.mutant.deep.ui.Image;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cipi
 */
public class BColumn {
	public int size;
	public List<BNeuron> neurons = new ArrayList<BNeuron>();
	public int sqr;
	
	public BColumn(int size, int neuronSize) {
		this.size = size;
		for (int i=0;i<size;i++) {
			BNeuron bn = new BNeuron(neuronSize);
			for (int j=0;j<1;j++)
			{
				bn.initRandom();
			}
			neurons.add(bn);
		}
		sqr = (int) Math.sqrt(size);
	}
	public List<Image> generateNeuronsImages()
	{
		List<Image> images = new ArrayList<Image>();
		for (int i=0;i<size;i++)
		{
			images.add(neurons.get(i).generateImage());
		}
		return images;
	}
	
	public void train(byte[] pixels){
		final int index = (int) (Math.random()*(size));
		BNeuron bn  = neurons.get(index);
		if (bn.isFiring(pixels))
		{
//			neurons.get((index-1+size)%size).addPositive(pixels,1);
//			neurons.get((index+1)%size).addPositive(pixels,1);
//			neurons.get((index-sqr+size)%size).addPositive(pixels,1);
//			neurons.get((index+sqr)%size).addPositive(pixels,1);
			bn.addPositive(pixels,40);

		}
		else
		{
//			neurons.get((index-1+size)%size).addNegative(pixels,1);
//			neurons.get((index+1)%size).addNegative(pixels,1);
//			neurons.get((index-sqr+size)%size).addNegative(pixels,1);
//			neurons.get((index+sqr)%size).addNegative(pixels,1);
			bn.addNegative(pixels,40);
		}
	}
	public Image generateImage(byte[] input)
	{
		byte[] data = new byte[size];
		for (int i = 0; i < data.length; i++) {
			if(neurons.get(i).isFiring(input)){
				data[i] = (byte)255;
			}else{
				data[i] = (byte)0;
			}
		}
		return new Image(data);
	}
}
