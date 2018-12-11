package fr.insee.advent.days;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import fr.insee.advent.utils.Reponse;

public class Day7 {

	private List<Paire> paires = new ArrayList<>();

	public static void main(String[] args) {
		Reponse.repondre(Day7.class);
	}

	public String ex1(Stream<String> input) {
		parseSiNecessaire(input);
		StringBuilder sb = new StringBuilder();
		TreeSet<String> lettresPasApresOrdonnees = new TreeSet<>();
		String lettreFinale = trouverLettreFinale();
		while (paires.size() > 0) {
			// System.out.println("paires size " + paires.size());
			lettresPasApresOrdonnees(lettresPasApresOrdonnees);
			sb.append(lettresPasApresOrdonnees.first());
			// System.out.println("sb " + sb.toString());
			removeAvant(lettresPasApresOrdonnees.first());

			lettresPasApresOrdonnees.remove(lettresPasApresOrdonnees.first());
		}
		sb.append(lettresPasApresOrdonnees.stream().collect(Collectors.joining()));
		sb.append(lettreFinale);

		return sb.toString();
	}

	public Integer ex2(Stream<String> input) {
		parseSiNecessaire(input);

		return 0;
	}

	private void removeAvant(String lettresAEnlever) {
		this.paires = paires.stream().filter(paire -> !lettresAEnlever.equals(paire.getAvant())).collect(Collectors.toList());
	}

	private void lettresPasApresOrdonnees(TreeSet<String> lettresPasApresOrdonnees) {
		Set<String> lettresAvant = paires
			.stream()
			.map(Paire::getAvant)
			.collect(Collectors.toSet());
		Set<String> lettresApres = paires
			.stream()
			.map(Paire::getApres)
			.collect(Collectors.toSet());

		lettresAvant.removeAll(lettresApres);
		lettresPasApresOrdonnees.addAll(lettresAvant);
		System.out.println("lettresPasApresOrdonnees " + lettresPasApresOrdonnees.stream().collect(Collectors.joining()));
	}

	private void parseSiNecessaire(Stream<String> input) {
		if (paires.size() == 0) {
			paires = input
				.map(ligne -> new Paire(StringUtils.substringBetween(ligne, "Step ", " must be finished before step "), StringUtils.substringBetween(ligne, " must be finished before step ", " can begin.")))
				.collect(Collectors.toList());
		}
	}

	private String trouverLettreFinale() {
		TreeSet<String> lettresAvant = paires
			.stream()
			.map(Paire::getAvant)
			.collect(Collectors.toCollection(() -> new TreeSet<>()));
		TreeSet<String> lettresApres = paires
			.stream()
			.map(Paire::getApres)
			.collect(Collectors.toCollection(() -> new TreeSet<>()));

		lettresApres.removeAll(lettresAvant);
		return lettresApres.first();
	}

	public class Paire {

		private String avant, apres;

		public String getAvant() {
			return avant;
		}

		public Paire(String avant, String apres) {
			super();
			this.avant = avant;
			this.apres = apres;
		}

		public void setAvant(String avant) {
			this.avant = avant;
		}

		public String getApres() {
			return apres;
		}

		public void setApres(String apres) {
			this.apres = apres;
		}

	}

}