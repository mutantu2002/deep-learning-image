package home.mutant.forward;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FNeuron {
	public int x;
	public int y;
	public Map<FNeuron, Correlation> correlations = new LinkedHashMap<FNeuron, Correlation>();
	public List<FNeuron> inputCorrelations = new ArrayList<FNeuron>();
	int sumNegative=0;
	int sumPositive=0;
	
	public boolean isFiring = false;
	public double output;
	
	public void lightCorrelations()
	{
		for (FNeuron nextNeuron:correlations.keySet())
		{
			nextNeuron.isFiring=true;
		}
	}

	public void lightInputCorrelations()
	{
		for (FNeuron nextNeuron:inputCorrelations)
		{
			double sumAll = (double)nextNeuron.sumPositive+(double)nextNeuron.sumNegative;
			double sp = nextNeuron.sumPositive==0? 0: nextNeuron.correlations.get(this).positive/sumAll;
			double np = nextNeuron.sumNegative==0? 0: nextNeuron.correlations.get(this).negative/sumAll;
			System.out.println(sp-np);
			nextNeuron.output =127+500000* (sp-np);
			if (nextNeuron.output<0)nextNeuron.output=0;
			if (nextNeuron.output>255)nextNeuron.output=255;
		}
	}
	
	public void makeCorrelations() {
		for(FNeuron nextNeuron:correlations.keySet())
		{
			if(isFiring)
			{
				if (nextNeuron.isFiring)
				{
					addCorrelation(nextNeuron,true);
				}
				else
				{
					addCorrelation(nextNeuron,false);
				}
			}
			else
			{
				if (nextNeuron.isFiring)
				{
					addCorrelation(nextNeuron,false);
				}
				else
				{
					addCorrelation(nextNeuron,true);
				}
			}
		}
	}

	private void addCorrelation(FNeuron nextNeuron, boolean isPositive) {
		if (isPositive)
		{
			correlations.get(nextNeuron).positive++;
			sumPositive++;
		}
		else
		{
			correlations.get(nextNeuron).negative++;
			sumNegative++;
		}
	}
	
	public void applyCorrelations(double scale)
	{
		for (FNeuron nextNeuron:correlations.keySet())
		{
			double p = correlations.get(nextNeuron).positive;
			double n = correlations.get(nextNeuron).negative;
			double all = (double)(sumPositive+sumNegative);
			p=all==0?0:p/all;
			n=all==0?0:n/all;
			nextNeuron.output+=(p-n)*scale;
		}
	}
	public FNeuron pickLink() {
		if(correlations.size()==0) return null;
		if (sumPositive==0) return null;
		int r = (int) (Math.random()*sumPositive);
		int sum=0;
		for (FNeuron nextNeuron:correlations.keySet())
		{
			int correlation = correlations.get(nextNeuron).positive;
			if (correlation==0) continue;
			if (sum<=r && r<sum+correlation)
			{
				return nextNeuron;
			}
			else
			{
				sum+=correlation;
			}
		}
		return null;
	}
	
	public FNeuron pickNonLink() {
		if(correlations.size()==0) return null;
		if (sumNegative==0)return null;
		int r = (int) (Math.random()*sumNegative);
		int sum=0;
		for (FNeuron nextNeuron:correlations.keySet())
		{
			int correlation = correlations.get(nextNeuron).negative;
			if (correlation==0) continue;
			if (sum<=r && r<sum+correlation)
			{
				return nextNeuron;
			}
			else
			{
				sum+=correlation;
			}
		}
		return null;
	}

	public void randomizeCorrelations() {
		for (FNeuron nextNeuron:correlations.keySet())
		{
			addCorrelation(nextNeuron, true);
			addCorrelation(nextNeuron, false);
			addCorrelation(nextNeuron, Math.random()>0.5);
		}
	}
}
