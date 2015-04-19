/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mutant.forward.bayes.gaussian;

import home.mutant.deep.ui.Image;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cipi
 */
public class GBNeuron {
	List<GBSynapse> synapses = new ArrayList<GBSynapse>();
	public GBNeuron(int size){
		for (int i = 0; i < size; i++) {
			synapses.add(new GBSynapse());
		}
	}
	public void trainImage(Image image){
		byte[] pixels = image.getDataOneDimensional();
		for (int i = 0; i < pixels.length; i++) {
			synapses.get(i).addTarget(pixels[i]);
		}
	}
	public void trainImages(List<Image> images){
		for (Image image : images) {
			trainImage(image);
		}
	}
	public double getPosterior(Image image){
		double posterior = 0;
		byte[] pixels = image.getDataOneDimensional();
		for (int i = 0; i < pixels.length; i++) {
			posterior+=synapses.get(i).getPosterior(pixels[i]);
		}
		return posterior;
	}
	public void calculateMeanDeviation(){
		for (GBSynapse synapse : synapses) {
			synapse.calculateMeanDeviation();
		}
	}
}
