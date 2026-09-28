package fr.l2info.oprog;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Cas de test pour une ville donnée.
 */
public class VilleTest {

    private static final double DELTA = 0.001d;

    private Ville creerVille() {
        return new Ville(new File("target/classes/data/5stations.csv"));
    }

    private Ville creerVille(String nomCsv) {
        return new Ville(new File("target/classes/data/" + nomCsv));
    }

    @Test
    public void verifierVilleVidePuisAcquisition() {
        Ville ville = creerVille();
        Map<String, Integer> statsAvant = ville.statistiques();
        Assert.assertEquals(Integer.valueOf(0), statsAvant.get("stock-OK"));
        Assert.assertEquals(Integer.valueOf(0), statsAvant.get("stations-OK"));
        Assert.assertEquals(Integer.valueOf(0), statsAvant.get("total-muscu"));
        Assert.assertEquals(Integer.valueOf(0), statsAvant.get("total-elec"));

        ville.acquerirVelos(2, 3);

        Map<String, Integer> statsApres = ville.statistiques();
        Assert.assertEquals(Integer.valueOf(5), statsApres.get("stock-OK"));
        Assert.assertEquals(Integer.valueOf(2), statsApres.get("total-muscu"));
        Assert.assertEquals(Integer.valueOf(3), statsApres.get("total-elec"));
        Assert.assertNull(ville.station("N importe quoi"));
    }

    @Test
    public void testQuiEchoueADebugger() {
        Ville maPetiteVille = creerVille();
        Iterator<String> it = maPetiteVille.circuit();
        String[] expected = { "Gare Viotte", "Place Flore", "Médiathèque", "Pont Battant", "Fort Griffon"};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertTrue(it.hasNext());
            Assert.assertEquals(expected[i], it.next());
        }
        Assert.assertFalse(it.hasNext());
    }

    @Test
    public void testStatsVide() {
        Ville maPetiteVille = creerVille();
        maPetiteVille.acquerirVelos(50, 20);
        Map<String, Integer> stats = maPetiteVille.statistiques();

        Assert.assertEquals(Integer.valueOf(70), stats.get("stock-OK"));
        Assert.assertEquals(Integer.valueOf(0), stats.get("stock-aReviser"));
        Assert.assertEquals(Integer.valueOf(0), stats.get("stock-abimes"));
        Assert.assertEquals(Integer.valueOf(0), stats.get("stations-OK"));
        Assert.assertEquals(Integer.valueOf(0), stats.get("stations-aReviser"));
        Assert.assertEquals(Integer.valueOf(0), stats.get("stations-abimes"));
        Assert.assertEquals(Integer.valueOf(20), stats.get("total-elec"));
        Assert.assertEquals(Integer.valueOf(50), stats.get("total-muscu"));
    }

    @Test
    public void testCircuitSansDoublonEtComplet() {
        Ville maPetiteVille = creerVille();
        Iterator<String> it = maPetiteVille.circuit();
        Set<String> visites = new HashSet<>();
        int nb = 0;

        while (it.hasNext()) {
            String nomStation = it.next();
            Assert.assertFalse(visites.contains(nomStation));
            visites.add(nomStation);
            nb++;
        }

        Assert.assertEquals(5, nb);
    }

    @Test
    public void testStationExistanteEtAbsente() {
        Ville maPetiteVille = creerVille();
        Assert.assertNotNull(maPetiteVille.station("Gare Viotte"));
        Assert.assertNull(maPetiteVille.station("Station inconnue"));
    }

    @Test
    public void testCreerAbonneValide() {
        Ville maPetiteVille = creerVille();
        Abonne abonne = maPetiteVille.creerAbonne("Alice", "12345-12345-01234567890-06");
        Assert.assertNotNull(abonne);
        Assert.assertEquals("Alice", abonne.getNom());
    }

    @Test
    public void testCreerAbonneNomInvalide() {
        Ville maPetiteVille = creerVille();
        Abonne abonne = maPetiteVille.creerAbonne("Xx_DarkSasuke666_xX", "12345-12345-01234567890-06");
        Assert.assertNull(abonne);
    }

    @Test
    public void testCreerAbonneRibInvalide() {
        Ville maPetiteVille = creerVille();
        Abonne abonne = maPetiteVille.creerAbonne("Alice", "12345-12345-01234567890-07");
        Assert.assertNull(abonne);
    }

    @Test
    public void testCreerAbonneNomTrim() {
        Ville maPetiteVille = creerVille();
        Abonne abonne = maPetiteVille.creerAbonne("   Alice   ", "12345-12345-01234567890-06");
        Assert.assertNotNull(abonne);
        Assert.assertEquals("Alice", abonne.getNom());
    }

    @Test
    public void testAcquerirVelosNegative() {
        Ville maPetiteVille = creerVille();
        maPetiteVille.acquerirVelos(-5, 0);
        Map<String, Integer> stats = maPetiteVille.statistiques();

        Assert.assertEquals(Integer.valueOf(0), stats.get("stock-OK"));
        Assert.assertEquals(Integer.valueOf(0), stats.get("total-elec"));
        Assert.assertEquals(Integer.valueOf(0), stats.get("total-muscu"));
    }

    @Test
    public void testEntretenirVelosRepareEtRevise() {
        Ville maPetiteVille = creerVille();

        VeloMusculaire veloAbime = new VeloMusculaire();
        veloAbime.decrocher();
        veloAbime.abimer();

        VeloElectrique veloAReviser = new VeloElectrique();
        veloAReviser.decrocher();
        veloAReviser.parcourir(1000.0d);

        maPetiteVille.velosStock.add(veloAbime);
        maPetiteVille.velosStock.add(veloAReviser);

        maPetiteVille.entretenirVelos();

        Assert.assertFalse(veloAbime.estAbime());
        Assert.assertTrue(veloAReviser.prochaineRevision() > 0.0d);
    }

    @Test
    public void testStatistiquesPrioriteAbimeSurReviser() {
        Ville maPetiteVille = creerVille();

        VeloMusculaire veloAbimeEtAReviser = new VeloMusculaire();
        veloAbimeEtAReviser.decrocher();
        veloAbimeEtAReviser.parcourir(1000.0d);
        veloAbimeEtAReviser.abimer();

        VeloElectrique veloSeulementAReviser = new VeloElectrique();
        veloSeulementAReviser.decrocher();
        veloSeulementAReviser.parcourir(1000.0d);

        maPetiteVille.velosStock.add(veloAbimeEtAReviser);
        maPetiteVille.velosStock.add(veloSeulementAReviser);

        Map<String, Integer> stats = maPetiteVille.statistiques();
        Assert.assertEquals(Integer.valueOf(1), stats.get("stock-abimes"));
        Assert.assertEquals(Integer.valueOf(1), stats.get("stock-aReviser"));
        Assert.assertEquals(Integer.valueOf(0), stats.get("stock-OK"));
    }

    @Test
    public void testReapprovisionnerRemplitUneStation() {
        Ville maPetiteVille = creerVille();
        maPetiteVille.acquerirVelos(3, 2);

        maPetiteVille.reapprovisionner(2, 1);

        Map<String, Integer> stats = maPetiteVille.statistiques();
        int nbEnStation = stats.get("stations-OK") + stats.get("stations-aReviser") + stats.get("stations-abimes");
        Assert.assertTrue(nbEnStation > 0);
        Assert.assertEquals(5, stats.get("total-elec") + stats.get("total-muscu"));
    }

    @Test
    public void testFacturationMoisDonne() {
        Ville maPetiteVille = creerVille();
        Abonne abonne = maPetiteVille.creerAbonne("Bob", "12345-12345-01234567890-06");
        Assert.assertNotNull(abonne);

        VeloMusculaire velo = new VeloMusculaire();

        Calendar debut = new GregorianCalendar(2026, Calendar.MAY, 10, 10, 0, 0);
        debut.set(Calendar.MILLISECOND, 0);
        long debutMs = debut.getTimeInMillis();
        long finMs = debutMs + 60L * 60L * 1000L;

        Assert.assertEquals(0, maPetiteVille.jRegistre.emprunter(abonne, velo, debutMs));
        Assert.assertEquals(0, maPetiteVille.jRegistre.retourner(velo, finMs));

        Map<Abonne, Double> facture = maPetiteVille.facturation(5, 2026);
        Assert.assertNotNull(facture);
        Assert.assertTrue(facture.containsKey(abonne));
        Assert.assertTrue(facture.get(abonne) > 0.0d);
    }

    @Test
    public void testFacturationVide() {
        Ville maPetiteVille = creerVille();
        Map<Abonne, Double> facture = maPetiteVille.facturation(5, 2026);
        Assert.assertNotNull(facture);
        Assert.assertTrue(facture.isEmpty());
    }

    @Test
    public void testStationRechercheCase() {
        Ville maPetiteVille = creerVille();
        Assert.assertNull(maPetiteVille.station("gare viotte"));
        Assert.assertNotNull(maPetiteVille.station("Gare Viotte"));
    }

    @Test
    public void testStationAvecNomNullRetourneNull() {
        Ville maPetiteVille = creerVille();
        Assert.assertNull(maPetiteVille.station(null));
    }

    @Test
    public void testStationAvecNomVideRetourneNull() {
        Ville maPetiteVille = creerVille();
        Assert.assertNull(maPetiteVille.station(""));
        Assert.assertNull(maPetiteVille.station("   "));
    }

    @Test
    public void testReapprovisionnerIgnoreVelosAbimesOuAReviser() {
        Ville maPetiteVille = creerVille();

        VeloMusculaire veloAbime = new VeloMusculaire();
        veloAbime.decrocher();
        veloAbime.abimer();

        VeloElectrique veloAReviser = new VeloElectrique();
        veloAReviser.decrocher();
        veloAReviser.parcourir(1000.0d);

        maPetiteVille.velosStock.add(veloAbime);
        maPetiteVille.velosStock.add(veloAReviser);

        maPetiteVille.reapprovisionner(10, 10);

        Map<String, Integer> stats = maPetiteVille.statistiques();
        Assert.assertEquals(Integer.valueOf(0), stats.get("stations-OK"));
        Assert.assertEquals(Integer.valueOf(0), stats.get("stations-aReviser"));
        Assert.assertEquals(Integer.valueOf(0), stats.get("stations-abimes"));
        Assert.assertEquals(Integer.valueOf(1), stats.get("stock-abimes"));
        Assert.assertEquals(Integer.valueOf(1), stats.get("stock-aReviser"));
    }

    @Test
    public void testFacturationMontantExact() {
        Ville maPetiteVille = creerVille();
        Abonne abonne = maPetiteVille.creerAbonne("David", "12345-12345-01234567890-06");
        Assert.assertNotNull(abonne);

        VeloMusculaire vm = new VeloMusculaire();
        VeloElectrique ve = new VeloElectrique();

        Calendar debutMois = new GregorianCalendar(2026, Calendar.MAY, 12, 10, 0, 0);
        debutMois.set(Calendar.MILLISECOND, 0);
        long t0 = debutMois.getTimeInMillis();

        Assert.assertEquals(0, maPetiteVille.jRegistre.emprunter(abonne, vm, t0));
        Assert.assertEquals(0, maPetiteVille.jRegistre.retourner(vm, t0 + 30L * 60L * 1000L));

        Assert.assertEquals(0, maPetiteVille.jRegistre.emprunter(abonne, ve, t0 + 40L * 60L * 1000L));
        Assert.assertEquals(0, maPetiteVille.jRegistre.retourner(ve, t0 + 60L * 60L * 1000L));

        Map<Abonne, Double> facture = maPetiteVille.facturation(5, 2026);
        Assert.assertNotNull(facture);
        Assert.assertEquals(1.0d + 1.0d, facture.get(abonne), DELTA);
    }

    @Test
    public void testFacturationInclutRetourDebutMois() {
        Ville maPetiteVille = creerVille();
        Abonne abonne = maPetiteVille.creerAbonne("Emma", "12345-12345-01234567890-06");
        Assert.assertNotNull(abonne);

        VeloMusculaire velo = new VeloMusculaire();

        Calendar retour = new GregorianCalendar(2026, Calendar.MAY, 1, 0, 0, 0);
        retour.set(Calendar.MILLISECOND, 0);
        long fin = retour.getTimeInMillis();
        long debut = fin - 30L * 60L * 1000L;

        Assert.assertEquals(0, maPetiteVille.jRegistre.emprunter(abonne, velo, debut));
        Assert.assertEquals(0, maPetiteVille.jRegistre.retourner(velo, fin));

        Map<Abonne, Double> factureMai = maPetiteVille.facturation(5, 2026);
        Assert.assertEquals(1.0d, factureMai.get(abonne), DELTA);
    }

    @Test
    public void testFacturationExclutMoisSuivant() {
        Ville maPetiteVille = creerVille();
        Abonne abonne = maPetiteVille.creerAbonne("Farah", "12345-12345-01234567890-06");
        Assert.assertNotNull(abonne);

        VeloMusculaire velo = new VeloMusculaire();

        Calendar debut = new GregorianCalendar(2026, Calendar.MAY, 31, 23, 45, 0);
        debut.set(Calendar.MILLISECOND, 0);
        long debutMs = debut.getTimeInMillis();

        Calendar fin = new GregorianCalendar(2026, Calendar.JUNE, 1, 0, 15, 0);
        fin.set(Calendar.MILLISECOND, 0);
        long finMs = fin.getTimeInMillis();

        Assert.assertEquals(0, maPetiteVille.jRegistre.emprunter(abonne, velo, debutMs));
        Assert.assertEquals(0, maPetiteVille.jRegistre.retourner(velo, finMs));

        Map<Abonne, Double> factureMai = maPetiteVille.facturation(5, 2026);
        Assert.assertEquals(0.0d, factureMai.get(abonne), DELTA);
    }

    @Test
    public void testFacturationIgnoreEmpruntNonRendu() {
        Ville maPetiteVille = creerVille();
        Abonne abonne = maPetiteVille.creerAbonne("Gina", "12345-12345-01234567890-06");
        Assert.assertNotNull(abonne);

        VeloElectrique velo = new VeloElectrique();
        Calendar debut = new GregorianCalendar(2026, Calendar.MAY, 20, 9, 0, 0);
        debut.set(Calendar.MILLISECOND, 0);

        Assert.assertEquals(0, maPetiteVille.jRegistre.emprunter(abonne, velo, debut.getTimeInMillis()));

        Map<Abonne, Double> facture = maPetiteVille.facturation(5, 2026);
        Assert.assertEquals(0.0d, facture.get(abonne), DELTA);
    }

    @Test
    public void testFacturationDeuxAbonnesIsoles() {
        Ville maPetiteVille = creerVille();
        Abonne abonne1 = maPetiteVille.creerAbonne("Hugo", "12345-12345-01234567890-06");
        Abonne abonne2 = maPetiteVille.creerAbonne("Ines", "12345-12345-01234567890-06");
        Assert.assertNotNull(abonne1);
        Assert.assertNotNull(abonne2);

        long t0 = new GregorianCalendar(2026, Calendar.MAY, 15, 9, 0, 0).getTimeInMillis();
        VeloMusculaire vm = new VeloMusculaire();
        VeloElectrique ve = new VeloElectrique();

        Assert.assertEquals(0, maPetiteVille.jRegistre.emprunter(abonne1, vm, t0));
        Assert.assertEquals(0, maPetiteVille.jRegistre.retourner(vm, t0 + 30L * 60L * 1000L));

        Assert.assertEquals(0, maPetiteVille.jRegistre.emprunter(abonne2, ve, t0 + 40L * 60L * 1000L));
        Assert.assertEquals(0, maPetiteVille.jRegistre.retourner(ve, t0 + 100L * 60L * 1000L));

        Map<Abonne, Double> facture = maPetiteVille.facturation(5, 2026);
        Assert.assertEquals(2, facture.size());
        Assert.assertTrue(facture.containsKey(abonne1));
        Assert.assertTrue(facture.containsKey(abonne2));
        Assert.assertEquals(1.0d, facture.get(abonne1), DELTA);
        Assert.assertEquals(3.0d, facture.get(abonne2), DELTA);
    }

    @Test
    public void testAcquerirVelosQuantitesExactes() {
        Ville maPetiteVille = creerVille();

        maPetiteVille.acquerirVelos(2, 1);
        maPetiteVille.acquerirVelos(3, 4);

        Map<String, Integer> stats = maPetiteVille.statistiques();
        Assert.assertEquals(Integer.valueOf(10), stats.get("stock-OK"));
        Assert.assertEquals(Integer.valueOf(5), stats.get("total-muscu"));
        Assert.assertEquals(Integer.valueOf(5), stats.get("total-elec"));
    }

    @Test
    public void testReapprovisionnerAvecStockLimite() {
        Ville maPetiteVille = creerVille();
        maPetiteVille.acquerirVelos(1, 0);

        maPetiteVille.reapprovisionner(10, 10);

        Map<String, Integer> stats = maPetiteVille.statistiques();
        Assert.assertEquals(Integer.valueOf(1), stats.get("total-muscu"));
        Assert.assertEquals(Integer.valueOf(0), stats.get("total-elec"));
        Assert.assertEquals(Integer.valueOf(1), stats.get("stations-OK"));
        Assert.assertEquals(Integer.valueOf(0), stats.get("stock-OK"));
    }

    @Test
    public void testChevauchement() {
        Ville maPetiteVille = creerVille();
        Abonne abonne = maPetiteVille.creerAbonne("Chris", "12345-12345-01234567890-06");
        Assert.assertNotNull(abonne);

        VeloMusculaire velo = new VeloMusculaire();
        long t0 = 1000L;

        Assert.assertEquals(0, maPetiteVille.jRegistre.emprunter(abonne, velo, t0 + 10));
        Assert.assertEquals(0, maPetiteVille.jRegistre.retourner(velo, t0 + 15));
        Assert.assertEquals(0, maPetiteVille.jRegistre.emprunter(abonne, velo, t0 + 3));

        int retour = maPetiteVille.jRegistre.retourner(velo, t0 + 8);
        Assert.assertEquals(0, retour);
    }

    @Test
    public void testCircuitBesanconComplet() throws Exception {
        StationReader reader = new StationReader(new File("target/classes/data/velociteBesancon.csv"));
        int nbStationsAttendu = reader.getStations().length;

        Ville ville = creerVille("velociteBesancon.csv");
        Iterator<String> it = ville.circuit();
        Set<String> visites = new HashSet<>();
        int nb = 0;

        while (it.hasNext()) {
            String nom = it.next();
            Assert.assertTrue(visites.add(nom));
            nb++;
        }

        Assert.assertEquals(nbStationsAttendu, nb);
    }

    @Test
    public void testCircuitMemeReader() throws Exception {
        Station[] stations = new StationReader(new File("target/classes/data/velociteBesancon.csv")).getStations();
        Set<String> attendu = new HashSet<>();
        for (Station station : stations) {
            attendu.add(station.getNom());
        }

        Ville ville = creerVille("velociteBesancon.csv");
        Set<String> reel = new HashSet<>();
        Iterator<String> it = ville.circuit();
        while (it.hasNext()) {
            reel.add(it.next());
        }

        Assert.assertEquals(attendu, reel);
    }

    @Test
    public void testVilleTrim() throws Exception {
        String[] fichiers = {"velociteBesanconTrim1.csv", "velociteBesanconTrim10.csv"};

        for (String fichier : fichiers) {
            Station[] stations = new StationReader(new File("target/classes/data/" + fichier)).getStations();
            Ville ville = creerVille(fichier);
            Assert.assertNotNull(ville.station(stations[0].getNom()));
            Assert.assertNotNull(ville.station(stations[stations.length - 1].getNom()));
        }
    }

}
