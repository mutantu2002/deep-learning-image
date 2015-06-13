/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mutant.forward.bayes.gaussian;

import home.mutant.deep.ui.Image;
import home.mutant.forward.bayes.multi.pattern.Globals;

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
	public void trainImage(Image image, int weight){
		byte[] pixels = image.getDataOneDimensional();
		for (int i = 0; i < pixels.length; i++) {
			synapses.get(i).addTarget(pixels[i]);
		}
	}
	
	public void trainPixels(byte[] pixels){
		for (int i = 0; i < pixels.length; i++) {
			synapses.get(i).addTarget(pixels[i]);
		}
	}
	
	public void trainInput(int[] input){
		for (int i = 0; i < input.length; i++) {
			synapses.get(i).addTarget(input[i]);
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
	public double getPosterior(int[] input){
		double posterior = 0;
		for (int i = 0; i < input.length; i++) {
			posterior+=synapses.get(i).getPosterior(input[i]);
		}
		return posterior;
	}
	
	public void calculateMeanDeviation(){
		for (GBSynapse synapse : synapses) {
			synapse.calculateMeanDeviation();
		}
	}
	
	public double countSimilar(byte[] input) {
		double count=0;
		for (int i=0;i<synapses.size();i++) {
			int inputI=input[i];
			if (inputI<0)inputI+=255;
			int patternI=(int) synapses.get(i).mean;
			int diff =Math.abs(inputI-patternI);
			count+=Globals.MAX_PIXEL_VALUE-diff;
		}
		return count/synapses.size();
	}
	public boolean isSimilar(byte[] input){
		double count = countSimilar(input);
		//System.out.println(count);
		if (count/Globals.MAX_PIXEL_VALUE>=Globals.SIMILARITY) return true;
		return false;
	}
	public Image generateImage(){
		byte[] pixels=new byte[synapses.size()];
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = (byte) (synapses.get(i).mean);
		}
		return new Image(pixels);
	}
}
