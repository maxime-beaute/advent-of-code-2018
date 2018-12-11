package fr.insee.advent.days;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import fr.insee.advent.utils.InputUtils;

public class Day3Test {

	@Test
	public void ex1() throws Exception {
		Day3 day = new Day3();
		Assert.assertEquals(new Long(4), day.ex1(InputUtils.lireInputTest("Day3-test1")));
	}

	@Test
	public void ex2() throws Exception {
		Day3 day = new Day3();
		Assert.assertEquals(new Long(3), day.ex2(InputUtils.lireInputTest("Day3-test1")));
	}
}
