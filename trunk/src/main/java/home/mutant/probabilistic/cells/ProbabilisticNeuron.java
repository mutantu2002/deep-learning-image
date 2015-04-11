package home.mutant.probabilistic.cells;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProbabilisticNeuron implements Serializable
{
	private static final long serialVersionUID = 1L;
	public int X;
	public int Y;
	public List<ProbabilisticNeuron> links = new ArrayList<ProbabilisticNeuron>();
	public List<ProbabilisticNeuron> nonLinks = new ArrayList<ProbabilisticNeuron>();
	public Map<ProbabilisticNeuron, Integer> correlations = new HashMap<ProbabilisticNeuron,Integer>();
	List<Integer> occurences = new ArrayList<Integer>();
	public boolean isFiring=false;
	int totalSamples = 0;
	public double output;
	
	public ProbabilisticNeuron(int x, int y) 
	{
		super();
		X = x;
		Y = y;
	}

	public void addLink(ProbabilisticNeuron neuron)
	{
		Integer correlation = correlations.get(neuron);
		if (correlation == null)
		{
			correlation = new Integer(0);
		}
		correlation++;
		correlations.put(neuron, correlation);
		//System.out.println(correlation);
	}
	public void addNonLink(ProbabilisticNeuron neuron)
	{
		Integer correlation = correlations.get(neuron);
		if (correlation == null)
		{
			correlation = new Integer(0);
		}
		correlation--;
		correlations.put(neuron, correlation);
	}

	private double getRadius(ProbabilisticNeuron neuron) 
	{
		double radius  = Math.sqrt((X-neuron.X)*(X-neuron.X)+(Y-neuron.Y)*(Y-neuron.Y));
		return radius;
	}
	
	public void applyCorrelationsToLinks()
	{
		for(ProbabilisticNeuron neuron:correlations.keySet())
		{
			neuron.output+=correlations.get(neuron)*0.0001;
			if (neuron.output<0)neuron.output=0;
			if (neuron.output>255) neuron.output=255;
		}
	}
	
	public void applyCorrelationsToLink()
	{
		int indexStop = (int) (correlations.keySet().size()*Math.random());
		int index=0;
		for(ProbabilisticNeuron neuron:correlations.keySet())
		{
			if (index==indexStop)
			{
				neuron.output+=correlations.get(neuron)*100;
				if (neuron.output<0)neuron.output=0;
				if (neuron.output>255) neuron.output=255;
				break;
			}
		}
	}
	
	public ProbabilisticNeuron pickLink()
	{
		int size = links.size();
		if (size>100000)
		{
			links.remove(Math.random()*size);
			size--;
		}
		if (size==0) return null;
		int index = (int) (Math.random()*1*size);
		if (index>=size) return null;
		ProbabilisticNeuron ret =  links.get(index);
//		if (ret.output>0)
//			links.remove(ret);
		return ret;
	}
	
	public ProbabilisticNeuron pickNonLink()
	{
		int size = nonLinks.size();
		if (size>200000)
		{
			nonLinks.remove(Math.random()*size);
			size--;
		}
		if (size==0) return null;
		int index = (int) (Math.random()*1*size);
		if (index>=size) return null;
		ProbabilisticNeuron ret =  nonLinks.get(index);
//		if (ret.output>0)
//			links.remove(ret);
		return ret;
	}
	
}
