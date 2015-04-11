package home.mutant.probabilistic.mains.runnables;

import home.mutant.probabilistic.cells.ProbabilisticNeuron;
import home.mutant.probabilistic.mains.RunProbabilisticNet;
import home.mutant.probabilistic.nets.ProbabilisticNet;

public class ProbabilisticNetRunnable implements Runnable 
{
	ProbabilisticNet net;
	public boolean alive=true;
	public ProbabilisticNetRunnable(ProbabilisticNet net) 
	{
		super();
		this.net = net;
	}
	
	public void run() 
	{
		while(alive)
		{
			for(Integer key:net.neurons.keySet())
			{
				ProbabilisticNeuron probabilisticNeuron = net.neurons.get(key);
				probabilisticNeuron.output-=1;
				if (probabilisticNeuron.output<0)probabilisticNeuron.output=0;
				if (probabilisticNeuron.output>200)probabilisticNeuron.isFiring=true;
			}
			for(Integer key:net.neurons.keySet())
			{
				ProbabilisticNeuron probabilisticNeuron = net.neurons.get(key);
				if (probabilisticNeuron.isFiring){
					probabilisticNeuron.isFiring=false;
					probabilisticNeuron.output-=100;
					for (int i=0;i<1;i++)
					{
						ProbabilisticNeuron neuronPicked = probabilisticNeuron.pickLink();
						if (neuronPicked != null)
						{
							neuronPicked.output+=150;if (neuronPicked.output>255)neuronPicked.output=255;
							
						}
					}
					ProbabilisticNeuron neuronPicked = probabilisticNeuron.pickNonLink();
					if (neuronPicked != null)
					{
						neuronPicked.output-=150;if (neuronPicked.output<0)neuronPicked.output=0;
					}
				}
			}			
		}
	}

	
	public void run4() 
	{
		ProbabilisticNeuron neuron = null;
		ProbabilisticNeuron nextNeuron = null;
		int count=0;
		while(alive)
		{
//			try {
//				if (count++%50==0)
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			int indexNeuron = (int) (Math.random()*net.X*net.Y);
			neuron = net.neurons.get(indexNeuron);

			if (neuron==null || neuron.output==0) 
			{
				neuron = null;
				continue;
			}
			
//			int indexNextNeuron = (int) (Math.random()*net.X*net.Y);
//			nextNeuron = net.neurons.get(indexNextNeuron);
//			
//			if (nextNeuron!=null && nextNeuron.output>0) 
//			{
//				//neuron.links.add(nextNeuron);
//				//nextNeuron.links.add(neuron);
//			}
			
			neuron.output-=10;
			if (neuron.output<0)neuron.output=0;
//			if (indexNeuron>RunProbabilisticNet.IMAGE_SIZE*RunProbabilisticNet.IMAGE_SIZE)
//				continue;
			if (neuron.output<200)
				continue;
			neuron.output=0;
			neuron.applyCorrelationsToLinks();
		}
	}
	public void run5() 
	{
		ProbabilisticNeuron neuron = null;
		ProbabilisticNeuron nextNeuron = null;
		int count=0;
		while(alive)
		{
//			try {
//				if (count++%50==0)
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			int indexNeuron = (int) (Math.random()*net.X*net.Y);
			neuron = net.neurons.get(indexNeuron);

			if (neuron==null || neuron.output==0) 
			{
				neuron = null;
				continue;
			}
			
//			int indexNextNeuron = (int) (Math.random()*net.X*net.Y);
//			nextNeuron = net.neurons.get(indexNextNeuron);
//			
//			if (nextNeuron!=null && nextNeuron.output>0) 
//			{
//				//neuron.links.add(nextNeuron);
//				//nextNeuron.links.add(neuron);
//			}
			
			//neuron.output-=16;
			if (neuron.output<0)neuron.output=0;
//			if (indexNeuron>RunProbabilisticNet.IMAGE_SIZE*RunProbabilisticNet.IMAGE_SIZE)
//				continue;
			if (neuron.output<200)
				continue;
			//neuron.output=0;
			ProbabilisticNeuron neuronPicked = neuron.pickLink();
			if (neuronPicked != null)
			{
				
				neuronPicked.output+=41;if (neuronPicked.output>255)neuronPicked.output=255;
				//System.out.println(neuron.output);
				
			}
			neuronPicked = neuron.pickNonLink();
			if (neuronPicked != null)
			{
				//System.out.println("PICKED" + neuron);
				neuronPicked.output-=41;if (neuronPicked.output<0)neuronPicked.output=0;
			}
			
		}
	}

	public void run2() 
	{
		ProbabilisticNeuron neuron = null;
		ProbabilisticNeuron lastNeuron = null;
		while(true)
		{
			if (neuron==null)
			{
				int indexNeuron = (int) (Math.random()*net.X*net.Y);
				neuron = net.neurons.get(indexNeuron);
			}

			if (neuron==null || neuron.output==0) 
			{
				neuron = null;
				continue;
			}
			
			//if(neuron.output<0)neuron.output = 0;
			//System.out.println(neuron.output);
			if (neuron.output>0)
			{
				if (lastNeuron!=null)
				{
					
					if (neuron.Y>=RunProbabilisticNet.IMAGE_SIZE)
						lastNeuron.links.add(neuron);
					else
					{
						if(lastNeuron.Y<RunProbabilisticNet.IMAGE_SIZE)
						{
							int x= neuron.X-lastNeuron.X;
							int y= neuron.Y-lastNeuron.Y;
							double radius = Math.sqrt(x*x+y*y);
							if (radius<10)//RunProbabilisticNet.IMAGE_SIZE)
								lastNeuron.links.add(neuron);
						}
					}
					

				}
				
				lastNeuron = neuron;
			}
			neuron.output-=90;
			if (neuron.output<0)neuron.output=0;
			neuron = neuron.pickLink();
			if (neuron == null) continue;
			//System.out.println("PICKED" + neuron);
			neuron.output+=60;
			if (neuron.output>255)
				neuron.output=255;
			neuron=null;
		}
	}

	
}
