package net.clonecomputers.lab.imageproc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class CoinCounter {
	
	static Logger logger = Logger.getLogger(CoinCounter.class.getName());
	public static final Level LEVEL = Level.INFO;
	
	public static final double EDGE_THRESHOLD = 200;
	public static final int CIRCLE_THRESHOLD = 92;

	public static void main(String[] args) {
		Handler handler = new ConsoleHandler();
		handler.setLevel(LEVEL);
		logger.addHandler(handler);
		logger.setLevel(LEVEL);
		if(args.length < 1) {
			System.err.println("Usage: CoinCounter <path to image>");
			System.exit(1);
		}
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(args[0]));
		} catch(IOException e) {
			System.err.println("Error: " + e.getLocalizedMessage());
			System.err.println("Unable to load image from " + new File(args[0]).getAbsolutePath());
			System.exit(-1);
		}
		EdgeFinder finder = new EdgeFinder();
		System.out.println("Preparing Image...");
		boolean[][] edges = finder.processImage(img, EDGE_THRESHOLD);
		Accumulator<int[]> acc = new CircleAccumulator(edges, 10, 100);
		System.out.println("Finding Circles...");
		acc.accumulate();
		Set<int[]> circles = acc.threshold(CIRCLE_THRESHOLD);
		System.out.println("Drawing " + circles.size() + " Circles...");
		Graphics g = img.getGraphics();
		for(int[] c : circles) {
			g.setColor(Color.CYAN);
			g.drawOval(c[1] - c[0], c[2] - c[0], 2*c[0], 2*c[0]); // c[0] = radius, c[1] = cx, c[2] = cy
		}
		g.dispose();
		System.out.println("Saving New Image...");
		try {
			ImageIO.write(img, "png", new File("circles.png"));
		} catch(IOException e) {
			System.err.println("Error: " + e.getLocalizedMessage());
			System.err.println("Unable to save image as circles.png");
		}
		System.out.println("Done!");
	}

}

/*
 *	Prints ASCII image of edges
 *
 *	StringBuilder bitmap = new StringBuilder();
 *	for(boolean[] bar : edges) {
 *		for(boolean b : bar) {
 *			bitmap.append(b ? "*" : " ");
 *		}
 *		bitmap.append("\n");
 *	}
 *	bitmap.deleteCharAt(bitmap.length() - 1);
 *	System.out.println("---Start---");
 *	System.out.println(bitmap);
 *	System.out.println("----End----");
 */
