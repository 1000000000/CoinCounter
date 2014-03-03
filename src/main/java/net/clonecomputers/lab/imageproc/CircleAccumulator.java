package net.clonecomputers.lab.imageproc;

import java.awt.Point;

import java.util.HashSet;
import java.util.Set;

public class CircleAccumulator implements Accumulator<int[]> {

	private static final double SQRT2 = Math.sqrt(2);

	private final int minR;
	private final int maxR;
	private final boolean[][] edges;
	private final int[][][] acc;
	private boolean isDone = false;

	public CircleAccumulator(boolean[][] edgeImage, int minRadius, int maxRadius) {
		if(edgeImage.length < 1 || edgeImage[0].length < 1) throw new IllegalArgumentException("edgeImage must have positive dimensions");
		if(minRadius > maxRadius) throw new IllegalArgumentException("minRadius must be less than maxRadius");
		minR = minRadius;
		maxR = maxRadius;
		edges = edgeImage;
		acc = new int[maxR-minR][edges.length][edges[0].length];
	}

	@Override
	public void accumulate() {
		for(int r = 0; r < acc.length; ++r) {
			for(int x = 0; x < acc[r].length; ++x) {
				for(int y = 0; y < acc[r][x].length; ++y) {
					for(Point p : getCirclePoints(r + minR, x, y)) {
						if(p.x >= 0 && p.y >= 0 && p.x < acc[r].length && p.y < acc[r][x].length && edges[x][y]) {
							++acc[r][p.x][p.y];
						}
					}
				}
			}
		}
		isDone = true;
	}

	public int[][][] getAccumulator() throws IllegalStateException {
		if(!isDone) throw new IllegalStateException("accumulate() must be called before getAccumulator()");
		return acc;
	}

	static Set<Point> getCirclePoints(int r, int cx, int cy) {
		Set<Point> points = new HashSet<Point>();
		points.add(new Point(cx + r, cy));
		points.add(new Point(cx - r, cy));
		points.add(new Point(cx, cy + r));
		points.add(new Point(cx, cy - r));
		int y = r;
		for(int x = 1; x <= y; ++x) {
			y = (int) Math.round(Math.sqrt(Math.pow(r, 2) - Math.pow(x, 2)));
			points.add(new Point(cx + x, cy + y));
			points.add(new Point(cx + x, cy - y));
			points.add(new Point(cx - x, cy + y));
			points.add(new Point(cx - x, cy - y));
			points.add(new Point(cx + y, cy + x));
			points.add(new Point(cx + y, cy - x));
			points.add(new Point(cx - y, cy + x));
			points.add(new Point(cx - y, cy - x));
		}
		return points;
	}

	@Override
	public Set<int[]> threshold(int thresh) throws IllegalStateException {
		if(!isDone) throw new IllegalStateException("accumulate() must be called before threshold(int)");
		Set<int[]> circles = new HashSet<int[]>();
		for(int r = 0; r < acc.length; ++r) {
			for(int x = 0; x < acc[r].length; ++x) {
				for(int y = 0; y < acc[r][x].length; ++y) {
					if(acc[r][x][y] > thresh) circles.add(new int[]{r +minR, x, y});
				}
			}
		}
		return circles;
	}

}
