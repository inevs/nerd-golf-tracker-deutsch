package de.itagile.golf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

class EinfacheScorecardTest {

	private Scorecard scorecard = new EinfacheScorecard();
	
	@Test
	void inkrementiertSchlagzahlEinmal() {
		scorecard.erhoeheAnzahlSchlaege();

		assertThat(scorecard.anzahlSchlaege(), is(1));
	}
	
	@Test
	void inkrementiertSchlagzahlMehrmals() {
		scorecard.erhoeheAnzahlSchlaege();
		scorecard.erhoeheAnzahlSchlaege();
		assertThat(scorecard.anzahlSchlaege(), is(2));
	}
	
	@Test
	void setztSchlagzahlZurueck() {
		scorecard.erhoeheAnzahlSchlaege();
		scorecard.schliesseLochAb();
		assertThat(scorecard.anzahlSchlaege(), is(0));
	}
	
	@Test
	void hatInitialErstesLoch() {
		assertThat(scorecard.aktuellesLoch(), is(1));
	}
	
	@Test
	void zaehltAktuellesLoch() {
		scorecard.schliesseLochAb();
		assertThat(scorecard.aktuellesLoch(), is(2));
	}

	@Test
	void setztSchlagzahlZurueckBeimLochwechsel() {
		// Simulate the bug scenario from issue #65
		scorecard.erhoeheAnzahlSchlaege();  // 1 Schlag auf Loch 1
		assertThat(scorecard.anzahlSchlaege(), is(1));

		scorecard.schliesseLochAb();  // Nächstes Loch (Loch 2)
		assertThat(scorecard.anzahlSchlaege(), is(0));  // Schlagzahl zurückgesetzt
		assertThat(scorecard.aktuellesLoch(), is(2));

		scorecard.erhoeheAnzahlSchlaege();  // 1 Schlag auf Loch 2
		assertThat(scorecard.anzahlSchlaege(), is(1));  // Sollte 1 sein, nicht 2
	}

}
