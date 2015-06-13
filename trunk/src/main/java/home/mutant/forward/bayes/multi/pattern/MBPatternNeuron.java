package home.mutant.forward.bayes.multi.pattern;

import java.util.Set;

import home.mutant.deep.ui.Image;
import home.mutant.deep.utils.MnistDatabase;
import home.mutant.forward.bayes.multi.MBNeuron;

public class MBPatternNeuron extends MBNeuron implements Comparable<MBPatternNeuron>{
	public int imagePatternIndex = 0;
	byte[] pattern;
	
	public MBPatternNeuron(int size, int imagePatternIndex , Set<Integer> indexes) {
		super(size, indexes);
		this.imagePatternIndex = imagePatternIndex;
		pattern = new byte[size];
		int i=0;
		for (Integer index : indexes) { 
			pattern[i++] = MnistDatabase.trainImages.get(imagePatternIndex).getDataOneDimensional()[index];
		}
		//initRandom();
		addPositiveByIndex(MnistDatabase.trainImages.get(imagePatternIndex).getDataOneDimensional());
	}
	public Image generateImage(){
		byte[] img = new byte[indexes.size()];
		int count=0;
		int i=0;
		for (Integer index : indexes) {
			img[count++]=pattern[i++];
		}
		return new Image(img);
	}
	public boolean isSimilar(byte[] input){
		double count = countSimilar(input);
		//System.out.println(count);
		if (count/Globals.MAX_PIXEL_VALUE>=Globals.SIMILARITY) return true;
		return false;
	}
//	public double countSimilar(byte[] input) {
//		double count=0;
//		for (Integer index : indexes) {
//			if (input[index]==0 && pattern[index]==0){count++;continue;}
//			if (input[index]!=0 && pattern[index]!=0){count++;continue;}
//		}
//		return count;
//	}
	public double countSimilar(byte[] input) {
		double count=0;
		int i=0;
		for (Integer index : indexes) {
			int inputI=input[index];
			if (inputI<0)inputI+=255;
			int patternI=pattern[i++];
			if (patternI<0)patternI+=255;
			int diff =Math.abs(inputI-patternI);
			count+=Globals.MAX_PIXEL_VALUE-diff;
		}
		return count/indexes.size();
	}
//	public double countSimilar(byte[] input) {
//		double count=0;
//		int i=0;
//		for (Integer index : indexes) {
//			int inputI=input[index];
//			if (inputI<0)inputI+=255;
//			int patternI=pattern[i++];
//			if (patternI<0)patternI+=255;
//			int diff =(inputI-patternI)*(inputI-patternI);
//			count+=diff;
//		}
//		return Globals.MAX_PIXEL_VALUE - count/Globals.MAX_PIXEL_VALUE/indexes.size();
//	}
//	public double countSimilar(byte[] input) {
//		double countI=0;
//		double countP=0;
//		for (Integer index : indexes) {
//			int inputI=input[index];
//			if (inputI<0)inputI+=255;
//			int patternI=pattern[index];
//			if (patternI<0)patternI+=255;
//			countI+=inputI;
//			countP+=patternI;
//		}
//		return indexes.size()-Math.abs(countI-countP)/MLRunGaussianMBPatternNet.MAX_PIXEL_VALUE;
//	}
	public boolean isSimilar(int indexImage){
		return isSimilar(MnistDatabase.trainImages.get(indexImage).getDataOneDimensional());
	}
	public boolean trainImage(int indexImage) {
		Image image = MnistDatabase.trainImages.get(indexImage);
		boolean similar = isSimilar(image.getDataOneDimensional());
		if (similar){
//			int i=0;
//			for (Integer index : indexes) {
//				int inputI=image.getDataOneDimensional()[index];
//				if (inputI<0)inputI+=256;
//				int patternI=pattern[i];
//				if (patternI<0)patternI+=256;
//				pattern[i]=(byte) ((100000.*patternI+inputI)/100001.);
//				i++;
//			}
			addPositiveByIndex(image.getDataOneDimensional());
		}
		else addNegativeByIndex(image.getDataOneDimensional());
		return similar;
	}
	@Override
	public int compareTo(MBPatternNeuron o) {
		// TODO Auto-generated method stub
		return (int) (sumPositive-o.sumPositive);
	}
}
