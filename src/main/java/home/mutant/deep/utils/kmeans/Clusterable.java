package home.mutant.deep.utils.kmeans;

public interface Clusterable {

	public double d(Clusterable clusterable);
	public double[] getWeights();
	public Clusterable randomize();
	public Clusterable copy();
}
