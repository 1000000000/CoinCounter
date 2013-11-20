package net.clonecomputers.lab.imageproc;

public interface Accumulator<T> {

	public void accumulate();
	public T threshold(double thresh);
	
}
