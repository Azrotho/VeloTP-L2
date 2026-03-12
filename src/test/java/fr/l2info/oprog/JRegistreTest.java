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

    @Before
    public void setup() throws IncorrectNameException {
        Abonne abonne = new Abonne("IHUQSDFGHUDUYFDSUFHDSHJDSFKJDSHFJIDSHDSH", "12345-12345-01234567890-06");
        Abonne abonneRibInvalide = new Abonne("IHUQSDFGHUDUYFDSUFHDSHJDSFKJDSHFJIDSHDSH");

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

        station = new Station("Feur Station", 4, 4, 42); // Station valide
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
        VeloMusculaire vm = (VeloMusculaire) velos.get(Velos.VM_NEUF);
        int result = station.arrimerVelo(vm, 1);
        Assert.assertEquals(-4, result);
        Assert.assertNotEquals(null, station.veloALaBorne(1));
    }

    @Test
    public void emprunterVeloStationTest() {
        JRegistre jRegistre = new JRegistre();
        station.setRegistre(jRegistre);
        VeloMusculaire vm = (VeloMusculaire) velos.get(Velos.VM_NEUF);
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
        VeloMusculaire vm = (VeloMusculaire) velos.get(Velos.VM_NEUF);
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
        VeloMusculaire vm = (VeloMusculaire) velos.get(Velos.VM_NEUF);
        VeloMusculaire vm2 = (VeloMusculaire) velos.get(Velos.VM_NEUF_A);
        int result = station.arrimerVelo(vm, 1);
        Assert.assertEquals(-4, result);
        result = station.arrimerVelo(vm2, 2);
        Assert.assertEquals(-4, result);
        Assert.assertNotEquals(null, station.veloALaBorne(1));

        Velo velo = station.emprunterVelo(abonnes[0], 1);
        Assert.assertNotEquals(null, velo);

        velo = station.emprunterVelo(abonnes[0], 2);
        Assert.assertEquals(null, velo);
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
        result = jRegistre.emprunter(abonnes[0], veloMusculaire, station.maintenant() + 10);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testEprunterUnVeloPuisRendrePuisEmprunterDansLePasse() {
        JRegistre jRegistre = new JRegistre();
        Velo veloMusculaire = velos.get(Velos.VM_NEUF);
        int result = jRegistre.emprunter(abonnes[0], veloMusculaire, station.maintenant());
        Assert.assertEquals(0, result);
        result = jRegistre.retourner(veloMusculaire, station.maintenant() + 15);
        Assert.assertEquals(0, result);
        result = jRegistre.emprunter(abonnes[0], veloMusculaire, station.maintenant() + 10);
        Assert.assertEquals(-2, result);
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
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 5);
        int result = jRegistre.nbEmpruntsEnCours(abonnes[0]);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testRetourWithEmpruntRenduEmpruntRendu() {
        JRegistre jRegistre = new JRegistre();
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant());
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 5);
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant() + 10);
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 15);
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant() + 20);
        int result = jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 25);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testRetourWithEmpruntRenduEmpruntRenduRendu() {
        JRegistre jRegistre = new JRegistre();
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant());
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 5);
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant() + 10);
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 15);
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant() + 20);
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 22);
        int result = jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 25);
        Assert.assertEquals(-2, result);
    }

    @Test
    public void testRetourWithEmpruntRenduEmpruntRenduRenduChevauche1() {
        JRegistre jRegistre = new JRegistre();
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant());
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 5);
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant() + 10);
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 15);
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant() + 20);
        int result = jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant());
        Assert.assertEquals(-3, result);
    }

    @Test
    public void testRetourWithEmpruntRenduEmpruntRenduRenduChevauche2() {
        JRegistre jRegistre = new JRegistre();
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant());
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 5);
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant() + 10);
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 15);
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant() + 20);
        int result = jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 10);
        Assert.assertEquals(-3, result);
    }

    @Test
    public void testRetourWithEmpruntRenduEmpruntRenduRenduChevauche3() {
        JRegistre jRegistre = new JRegistre();
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant());
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 5);
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant() + 10);
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 15);
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant() + 20);
        int result = jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 4);
        Assert.assertEquals(-3, result);
    }

    @Test
    public void testRetourWithEmpruntRenduEmpruntRenduRenduChevauche4() {
        JRegistre jRegistre = new JRegistre();
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant());
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 5);
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant() + 10);
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 15);
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant() + 20);
        int result = jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 19);
        Assert.assertEquals(-3, result);
    }

    @Test
    public void testRetourWithEmpruntRenduEmpruntRenduRenduChevauche5() {
        JRegistre jRegistre = new JRegistre();
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant());
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 5);
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant() + 10);
        jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() + 15);
        jRegistre.emprunter(abonnes[0], velos.get(Velos.VM_NEUF), station.maintenant() + 20);
        int result = jRegistre.retourner(velos.get(Velos.VM_NEUF), station.maintenant() - 7);
        Assert.assertEquals(-3, result);
    }

    @Test
    public void testRetourWithEmpruntRenduEmpruntRenduRenduChevauche6() {
        JRegistre jRegistre = new JRegistre();
        Velo vm1 = velos.get(Velos.VM_NEUF);
        jRegistre.emprunter(abonnes[0], vm1, station.maintenant());
        jRegistre.retourner(vm1, station.maintenant() + 5);
        jRegistre.emprunter(abonnes[0], vm1, station.maintenant() + 10);
        int result = jRegistre.retourner(vm1, station.maintenant() + 20);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testRetourWithEmpruntRenduEmpruntRenduRenduChevauche7() {
        JRegistre jRegistre = new JRegistre();
        Velo vm = velos.get(Velos.VM_NEUF);
        jRegistre.emprunter(abonnes[0], vm, station.maintenant() + 10);
        jRegistre.retourner(vm, station.maintenant() + 15);
        jRegistre.emprunter(abonnes[0], vm, station.maintenant() + 3);
        int result = jRegistre.retourner(vm, station.maintenant() + 20);
        Assert.assertEquals(-3, result);
    }

    @Test
    public void testRetourWithEmpruntRenduEmpruntRenduRenduChevauche8() {
        JRegistre jRegistre = new JRegistre();
        Velo vm = velos.get(Velos.VM_NEUF);
        jRegistre.emprunter(abonnes[0], vm, station.maintenant() + 10);
        jRegistre.retourner(vm, station.maintenant() + 15);
        jRegistre.emprunter(abonnes[0], vm, station.maintenant() + 3);
        int result = jRegistre.retourner(vm, station.maintenant() + 14);
        Assert.assertEquals(-3, result);
    }

    @Test
    public void testplop() {
        JRegistre jRegistre = new JRegistre();
        Velo vm = velos.get(Velos.VM_NEUF);
        jRegistre.emprunter(abonnes[0], vm, station.maintenant() + 10);
        jRegistre.retourner(vm, station.maintenant() + 15);
        jRegistre.emprunter(abonnes[0], vm, station.maintenant() + 3);
        int result = jRegistre.retourner(vm, station.maintenant() + 8);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testFacturation() {
        JRegistre jRegistre = new JRegistre();
        Velo vm = velos.get(Velos.VM_NEUF);
        long maintenant = station.maintenant();
        long debutEmprunt = maintenant - 3600000;
        long finEmprunt = maintenant;
        jRegistre.emprunter(abonnes[0], vm, debutEmprunt);
        jRegistre.retourner(vm, finEmprunt);
        jRegistre.emprunter(abonnes[0], vm, maintenant + 1000);
        double result = jRegistre.facturation(abonnes[0], debutEmprunt - 1000, finEmprunt + 1000);
        Assert.assertEquals(2.0d, result, 0.001d);
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
