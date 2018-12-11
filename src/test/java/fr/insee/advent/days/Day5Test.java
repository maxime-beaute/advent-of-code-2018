package fr.insee.advent.days;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import fr.insee.advent.utils.InputUtils;

public class Day5Test {

	@Test
	public void ex1() throws Exception {
		Day5 day = new Day5();
		List<Character> polymer = InputUtils.lireInputTest("Day5-test1").collect(Collectors.toList()).get(0).chars().mapToObj(c -> (char) c).collect(Collectors.toList());

		List<Character> ex1 = day.getChaineEx1(polymer);
		List<Character> ex1carre = day.getChaineEx1(ex1);
		Assert.assertEquals(new Integer(10), day.ex1(InputUtils.lireInputTest("Day5-test1")));
		Assert.assertEquals(ex1, ex1carre);
	}

}
