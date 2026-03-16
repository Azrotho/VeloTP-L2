package fr.l2info.oprog;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class TestCorentin {
    Station s= Mockito.spy(new Station("Fred",4,1,10));
    IRegistre reg=Mockito.mock(IRegistre.class);
    Velo v=new VeloMusculaire();
    Abonne a=Mockito.mock(Abonne.class);

    public TestCorentin() throws IncorrectNameException {
    }

    public void testEmpruntAbonneNullOuBorneNegativeOuTropGrandeOuVideAvantDeReussir(){
        s.setRegistre(reg);
        Mockito.when(a.estBloque()).thenReturn(true);
        Assert.assertEquals(0,s.arrimerVelo(v,1));
        Assert.assertNull(s.emprunterVelo(null,1));
        Assert.assertNull(s.emprunterVelo(a,-1));
        Assert.assertNull(s.emprunterVelo(a,100));
        Assert.assertNull(s.emprunterVelo(a,5));
        Assert.assertNull(s.emprunterVelo(a,1));
        Mockito.when(a.estBloque()).thenReturn(false);
        Assert.assertEquals(v,s.emprunterVelo(a,1));
    }

    @Test
    public void testEmpruntVelo() {
        Mockito.when(a.estBloque()).thenReturn(true);
        s.arrimerVelo(v,1);
        Assert.assertNull(s.emprunterVelo(a,1));
        Assert.assertEquals(10,s.nbBornesLibres());
        s.setRegistre(reg);
        long dans10minutes=System.currentTimeMillis()+10*60*1000;
        Mockito.when(s.maintenant()).thenReturn(dans10minutes);

        Mockito.when(reg.emprunter(a,v,s.maintenant())).thenReturn(-1);//j'en peux plus je n'arrive pas à chopper les 3 lignes du code sortie différent, je vais espérer que c'est un bug de IntelliJ...
        Assert.assertNull(s.emprunterVelo(a,1));
        Assert.assertEquals(10,s.nbBornesLibres());
        Mockito.when(reg.emprunter(a,v,s.maintenant())).thenReturn(0);

        Assert.assertEquals(0,s.arrimerVelo(v,1));
        Assert.assertNull(s.emprunterVelo(a, 1));
    }

    @Test
    public void testArrimerVelo(){
        s.setRegistre(reg);
        Assert.assertEquals(0,s.arrimerVelo(v,1));
        Assert.assertEquals(v,s.veloALaBorne(1));
        Assert.assertEquals(-1,s.arrimerVelo(null,1));//abo null
        Assert.assertEquals(-1,s.arrimerVelo(v,-1));//borne negative
        Assert.assertEquals(-1,s.arrimerVelo(v,100));//borne trop grande
        Assert.assertEquals(-2,s.arrimerVelo(v,1));//deja arrimed
        Velo v2=new VeloElectrique();
        Assert.assertEquals(-2,s.arrimerVelo(v2,1));//deja prit
    }

    @Test
    public void testEmpruntRegistreBloqueArrimeVelo(){
        Mockito.when(a.estBloque()).thenReturn(true);
        s.setRegistre(reg);
        long dans10minutes=System.currentTimeMillis()+10*60*1000;
        Mockito.when(s.maintenant()).thenReturn(dans10minutes);
        Mockito.when(reg.retourner(v,s.maintenant())).thenReturn(-1);
        Assert.assertEquals(-4,s.arrimerVelo(v,1));
    }

    @Test
    public void testnbBornesLibres(){
        Assert.assertEquals(10,s.nbBornesLibres());
        s.setRegistre(reg);
        Assert.assertEquals(0,s.arrimerVelo(v,1));
        Assert.assertEquals(9,s.nbBornesLibres());
        Velo v2=new VeloElectrique();
        Assert.assertEquals(0,s.arrimerVelo(v2,2));
        Assert.assertEquals(8,s.nbBornesLibres());
    }

    @Test
    public void testVeloALaBorne(){
        Assert.assertNull(s.veloALaBorne(1));
        s.setRegistre(reg);
        Assert.assertEquals(0,s.arrimerVelo(v,1));
        Assert.assertEquals(v,s.veloALaBorne(1));
        Assert.assertNull(s.veloALaBorne(-1));
        Assert.assertNull(s.veloALaBorne(42));
    }

    @Test
    public void testVeloALaBorne2(){
        s.setRegistre(reg);
        Assert.assertEquals(0,s.arrimerVelo(v,1));
        Assert.assertNull(s.veloALaBorne(0));
        Assert.assertNull(s.veloALaBorne(11));
    }
    @Test
    public void Nom(){
        Assert.assertEquals("Fred",s.getNom());
    }
    @Test
    public void Capacite(){
        Assert.assertEquals(10,s.capacite());
    }
}
