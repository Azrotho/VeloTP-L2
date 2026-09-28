package fr.l2info.oprog;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VeloTest {

    public static final double DELTA = 0.001d;
    @Test
    public void testVeloMusculaire() {
        VeloMusculaire vm = new VeloMusculaire();
    }

    @Test
    public void testVeloElectrique() {
        VeloElectrique ve = new VeloElectrique();
    }

    @Test
    public void testVeloMusculaireParcourNonEmprunte() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.arrimer();
        vm.parcourir(5.0d);
        Assert.assertEquals(0d, vm.kilometrage(), DELTA);
    }

    @Test
    public void testVeloElectriqueParcourNonEmprunte() {
        VeloElectrique ve = new VeloElectrique();
        ve.arrimer();
        ve.parcourir(5.0d);
        Assert.assertEquals(0d, ve.kilometrage(), DELTA);
    }

    @Test
    public void testVeloElectriqueParcourEmprunte() {
        VeloElectrique ve = new VeloElectrique();
        ve.decrocher();
        ve.parcourir(5.0d);
        Assert.assertEquals(5.0d, ve.kilometrage(), DELTA);
    }

    @Test
    public void testVeloMusculaireParcourEmprunte() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.decrocher();
        vm.parcourir(5.0d);
        Assert.assertEquals(5.0d, vm.kilometrage(), DELTA);
    }

    @Test
    public void testVeloElectriqueParcourEmprunte2() {
        VeloElectrique ve = new VeloElectrique();
        ve.decrocher();
        ve.parcourir(-5.0d);
        Assert.assertEquals(0.0d, ve.kilometrage(), DELTA);
    }

    @Test
    public void testVeloMusculaireParcourEmprunte2() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.decrocher();
        vm.parcourir(-5.0d);
        Assert.assertEquals(0.0d, vm.kilometrage(), DELTA);
    }

    @Test
    public void testVeloMusculaireParcourNextRevision() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.decrocher();
        vm.parcourir(5.0d);
        Assert.assertEquals(500d - 5.0d, vm.prochaineRevision(), DELTA);
    }

    @Test
    public void testVeloElectricParcourNextRevision() {
        VeloElectrique ve = new VeloElectrique();
        ve.decrocher();
        ve.parcourir(5.0d);
        Assert.assertEquals(500d - 5.0d, ve.prochaineRevision(), DELTA);
    }

    @Test
    public void testVeloElectricParcourNextRevision2() {
        VeloElectrique ve = new VeloElectrique();
        ve.decrocher();
        ve.parcourir(505.0d);
        Assert.assertTrue((ve.prochaineRevision() <= 0));
    }

    @Test
    public void testVeloMusculaireParcourNextRevision2() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.decrocher();
        vm.parcourir(505.0d);
        Assert.assertTrue((vm.prochaineRevision() <= 0));
    }

    @Test
    public void testVeloMusculaireDecroche() {
        VeloMusculaire vm = new VeloMusculaire();
        int decrocher = vm.decrocher();
        Assert.assertEquals(-1, decrocher);
    }

    @Test
    public void testVeloElectriqueDecroche() {
        VeloElectrique ve = new VeloElectrique();
        int decrocher = ve.decrocher();
        Assert.assertEquals(-1, decrocher);
    }

    @Test
    public void testVeloMusculaireDecrocheDecroche() {
        VeloMusculaire vm = new VeloMusculaire();
        int decrocher = vm.decrocher();
        decrocher = vm.decrocher();
        Assert.assertEquals(-1, decrocher);
    }

    @Test
    public void testVeloElectriqueDecrocheDecroche() {
        VeloElectrique ve = new VeloElectrique();
        int decrocher = ve.decrocher();
        decrocher = ve.decrocher();
        Assert.assertEquals(-1, decrocher);
    }

    @Test
    public void testVeloMusculaireArimer() {
        VeloMusculaire vm = new VeloMusculaire();
        int decrocher = vm.arrimer();
        Assert.assertEquals(0, decrocher);
    }

    @Test
    public void testVeloElectriqueArimer() {
        VeloElectrique ve = new VeloElectrique();
        int decrocher = ve.arrimer();
        Assert.assertEquals(0, decrocher);
    }

    @Test
    public void testVeloMusculaireDecrocheArimer() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.decrocher();
        int decrocher = vm.arrimer();
        Assert.assertEquals(0, decrocher);
    }

    @Test
    public void testVeloElectriqueDecrocheArimer() {
        VeloElectrique ve = new VeloElectrique();
        ve.decrocher();
        int decrocher = ve.arrimer();
        Assert.assertEquals(0, decrocher);
    }

    @Test
    public void testVeloMusculaireArimerArimer() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.decrocher();
        int decrocher = vm.arrimer();
        decrocher = vm.arrimer();
        Assert.assertEquals(-1, decrocher);
    }

    @Test
    public void testVeloElectriqueArimerArimer() {
        VeloElectrique ve = new VeloElectrique();
        ve.decrocher();
        int decrocher = ve.arrimer();
        decrocher = ve.arrimer();
        Assert.assertEquals(-1, decrocher);
    }

    @Test
    public void testVeloMusculaireNeuf() {
        VeloMusculaire vm = new VeloMusculaire();
        Assert.assertFalse(vm.estAbime());
    }

    @Test
    public void testVeloElectriqueNeuf() {
        VeloElectrique ve = new VeloElectrique();
        Assert.assertFalse(ve.estAbime());
    }

    @Test
    public void testVeloMusculaireAbime() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.abimer();
        Assert.assertTrue(vm.estAbime());
    }

    @Test
    public void testVeloElectriqueAbime() {
        VeloElectrique ve = new VeloElectrique();
        ve.abimer();
        Assert.assertTrue(ve.estAbime());
    }

    @Test
    public void testVeloElectriqueReviserNeuf() {
        VeloElectrique ve = new VeloElectrique();
        ve.arrimer();
        int result = ve.reviser();
        Assert.assertEquals(-1, result);
    }

    @Test
    public void testVeloMusculaireReviserNeuf() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.arrimer();
        int result = vm.reviser();
        Assert.assertEquals(-1, result);
    }

    @Test
    public void testVeloElectriqueReviserNeufDecrocher() {
        VeloElectrique ve = new VeloElectrique();
        ve.decrocher();
        int result = ve.reviser();
        Assert.assertEquals(0, result);
    }

    @Test
    public void testVeloMusculaireReviserNeufDecrocher() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.decrocher();
        int result = vm.reviser();
        Assert.assertEquals(0, result);
    }

    @Test
    public void testVeloElectriqueReviser() {
        VeloElectrique ve = new VeloElectrique();
        ve.parcourir(666.0d);
        ve.arrimer();
        int result = ve.reviser();
        Assert.assertTrue((ve.prochaineRevision() <= 0));
    }

    @Test
    public void testVeloMusculaireReviser() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.parcourir(666.0d);
        vm.arrimer();
        int result = vm.reviser();
        Assert.assertTrue((vm.prochaineRevision() <= 0));
    }

    @Test
    public void testVeloElectriqueReviser2() {
        VeloElectrique ve = new VeloElectrique();
        ve.abimer();
        ve.parcourir(666.0d);
        ve.arrimer();
        int result = ve.reviser();
        Assert.assertTrue(ve.estAbime());
    }

    @Test
    public void testVeloMusculaireReviser2() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.parcourir(666.0d);
        vm.abimer();
        vm.arrimer();
        int result = vm.reviser();
        Assert.assertTrue(vm.estAbime());
    }

    @Test
    public void testVeloElectriqueDecrocherReviser() {
        VeloElectrique ve = new VeloElectrique();
        ve.decrocher();
        ve.parcourir(666.0d);
        int result = ve.reviser();
        Assert.assertFalse((ve.prochaineRevision() <= 0));
    }

    @Test
    public void testVeloMusculaireDecrocherReviser() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.decrocher();
        vm.parcourir(666.0d);
        int result = vm.reviser();
        Assert.assertFalse((vm.prochaineRevision() <= 0));
    }

    @Test
    public void testVeloElectriqueDecrocherReviser2() {
        VeloElectrique ve = new VeloElectrique();
        ve.decrocher();
        ve.abimer();
        ve.parcourir(666.0d);
        int result = ve.reviser();
        Assert.assertFalse((ve.prochaineRevision() <= 0));
    }

    @Test
    public void testVeloMusculaireDecrocherReviser2() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.decrocher();
        vm.abimer();
        vm.parcourir(666.0d);
        int result = vm.reviser();
        Assert.assertFalse((vm.prochaineRevision() <= 0));
    }

    @Test
    public void testVeloMusculaireReparer() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.arrimer();
        int result = vm.reparer();
        Assert.assertEquals(-1, result);
    }

    @Test
    public void testVeloElectriqueReparer() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.arrimer();
        int result = vm.reparer();
        Assert.assertEquals(-1, result);
    }

    @Test
    public void testVeloMusculaireNeufDecrocherReparer() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.arrimer();
        vm.decrocher();
        int result = vm.reparer();
        Assert.assertEquals(-2, result);
    }

    @Test
    public void testVeloElectriqueNeufDecrocherReparer() {
        VeloElectrique ve = new VeloElectrique();
        ve.arrimer();
        ve.decrocher();
        int result = ve.reparer();
        Assert.assertEquals(-2, result);
    }

    @Test
    public void testVeloMusculaireAbimeDecrocherReparer() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.arrimer();
        vm.decrocher();
        vm.abimer();
        int result = vm.reparer();
        Assert.assertEquals(0, result);
    }

    @Test
    public void testVeloElectriqueAbimeDecrocherReparer() {
        VeloElectrique ve = new VeloElectrique();
        ve.arrimer();
        ve.decrocher();
        ve.abimer();
        int result = ve.reparer();
        Assert.assertEquals(0, result);
    }

    @Test
    public void testTarifMusculaire() {
        VeloMusculaire vm = new VeloMusculaire();
        double tarif = vm.tarif();
        Assert.assertEquals(2.0d, vm.tarif(), DELTA);
    }

    @Test
    public void testTarifEletrique() {
        VeloElectrique ve = new VeloElectrique();
        double tarif = ve.tarif();
        Assert.assertEquals(3.0d, ve.tarif(), DELTA);
    }

    @Test
    public void testStringMusculaire() {
        VeloMusculaire vm = new VeloMusculaire();
        Assert.assertTrue((vm.toString().contains("musculaire")));
    }

    @Test
    public void testStringEletrique() {
        VeloElectrique ve = new VeloElectrique();
        Assert.assertTrue((ve.toString().contains("électrique")));
    }

    @Test
    public void testStringMusculaire2() {
        VeloMusculaire vm = new VeloMusculaire();
        vm.parcourir(600660560.0d);
        Assert.assertTrue((vm.toString().contains("révision")));
    }

    @Test
    public void testStringEletrique2() {
        VeloElectrique ve = new VeloElectrique();
        ve.parcourir(600660560.0d);
        Assert.assertTrue((ve.toString().contains("révision")));
    }





}
