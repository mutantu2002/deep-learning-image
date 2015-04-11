package home.mutant.forward;

import home.mutant.deep.ui.ResultFrame;
import home.mutant.deep.utils.ImageUtils;
import home.mutant.deep.utils.MnistDatabase;

import java.util.Arrays;

public class ShowCorrelations {

	public static final int NO_IMAGES = 200;
	public static void main(String[] args) throws Exception 
	{
		ResultFrame frame = new ResultFrame(100, 600);
		MnistDatabase.loadGradientImages();
		FNet net = new FNet(Arrays.asList(28, 84, 50, 40));
		net.layers[0].roundFillNeighbours(net.layers[1], 20, 1000);
		net.layers[1].roundFillNeighbours(net.layers[2], 10, 300);
		net.layers[2].uniformFillNeighbours(net.layers[3], 300);
		net.layers[0].randomizeCorrelations();
		net.layers[1].randomizeCorrelations();
		
		for (int i=0;i<NO_IMAGES;i++)
		{
			net.clear();
			net.layers[0].setInput(MnistDatabase.trainImages.get(i));
			net.layers[1].activateAll();
			net.layers[0].makeCorrelations();
			//net.layers[1].setInput(ImageUtils.scaleImage(MnistDatabase.trainImages.get(0), 3));
			//net.makeCorrelations();
			//net.draw(frame);
//			System.in.read();
//			System.in.read();
		}
		
		while(true)
		{
			net.clear();
//			net.layers[0].neurons[(int) (Math.random()*28)][(int) (Math.random()*28)].lightCorrelations();
//			net.layers[1].neurons[(int) (Math.random()*84)][(int) (Math.random()*84)].lightCorrelations();
//			net.layers[2].neurons[(int) (Math.random()*50)][(int) (Math.random()*50)].lightCorrelations();
			
			net.layers[1].neurons[(int) (Math.random()*84)][(int) (Math.random()*84)].lightInputCorrelations();
			//net.layers[2].neurons[(int) (Math.random()*50)][(int) (Math.random()*50)].lightInputCorrelations();
			//net.layers[3].neurons[(int) (Math.random()*40)][(int) (Math.random()*40)].lightInputCorrelations();
			
			net.drawGray(frame);
			System.in.read();
			System.in.read();
		}

	}


}
