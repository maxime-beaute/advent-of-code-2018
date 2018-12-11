package fr.insee.advent.days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import fr.insee.advent.utils.Reponse;

public class Day4 {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private List<Roulement> historique = new ArrayList<>();

	public static void main(String[] args) {
		Reponse.repondre(Day4.class);
	}

	public Long ex1(Stream<String> input) {
		parseSiNecessaire(input);
		Map<Long, Long> tempsEndormiParGarde = historique
			.stream()
			.collect(Collectors.groupingBy(Roulement::getIdGarde, Collectors.summingLong(r -> r.getDateFin().getTime() - r.getDateDebut().getTime())));
		Entry<Long, Long> gardeLePlusEndormi = tempsEndormiParGarde
			.entrySet()
			.stream()
			// .peek(e -> System.out.println("#" + e.getKey() + " : " + e.getValue()))
			.max(Comparator.comparing(Entry::getValue))
			.get();
		System.out.println("Id du garde le plus endormi : #" + gardeLePlusEndormi.getKey());

		List<Roulement> roulementsGardeLePlusEndormi = historique
			.stream()
			.filter(r -> r.getIdGarde().equals(gardeLePlusEndormi.getKey()))
			// .peek(r -> System.out.println(r))
			.collect(Collectors.toList());

		Long minuteMax = compterNbEndormissementParMinutePourUnGarde(roulementsGardeLePlusEndormi)
			.entrySet()
			.stream()
			.max(Comparator.comparing(Entry::getValue))
			.get()
			.getKey();

		System.out.println("Minute à laquelle il s'endort le plus souvent : " + minuteMax);

		return gardeLePlusEndormi.getKey() * minuteMax;
	}

	public Long ex2(Stream<String> input) {
		parseSiNecessaire(input);

		SimpleEntry<Long, Entry<Long, Long>> gardeFoisMinute = historique
			.stream()
			.collect(Collectors.groupingBy(Roulement::getIdGarde))
			.entrySet()
			.stream()
			// .peek(e -> System.out.println("----- #" + e.getKey()))
			.map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), getMaxMinuteEndormissement(e)))
			.max(Comparator.comparing(e -> e.getValue().getValue()))
			.get();

		System.out.println("Id du garde le plus souvent endormi la même minute : #" + gardeFoisMinute.getKey());
		System.out.println("Minute concernée : " + gardeFoisMinute.getValue());
		return gardeFoisMinute.getKey() * gardeFoisMinute.getValue().getKey();
	}

	private Map<Long, Long> compterNbEndormissementParMinutePourUnGarde(List<Roulement> tousLesRoulementsDUnGarde) {
		Map<Long, Long> nbEndormissementParMinute = new HashMap<>();
		for (Long minute = 0l; minute < 59l; minute++) {
			Long nbEndormissement = 0l;
			for (Roulement roulement : tousLesRoulementsDUnGarde) {
				Calendar calDebut = Calendar.getInstance();
				calDebut.setTime(roulement.getDateDebut());
				Calendar calFin = Calendar.getInstance();
				calFin.setTime(roulement.getDateFin());
				if (calDebut.get(Calendar.MINUTE) <= minute && minute < calFin.get(Calendar.MINUTE)) {
					nbEndormissement++;
				}
				nbEndormissementParMinute.put(minute, nbEndormissement);
			}
		}
		return nbEndormissementParMinute;
	}

	private Entry<Long, Long> getMaxMinuteEndormissement(Entry<Long, List<Roulement>> e) {
		return compterNbEndormissementParMinutePourUnGarde(e.getValue())
			.entrySet()
			.stream()
			.max(Comparator.comparing(Entry::getValue))
			.get();
	}

	private void parseSiNecessaire(Stream<String> input) {
		if (historique.size() == 0) {
			List<String> lignes = input
				.sorted(Comparator.comparing(l -> parseDate(l).getTime()))
				.collect(Collectors.toList());

			Long idGardeRoulementEnCours = null;

			Roulement roulementEnCours = new Roulement();
			for (String ligne : lignes) {
				if (StringUtils.contains(ligne, "Guard")) {
					idGardeRoulementEnCours = parseIdGarde(ligne);
				}
				if (StringUtils.contains(ligne, "falls asleep")) {
					roulementEnCours = new Roulement();
					roulementEnCours.setIdGarde(idGardeRoulementEnCours);
					roulementEnCours.setDateDebut(parseDate(ligne));
				}
				if (StringUtils.contains(ligne, "wakes up")) {
					roulementEnCours.setDateFin(parseDate(ligne));
					historique.add(roulementEnCours);
				}
			}
		}
	}

	private Date parseDate(String ligne) {
		try {
			return dateFormat.parse(StringUtils.substringBetween(ligne, "[", "]"));
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Long parseIdGarde(String ligne) {
		return Long.parseLong(StringUtils.substringBetween(ligne, "Guard #", " begins shift"));
	}

	private class Roulement {

		Long idGarde;
		Date dateDebut;
		Date dateFin;

		public Long getIdGarde() {
			return idGarde;
		}

		public void setIdGarde(Long idGarde) {
			this.idGarde = idGarde;
		}

		public Date getDateDebut() {
			return dateDebut;
		}

		public void setDateDebut(Date dateDebut) {
			this.dateDebut = dateDebut;
		}

		public Date getDateFin() {
			return dateFin;
		}

		public void setDateFin(Date dateFin) {
			this.dateFin = dateFin;
		}

		@Override
		public String toString() {
			return "Roulement [idGarde=" + idGarde + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + "]";
		}
	}

}