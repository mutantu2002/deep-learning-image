/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mutant.forward.bayes;

import home.mutant.deep.ui.Image;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cipi
 */
public class BNet {
	public int size;
	public int subSize;
	public int step;
	public int noNeuronsPerColumn;
	public List<BColumn> columns = new ArrayList<BColumn>();

	public BNet(int size, int subSize, int step, int noNeuronsPerColumn) {
		this.size = size;
		this.subSize = subSize;
		this.step = step;
		this.noNeuronsPerColumn = noNeuronsPerColumn;
		int noColumns = (size-subSize)/step+1;
		noColumns=noColumns*noColumns;
		for (int i = 0; i < noColumns; i++) {
			columns.add(new BColumn(noNeuronsPerColumn, subSize*subSize));
		}
	}
	public void trainImage(Image image){
		List<byte[]> pixels = image.divideImage(subSize, step);
		for (int i = 0; i < pixels.size(); i++) {
			columns.get(i).train(pixels.get(i));
		}
	}
	public void trainImages(List<Image> images){
		for (Image image : images) {
			trainImage(image);
		}
	}
	public Image generateImage(Image inputImage){
		int noColumnsX = (size-subSize)/step+1;
		int sqr = (int) Math.sqrt(noNeuronsPerColumn);
		Image image = new Image(sqr*noColumnsX,sqr*noColumnsX);
		List<byte[]> pixels = inputImage.divideImage(subSize, step);
		
		for (int i = 0; i < pixels.size(); i++) {
			Image subImage = columns.get(i).generateImage(pixels.get(i));
			image.pasteImage(subImage, (i%noColumnsX)*sqr, (i/noColumnsX)*sqr);
		}
		return image;
	}
}
