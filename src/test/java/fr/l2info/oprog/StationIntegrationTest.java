package fr.l2info.oprog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StationIntegrationTest {

    Abonne[] abonnes = new Abonne[2];
    Map<Velos, Velo> velos = new HashMap<>();

    Station station;
    Station autreStation;

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

        station = new Station("Besancon Station", 47.2378, 6.0241, 4);
        autreStation = new Station("Dijon Station", 47.3220, 5.0415, 6);
    }

    @Test
    public void gettersStationTest() {
        Assert.assertEquals("Besancon Station", station.getNom());
        Assert.assertEquals(4, station.capacite());
        Assert.assertEquals(4, station.nbBornesLibres());
        Assert.assertNull(station.veloALaBorne(1));
        Assert.assertNull(station.veloALaBorne(5));
        Assert.assertNull(station.veloALaBorne(0));
    }

    @Test
    public void distanceStationTest() {
        double distance = station.distance(autreStation);
        Assert.assertTrue(distance > 70.0 && distance < 80.0);
    }

    @Test
    public void arrimerVeloSansRegistreTest() {
        Velo vm = velos.get(Velos.VM_NEUF);
        vm.decrocher();
        int result = station.arrimerVelo(vm, 1);
        Assert.assertEquals(-2, result);
    }

    @Test
    public void arrimerVeloInvalideTest() {
        JRegistre registre = new JRegistre();
        station.setRegistre(registre);

        Velo vm = velos.get(Velos.VM_NEUF);
        vm.decrocher();

        Assert.assertEquals(-1, station.arrimerVelo(null, 1));
        Assert.assertEquals(-1, station.arrimerVelo(vm, 0));
        Assert.assertEquals(-1, station.arrimerVelo(vm, 5));
    }

    @Test
    public void arrimerVeloEchecAccrochageTest() {
        JRegistre registre = new JRegistre();
        station.setRegistre(registre);

        Velo vm = velos.get(Velos.VM_NEUF);
        vm.decrocher();
        station.arrimerVelo(vm, 1);

        int result = station.arrimerVelo(vm, 2);
        Assert.assertEquals(-3, result);
    }

    @Test
    public void arrimerVeloEchecRegistreTest() {
        JRegistre registre = new JRegistre();
        station.setRegistre(registre);

        Velo vm = velos.get(Velos.VM_NEUF);
        vm.decrocher();

        int result = station.arrimerVelo(vm, 1);
        Assert.assertEquals(-4, result);
        Assert.assertEquals(vm, station.veloALaBorne(1));
        Assert.assertEquals(3, station.nbBornesLibres());
    }

    @Test
    public void arrimerVeloBorneOccupeeTest() {
        JRegistre registre = new JRegistre();
        station.setRegistre(registre);

        Velo vm = velos.get(Velos.VM_NEUF);
        Velo vm2 = velos.get(Velos.VM_NEUF_A);
        vm.decrocher();
        vm2.decrocher();

        station.arrimerVelo(vm, 1);

        int result2 = station.arrimerVelo(vm2, 1);
        Assert.assertEquals(-2, result2);
    }

    @Test
    public void emprunterVeloStationTest() {
        JRegistre registre = new JRegistre();
        station.setRegistre(registre);

        Velo vm = velos.get(Velos.VM_NEUF);
        vm.decrocher();
        station.arrimerVelo(vm, 1);

        Velo emprunte = station.emprunterVelo(abonnes[0], 1);
        Assert.assertNotNull(emprunte);
        Assert.assertEquals(vm, emprunte);
        Assert.assertNull(station.veloALaBorne(1));
        Assert.assertEquals(4, station.nbBornesLibres());
    }

    @Test
    public void emprunterVeloSansRegistreTest() {
        Velo vm = velos.get(Velos.VM_NEUF);
        station.setRegistre(new JRegistre());
        vm.decrocher();
        station.arrimerVelo(vm, 1);
        station.setRegistre(null);

        Velo emprunte = station.emprunterVelo(abonnes[0], 1);
        Assert.assertNull(emprunte);
    }

    @Test
    public void emprunterVeloStationAbonneBloquerTest() {
        JRegistre registre = new JRegistre();
        station.setRegistre(registre);

        Velo vm = velos.get(Velos.VM_NEUF);
        vm.decrocher();
        station.arrimerVelo(vm, 1);

        Velo emprunte = station.emprunterVelo(abonnes[1], 1);
        Assert.assertNull(emprunte);
    }

    @Test
    public void emprunterVeloBorneInvalideOuVideTest() {
        JRegistre registre = new JRegistre();
        station.setRegistre(registre);

        Assert.assertNull(station.emprunterVelo(abonnes[0], 0));
        Assert.assertNull(station.emprunterVelo(abonnes[0], 5));
        Assert.assertNull(station.emprunterVelo(abonnes[0], 1));
    }

    @Test
    public void emprunterVeloEchecDecrochageTest() {
        JRegistre registre = new JRegistre();
        station.setRegistre(registre);

        Velo vm = velos.get(Velos.VM_NEUF);
        vm.decrocher();
        station.arrimerVelo(vm, 1);

        vm.decrocher();

        Velo emprunte = station.emprunterVelo(abonnes[0], 1);
        Assert.assertNull(emprunte);
    }

    @Test
    public void emprunterVeloDejaEmprunteTest() {
        JRegistre registre = new JRegistre();
        station.setRegistre(registre);

        Velo vm1 = velos.get(Velos.VM_NEUF);
        Velo vm2 = velos.get(Velos.VE);
        vm1.decrocher();
        vm2.decrocher();
        station.arrimerVelo(vm1, 1);
        station.arrimerVelo(vm2, 2);

        Velo emprunte1 = station.emprunterVelo(abonnes[0], 1);
        Assert.assertNotNull(emprunte1);

        Velo emprunte2 = station.emprunterVelo(abonnes[0], 2);
        Assert.assertNull(emprunte2);
        Assert.assertEquals(vm2, station.veloALaBorne(2));
    }

    @Test
    public void equilibrerMoitieStationTest() {
        JRegistre registre = new JRegistre();
        station.setRegistre(registre);

        Set<Velo> reserve = new HashSet<>();
        reserve.add(velos.get(Velos.VM_NEUF));
        reserve.add(velos.get(Velos.VE));
        reserve.add(velos.get(Velos.VM_NEUF_A));

        station.equilibrer(reserve);

        Assert.assertEquals(2, station.capacite() - station.nbBornesLibres());
        Assert.assertEquals(1, reserve.size());
    }

    @Test
    public void equilibrerRetraitVeloHSSTest() {
        JRegistre registre = new JRegistre();
        station.setRegistre(registre);

        Velo abime = velos.get(Velos.VM_ABIMER);
        Velo aReviser = velos.get(Velos.VM_REVISION);
        abime.decrocher();
        aReviser.decrocher();
        station.arrimerVelo(abime, 1);
        station.arrimerVelo(aReviser, 2);

        Set<Velo> reserve = new HashSet<>();
        reserve.add(velos.get(Velos.VM_NEUF));
        reserve.add(velos.get(Velos.VE));

        station.equilibrer(reserve);

        Assert.assertEquals(2, station.capacite() - station.nbBornesLibres());
        Assert.assertTrue(reserve.contains(abime));
        Assert.assertTrue(reserve.contains(aReviser));
        Assert.assertFalse(reserve.contains(velos.get(Velos.VM_NEUF)));
        Assert.assertFalse(reserve.contains(velos.get(Velos.VE)));
    }

    @Test
    public void equilibrerPasAssezStockTest() {
        JRegistre registre = new JRegistre();
        station.setRegistre(registre);

        Set<Velo> reserve = new HashSet<>();
        reserve.add(velos.get(Velos.VM_NEUF));

        station.equilibrer(reserve);

        Assert.assertEquals(1, station.capacite() - station.nbBornesLibres());
        Assert.assertEquals(0, reserve.size());
    }

    @Test
    public void equilibrerReintegrerRevisionTest() {
        JRegistre registre = new JRegistre();
        station.setRegistre(registre);

        Set<Velo> reserve = new HashSet<>();
        reserve.add(velos.get(Velos.VM_REVISION));

        station.equilibrer(reserve);

        Assert.assertEquals(1, station.capacite() - station.nbBornesLibres());
        Assert.assertEquals(0, reserve.size());
    }

    @Test
    public void equilibrerFinesseFilterTest() {
        JRegistre registre = new JRegistre();
        station.setRegistre(registre);

        Set<Velo> reserve = new HashSet<>();
        reserve.add(null);
        reserve.add(velos.get(Velos.VM_ABIMER));
        reserve.add(velos.get(Velos.VM_REVISION));
        reserve.add(velos.get(Velos.VM_NEUF));
        station.equilibrer(reserve);

        Assert.assertEquals(2, station.capacite() - station.nbBornesLibres());
        Assert.assertTrue(reserve.contains(null));
        Assert.assertTrue(reserve.contains(velos.get(Velos.VM_ABIMER)));
        Assert.assertFalse(reserve.contains(velos.get(Velos.VM_REVISION)));
        Assert.assertFalse(reserve.contains(velos.get(Velos.VM_NEUF)));
    }

    @Test
    public void equilibrerRetraitSurplusTest() {
        JRegistre registre = new JRegistre();
        autreStation.setRegistre(registre);

        Velo v1 = velos.get(Velos.VM_NEUF);
        Velo v2 = velos.get(Velos.VE);
        Velo v3 = velos.get(Velos.VM_NEUF_A);
        Velo v4 = velos.get(Velos.VE_NEUF_A);

        v1.decrocher();
        v2.decrocher();
        v3.decrocher();
        v4.decrocher();
        autreStation.arrimerVelo(v1, 1);
        autreStation.arrimerVelo(v2, 2);
        autreStation.arrimerVelo(v3, 3);
        autreStation.arrimerVelo(v4, 4);

        Set<Velo> reserve = new HashSet<>();

        autreStation.equilibrer(reserve);

        Assert.assertEquals(3, autreStation.capacite() - autreStation.nbBornesLibres());
        Assert.assertEquals(1, reserve.size());
    }

    @Test
    public void veloALaBorneInvalidTest() {
        Assert.assertNull(station.veloALaBorne(0));
        Assert.assertNull(station.veloALaBorne(station.capacite() + 1));
    }

    @Test
    public void arrimerVeloNullTest() {
        Assert.assertEquals(-1, station.arrimerVelo(null, 1));
    }

    @Test
    public void arrimerVeloInvalidBorneTest() {
        Velo v = velos.get(Velos.VM_NEUF);
        v.decrocher();
        Assert.assertEquals(-1, station.arrimerVelo(v, 0));
        Assert.assertEquals(-1, station.arrimerVelo(v, station.capacite() + 1));
    }

    @Test
    public void arrimerVeloNullRegistreTest() {
        station.setRegistre(null);
        Velo v = velos.get(Velos.VM_NEUF);
        v.decrocher();
        Assert.assertEquals(-2, station.arrimerVelo(v, 1));
    }

    @Test
    public void emprunterVeloNullAbonneTest() {
        station.setRegistre(new JRegistre());
        Velo v = velos.get(Velos.VM_NEUF);
        v.decrocher();
        station.arrimerVelo(v, 1);
        Assert.assertNull(station.emprunterVelo(null, 1));
    }

    @Test
    public void emprunterVeloInvalidBorneTest() {
        station.setRegistre(new JRegistre());
        Assert.assertNull(station.emprunterVelo(abonnes[0], 0));
        Assert.assertNull(station.emprunterVelo(abonnes[0], station.capacite() + 1));
    }

    @Test
    public void emprunterVeloNullRegistreTest() {
        station.setRegistre(null);
        Assert.assertNull(station.emprunterVelo(abonnes[0], 1));
    }

    @Test
    public void equilibrerNullSetTest() {
        station.equilibrer(null);
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
