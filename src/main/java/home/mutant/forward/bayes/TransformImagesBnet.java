/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mutant.forward.bayes;

import home.mutant.deep.ui.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cipi
 */
public class TransformImagesBnet {
	List<Image> images;
	BNet net;
	List<Thread> threads = new ArrayList<Thread>();
	
	public static final int NO_THREADS=6;
	
	public TransformImagesBnet(List<Image> images, BNet net) {
		this.images = images;
		this.net = net;
	}

	public void transform(double scale) {
		int step = images.size() / NO_THREADS;
		for (int i = 0; i < NO_THREADS; i++) {
			threads.add(new Thread(new TransformImagesRunnable(images.subList(i*step, (i+1)*step), net,scale)));
			threads.get(i).start();
		}
		for (int i = 0; i < NO_THREADS; i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException ex) {
				Logger.getLogger(TransformImagesBnet.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
