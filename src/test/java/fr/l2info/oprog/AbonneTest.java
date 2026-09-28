package fr.l2info.oprog;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test unitaire pour les abonnés.
 */
public class AbonneTest {

    @Test
    public void testNom() throws IncorrectNameException {
        // création d'un nouvel abonné
        Abonne a = new Abonne("Fred");
        // vérification de son nom
        Assert.assertEquals("Fred", a.getNom());
    }

    @Test(expected = IncorrectNameException.class)
    public void testNomInvalide() throws IncorrectNameException {
        Abonne a = new Abonne("Xx_DarkSasuke666_xX");
    }

    @Test
    public void testNomValideTiret() throws IncorrectNameException {
        Abonne a = new Abonne("Jean-Louis");
    }

    @Test
    public void testNomValideEspace() throws IncorrectNameException {
        Abonne a = new Abonne("Jean Louis");
    }

    @Test
    public void testNomValideEspaceTiret() throws IncorrectNameException {
        Abonne a = new Abonne("Jean-Louis Michel");
    }

    @Test(expected = IncorrectNameException.class)
    public void testNomNull() throws IncorrectNameException {
        Abonne a = new Abonne(null);
    }

    @Test(expected = IncorrectNameException.class)
    public void testNomVide() throws IncorrectNameException {
        Abonne a = new Abonne("");
    }

    @Test
    public void testNomWithRibCheckName() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", "12345-12345-01234567890-06"); // Rib Valide
        Assert.assertEquals("Fred", a.getNom());
    }

    @Test(expected = IncorrectNameException.class)
    public void testNomWithRibCheckNameInvalid() throws IncorrectNameException {
        Abonne a = new Abonne("Xx_DarkSasuke666_xX", "12345-12345-01234567890-06"); // Rib Valide
    }

    @Test
    public void testNomWithRibCheckBlock() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", "12345-12345-01234567890-06"); // Rib Valide
        Assert.assertFalse(a.estBloque());
    }

    @Test
    public void testNomWithInvalidRibCheckBlock() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", "12345-12345-01234657890-06"); // Rib Invalide
        Assert.assertTrue(a.estBloque());
    }

    @Test
    public void testNomWithInvalidRibCheckBlock2() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", "12345-1235-01234657890-06"); // Rib Invalide
        Assert.assertTrue(a.estBloque());
    }

    @Test
    public void testNomWithInvalidRibCheckBlock3() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", "12345-12345-012657890-06"); // Rib Invalide
        Assert.assertTrue(a.estBloque());
    }

    @Test
    public void testNomWithInvalidRibCheckBlock4() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", "12345-12345-01234657890-0645"); // Rib Invalide
        Assert.assertTrue(a.estBloque());
    }

    @Test
    public void testNomWithWeirdRibCheckBlock() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", "12-12-12-74"); // Rib invalide dans la forme mais la vérification de clé passe
        Assert.assertTrue(a.estBloque());
    }

    @Test
    public void testNomWithNull() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", null);
        Assert.assertTrue(a.estBloque());
    }

    @Test
    public void testNomWithEmpty() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", "");
        Assert.assertTrue(a.estBloque());
    }

    @Test
    public void testNomWithTooLong() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", "12-12-12-12-12-12");
        Assert.assertTrue(a.estBloque());
    }

    @Test
    public void testNomWithEspace1() throws IncorrectNameException {
        // création d'un nouvel abonné
        Abonne a = new Abonne("Fred    ");
        // vérification de son nom
        Assert.assertEquals("Fred", a.getNom());
    }

    @Test
    public void testNomWithEspace2() throws IncorrectNameException {
        // création d'un nouvel abonné
        Abonne a = new Abonne("             Fred");
        // vérification de son nom
        Assert.assertEquals("Fred", a.getNom());
    }


    @Test
    public void testNomWithEspace3() throws IncorrectNameException {
        // création d'un nouvel abonné
        Abonne a = new Abonne("             Fred          ");
        // vérification de son nom
        Assert.assertEquals("Fred", a.getNom());
    }

    @Test
    public void testEqual() throws IncorrectNameException {
        // création d'un nouvel abonné
        Abonne a = new Abonne("Fred");
        Abonne b = new Abonne("Jean");
        Assert.assertNotEquals(a, b);
    }

    @Test
    public void testEqualsDifferentId() throws IncorrectNameException {
        Abonne a = new Abonne("Fred");
        Abonne b = new Abonne("Fred"); // Aura un ID différent (compteur + 1)

        Assert.assertNotEquals(a, b);
    }

    @Test
    public void testEqualsDifferentIdHash() throws IncorrectNameException {
        Abonne a = new Abonne("Fred");
        Abonne b = new Abonne("Fred"); // Aura un ID différent (compteur + 1)

        Assert.assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testEqual2() throws IncorrectNameException {
        // création d'un nouvel abonné
        Abonne a = new Abonne("Fred");
        Assert.assertEquals(a,a);
    }

    @Test
    public void testEqual3() throws IncorrectNameException {
        // création d'un nouvel abonné
        Abonne a = new Abonne("Fred");
        Assert.assertNotEquals(12, a);
    }

    @Test
    public void testEqual4() throws IncorrectNameException {
        // création d'un nouvel abonné
        Abonne a = new Abonne("Fred");
        Assert.assertFalse(a.equals(12));
    }

    @Test
    public void testEqualNull() throws IncorrectNameException {
        Abonne a = new Abonne("Fred");
        Assert.assertNotEquals(null, a); // 'a instanceof Abonne' sera faux si a est null
    }

    @Test
    public void testID() throws IncorrectNameException {
        Abonne a = new Abonne("Fred");
        Assert.assertTrue(a.getID() > 0);
    }

    @Test
    public void testDebloqueInvalide() throws IncorrectNameException {
        Abonne a = new Abonne("Fred");
        a.debloquer();
        Assert.assertTrue(a.estBloque());
    }

    @Test
    public void testDebloque() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", "12345-12345-01234567890-06"); // Valide
        a.debloquer();
        Assert.assertFalse(a.estBloque());
    }

    @Test
    public void testBlockDebloqueValid() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", "12345-12345-01234567890-06"); // Valide
        a.bloquer();
        a.debloquer();
        Assert.assertFalse(a.estBloque());
    }

    @Test
    public void testBlockValid() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", "12345-12345-01234567890-06"); // Valide
        a.bloquer();
        Assert.assertTrue(a.estBloque());
    }

    @Test
    public void testMiseAJourInvalideSujetValide() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", "12345-12345-01234567890-06"); // Valide
        a.miseAJourRIB("12345-12345-01234567890-07"); // Invalide
        Assert.assertFalse(a.estBloque()); // Normalement reste débloqué mais ne mettra pas à jour
    }

    @Test
    public void testMiseAJourInvalideSujetInvalide() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", "12345-12345-01234567890-07"); // Invalide
        a.miseAJourRIB("12345-12345-01234567890-07"); // Invalide
        Assert.assertTrue(a.estBloque()); // Normalement reste débloqué mais ne mettra pas à jour
    }

    @Test
    public void testMiseAJourValideSujetInvalide() throws IncorrectNameException {
        Abonne a = new Abonne("Fred", "12345-12345-01234567890-07"); // Invalide
        a.miseAJourRIB("12345-12345-01234567890-06"); // Valide
        Assert.assertFalse(a.estBloque()); // Normalement reste débloqué mais ne mettra pas à jour
    }


}
