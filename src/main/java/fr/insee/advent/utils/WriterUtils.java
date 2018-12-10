package fr.insee.advent.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class WriterUtils {

	public static void ecrire(String nomFichier, List<String> lignesAEcrire) {
		try {
			// Writer writer = new FileWriter(WriterUtils.class.getClassLoader().getResource("main/output/" + nomFichier).getPath());
			// File file = new File(WriterUtils.class.getClassLoader().getResource("main/output/" + nomFichier).getPath());
			String path = "src/main/resources/main/output/" + nomFichier;
			File file = new File(path);
			PrintWriter writer = new PrintWriter(file);

			for (String ligne : lignesAEcrire) {
				writer.write(ligne + "\n");
			}

			writer.close();
			System.out.println("Ecriture d'un fichier dans " + path + ".");
		}
		catch (IOException e) {
			System.out.println("Erreur à l'écriture du fichier " + nomFichier + ".");
			e.printStackTrace();
		}

	}

}
