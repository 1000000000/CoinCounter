package net.clonecomputers.lab.imageproc;

import java.awt.image.BufferedImage;

public class EdgeFinder {

	public synchronized double[][] processImage(BufferedImage image) {
		return findEdges(graySmooth(image));
	}
	
	double[][] graySmooth(BufferedImage image) {
		double[][] graySmoothed = new double[image.getWidth()][image.getHeight()];
		for(int x = 0; x < graySmoothed.length; ++x) {
			for(int y = 0; y < graySmoothed[x].length; ++y) {
				if(x < 1 || x >= graySmoothed.length - 1 || y < 1 || y >= graySmoothed[x].length - 1) {
					graySmoothed[x][y] = 0;
					continue;
				}
				graySmoothed[x][y] = smooth(x, y, image);
			}
		}
		return graySmoothed;
	}
	
	double[][] findEdges(double[][] smoothedGray) {
		double[][] edges = new double[smoothedGray.length][smoothedGray[0].length];
		for(int x = 0; x < edges.length; ++x) {
			for(int y = 0; y < edges[x].length; ++y) {
				if(x < 1 || x >= edges.length - 1 || y < 1 || y >= edges[x].length - 1) {
					edges[x][y] = 0;
					continue;
				}
				edges[x][y] = Math.hypot(edgeX(x, y, smoothedGray), edgeY(x, y, smoothedGray));
			}
		}
		return edges;
	}
	
	private double grayscale(int color) {
		int r = (color & 0xFF0000) >> 16;
		int g = (color & 0xFF00) >> 8;
		int b = (color & 0xFF);
		return (r + g + b)/3D;
	}
	
	private double smooth(int x, int y, BufferedImage image) {
		double newValue = 0;
		for(int i = x - 1; i <= x + 1; ++i) {
			for(int j = y - 1; j <= y + 1; ++j) {
				newValue += grayscale(image.getRGB(i, j));
			}
		}
		return newValue/9;
	}
	
	private double edgeX(int x, int y, double[][] smoothedGray) {
		return	1*smoothedGray[x-1][y-1] + 2*smoothedGray[x][y-1] + 1*smoothedGray[x+1][y-1] -
				1*smoothedGray[x-1][y+1] - 2*smoothedGray[x][y+1] - 1*smoothedGray[x+1][y+1];
	}
	
	private double edgeY(int x, int y, double[][] smoothedGray) {
		return	1*smoothedGray[x-1][y-1] - 1*smoothedGray[x+1][y-1] +
				2*smoothedGray[x-1][y]   - 2*smoothedGray[x+1][y]   +
				1*smoothedGray[x-1][y+1] - 1*smoothedGray[x+1][y+1];
	}
	
}
