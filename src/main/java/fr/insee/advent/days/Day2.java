package fr.insee.advent.days;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import fr.insee.advent.exception.AdventException;
import fr.insee.advent.utils.Reponse;

public class Day2 {

	public static void main(String[] args) {
		Reponse.repondre(Day2.class);
	}

	public long ex1(Stream<String> input) {
		return calculerChecksum(input
			.map(ligne -> new ComposantesChecksum(ligne))
			.collect(Collectors.toList()));
	}

	public String ex2(Stream<String> input) throws AdventException {
		List<String> lignes = input.collect(Collectors.toList());
		for (String ligne : lignes) {
			for (String ligneAComparer : lignes) {
				if (calculerNombreLettresDifferentes(ligne, ligneAComparer) == 1) {
					return recupererLettresCommunes(ligne, ligneAComparer);
				}
			}
		}
		return "";
	}

	private long calculerChecksum(List<ComposantesChecksum> composantes) {
		long nbDeuxLettres = composantes
			.stream()
			.filter(ComposantesChecksum::isDeuxLettresIdentiques)
			.count();

		long nbTroisLettres = composantes
			.stream()
			.filter(ComposantesChecksum::isTroisLettresIdentiques)
			.count();

		return nbDeuxLettres * nbTroisLettres;
	}

	private long calculerNombreLettresDifferentes(String s1, String s2) throws AdventException {
		long nbDiff = 0;
		if (s1.length() != s2.length()) {
			throw new AdventException("Certains IDs sont de taille différentes.");
		}
		else {
			for (int i = 0; i < s1.length(); i++) {
				if (s1.charAt(i) != s2.charAt(i)) {
					nbDiff++;
				}
			}
		}
		return nbDiff;
	}

	private String recupererLettresCommunes(String s1, String s2) throws AdventException {
		StringBuilder sb = new StringBuilder();
		if (s1.length() != s2.length()) {
			throw new AdventException("Certains IDs sont de taille différentes.");
		}
		else {
			for (int i = 0; i < s1.length(); i++) {
				if (s1.charAt(i) == s2.charAt(i)) {
					sb.append(s1.charAt(i));
				}
			}
		}
		return sb.toString();
	}

	private class ComposantesChecksum {

		private final static String LETTRES = "azertyuiopqsdfghjklmwxcvbn";

		private boolean isDeuxLettresIdentiques, isTroisLettresIdentiques;

		public ComposantesChecksum(String mot) {
			for (char lettre : LETTRES.toCharArray()) {
				if (StringUtils.countMatches(mot, lettre) == 2) {
					isDeuxLettresIdentiques = true;
				}
				if (StringUtils.countMatches(mot, lettre) == 3) {
					isTroisLettresIdentiques = true;
				}
			}
		}

		public boolean isDeuxLettresIdentiques() {
			return isDeuxLettresIdentiques;
		}

		public void setDeuxLettresIdentiques(boolean isDeuxLettresIdentiques) {
			this.isDeuxLettresIdentiques = isDeuxLettresIdentiques;
		}

		public boolean isTroisLettresIdentiques() {
			return isTroisLettresIdentiques;
		}

		public void setTroisLettresIdentiques(boolean isTroisLettresIdentiques) {
			this.isTroisLettresIdentiques = isTroisLettresIdentiques;
		}

	}
}