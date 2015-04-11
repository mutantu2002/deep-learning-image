package home.mutant.probabilistic.nets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import home.mutant.deep.ui.Image;
import home.mutant.deep.utils.ImageUtils;
import home.mutant.deep.utils.MnistDatabase;
import home.mutant.probabilistic.cells.ProbabilisticNeuron;
import home.mutant.probabilistic.mains.RunProbabilisticNet;


public class ProbabilisticNet implements Serializable
{
	public int X;
	public int Y;
	public Map<Integer, ProbabilisticNeuron> neurons = new HashMap<Integer, ProbabilisticNeuron>();
	public ProbabilisticNeuron lastActivated = null;
	public transient Image image;
	public ProbabilisticNet(int x, int y) 
	{
		super();
		X = x;
		Y = y;
		
	}
	public Image generateImage()
	{
		image = new Image(X, Y);
		for (int x=0;x<X;x++)
		{
			for (int y=0;y<Y;y++)
			{
				ProbabilisticNeuron neuron = neurons.get(y*X+x);
				if (neuron!=null && neuron.output>200)
				{
					image.setPixel(x, y, (byte) 255);
				}
			}
		}
		return image;
	}
	public Image generateSample()
	{
		int count=0;
		ProbabilisticNeuron neuron=null;
		for(Integer key:neurons.keySet())
		{
			neurons.get(key).output=0;
		}
//		while (neuron==null)
//		{
//			neuron = neurons.get((int)(Math.random()*X*X));
//			if (neuron!=null && neuron.links.size()==0) neuron=null;
//		}
		byte[] pixels = ImageUtils.scaleImage(MnistDatabase.trainImages.get(((int)(Math.random()*30))),RunProbabilisticNet.IMAGE_SIZE/28.).getDataOneDimensional();
		for (int i=0;i<X*X;i++)
		{
			neuron = neurons.get(i);
			if (neuron!=null)
			{
				double pixel = pixels[i];
				if (pixel<0)pixel+=255;
				if(pixel>150)//Math.random()*255)
				{
					neuron.output=255;
				}
			}
		}
		while(count++<1)
		{
//			ProbabilisticNeuron neuronPicked = neuron.pickLink();
//			if (neuronPicked!=null) 
//			{
//				neuronPicked.output+=41;if (neuronPicked.output>255)neuronPicked.output=255;
//				//neuron=neuronPicked;
//				//count++;
//			}
//			neuronPicked = neuron.pickNonLink();
//			if (neuronPicked!=null) 
//			{
//				neuronPicked.output-=91;if (neuronPicked.output<0)neuronPicked.output=0;
//				//neuron=neuronPicked;
//				//count++;
//			}
			
			
			for(Integer key:neurons.keySet())
			{
				ProbabilisticNeuron probabilisticNeuron = neurons.get(key);
				probabilisticNeuron.output-=1;
				if (probabilisticNeuron.output<0)probabilisticNeuron.output=0;
				if (probabilisticNeuron.output>200)probabilisticNeuron.isFiring=true;
			}
			for(Integer key:neurons.keySet())
			{
				ProbabilisticNeuron probabilisticNeuron = neurons.get(key);
				if (probabilisticNeuron.isFiring){
					probabilisticNeuron.isFiring=false;
					//probabilisticNeuron.output=0;
				
					for (int i=0;i<200;i++)
					{
						ProbabilisticNeuron neuronPicked = probabilisticNeuron.pickLink();
						if (neuronPicked != null)
						{
							neuronPicked.output+=10;if (neuronPicked.output>255)neuronPicked.output=255;
							
						}
					}
					for (int i=0;i<200;i++)
					{
						ProbabilisticNeuron neuronPicked = probabilisticNeuron.pickNonLink();
						if (neuronPicked != null)
						{
							neuronPicked.output-=10;if (neuronPicked.output<0)neuronPicked.output=0;
						}
					}
				}
			}

		}
		return generateImage();
	}
	public void saveNet()
	{
		ObjectOutputStream oout=null;;
		
        try
        {
        	oout = new ObjectOutputStream( new FileOutputStream("1.net"));
        	oout.writeObject(this);
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}
        finally
        {
        	try 
        	{
				if (oout!=null)oout.close();
			} 
        	catch (IOException e) 
        	{
				e.printStackTrace();
			}
        }
	}
}
