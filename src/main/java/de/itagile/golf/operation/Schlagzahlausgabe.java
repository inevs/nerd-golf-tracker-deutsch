package de.itagile.golf.operation;

import de.itagile.golf.Operation;
import de.itagile.golf.Scorecard;

public class Schlagzahlausgabe implements Operation {

	private Operation folgeoperation;

	public Schlagzahlausgabe(Operation folgeoperation) {
		this.folgeoperation = folgeoperation;
	}

	@Override
	public String fuehreAus(Scorecard scorecard) {
		int anzahl = scorecard.anzahlSchlaege();
		String schlagWort = anzahl == 1 ? "Schlag" : "Schläge";
		String vorlage = "Du hast %d %s %s";
		return String.format(vorlage,
							 anzahl,
							 schlagWort,
							 folgeoperation.fuehreAus(scorecard));
	}
}
