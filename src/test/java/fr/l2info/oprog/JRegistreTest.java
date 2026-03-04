package fr.l2info.oprog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JRegistreTest {

    Abonne[] abonnes = new Abonne[2];
    Map<Velos, Velo> velos = new HashMap<>();

    Station station;


    @Before     // méthode exécutée avant chaque test
    public void setup() throws IncorrectNameException {
        Abonne abonne = new Abonne("IHUQSDFGHUDUYFDSUFHDSHJDSFKJDSHFJIDSHDSH", "12345-12345-01234567890-06"); // Abonnee valide
        Abonne abonneRibInvalide = new Abonne("IHUQSDFGHUDUYFDSUFHDSHJDSFKJDSHFJIDSHDSH"); // Abonnee RIB Invalide

        VeloMusculaire vmNeuf = new VeloMusculaire();
        VeloMusculaire vmAbimer = new VeloMusculaire();
        vmAbimer.abimer();
        VeloMusculaire vmRevision = new VeloMusculaire();
        vmRevision.parcourir(99999999.0d);

        VeloMusculaire vmNeufA = new VeloMusculaire();
        VeloMusculaire vmAbimerA = new VeloMusculaire();
        vmAbimerA.abimer();
        VeloMusculaire vmRevisionA = new VeloMusculaire();
        vmRevisionA.parcourir(99999999.0d);

        VeloElectrique ve = new VeloElectrique();
        VeloElectrique veloEAbimer = new VeloElectrique();
        veloEAbimer.abimer();
        VeloElectrique veloERevision = new VeloElectrique();
        veloERevision.parcourir(99999999.0d);

        VeloElectrique veloNeufA = new VeloElectrique();
        VeloElectrique veloAbimerA = new VeloElectrique();
        VeloElectrique veloRevisionA = new VeloElectrique();
        veloAbimerA.abimer();
        veloRevisionA.parcourir(99999999.0d);

        velos.put(Velos.VM_NEUF, vmNeuf);
        velos.put(Velos.VM_ABIMER, vmAbimer);
        velos.put(Velos.VM_REVISION, vmRevision);
        velos.put(Velos.VM_NEUF_A, vmNeufA);
        velos.put(Velos.VM_ABIMER_A, vmAbimerA);
        velos.put(Velos.VM_REVISION_A, vmRevisionA);
        velos.put(Velos.VE, ve);
        velos.put(Velos.VE_ABIMER, veloEAbimer);
        velos.put(Velos.VE_REVISION, veloERevision);
        velos.put(Velos.VE_NEUF_A, veloNeufA);
        velos.put(Velos.VE_ABIMER_A, veloAbimerA);
        velos.put(Velos.VE_REVISION_A, veloRevisionA);

        abonnes[0] = abonne;
        abonnes[1] = abonneRibInvalide;

        station = new Station("Feur Station", 4,4, 42); // Station valide
    }

    @Test
    public void testCreeRegistreTest() {
        JRegistre jRegistre = new JRegistre();
    }

    @Test
    public void assignRegistreStationTest() {
        JRegistre jRegistre = new JRegistre();
        station.setRegistre(jRegistre);
    }

    @Test
    public void arrimerVeloStationTest() {
        JRegistre jRegistre = new JRegistre();
        station.setRegistre(jRegistre);
        VeloMusculaire vm  = (VeloMusculaire) velos.get(Velos.VM_NEUF);
        int result = station.arrimerVelo(vm, 1);
        Assert.assertEquals(-4, result);
        Assert.assertNotEquals(null, station.veloALaBorne(1));
    }

    @Test
    public void emprunterVeloStationTest() {
        JRegistre jRegistre = new JRegistre();
        station.setRegistre(jRegistre);
        VeloMusculaire vm  = (VeloMusculaire) velos.get(Velos.VM_NEUF);
        int result = station.arrimerVelo(vm, 1);
        Assert.assertEquals(-4, result);
        Assert.assertNotEquals(null, station.veloALaBorne(1));

        // une fois le vélo à la borne
        Velo velo = station.emprunterVelo(abonnes[0], 1);
        Assert.assertNotEquals(null, velo);
    }

    @Test
    public void emprunterVeloStationAbonneBloquerTest() {
        JRegistre jRegistre = new JRegistre();
        station.setRegistre(jRegistre);
        VeloMusculaire vm  = (VeloMusculaire) velos.get(Velos.VM_NEUF);
        int result = station.arrimerVelo(vm, 1);
        Assert.assertEquals(-4, result);
        Assert.assertNotEquals(null, station.veloALaBorne(1));

        // une fois le vélo à la borne
        Velo velo = station.emprunterVelo(abonnes[1], 1);
        Assert.assertEquals(null, velo);
    }

    @Test
    public void emprunter2foisVeloStationAbonneTest() {
        JRegistre jRegistre = new JRegistre();
        station.setRegistre(jRegistre);
        VeloMusculaire vm  = (VeloMusculaire) velos.get(Velos.VM_NEUF);
        VeloMusculaire vm2 = (VeloMusculaire) velos.get(Velos.VM_NEUF_A);
        int result = station.arrimerVelo(vm, 1);
        Assert.assertEquals(-4, result);
        result = station.arrimerVelo(vm2, 2);
        Assert.assertEquals(-4, result);
        Assert.assertNotEquals(null, station.veloALaBorne(1));

        // une fois le vélo à la borne
        Velo velo = station.emprunterVelo(abonnes[0], 1);
        Assert.assertNotEquals(null, velo);

        velo = station.emprunterVelo(abonnes[0], 2);
        Assert.assertNotEquals(null, velo);
    }

    @Test
    public void testEprunterUnVelo() {
        JRegistre jRegistre = new JRegistre();
        Velo veloMusculaire = velos.get(Velos.VM_NEUF);
        int result = jRegistre.emprunter(abonnes[0], veloMusculaire, station.maintenant());
        Assert.assertEquals(0, result);
    }

    @Test
    public void testEprunterUnVelo2Fois() {
        JRegistre jRegistre = new JRegistre();
        Velo veloMusculaire = velos.get(Velos.VM_NEUF);
        int result = jRegistre.emprunter(abonnes[0], veloMusculaire, station.maintenant());
        Assert.assertEquals(0, result);
        result = jRegistre.emprunter(abonnes[0], veloMusculaire, station.maintenant());
        Assert.assertEquals(-2, result);
    }

    @Test
    public void testEprunterUnVeloPuisRendre() {
        JRegistre jRegistre = new JRegistre();
        Velo veloMusculaire = velos.get(Velos.VM_NEUF);
        int result = jRegistre.emprunter(abonnes[0], veloMusculaire, station.maintenant());
        Assert.assertEquals(0, result);
        result = jRegistre.retourner(veloMusculaire, station.maintenant() + 5);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testEprunterUnVeloPuisRendrePuisEmprunter() {
        JRegistre jRegistre = new JRegistre();
        Velo veloMusculaire = velos.get(Velos.VM_NEUF);
        int result = jRegistre.emprunter(abonnes[0], veloMusculaire, station.maintenant());
        Assert.assertEquals(0, result);
        result = jRegistre.retourner(veloMusculaire, station.maintenant() + 5);
        Assert.assertEquals(0, result);
        result = jRegistre.emprunter(abonnes[0], veloMusculaire, station.maintenant()+ 10);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testEmprunterAboNull() {
        Velo veloMusculaire = velos.get(Velos.VM_NEUF);
        JRegistre jRegistre = new JRegistre();
        int result = jRegistre.emprunter(null, veloMusculaire, station.maintenant());
        Assert.assertEquals(-1, result);
    }

    @Test
    public void testEmprunterVeloNull() {
        JRegistre jRegistre = new JRegistre();
        int result = jRegistre.emprunter(abonnes[0], null, station.maintenant());
        Assert.assertEquals(-1, result);
    }

    @Test
    public void testRetournerVeloNull() {
        JRegistre jRegistre = new JRegistre();
        int result = jRegistre.retourner(null, station.maintenant());
        Assert.assertEquals(-1, result);
    }

    @Test
    public void testRetournerVeloNonEmprunter() {
        JRegistre jRegistre = new JRegistre();
        int result = jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant());
        Assert.assertEquals(-2, result);
    }

    @Test
    public void testEmpruntsEnCoursWithEmprunt() {
        JRegistre jRegistre = new JRegistre();
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant());
        int result = jRegistre.nbEmpruntsEnCours(abonnes[0]);
        Assert.assertEquals(1, result);
    }

    @Test
    public void testEmpruntsEnCoursWithoutEmprunt() {
        JRegistre jRegistre = new JRegistre();
        int result = jRegistre.nbEmpruntsEnCours(abonnes[0]);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testEmpruntsEnCoursWithEmpruntRendu() {
        JRegistre jRegistre = new JRegistre();
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant());
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant()+5);
        int result = jRegistre.nbEmpruntsEnCours(abonnes[0]);
        Assert.assertEquals(0, result);
    }



    public enum Velos {
        VM_NEUF,
        VM_ABIMER,
        VM_REVISION,
        VM_NEUF_A,
        VM_ABIMER_A,
        VM_REVISION_A,
        VE,
        VE_ABIMER,
        VE_REVISION,
        VE_NEUF_A,
        VE_ABIMER_A,
        VE_REVISION_A

    }


}

