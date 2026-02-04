package fr.l2info.oprog;

import java.util.HashMap;
import java.util.List;

/**
 * Classe représentant un abonné au service VéloCité.
 */
public class Abonne {

    private int id;
    private String nom;
    private String rib;
    private boolean isBloque = true;

    private static int compteur = 0;

    /**
     * Créé un abonné dont le nom est passé en paramètre, sans informations bancaires.
     *  Si le nom de l'abonné n'est pas correct (vide ou ne contenant pas des lettres éventuellementséparées par des espaces ou des traits d'union), le constructeur déclenchera l'exception IncorrectNameException.
     *  On notera que le nom peut contenir des espaces inutiles en début et en fin, mais ceux-ci seront retirés pour enregistrer cette donnée.
     * @param nom le nom du nouvel abonné.
     * @throws IncorrectNameException si le nom de l'abonné n'est pas correct.
     */
    public Abonne(String nom) throws IncorrectNameException {
        nom = nom.strip();
        if(!isAlpha(nom)) {
            throw new IncorrectNameException();
        }
        compteur = compteur + 1;
        this.nom = nom;
        this.id = compteur;
    }

    /**
     * Créé un abonné dont le nom est passé en paramètre, avec les informations bancaires spécifiées dans le second paramètre.
     *  Le comportement attendu est le même que celui du constructeur précédent. Le RIB n'est enregistré que si celui-ci est valide.
     * @param nom le nom du nouvel abonné.
     * @param rib le RIB
     * @throws IncorrectNameException si le nom de l'abonné n'est pas correct.
     */
    public Abonne(String nom, String rib) throws IncorrectNameException {
        nom = nom.strip();
        if(!isAlpha(nom)) {
            throw new IncorrectNameException();
        }
        this.nom = nom;
        compteur = compteur + 1;
        this.id = compteur;
        if(isRIBValid(rib)) {
            this.rib = rib;
            this.debloquer();
        }
    }

    /**
     * Renvoie l'identifiant de l'abonné, généré autoamtiquement à sa création.
     * @return l'identifiant de l'abonné.
     */
    public int getID() {
        return this.id;
    }

    /**
     * Renvoie le nom de l'abonné.
     * @return le nom de l'abonné, sans les éventuels espace en début et en fin de chaîne.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Met à jour l'ancien RIB pour un nouveau. Si le nouveau RIB n'est pas valide, l'abonné conserve ses anciennes coordonnées bancaires.
     * @param rib nouveau RIB pour la mise à jour.
     */
    public void miseAJourRIB(String rib) {
        // Vérifié que le rib est valide
        if(isRIBValid(rib)) {
            this.rib = rib;
            this.debloquer();
        }
    }

    /**
     * Permet de bloquer volontairement un abonné.
     */
    public void bloquer() {
        this.isBloque = true;
    }

    /**
     * Permet de débloquer un abonné.
     */
    public void debloquer() {
        this.isBloque = false;
    }

    /**
     * Vérifie si un abonné est bloqué. Celui-ci peut être bloqué volontairement ou parce que ses coordonnées bancaires sont invalides.
     * @return true si l'abonné est considéré comme bloqué, false sinon.
     */
    public boolean estBloque() {
        return isBloque || !(isRIBValid(this.rib));
    }

    /**
     * permet de tester si deux abonnés sont identiques. Pour cela, on vérifiera si leur identifiant est le même.
     * @param a l'abonné avec lequel est comparé l'instance courante.
     * @return true si les deux objets ont le même ID, false sinon.
     */
    public boolean equals(Object a) {
        if(a instanceof Abonne) {
            return this.id == ((Abonne) a).id;
        }
        return false;
    }

    /**
     * Utilisée en interne par Java pour obtenir un hash de l'objet. Cette méthode est utilisée pour les structures de collection de type HashSet ou HashMap.
     * @return le hash de l'instance courante.
     */
    public int hashCode() {
        return this.id;
    }

    // Source - https://stackoverflow.com/a/5238524
    // Posted by adarshr
    // Retrieved 2026-02-04, License - CC BY-SA 2.5
    private boolean isAlpha(String name) {
        return name.matches("[a-zA-Z- ]+");
    }

    private boolean isRIBValid(String rib) {
        if(rib == null) return false;
        if(rib.isEmpty()) return false;
//        HashMap<Character, Integer> charValueMap = new HashMap<>();
//        charValueMap.put('A', 1);
//        charValueMap.put('J', 1);
//        charValueMap.put('B', 2);
//        charValueMap.put('K', 2);
//        charValueMap.put('S', 2);
//        charValueMap.put('C', 3);
//        charValueMap.put('L', 3);
//        charValueMap.put('T', 3);
//        charValueMap.put('D', 4);
//        charValueMap.put('M', 4);
//        charValueMap.put('U', 4);
//        charValueMap.put('E', 5);
//        charValueMap.put('N', 5);
//        charValueMap.put('V', 5);
//        charValueMap.put('F', 6);
//        charValueMap.put('O', 6);
//        charValueMap.put('W', 6);
//        charValueMap.put('G', 7);
//        charValueMap.put('P', 7);
//        charValueMap.put('X', 7);
//        charValueMap.put('H', 8);
//        charValueMap.put('Q', 8);
//        charValueMap.put('Y', 8);
//        charValueMap.put('I', 9);
//        charValueMap.put('R', 9);
//        charValueMap.put('Z', 9);
//
//        // On va remplacer les lettres par des chiffres dans la string
//
//        for(int i = 0; i < rib.length(); i++) {
//            if(charValueMap.containsKey(rib.charAt(i))) {
//                String temp = String.valueOf(rib.charAt(i));
//                rib = rib.replace(temp, String.valueOf(charValueMap.get(rib.charAt(i))));
//            }
//        }


        String regex = "[-]";
        String[] ribArray = rib.split(regex);

        if(ribArray.length != 4) return false;
        if(ribArray[0].length() != 5) return false;
        if(ribArray[1].length() != 5) return false;
        if(ribArray[2].length() != 11) return false;
        if(ribArray[3].length() != 2) return false;


        long codeBanque      = Integer.parseInt(ribArray[0]);
        long codeGuichet     = Integer.parseInt(ribArray[1]);
        long numeroCompte    = Integer.parseInt(ribArray[2]);
        long controleKey     = Integer.parseInt(ribArray[3]);

        long check = 97 - ((89 * codeBanque + 15 * codeGuichet + 3 * numeroCompte) % 97);

        return check == controleKey;
    }


}
