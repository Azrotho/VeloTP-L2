package fr.l2info.oprog;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;

/**
 * Cas de test pour une ville donnée.
 */
public class VilleTest {

    @Test
    public void testQuiEchoueADebugger() {
        Ville maPetiteVille = new Ville(new File("target/classes/data/5stations.csv"));
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
        Ville maPetiteVille = new Ville(new File("target/classes/data/5stations.csv"));
        maPetiteVille.acquerirVelos(50, 20);
        maPetiteVille.statistiques().forEach((stats, value) -> System.out.println(stats + " " + value));
    }

}
