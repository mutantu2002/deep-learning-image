/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.mutant.forward.bayes.multi;

import home.mutant.forward.Correlation;
import home.mutant.forward.bayes.BNeuron;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author cipi
 */
public class MBNeuron extends BNeuron{
	public Set<Integer> indexes = new LinkedHashSet<Integer>();
	public MBNeuron(int size, int inputSize) {
		super(size);
		while(indexes.size()<size){
			indexes.add((int)(Math.random()*inputSize));
		}
	}
	
	public MBNeuron(int size, Set<Integer> indexes) {
		super(size);
		this.indexes = indexes;
	}
	
	public void addPositiveByIndex(byte[] pixels){
		int i=0;
		for (Integer index : indexes) {
			if(pixels[index]!=0)correlations.get(i).positive++;
			i++;
		}
		sumPositive++;
	}
	public void addNegativeByIndex(byte[] pixels){
		int i=0;
		for (Integer index : indexes) {
			if(pixels[index]!=0)correlations.get(i).negative++;
			i++;
		}
		sumNegative++;
	}
	
	@Override
	public double output(byte[] input)
	{
		double pPlus=0;
		double pMinus=0;
		int i=0;
		for (Integer index : indexes) {
			Correlation corr = correlations.get(i++);
			double pp = corr.positive;
			double pn = corr.negative;
			if (input[index]==0)
			{
				pp=sumPositive-pp;
				pn=sumNegative-pn;
			}
			pPlus+=Math.log(pp/sumPositive);
			pMinus+=Math.log(pn/sumNegative);
		}
		double probPlus = pPlus+Math.log(sumPositive/(sumPositive+sumNegative));
		double probMinus = pMinus+Math.log(sumNegative/(sumPositive+sumNegative));
//		probPlus = Math.exp(probPlus+100);
//		probMinus = Math.exp(probMinus+100);
		return probPlus-probMinus;///(probPlus+probMinus);
	}
}
