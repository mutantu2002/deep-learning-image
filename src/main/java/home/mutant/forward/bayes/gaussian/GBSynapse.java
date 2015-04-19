/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mutant.forward.bayes.gaussian;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cipi
 */
public class GBSynapse {
	public Map<Integer,Integer> statistics = new HashMap<Integer, Integer>();
	public double mean = 0;
	public double deviation = 0;
	public void addTarget(byte pixel){
		int ipixel = pixel;
		if(ipixel<0)ipixel+=255;
		Integer n = statistics.get(ipixel);
		if (n==null) n=0;
		n++;
		statistics.put(ipixel, n);
	}
	public void calculateMeanDeviation(){
		mean = 0;
		int count = 0;
		for (Map.Entry<Integer, Integer> entrySet : statistics.entrySet()) {
			Integer key = entrySet.getKey();
			Integer value = entrySet.getValue();
			mean+=key*value;
			count+=value;
		}
		mean /= count;
		deviation=0;
		for (Map.Entry<Integer, Integer> entrySet : statistics.entrySet()) {
			Integer key = entrySet.getKey();
			Integer value = entrySet.getValue();
			deviation+=(mean-key*value)*(mean-key*value);
		}
		deviation=Math.sqrt(deviation/count);
	}
	public double getPosterior(byte pixel){
		int ipixel = pixel;
		if(ipixel<0)ipixel+=255;
		if(deviation==0){
			if(mean==ipixel)return 0;
			else return -1;
		}
		else{
			return Math.log(1./2.*Math.PI/deviation)-(ipixel-mean)*(ipixel-mean)/2/deviation/deviation;
		}
	}
}
