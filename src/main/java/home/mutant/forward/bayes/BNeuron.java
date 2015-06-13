package home.mutant.forward.bayes;

import home.mutant.deep.ui.Image;
import home.mutant.forward.Correlation;
import java.util.ArrayList;
import java.util.List;

public class BNeuron 
{
	public List<Correlation> correlations = new ArrayList<Correlation>();
	public double sumPositive;
	public double sumNegative;
	public BNeuron(int size)
	{
		for (int i=0;i<size;i++)
		{
			correlations.add(new Correlation(1,1));
		}
		sumPositive=2;
		sumNegative=2;
	}
	public void calculatePosteriors(byte[] input)
	{
		double pPlus=0;
		double pMinus=0;
		for (int i=0;i<input.length;i++)
		{
			Correlation corr = correlations.get(i);
			double pp = corr.positive;
			double pn = corr.negative;
			if (input[i]==0)
			{
				pp=sumPositive-pp;
				pn=sumNegative-pn;
			}
			pPlus+=Math.log(pp/sumPositive);
			pMinus+=Math.log(pn/sumNegative);
		}
		double probPlus = pPlus+Math.log(sumPositive/(sumPositive+sumNegative));
		double probMinus = pMinus+Math.log(sumNegative/(sumPositive+sumNegative));
		System.out.println(pPlus+ " : "+probPlus );
		System.out.println(pMinus+ " : "+probMinus );
		System.out.println();
	}
	public boolean isFiring(byte[] input)
	{
		double pPlus=0;
		double pMinus=0;
		for (int i=0;i<input.length;i++)
		{
			Correlation corr = correlations.get(i);
			double pp = corr.positive;
			double pn = corr.negative;
			if (input[i]==0)
			{
				pp=sumPositive-pp;
				pn=sumNegative-pn;
			}
			pPlus+=Math.log(pp/sumPositive);
			pMinus+=Math.log(pn/sumNegative);
		}
		double probPlus = pPlus+Math.log(sumPositive/(sumPositive+sumNegative));
		double probMinus = pMinus+Math.log(sumNegative/(sumPositive+sumNegative));
		return probPlus>=probMinus;
	}
	public double output(byte[] input)
	{
		double pPlus=0;
//		double pMinus=0;
		for (int i=0;i<input.length;i++)
		{
			Correlation corr = correlations.get(i);
			double pp = corr.positive;
//			double pn = corr.negative;
			if (input[i]==0)
			{
				pp=sumPositive-pp;
//				pn=sumNegative-pn;
			}
			pPlus+=Math.log(pp/sumPositive);
//			pMinus+=Math.log(pn/sumNegative);
		}
		double probPlus = pPlus;//+Math.log(sumPositive/(sumPositive+sumNegative));
//		double probMinus = pMinus+Math.log(sumNegative/(sumPositive+sumNegative));
//		probPlus = Math.exp(probPlus+100);
//		probMinus = Math.exp(probMinus+100);
		return probPlus;///(probPlus+probMinus);
	}
	public void addPositive(byte[] input, int weight)
	{
		for (int i=0;i<input.length;i++)
		{
			if (input[i]!=0)
			{
				correlations.get(i).positive+=weight;
			}
		}
		sumPositive+=weight;
	}
	public void addPositive(byte[] input)
	{
		addPositive(input, 1);
	}
	public void addNegative(byte[] input, int weight)
	{
		for (int i=0;i<input.length;i++)
		{
			if (input[i]!=0)
			{
				correlations.get(i).negative+=weight;
			}
		}
		sumNegative+=weight;
	}
	public void addNegative(byte[] input)
	{
		addNegative(input,1);
	}
	public Image generateImage(){
		byte[] pixels = new byte[correlations.size()];
		for (int i = 0; i < pixels.length; i++) {
			Correlation corr = correlations.get(i);
			double pp = corr.positive;
			double pn = corr.negative;
			if (pp/sumPositive>=pn/sumNegative && pp>1)
			{
				pixels[i]=(byte)255;
			}
			else
			{
				pixels[i]=(byte)0;
			}
		}
		return new Image(pixels);
    }
	public void initRandom() {
		byte[] input = new byte[correlations.size()];
		fillRandom(input);
		addNegative(input);
		fillRandom(input);
		addPositive(input);
	}
	public static void fillRandom(byte[] input)
	{
		for (int i = 0; i < input.length; i++) {
			input[i]=Math.random()>0.5?(byte)0:1;
		}
	}
}
