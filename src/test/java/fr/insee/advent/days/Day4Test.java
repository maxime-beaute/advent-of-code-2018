package fr.insee.advent.days;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import fr.insee.advent.utils.InputUtils;

public class Day4Test {

	@Test
	public void ex1() throws Exception {
		Day4 day = new Day4();
		Assert.assertEquals(new Long(240), day.ex1(InputUtils.lireInputTest("Day4-test1")));
	}

	@Test
	public void ex2() throws Exception {
		Day4 day = new Day4();
		Assert.assertEquals(new Long(4455), day.ex2(InputUtils.lireInputTest("Day4-test1")));
	}

}
