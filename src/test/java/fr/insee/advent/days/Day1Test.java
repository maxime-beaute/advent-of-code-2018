package fr.insee.advent.days;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import fr.insee.advent.utils.InputUtils;

public class Day1Test {

	@Test
	public void ex2() throws Exception {
		Day1 day = new Day1();
		Assert.assertEquals(new Integer(10), day.ex2(InputUtils.lireInputTest("Day1-ex2-test2")));
	}
}
