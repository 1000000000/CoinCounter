package net.clonecomputers.lab.imageproc;

import static java.lang.Math.hypot;

import static org.junit.Assert.*;

import java.awt.Point;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class CircleAccumulatorTest {
	
	@Test
	public void testGetCirclePoints() {
		Set<Point> r7Points = CircleAccumulator.getCirclePoints(7, 0, 0);
		Set<Point> r8Points = CircleAccumulator.getCirclePoints(8, 0, 0);
		assertTrue("getCirclePoints with r = 7 misses (5,5): " + r7Points, r7Points.contains(new Point(5, 5)));
		assertFalse("getCirclePoints with r = 8 has (6,6): " + r8Points, r8Points.contains(new Point(6, 6)));
	}

	@Test(expected=IllegalStateException.class)
	public void testIllegalCallOfThreshold() {
		CircleAccumulator test = new CircleAccumulator(new boolean[][]{{true}}, 0, 1);
		test.threshold(1);
	}

	@Test
	public void testAccumulate() {
		CircleAccumulator test = new CircleAccumulator(new boolean[][]{	{false, false, true , true , true , false, false}, // Circle with
										{false, true , false, false, false, true , false}, // Radius 3 and
										{true , false, false, false, false, false, true }, // Center (3,3)
										{true , false, false, false, false, false, true },
										{true , false, false, false, false, false, true },
										{false, true , false, false, false, true , false},
										{false, false, true , true , true , false, false}}, 1, 6);
		test.accumulate();
		Set<int[]> temp = test.threshold(16);
		int[][] result = temp.toArray(new int[temp.size()][3]);
		System.out.println("Accumulator: " + Arrays.deepToString(test.getAccumulator()));
		assertEquals("Too many or too few results: " + Arrays.deepToString(result), 1, result.length);
		assertArrayEquals("Incorrect Result! Actual: " + Arrays.toString(result[0]) + ", Expected: [3, 3, 3]", new int[]{3, 3, 3}, result[0]);
	}

}
