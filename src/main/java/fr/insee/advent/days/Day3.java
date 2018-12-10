package fr.insee.advent.days;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import fr.insee.advent.exception.AdventException;
import fr.insee.advent.utils.Reponse;
import fr.insee.advent.utils.WriterUtils;

public class Day3 {

	public static final String NOM_FICHIER_PRINT = "Day3-print";
	public static final int LIMITE = 1500;
	public Case[][] tableau = null;
	public Set<Long> idsZones = new HashSet<>();

	public static void main(String[] args) {
		Reponse.repondre(Day3.class);
	}

	public Long ex1(Stream<String> input) throws AdventException {
		creerTableauSiNecessaire(input);

		return Stream
			.of(tableau)
			.mapToLong(ligneTableau -> Stream
				.of(ligneTableau)
				.filter(Objects::nonNull)
				.filter(Case::isChevauchement)
				.count())
			.sum();
	}

	public Long ex2(Stream<String> input) throws AdventException {
		creerTableauSiNecessaire(input);

		return recupererIdZoneSansChevauchement(tableau);
	}

	private void creerTableauSiNecessaire(Stream<String> input) throws AdventException {
		if (tableau == null) {
			tableau = initialiserUnTableau();
			input
				.map(ligne -> parseLigne(ligne))
				.forEach(rectangleAAjouter -> ajouterRectangleATableau(tableau, rectangleAAjouter));

			print(tableau, LIMITE);
		}
	}

	private Case[][] parseLigne(String ligne) {
		Long idOccupant = Long.parseLong(StringUtils.substringBetween(ligne, "#", " @"));
		Integer x = Integer.parseInt(StringUtils.substringBetween(ligne, "@ ", ","));
		Integer y = Integer.parseInt(StringUtils.substringBetween(ligne, ",", ":"));
		Integer longueur = Integer.parseInt(StringUtils.substringBetween(ligne, ": ", "x"));
		Integer largeur = Integer.parseInt(StringUtils.substringAfter(ligne, "x"));

		idsZones.add(idOccupant);
		Case[][] rectangle = creerRectangle(idOccupant, x, y, longueur, largeur);
		return rectangle;
	}

	private Case[][] creerRectangle(Long idOccupant, Integer x, Integer y, Integer longueur, Integer largeur) {
		Case[][] rectangle = initialiserUnTableau();
		for (int i = 0; i < longueur; i++) {
			for (int j = 0; j < largeur; j++) {
				rectangle[x + i][y + j] = new Case(idOccupant);
			}
		}
		return rectangle;
	}

	private Case[][] initialiserUnTableau() {
		Case[][] tableau = new Case[LIMITE][LIMITE];
		for (int i = 0; i < tableau.length; i++) {
			for (int j = 0; j < tableau[i].length; j++) {
				tableau[i][j] = new Case();
			}
		}
		return tableau;
	}

	private Case[][] ajouterRectangleATableau(Case[][] tableau, Case[][] rectangleAAjouter) {
		try {
			for (int i = 0; i < tableau.length; i++) {
				for (int j = 0; j < tableau[i].length; j++) {
					if (rectangleAAjouter[i][j] != null && rectangleAAjouter[i][j].getSeulIdOccupant() != null) {
						tableau[i][j].getIdsOccupant().add(rectangleAAjouter[i][j].getSeulIdOccupant());
					}
				}
			}
		}
		catch (AdventException e) {
			e.printStackTrace();
		}
		return tableau;
	}

	private Long recupererIdZoneSansChevauchement(Case[][] tableau) throws AdventException {
		Set<Long> idsZonesSansChevauchement = idsZones;
		for (int i = 0; i < tableau.length; i++) {
			for (int j = 0; j < tableau[i].length; j++) {
				if (tableau[i][j].getIdsOccupant().size() > 1) {
					idsZonesSansChevauchement.removeAll(tableau[i][j].getIdsOccupant());
				}
			}
		}
		if (idsZonesSansChevauchement.size() == 1) {
			return (Long) idsZonesSansChevauchement.toArray()[0];
		}
		else {
			System.out.println("Longueur idsZonesSansChevauchement : " + idsZonesSansChevauchement.size());
			System.out.println("idsZonesSansChevauchement : " + idsZonesSansChevauchement.stream().sorted().collect(Collectors.toList()).toString());
			throw new AdventException("Erreur - Longueur idsZonesSansChevauchement : " + idsZonesSansChevauchement.size() + " ; idsZonesSansChevauchement : " + idsZonesSansChevauchement.stream().sorted().collect(Collectors.toList()).toString());
		}
	}

	private void print(Case[][] tableau, int tailleTableau) throws AdventException {
		List<String> lignesAEcrire = new ArrayList<>();
		for (int i = 0; i < tailleTableau; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < tailleTableau; j++) {
				if (tableau[i][j] != null && tableau[i][j].getIdsOccupant().size() != 0) {
					sb.append(tableau[i][j].getIdsOccupant().size() + " ");
				}
				else {
					sb.append(". ");
				}
			}
			lignesAEcrire.add(sb.toString());
		}
		System.out.println("Ecriture du tableau dans " + NOM_FICHIER_PRINT + ".");
		WriterUtils.ecrire(NOM_FICHIER_PRINT, lignesAEcrire);
	}

	private class Case {

		private List<Long> idsOccupant = new ArrayList<>();

		public Case() {}

		public Case(Long idOccupant) {
			idsOccupant.add(idOccupant);
		}

		public List<Long> getIdsOccupant() {
			return idsOccupant;
		}

		public void setIdsOccupant(List<Long> idOccupant) {
			this.idsOccupant = idOccupant;
		}

		public boolean isChevauchement() {
			return idsOccupant.size() > 1;
		}

		public Long getSeulIdOccupant() throws AdventException {
			if (idsOccupant.size() == 0) {
				return null;
			}
			if (!isChevauchement()) {
				return idsOccupant.get(0);
			}
			else {
				throw new AdventException("Il y a plus d'un occupant (" + idsOccupant.size() + ") dans cette case.");
			}
		}
	}
}
