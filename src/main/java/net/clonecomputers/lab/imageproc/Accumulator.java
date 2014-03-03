package net.clonecomputers.lab.imageproc;

import java.util.Set;

public interface Accumulator<T> {

	public void accumulate();
	public Set<T> threshold(int thresh) throws IllegalStateException;
	
}
