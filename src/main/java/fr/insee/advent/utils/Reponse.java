package fr.insee.advent.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reponse {

	private static final List<String> NOMS_METHODES = Arrays.asList("ex1", "ex2");

	public static void formaterReponse(List<Object> reponsesExercice) {
		int numeroExercice = 1;
		for (Object reponse : reponsesExercice) {
			System.out.println("Ex n°" + numeroExercice + " : " + reponse.toString());
			numeroExercice++;
		}
	}

	public static void repondre(Class<?> classe) {
		try {
			System.out.println("--------------- " + classe.getSimpleName() + " ---------------");

			List<Object> reponsesExercice = new ArrayList<>();
			Object instance = classe.newInstance();
			for (Method method : classe.getMethods()) {
				if (NOMS_METHODES.contains(method.getName())) {
					reponsesExercice.add(method.invoke(instance, InputUtils.lireInput(classe.getSimpleName())));
				}
			}
			formaterReponse(reponsesExercice);
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.out.println("Erreur dans l'invocation des méthodes.");
			e.printStackTrace();
		}
	}
}
