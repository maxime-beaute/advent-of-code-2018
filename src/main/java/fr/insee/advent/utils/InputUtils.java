package fr.insee.advent.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;

public class InputUtils {

	public static Stream<String> lireInput(String nomFichier) {
		return lireInputGenerique("main/input/" + nomFichier);
	}

	public static Stream<String> lireInputTest(String nomFichier) {
		return lireInputGenerique("test/" + nomFichier);
	}

	public static Stream<Integer> lireInputToIntegers(String nomJour) {
		return lireInput(nomJour)
			.map(Integer::parseInt);
	}

	private static Stream<String> lireInputGenerique(String pathFichier) {
		try {
			InputStream is = InputUtils.class.getClassLoader().getResourceAsStream(pathFichier);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			return reader.lines();
		}
		catch (UnsupportedEncodingException e) {
			System.out.println("Erreur dans le choix de l'encodage du fichier " + pathFichier + ".");
			e.printStackTrace();
			return null;
		}
	}
}
