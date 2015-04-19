/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mutant.forward.bayes.multi;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cipi
 */
public class MBColumn {
	public List<MBNeuron> neurons = new ArrayList<MBNeuron>();
	
	public MBColumn(int size, int neuronSize, int inputSize) {
		for (int i=0;i<size;i++) {
			MBNeuron bn = new MBNeuron(neuronSize, inputSize);
			for (int j=0;j<1;j++)
			{
				bn.initRandom();
			}
			neurons.add(bn);
		}
	}

	void addPositive(byte[] pixels) {
		for(MBNeuron neuron:neurons){
			neuron.addPositiveByIndex(pixels);
		}
	}

	void addNegative(byte[] pixels) {
		for(MBNeuron neuron:neurons){
			neuron.addNegativeByIndex(pixels);
		}
	}

	double output(byte[] pixels) {
		double output=-1*Double.MAX_VALUE;
		for(MBNeuron neuron:neurons){
			final double output1 = neuron.output(pixels);
			if (output<output1)output=output1;
		}
		return output;
	}
	
}
