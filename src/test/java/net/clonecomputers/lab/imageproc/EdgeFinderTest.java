package net.clonecomputers.lab.imageproc;

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
		
		/*{{0, 0, 0, 0, 0, 0, 0, 0, 0},
													  {0, hypot(255/9, 255/9), hypot(4*255/9, 2*255/9), hypot(8*255/9, 2*255/9), 10*255/9, hypot(8*255/9, 2*255/9), hypot(4*255/9, 2*255/9), hypot(4*255/9, 2*255/9), hypot(255/9, 255/9), 0},
													  {0, hypot(2*255/9, 4*255/9), }};*/

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

}
