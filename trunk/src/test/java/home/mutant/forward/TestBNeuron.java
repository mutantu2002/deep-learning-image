package home.mutant.forward;

import home.mutant.forward.bayes.BNeuron;
import home.mutant.deep.ui.ResultFrame;
import home.mutant.deep.utils.MnistDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Ignore;

import org.junit.Test;

public class TestBNeuron {
	@Test
	@Ignore
	public void testBNeuron() throws Exception
	{
		BNeuron bn = new BNeuron(28*28);
		byte[] input = new byte[28*28];
		bn.calculatePosteriors(input);
		for (int i=0;i<1;i++)
		{
			fillRandom(input);
			bn.addNegative(input);
			fillRandom(input);
			bn.addPositive(input);
		}
		
		bn.calculatePosteriors(input);
		MnistDatabase.loadImages();
		byte[] lPixels = MnistDatabase.trainImages.get(0).getDataOneDimensional();
		for (int i=0;i<20;i++)
		{
			bn.addPositive(lPixels);
		}
		Arrays.fill(input, (byte)1);
		bn.calculatePosteriors(input);
		ResultFrame rf = new ResultFrame(100, 100);
		rf.showImage(bn.generateImage());
		System.out.println("");
	}
	
	@Test
	public void testBNeuronImage() throws Exception
	{
		List<BNeuron> lB = new ArrayList<BNeuron>();
		byte[] input = new byte[28*28];
		for (int i=0;i<100;i++) {
			BNeuron bn = new BNeuron(28*28);
			for (int j=0;j<1;j++)
			{
				fillRandom(input);
				bn.addNegative(input);
				fillRandom(input);
				bn.addPositive(input);
			}
			lB.add(bn);
		}
		MnistDatabase.loadImages();
		byte[] lPixels = MnistDatabase.trainImages.get(0).getDataOneDimensional();
		int count=0;
		for (BNeuron bn : lB) {
			final boolean firing = bn.isFiring(lPixels);
			System.out.println(firing);
			if(firing)count++;
		}
		System.out.println(count);
	}
	public void fillRandom(byte[] input)
	{
		for (int i = 0; i < input.length; i++) {
			input[i]=Math.random()>0.5?(byte)0:1;
		}
	}
}
