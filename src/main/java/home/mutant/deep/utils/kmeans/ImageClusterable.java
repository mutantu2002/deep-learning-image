package home.mutant.deep.utils.kmeans;

import home.mutant.deep.ui.Image;

public class ImageClusterable extends SimpleClusterable{

	public ImageClusterable(Image image) {
		super(image.getDataOneDimensional().length);
		for (int i = 0; i < weights.length; i++) {
			double pixel = image.getDataOneDimensional()[i];
			if(pixel<0)pixel+=256;
			weights[i]=pixel;
		}
	}
	public ImageClusterable(int size) {
		super(size);
	}
	
	public Image getImage(){
		byte[] pixels = new byte[weights.length];
		for (int i = 0; i < pixels.length; i++) {
			pixels[i]=(byte) weights[i];
		}
		return new Image(pixels);
	}
	@Override
	public Clusterable randomize() {
		ImageClusterable m = new ImageClusterable(weights.length);
		m.randomizeWeights();
		return m;
	}
	@Override
	public Clusterable copy() {
		ImageClusterable m = new ImageClusterable(weights.length);
		System.arraycopy(weights, 0, m.getWeights(), 0, weights.length);
		return m;
	}
}
