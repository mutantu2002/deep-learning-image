package home.mutant.forward;

import java.util.Arrays;

import home.mutant.deep.ui.ResultFrame;
import home.mutant.deep.utils.MnistDatabase;

public class RunFNet {

	public static final int NO_IMAGES = 800;
	public static void main(String[] args) throws Exception 
	{
		ResultFrame frame = new ResultFrame(100, 600);
		MnistDatabase.loadGradientImages();
		FNet net = new FNet(Arrays.asList(28, 84, 50, 40));
		net.layers[0].roundFillNeighbours(net.layers[1], 20, 300);
		net.layers[1].roundFillNeighbours(net.layers[2], 10, 200);
		net.layers[2].uniformFillNeighbours(net.layers[3], 300);
		net.layers[0].randomizeCorrelations();
		net.layers[1].randomizeCorrelations();
		
		for (int j=0;j<1;j++)
		{
			for (int i=0;i<NO_IMAGES;i++)
			{
				net.clear();
				net.layers[0].setInput(MnistDatabase.trainImages.get(i));
				net.step();
				net.layers[3].setInput(net.generateFinalImage(40, MnistDatabase.trainLabels.get(i)));
				net.makeCorrelations();
				net.draw(frame);
	//			System.in.read();
	//			System.in.read();
			}
		}
		
//		for (int i=0;i<20;i++)
//		{
//			net.layers[0].setInput(MnistDatabase.trainImages.get(i));
//			net.layers[1].setInput(net.generateRandomImage(56, 0.5));
//			net.layers[0].makeCorrelations();
//		}
//		
//		for (int i=0;i<20;i++)
//		{
//			net.clear();
//			net.layers[0].setInput(MnistDatabase.trainImages.get(i));
//			net.step();
//			net.layers[2].setInput(net.generateFinalImage(40, MnistDatabase.trainLabels.get(i)));
//			net.layers[1].makeCorrelations();
//		}
		
		while(true)
		{
			net.clear();
			net.layers[0].setInput(MnistDatabase.trainImages.get((int) (Math.random()*NO_IMAGES)));
			net.step();
			net.draw(frame);
			System.in.read();
			System.in.read();
		}

	}


}
