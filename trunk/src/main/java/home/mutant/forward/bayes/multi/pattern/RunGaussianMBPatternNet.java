package home.mutant.forward.bayes.multi.pattern;

import home.mutant.deep.ui.Image;
import home.mutant.deep.ui.ResultFrame;
import home.mutant.deep.utils.MnistDatabase;
import home.mutant.forward.bayes.gaussian.GBNeuron;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RunGaussianMBPatternNet {
	
	public static void main7(String[] args) throws IOException {
		MnistDatabase.loadImages();
		MBPatternNet net  = train();
		test(net);
	}

	public static void main2(String[] args) throws IOException {
		MnistDatabase.loadImages();
		MBPatternNet subBest = new MBPatternNet(0, Globals.SUBIMG_SIZE, MnistDatabase.trainImages.get(0).getDataOneDimensional().length,Globals.NO_TRAIN);
		MBPatternNet theNet = new MBPatternNet(0, Globals.SUBIMG_SIZE, MnistDatabase.trainImages.get(0).getDataOneDimensional().length,Globals.NO_TRAIN);
		for (int j=0;j<5;j++){
			double max=0;
			for (int i=0;i<50;i++)
			{
				MBPatternNet net  = train();
				double p = test(net);
				if (p>max)
				{
					subBest = net;
					max=p;
				}
				System.out.println();
				
			}
			System.out.println(max);
			theNet.columns.addAll(subBest.columns);
		}
		System.out.println("*************");
		test(theNet);
		
	}
	
	public static void main5(String[] args) throws IOException {
		MnistDatabase.loadImages();
		MBPatternNet theNet = new MBPatternNet(0, Globals.SUBIMG_SIZE, MnistDatabase.trainImages.get(0).getDataOneDimensional().length,Globals.NO_TRAIN);
		MBPatternNet net  = train();
		test(net);
		int size = net.columns.size();
		for (int j=0;j<50;j++){
			theNet.columns.clear();
			
			while (theNet.columns.size()<size*0.5)
			{
				theNet.columns.add(net.columns.get((int) (Math.random()*size)));
			}
			test(theNet);
		}
	}

	public static void main(String[] args) throws IOException {
		MnistDatabase.loadImages();
		MBPatternNet net  = train();
		List<MBPatternNeuron> allNeurons = new ArrayList<MBPatternNeuron>();
		for (MBPatternColumn column:net.columns)
		{
			allNeurons.addAll(column.neurons);
		}
		System.out.println(allNeurons.size());
		
		//test(allNeurons);
		Collections.sort(allNeurons, Collections.reverseOrder());
		List<Image> imgs = new ArrayList<Image>();
		for(int i=0;i<1000;i++){
			imgs.add(allNeurons.get(i).generateImage());
		}
		ResultFrame frame = new ResultFrame(600, 600);
		frame.showImages(imgs);
		test(allNeurons.subList(0, 2000));
		test(allNeurons.subList(0, 4000));
		test(allNeurons.subList(0, 6000));
		test(allNeurons.subList(0, 8000));
	}
	
	public static void main6(String[] args) throws IOException {
		MnistDatabase.loadImages();
		List<MBPatternNeuron> theNeurons = new ArrayList<MBPatternNeuron>();
		MBPatternNet net  = train();
		List<MBPatternNeuron> allNeurons = new ArrayList<MBPatternNeuron>();
		for (MBPatternColumn column:net.columns)
		{
			allNeurons.addAll(column.neurons);
		}
		test(allNeurons);
		int size = allNeurons.size();
		for (int j=0;j<50;j++){
			theNeurons.clear();
			
			while (theNeurons.size()<size*0.2)
			{
				theNeurons.add(allNeurons.get((int) (Math.random()*size)));
			}
			test(theNeurons);
		}
	}
	
	public static void main4(String[] args) throws IOException {
		MnistDatabase.loadImages();
		MBPatternNet theNet = new MBPatternNet(0, Globals.SUBIMG_SIZE, MnistDatabase.trainImages.get(0).getDataOneDimensional().length,Globals.NO_TRAIN);
		MBPatternNet net  = train();
		test(net);
		Collections.sort(net.columns, Collections.reverseOrder());
		int size = (int) (net.columns.size()*0.05);
		for (int j=0;j<50;j++){
			theNet.columns.clear();
			size+=1;
			for(int s=0;s<size;s++)
			{
				theNet.columns.add(net.columns.get(s));
			}
			test(theNet);
		}
	}
	private static MBPatternNet train() {
		MBPatternNet net = new MBPatternNet(Globals.NO_COLUMNS, Globals.SUBIMG_SIZE, MnistDatabase.trainImages.get(0).getDataOneDimensional().length,Globals.NO_TRAIN);
		net.trainImages();
		return net;
	}

	private static double test(MBPatternNet net) {
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
		//System.out.println(initCount+" - "+ allNeurons.size());
		int[] input = new int[allNeurons.size()];
		List<GBNeuron> b10 = new ArrayList<GBNeuron>();
		for (int i=0;i<10;i++){
			b10.add(new GBNeuron(allNeurons.size()));
		}
		//MBPatternNeuron.similarityRate = 0.75;
		for (int i=0;i<Globals.NO_TRAIN;i++)
		{
			byte[] pixels = MnistDatabase.trainImages.get(i).getDataOneDimensional();
			for (int j = 0; j < allNeurons.size(); j++) {
				int countSimilar = (int) allNeurons.get(j).countSimilar(pixels);
				input[j] = countSimilar>=Globals.MAX_PIXEL_VALUE*Globals.SIMILARITY?countSimilar:0;
			}
			int classIndex = MnistDatabase.trainLabels.get(i);
			b10.get(classIndex).trainInput(input);
		}

		for (int i=0;i<10;i++){
			b10.get(i).calculateMeanDeviation();
		}
		ResultFrame frame = new ResultFrame(600, 600);
		List<Image> imgs = new ArrayList<Image>();
		for (int i=0;i<10;i++)
		{
			imgs.add(b10.get(i).generateImage());
		}
		frame.showImages(imgs);
		int count=0;
		for (int i=0;i<Globals.NO_TEST;i++)
		{
			byte[] pixels = MnistDatabase.testImages.get(i).getDataOneDimensional();
			for (int j = 0; j < allNeurons.size(); j++) {
				int countSimilar = (int) allNeurons.get(j).countSimilar(pixels);
				input[j] = countSimilar>=Globals.MAX_PIXEL_VALUE*Globals.SIMILARITY?countSimilar:0;
			}
			if (getMax(b10,input)==MnistDatabase.testLabels.get(i))
			{
				count++;
			}
		}
		double percent = (count*100.)/Globals.NO_TEST;
		double percentPerFeature = percent/allNeurons.size();
		System.out.println(percent +" - "+allNeurons.size()+" features, "+percentPerFeature+" percent per feature");
		return percentPerFeature;
	}
	
	private static double test(List<MBPatternNeuron> allNeurons) {
		int[] input = new int[allNeurons.size()];
		List<GBNeuron> b10 = new ArrayList<GBNeuron>();
		for (int i=0;i<10;i++){
			b10.add(new GBNeuron(allNeurons.size()));
		}
		//MBPatternNeuron.similarityRate = 0.75;
		for (int i=0;i<Globals.NO_TRAIN;i++)
		{
			byte[] pixels = MnistDatabase.trainImages.get(i).getDataOneDimensional();
			for (int j = 0; j < allNeurons.size(); j++) {
				int countSimilar = (int) allNeurons.get(j).countSimilar(pixels);
				input[j] = countSimilar>=Globals.MAX_PIXEL_VALUE*Globals.SIMILARITY?countSimilar:0;
			}
			int classIndex = MnistDatabase.trainLabels.get(i);
			b10.get(classIndex).trainInput(input);
		}

		for (int i=0;i<10;i++){
			b10.get(i).calculateMeanDeviation();
		}
		//ResultFrame frame = new ResultFrame(600, 600);
		List<Image> imgs = new ArrayList<Image>();
		for (int i=0;i<10;i++)
		{
			imgs.add(b10.get(i).generateImage());
		}
		//frame.showImages(imgs);
		int count=0;
		for (int i=0;i<Globals.NO_TEST;i++)
		{
			byte[] pixels = MnistDatabase.testImages.get(i).getDataOneDimensional();
			for (int j = 0; j < allNeurons.size(); j++) {
				int countSimilar = (int) allNeurons.get(j).countSimilar(pixels);
				input[j] = countSimilar>=Globals.MAX_PIXEL_VALUE*Globals.SIMILARITY?countSimilar:0;
			}
			if (getMax(b10,input)==MnistDatabase.testLabels.get(i))
			{
				count++;
			}
		}
		double percent = (count*100.)/Globals.NO_TEST;
		double percentPerFeature = percent/allNeurons.size();
		System.out.println(percent +" - "+allNeurons.size()+" features, "+percentPerFeature+" percent per feature");
		return percentPerFeature;
	}
	
	public static int getMax(List<GBNeuron> neurons, int[] input)
	{
		double max = -1*Double.MAX_VALUE;
		int indexMax=-1;
		for (int b = 0; b < 10; b++)
		{
			final double output = neurons.get(b).getPosterior(input);
			if (output>max){
				max=output;
				indexMax=b;
			}
		}
		return indexMax;
	}

}
