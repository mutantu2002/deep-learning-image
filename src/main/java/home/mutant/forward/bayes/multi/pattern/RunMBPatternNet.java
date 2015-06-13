package home.mutant.forward.bayes.multi.pattern;

import home.mutant.deep.ui.Image;
import home.mutant.deep.ui.ResultFrame;
import home.mutant.deep.utils.MnistDatabase;
import home.mutant.forward.bayes.BNeuron;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunMBPatternNet {

	public static final int NO_TRAIN = 10000;
	public static final int NO_TEST = 1000;
	public static final int SUBIMG_SIZE = 25;
	public static final double SIMILARITY = 0.85;
	public static void main(String[] args) throws IOException {
		MnistDatabase.loadImages();
		MBPatternNet net = new MBPatternNet(400, SUBIMG_SIZE, MnistDatabase.trainImages.get(0).getDataOneDimensional().length,NO_TRAIN);
		net.trainImages();
		
		List<MBPatternNeuron> allNeurons = new ArrayList<MBPatternNeuron>();
		int initCount=0;
		
		for(MBPatternColumn column:net.columns){
			for (MBPatternNeuron neuron:column.neurons){
				double dispersion = Math.abs(neuron.sumPositive-neuron.sumNegative);
				//System.out.println(dispersion);
				initCount++;
				//if (dispersion>5500 && dispersion<6000)
					allNeurons.add(neuron);
			}
		}
		System.out.println(initCount+" - "+ allNeurons.size());
		byte[] input = new byte[allNeurons.size()];
		List<BNeuron> b10 = new ArrayList<BNeuron>();
		for (int i=0;i<10;i++){
			b10.add(new BNeuron(allNeurons.size()));
		}
		//MBPatternNeuron.similarityRate = 0.75;
		for (int i=0;i<NO_TRAIN;i++)
		{
			byte[] pixels = MnistDatabase.trainImages.get(i).getDataOneDimensional();
			Arrays.fill(input, (byte)0);
			int indexColumns = 0;
			for (int j = 0; j < net.columns.size(); j++) {
				int indexMax = net.columns.get(j).getMaxSimilar(pixels);
				input[indexColumns+indexMax]=(byte)255;
				indexColumns+=net.columns.get(j).neurons.size();
			}
			int classIndex = MnistDatabase.trainLabels.get(i);
			for (int b = 0; b < 10; b++) {
				if (b==classIndex)
				{
					b10.get(b).addPositive(input);
				}
				else
				{
					b10.get(b).addNegative(input);
				}
			}
		}
		Image l1 = new Image(input);
		ResultFrame frame = new ResultFrame(1300, 900);
		List<Image> imgs = new ArrayList<Image>();
		for (int i=0;i<10;i++)
		{
			imgs.add(b10.get(i).generateImage());
		}
		int count=0;
		for(byte pixel:input){
			if (pixel!=0)count++;
		}
		frame.showImages(imgs);
		System.out.println(count);
		count=0;
		for (int i=0;i<NO_TEST;i++)
		{
			byte[] pixels = MnistDatabase.trainImages.get(i).getDataOneDimensional();
			for (int j = 0; j < input.length; j++) {
				if(allNeurons.get(j).isSimilar(pixels)) input[j]=(byte)255;
				else input[j]=(byte)0;
			}
			if (getMax(b10,input)==MnistDatabase.trainLabels.get(i))
			{
				count++;
			}
		}
		System.out.println((count*100.)/NO_TEST);
	}
	
	public static int getMax(List<BNeuron> neurons, byte[] pixels)
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
