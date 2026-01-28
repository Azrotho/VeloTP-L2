package fr.l2info.oprog;

/**
 * Classe représentant un abonné au service VéloCité.
 */
public class Abonne {

    /**
     * Créé un abonné dont le nom est passé en paramètre, sans informations bancaires.
     *  Si le nom de l'abonné n'est pas correct (vide ou ne contenant pas des lettres éventuellementséparées par des espaces ou des traits d'union), le constructeur déclenchera l'exception IncorrectNameException.
     *  On notera que le nom peut contenir des espaces inutiles en début et en fin, mais ceux-ci seront retirés pour enregistrer cette donnée.
     * @param nom le nom du nouvel abonné.
     * @throws IncorrectNameException si le nom de l'abonné n'est pas correct.
     */
    public Abonne(String nom) throws IncorrectNameException {

    }

    /**
     * Créé un abonné dont le nom est passé en paramètre, avec les informations bancaires spécifiées dans le second paramètre.
     *  Le comportement attendu est le même que celui du constructeur précédent. Le RIB n'est enregistré que si celui-ci est valide.
     * @param nom le nom du nouvel abonné.
     * @param rib le RIB
     * @throws IncorrectNameException si le nom de l'abonné n'est pas correct.
     */
    public Abonne(String nom, String rib) throws IncorrectNameException {

    }

    /**
     * Renvoie l'identifiant de l'abonné, généré autoamtiquement à sa création.
     * @return l'identifiant de l'abonné.
     */
    public int getID() {
        return 0;
    }

    /**
     * Renvoie le nom de l'abonné.
     * @return le nom de l'abonné, sans les éventuels espace en début et en fin de chaîne.
     */
    public String getNom() {
        return "Fred";
    }

    /**
     * Met à jour l'ancien RIB pour un nouveau. Si le nouveau RIB n'est pas valide, l'abonné conserve ses anciennes coordonnées bancaires.
     * @param rib nouveau RIB pour la mise à jour.
     */
    public void miseAJourRIB(String rib) {

    }

    /**
     * Permet de bloquer volontairement un abonné.
     */
    public void bloquer() {

    }

    /**
     * Permet de débloquer un abonné.
     */
    public void debloquer() {

    }

    /**
     * Vérifie si un abonné est bloqué. Celui-ci peut être bloqué volontairement ou parce que ses coordonnées bancaires sont invalides.
     * @return true si l'abonné est considéré comme bloqué, false sinon.
     */
    public boolean estBloque() {
        return false;
    }

    /**
     * permet de tester si deux abonnés sont identiques. Pour cela, on vérifiera si leur identifiant est le même.
     * @param a l'abonné avec lequel est comparé l'instance courante.
     * @return true si les deux objets ont le même ID, false sinon.
     */
    public boolean equals(Object a) {
        return false;
    }

    /**
     * Utilisée en interne par Java pour obtenir un hash de l'objet. Cette méthode est utilisée pour les structures de collection de type HashSet ou HashMap.
     * @return le hash de l'instance courante.
     */
    public int hashCode() {
        return 0;
    }

}
