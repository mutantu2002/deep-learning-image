package home.mutant.forward;

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
	
	public void addPositive(byte[] input)
	{
		for (int i=0;i<input.length;i++)
		{
			if (input[i]!=0)
			{
				correlations.get(i).positive++;
			}
		}
		sumPositive++;
	}
	
	public void addNegative(byte[] input)
	{
		for (int i=0;i<input.length;i++)
		{
			if (input[i]!=0)
			{
				correlations.get(i).negative++;
			}
		}
		sumNegative++;
	}
	
}
