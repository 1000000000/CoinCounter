package net.clonecomputers.lab.imageproc;

import java.util.Set;

public interface Accumulator<T> {

	public void accumulate();
	public Set<T> threshold(double thresh);
	
}
