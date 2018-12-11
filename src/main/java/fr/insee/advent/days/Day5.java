package fr.insee.advent.days;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.insee.advent.utils.Reponse;

public class Day5 {

	public static final int TAILLE_TRANCHE = 1000;
	private final static String LETTRES = "azertyuiopqsdfghjklmwxcvbn";

	public static void main(String[] args) {
		Reponse.repondre(Day5.class);
	}

	public Integer ex1(Stream<String> input) {
		List<Character> polymer = input.collect(Collectors.toList()).get(0).chars().mapToObj(c -> (char) c).collect(Collectors.toList());

		List<Character> listPresqueFinale2 = getChaineEx1(polymer);

		System.out.println(listPresqueFinale2.stream().limit(100).map(Object::toString).collect(Collectors.joining()));
		return listPresqueFinale2.size();
	}

	public Integer ex2(Stream<String> input) {
		String chars = input.collect(Collectors.toList()).get(0);
		Map<Character, Integer> map = new HashMap<>();
		for (Character lettre : LETTRES.toCharArray()) {
			List<Character> polymer = chars.chars().mapToObj(c -> (char) c).filter(c -> !lettre.toString().equals(c.toString().toLowerCase())).collect(Collectors.toList());

			System.out.println(lettre + " - " + getChaineEx1(polymer).size());
			map.put(lettre, getChaineEx1(polymer).size());
		}
		Entry<Character, Integer> entry = map.entrySet().stream().min(Comparator.comparing(Entry::getValue)).get();
		System.out.println(entry.getKey() + " - " + entry.getValue());
		return entry.getValue();
	}

	public List<Character> getChaineEx1(List<Character> polymer) {
		List<Character> listPresqueFinale = collect(polymer, TAILLE_TRANCHE);
		List<Character> listPresqueFinale2 = collect(listPresqueFinale, TAILLE_TRANCHE + 1);
		int i = 2;
		while (listPresqueFinale.size() != listPresqueFinale2.size()) {
			listPresqueFinale = collect(listPresqueFinale2, TAILLE_TRANCHE + i);
			listPresqueFinale2 = collect(listPresqueFinale, TAILLE_TRANCHE + i + 1);
			i++;
		}
		return listPresqueFinale2;
	}

	private List<Character> collect(List<Character> polymer, int tailleTranche) {
		List<Character> listPresqueFinale = new ArrayList<>();
		for (int i = 0; i < polymer.size() / tailleTranche + 1; i++) {
			List<Character> part = polymer.stream().skip(i * tailleTranche).limit(tailleTranche).collect(Collectors.toList());
			ListIterator<Character> iterator = part.listIterator();
			reagit(iterator);
			listPresqueFinale.addAll(part);
		}
		System.out.println(listPresqueFinale.size());
		return listPresqueFinale;
	}

	private void reagit(ListIterator<Character> iterator) {
		if (iterator.hasNext()) {
			Character c = iterator.next();
			if (iterator.hasNext()) {
				Character next = iterator.next();
				if (isReagit(c, next)) {
					iterator.remove();
					iterator.previous();
					iterator.remove();
					if (iterator.hasPrevious()) {
						iterator.previous();

					}
					reagit(iterator);
				}
				else {
					iterator.previous();
					reagit(iterator);
				}
			}
		}
	}

	private boolean isReagit(Character c1, Character c2) {
		return !c1.equals(c2) && c1.toString().toUpperCase().equals(c2.toString().toUpperCase());
	}

}