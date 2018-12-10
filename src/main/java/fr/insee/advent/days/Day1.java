package fr.insee.advent.days;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.insee.advent.utils.Reponse;

public class Day1 {

	public static void main(String[] args) {
		Reponse.repondre(Day1.class);
	}

	public Integer ex1(Stream<String> input) {
		return input
			.mapToInt(Integer::parseInt)
			.sum();
	}

	public Integer ex2(Stream<String> input) {
		Set<Integer> nombresDejaPasses = new HashSet<>();
		nombresDejaPasses.add(0);
		List<Integer> nombres = input.map(Integer::parseInt).collect(Collectors.toList());
		Integer somme = 0;
		int i = 0;
		while (true) {
			somme += nombres.get(i++ % nombres.size());
			if (nombresDejaPasses.contains(somme)) {
				return somme;
			}
			else {
				nombresDejaPasses.add(somme);
			}
		}
	}
}