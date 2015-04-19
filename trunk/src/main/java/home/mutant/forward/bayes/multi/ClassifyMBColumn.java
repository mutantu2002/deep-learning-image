/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mutant.forward.bayes.multi;

import home.mutant.deep.ui.Image;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cipi
 */
public class ClassifyMBColumn {
	List<MBColumn> cNeurons = new ArrayList<MBColumn>();
	public ClassifyMBColumn(List<Image> images, List<Integer> labels){
		int size = images.get(0).getDataOneDimensional().length;
		for (int i = 0; i < 10; i++) {
			cNeurons.add(new MBColumn(10,600,size));
		}
		for (int i=0;i<images.size();i++) {
			int classIndex = labels.get(i);
			byte[] pixels = images.get(i).getDataOneDimensional();
			for (int b = 0; b < 10; b++) {
				if (b==classIndex)
				{
					cNeurons.get(b).addPositive(pixels);
				}
				else
				{
					cNeurons.get(b).addNegative(pixels);
				}
			}
		}
	}
	
	public double getRate(List<Image> images, List<Integer> labels){
		int count=0;
		for (int i=0;i<images.size();i++) {
			byte[] pixels = images.get(i).getDataOneDimensional();
			if(getMax(cNeurons, pixels)== labels.get(i)){
				count++;
			}
		}
		return (count*100.)/images.size();
	}
	public int getMax(List<MBColumn> neurons, byte[] pixels)
	{
		double max = -1*Double.MAX_VALUE;
		int indexMax=-1;
		for (int b = 0; b < 10; b++)
		{
			final double output = neurons.get(b).output(pixels);
			if (output>max){
				max=output;
				indexMax=b;
			}
		}
		return indexMax;
	}
}
