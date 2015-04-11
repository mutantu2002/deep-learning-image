package home.mutant.forward;

import home.mutant.deep.ui.Image;

public class FLayer {
	public int index;
	public int x;
	public FNeuron[][] neurons;
	Image img;
	double max=-100000;
	public FLayer(int index, int size){
		this.index = index;
		x=size;
		img = new Image(x,x);
		neurons = new FNeuron[x][x];
		for (int i=0;i<x;i++)
		{
			for (int j=0;j<x;j++)
			{
				neurons[i][j]=new FNeuron();
			}
		}
	}
	
	public void uniformFillNeighbours(FLayer nextLayer, int noNeighbours){
		for (int i=0;i<x;i++)
		{
			for(int j=0;j<x;j++)
			{
				FNeuron currentNeuron = neurons[i][j];
				int count=0;
				while(count<noNeighbours)
				{
					FNeuron nextNeuron = nextLayer.neurons[(int) (Math.random()*nextLayer.x)][(int) (Math.random()*nextLayer.x)];
					if (currentNeuron.correlations.get(nextNeuron)==null)
					{
						currentNeuron.correlations.put(nextNeuron, new Correlation());
						nextNeuron.inputCorrelations.add(currentNeuron);
						count++;
					}
				}
			}
		}
	}
	public void roundFillNeighbours(FLayer nextLayer, int radius, int noNeighbours){
		double step=((double)nextLayer.x)/x;
		for (int i=0;i<x;i++)
		{
			int xMiddle = (int)(i*step);
			for(int j=0;j<x;j++)
			{
				FNeuron currentNeuron = neurons[i][j];
				int yMiddle = (int)(j*step);
				int count=0;
				while(count<noNeighbours)
				{
					double alfa=2*Math.PI*Math.random();
					double r = radius*Math.sqrt(Math.random());
					int dx = xMiddle+(int) (r*Math.cos(alfa));
					int dy = yMiddle+(int) (r*Math.sin(alfa));
					if (dx>=0 && dx<nextLayer.x && dy>=0 && dy<nextLayer.x)
					{
						FNeuron nextNeuron = nextLayer.neurons[dx][dy];
						if (currentNeuron.correlations.get(nextNeuron)==null)
						{
							currentNeuron.correlations.put(nextNeuron, new Correlation());
							nextNeuron.inputCorrelations.add(currentNeuron);
							count++;
						}
					}
					else
					{
						count++;
					}
				}
			}
		}
	}
	
	public void makeCorrelations()
	{
		for (int i=0;i<x;i++)
		{
			for(int j=0;j<x;j++)
			{
				neurons[i][j].makeCorrelations();
			}
		}
	}
	public Image getImage()
	{
		for (int i=0;i<x;i++)
		{
			for(int j=0;j<x;j++)
			{
				FNeuron currentNeuron = neurons[i][j];
				if (currentNeuron.isFiring)
				{
					img.setPixel(i, j, (byte) 255);
				}
				else
				{
					img.setPixel(i, j, (byte) 0);
				}
			}
		}
		return img;
	}
	
	public Image getGrayImage()
	{
		for (int i=0;i<x;i++)
		{
			for(int j=0;j<x;j++)
			{
				FNeuron currentNeuron = neurons[i][j];
				img.setPixel(i, j, (byte) currentNeuron.output);

			}
		}
		return img;
	}
	
	public void activateAll()
	{
		for (int i=0;i<x;i++)
		{
			for(int j=0;j<x;j++)
			{
				neurons[j][i].output=255;
				neurons[j][i].isFiring = true;
			}
		}
	}
	public void setInput(Image image)
	{
		byte[] pixels = image.getDataOneDimensional();
		int indexPixels=0;
		for (int i=0;i<x;i++)
		{
			for(int j=0;j<x;j++)
			{
				double pixel = pixels[indexPixels++];
				if (pixel<0)pixel+=255;
				neurons[j][i].output=pixel;
				if (pixel>150)
					neurons[j][i].isFiring = true;
				else
					neurons[j][i].isFiring = false;
			}
		}
	}
	
	public void clear()
	{
		for (int i=0;i<x;i++)
		{
			for(int j=0;j<x;j++)
			{
				neurons[j][i].output=0;
				neurons[j][i].isFiring = false;
			}
		}
	}

	public void randomizeCorrelations()
	{
		for (int i=0;i<x;i++)
		{
			for(int j=0;j<x;j++)
			{
				neurons[i][j].randomizeCorrelations();
			}
		}
	}
	public void step() {
		for (int i=0;i<x;i++)
		{
			for(int j=0;j<x;j++)
			{
				if(neurons[i][j].output>max) 
				{
					System.out.println(index+":"+neurons[i][j].output);
					max = neurons[i][j].output;
				}
				neurons[i][j].isFiring=neurons[i][j].output>150;
			}
		}
		
		for (int i=0;i<x;i++)
		{
			for(int j=0;j<x;j++)
			{
				FNeuron fNeuron = neurons[i][j];
				if (fNeuron.isFiring)
				{
					fNeuron.applyCorrelations(6000);
//					for (int c=0;c<40;c++)
//					{
//						FNeuron next = fNeuron.pickLink();
//						
//						if (next!=null)
//						{
//							next.output+=300;
//						}
//						
//						next = fNeuron.pickNonLink();
//						if (next!=null)
//						{
//							next.output-=300;
//						}		
//					}
//					for(FNeuron next:fNeuron.correlations.keySet())
//					{
//						next.output += fNeuron.correlations.get(next).positive*500;
//						next.output -= fNeuron.correlations.get(next).negative*500;
//					}
				}
//				else
//				{
//					fNeuron.applyCorrelations(-800);
//				}
			}
		}
	}
}
