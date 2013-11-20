package net.clonecomputers.lab.imageproc;

import static java.lang.Math.hypot;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

public class EdgeFinderTest {
	
	private static BufferedImage testImage;
	private static final double IC = 255D/9D;
	private static final double[][] testImageSmoothedGray = {{0, 0,    0,    0,    0,    0,    0, 0, 0},
		 													 {0, 0,    0,    0,    0,    0,    0, 0, 0},
															 {0, 0, 1*IC, 2*IC, 3*IC, 2*IC, 1*IC, 0, 0},
															 {0, 0, 2*IC, 4*IC, 6*IC, 4*IC, 2*IC, 0, 0},
															 {0, 0, 3*IC, 6*IC, 9*IC, 6*IC, 3*IC, 0, 0},
															 {0, 0, 2*IC, 4*IC, 6*IC, 4*IC, 2*IC, 0, 0},
															 {0, 0, 1*IC, 2*IC, 3*IC, 2*IC, 1*IC, 0, 0},
		 													 {0, 0,    0,    0,    0,    0,    0, 0, 0},
															 {0, 0,    0,    0,    0,    0,    0, 0, 0}};
		
	private static final double[][] testSmoothedGray = {{0, 0, 0,   0,   0,   0, 0, 0, 0},
														{0, 0, 0,   0,   0,   0, 0, 0, 0},
														{0, 0, 0,   0,   0,   0, 0, 0, 0},
														{0, 0, 0, 255, 255, 255, 0, 0, 0},
														{0, 0, 0, 255, 255, 255, 0, 0, 0},
														{0, 0, 0, 255, 255, 255, 0, 0, 0},
														{0, 0, 0,   0,   0,   0, 0, 0, 0},
														{0, 0, 0,   0,   0,   0, 0, 0, 0},
														{0, 0, 0,   0,   0,   0, 0, 0, 0}};
	
	private static final double[][] testSmoothedGrayEdges = {{0, 0, 0, 0, 0, 0, 0, 0, 0},
															 {0, 0, 0, 0, 0, 0, 0, 0, 0},
															 {0, 0, hypot(255,255), hypot(765,255), hypot(1020,0), hypot(765,255), hypot(255,255), 0, 0},
															 {0, 0, hypot(255,765), hypot(765,765), hypot(1020,0), hypot(765,765), hypot(255,765), 0, 0},
															 {0, 0, hypot(0,1020),  hypot(0,1020),   			0, hypot(0,1020),  hypot(0,1020),  0, 0},
															 {0, 0, hypot(255,765), hypot(765,765), hypot(1020,0), hypot(765,765), hypot(255,765), 0, 0},
															 {0, 0, hypot(255,255), hypot(765,255), hypot(1020,0), hypot(765,255), hypot(255,255), 0, 0},
															 {0, 0, 0, 0, 0, 0, 0, 0, 0},
															 {0, 0, 0, 0, 0, 0, 0, 0, 0}};

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testImage = new BufferedImage(9, 9, BufferedImage.TYPE_INT_RGB);
		for(int x = 3; x < 6; ++x) {
			for(int y = 3; y < 6; ++y) {
				testImage.setRGB(x, y, 0xFFFFFF);
			}
		}
	}

	@Test
	public void testSmooth() {
		EdgeFinder edges = new EdgeFinder();
		double[][] smoothedGray = edges.graySmooth(testImage);
		try {
			for(int i = 0; i < smoothedGray.length; ++i) {
				assertArrayEquals(testImageSmoothedGray[i], smoothedGray[i], 0.5);
			}
		} finally {
			System.out.println("Smoothed Gray Expected:");
			System.out.println(Arrays.deepToString(testImageSmoothedGray));
			System.out.println("Smoothed Gray Actual:");
			System.out.println(Arrays.deepToString(smoothedGray));
		}
	}
	
	@Test
	public void testEdges() {
		EdgeFinder edgeFinder = new EdgeFinder();
		double[][] edges = edgeFinder.findEdges(testSmoothedGray);
		try {
			for(int i = 0; i < edges.length; ++i) {
				assertArrayEquals(testSmoothedGrayEdges[i], edges[i], 0.5);
			}
		} finally {
			System.out.println("Edges Expected:");
			System.out.println(Arrays.deepToString(testSmoothedGrayEdges));
			System.out.println("Edges Actual:");
			System.out.println(Arrays.deepToString(edges));
		}
	}

}
