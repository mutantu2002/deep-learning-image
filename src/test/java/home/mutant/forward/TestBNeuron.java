package home.mutant.forward;

import java.util.Arrays;

import org.junit.Test;

public class TestBNeuron {
	@Test
	public void testBNeuron() throws Exception
	{
		BNeuron bn = new BNeuron(100);
		byte[] input = new byte[100];
		bn.calculatePosteriors(input);
		for (int i=0;i<100;i++)
		{
			fillRandom(input);
			bn.addNegative(input);
		}
		bn.calculatePosteriors(input);
		
		for (int i=0;i<20;i++)
		{
			fillRandom(input);
			bn.addPositive(input);
		}
		Arrays.fill(input, (byte)1);
		bn.calculatePosteriors(input);
	}
	
	@Test
	public void testBNeuronImage() throws Exception
	{
		
	}
	public void fillRandom(byte[] input)
	{
		for (int i = 0; i < input.length; i++) {
			input[i]=Math.random()>0.5?(byte)0:1;
		}
	}
}
