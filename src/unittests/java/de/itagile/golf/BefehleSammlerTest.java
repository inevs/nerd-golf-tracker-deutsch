package de.itagile.golf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.itagile.golf.befehl.SchlagBefehl;

class BefehleSammlerTest {
    
    private BefehleSammler sammler;
    
    @BeforeEach
    void setup() {
        sammler = new BefehleSammler();
    }
    
    // Kern-Funktionalitäts-Tests
    
    @Test
    void sammeltAlleErwartetenBefehle() {
        List<Befehl> befehle = sammler.sammle();
        assertThat(befehle, hasSize(3));
    }
    
    @Test
    void gibtNichtNullListeZurueck() {
        List<Befehl> befehle = sammler.sammle();
        assertThat(befehle, is(notNullValue()));
    }
    
    @Test
    void gibtMutableListeZurueck() {
        List<Befehl> befehle = sammler.sammle();
        // Sollte ohne Exception funktionieren
        befehle.add(new SchlagBefehl());
        assertThat(befehle, hasSize(4));
    }
    
    // Befehl-spezifische Tests
    
    @Test
    void enthaeltSchlagBefehl() {
        List<String> kommandos = sammler.sammle().stream()
            .map(Befehl::kommando)
            .collect(toList());
        assertThat(kommandos, hasItem("Schlage Ball"));
    }
    
    @Test
    void enthaeltLochwechselBefehl() {
        List<String> kommandos = sammler.sammle().stream()
            .map(Befehl::kommando)
            .collect(toList());
        assertThat(kommandos, hasItem("Nächstes Loch"));
    }
    
    @Test
    void enthaeltHilfeBefehl() {
        List<String> kommandos = sammler.sammle().stream()
            .map(Befehl::kommando)
            .collect(toList());
        assertThat(kommandos, hasItem("Hilfe"));
    }
    
    // Befehl-Typ-Validierung
    
    @Test
    void alleElementeSindBefehlImplementierungen() {
        List<Befehl> befehle = sammler.sammle();
        for (Befehl befehl : befehle) {
            assertThat(befehl, is(instanceOf(Befehl.class)));
            assertThat(befehl.kommando(), is(notNullValue()));
            assertThat(befehl.operation(), is(notNullValue()));
            assertThat(befehl.beschreibung(), is(notNullValue()));
        }
    }
    
    // Integrations-Tests
    
    @Test
    void befehleKoennenVomInterpreterVerwendetWerden() {
        List<Befehl> befehle = sammler.sammle();
        Map<String, Operation> operationen = befehle.stream()
            .collect(toMap(Befehl::kommando, Befehl::operation));
        
        assertThat(operationen.get("Schlage Ball"), is(notNullValue()));
        assertThat(operationen.get("Nächstes Loch"), is(notNullValue()));
        assertThat(operationen.get("Hilfe"), is(notNullValue()));
    }
    
    // Performance & Konsistenz-Tests
    
    @Test
    void liefertKonsistenteErgebnisseBeiMehrmaligernAufruf() {
        List<Befehl> ersterAufruf = sammler.sammle();
        List<Befehl> zweiterAufruf = sammler.sammle();
        
        // Gleiche Anzahl
        assertThat(zweiterAufruf, hasSize(ersterAufruf.size()));
        
        // Gleiche Kommandos
        List<String> ersteKommandos = ersterAufruf.stream().map(Befehl::kommando).collect(toList());
        List<String> zweiteKommandos = zweiterAufruf.stream().map(Befehl::kommando).collect(toList());
        assertThat(zweiteKommandos, containsInAnyOrder(ersteKommandos.toArray()));
    }
    
    @Test
    void istPerformantBeiMehrmaligernAufrufen() {
        long startzeit = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            sammler.sammle();
        }
        long dauer = System.currentTimeMillis() - startzeit;
        assertThat(dauer, is(lessThan(100L))); // < 100ms für 1000 Aufrufe
    }
    
    // Unabhängigkeits-Tests
    
    @Test
    void erzeugtNeueBefehlsinstanzenBeiJedemAufruf() {
        List<Befehl> ersteBefehle = sammler.sammle();
        List<Befehl> zweiteBefehle = sammler.sammle();
        
        // Verschiedene Objekt-Referenzen
        for (int i = 0; i < ersteBefehle.size(); i++) {
            assertThat(zweiteBefehle.get(i), is(not(sameInstance(ersteBefehle.get(i)))));
        }
    }
}