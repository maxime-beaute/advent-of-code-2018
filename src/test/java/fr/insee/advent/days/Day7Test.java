package fr.insee.advent.days;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import fr.insee.advent.utils.InputUtils;

public class Day7Test {

	@Test
	public void ex1() throws Exception {
		Day7 day = new Day7();

		Assert.assertEquals("CABDFE", day.ex1(InputUtils.lireInputTest("Day7-test1")));
	}

}
