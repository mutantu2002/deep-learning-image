package home.mutant.forward;

import home.mutant.deep.ui.Image;
import home.mutant.deep.ui.ResultFrame;

import java.util.List;

public class FNet {
	public FLayer[] layers;
	
	public FNet(List<Integer> sizes) {
		layers = new FLayer[sizes.size()];
		for(int i =0;i<sizes.size();i++)
		{
			layers[i] = new FLayer(i,sizes.get(i));
		}
	}

	public void draw(ResultFrame frame, int y) {
		int wX=0;
		for (int l=0;l<layers.length;l++)
		{
			frame.putImage(layers[l].getImage(),y, wX);
			wX+=layers[l].x + 2;
		}
		frame.repaint();
	}

	public void drawGray(ResultFrame frame, int y) {
		int wX=0;
		for (int l=0;l<layers.length;l++)
		{
			frame.putImage(layers[l].getGrayImage(),y, wX);
			wX+=layers[l].x + 2;
		}
		frame.repaint();
	}
	
	public  Image generateRandomImage(int size, double density) {
		Image img =new Image(size,size);
		byte[] pixels = img.getDataOneDimensional();
		for (int i=0;i<pixels.length;i+=1)
		{
			if(Math.random()<density) pixels[i]=(byte) 255;
		}
		return img;
	}
	
	public Image generateFinalImage(int size, int output)
	{
		Image finalImg = new Image(size,size);
		byte[] pixels = finalImg.getDataOneDimensional();
		
		int dim =size * size/10;
		for (int i=dim*output;i<dim*(output+1);i++)
		{
			pixels[i]=(byte) 255;
		}
		return finalImg;
	}
	public void draw(ResultFrame frame) {
		draw(frame,0);
	}
	
	public void drawGray(ResultFrame frame) {
		drawGray(frame,0);
	}
	
	public void step(){
		for (int l=0;l<layers.length;l++)
		{
			layers[l].step();
		}
	}

	public void makeCorrelations() {
		for (int l=0;l<layers.length-1;l++)
		{
			layers[l].makeCorrelations();
		}
	}

	public void clear() {
		for (int l=0;l<layers.length;l++)
		{
			layers[l].clear();
		}
		
	}
}
