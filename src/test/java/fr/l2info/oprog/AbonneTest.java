package fr.l2info.oprog;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test unitaire pour les abonnés.
 */
public class AbonneTest {

    @Before     // méthode exécutée avant chaque test
    public void setup() {
        System.out.println("Execution du setup");
    }

    @After     // méthode exécutée après chaque test
    public void teardown() {
        System.out.println("Execution du teardown");
    }

    @Test
    public void testNom() throws IncorrectNameException {
        // création d'un nouvel abonné
        Abonne a = new Abonne("Fred");
        // vérification de son nom
        Assert.assertEquals("Fred", a.getNom());
    }


}
