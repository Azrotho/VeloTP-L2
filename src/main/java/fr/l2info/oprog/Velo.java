package fr.l2info.oprog;

/**
 * Classe abstraite décrivant les vélos
 */
public abstract class Velo {

    private double kilometrage = 0.0d;
    private double kilometrageDerniereRevision = 0.0d;
    private double kilometrageRevision = 500.0d;
    private boolean estAbime = false;
    private boolean estDecroche = true;


    /**
     * Indique le nombre de kilomètres total qu'a déjà parcouru le vélo.
     * @return le kilométrage du vélo.
     */
    public double kilometrage() {
        return this.kilometrage;
    }


    /**
     * Indique le nombre de kilomètres qu'il reste au vélo avant la prochaine révision.
     * Une valeur négative ou nulle indiquera qu'il est temps d'effectuer la révision.
     * @return le nombre de kilomètres avant la prochaine révision.
     */
    public double prochaineRevision() {
        return ((kilometrageRevision + kilometrageDerniereRevision) - kilometrage);
    }

    /**
     * Fait parcourir au vélo en cours d'emprunt le nombre de kilomètres passé en paramètre.
     * Si le vélo n'est pas emprunté, cette méthode est sans effet.
     * @param km le nombre de kilomètres parcourus.
     */
    public void parcourir(double km) {
        if(estDecroche) {
            if(km < 0.0d) {
                km = 0.0d;
            }
            kilometrage += km;
        }
    }

    /**
     * Permet de décrocher le vélo de sa borne.
     * @return  0 si le vélo a effectivement pu être décroché,
     *          -1 si le vélo est déjà décroché.
     */
    public int decrocher() {
        if(estDecroche) {
            return -1;
        } else {
            estDecroche = true;
            return 0;
        }
    }

    /**
     * Permet de raccrocher le vélo à une borne.
     * @return  0 si le vélo a effectivement pu être accroché,
     *          -1 si le vélo est déjà accroché.
     */
    public int arrimer() {
        if(!estDecroche) {
            return -1;
        } else {
            estDecroche = false;
            return 0;
        }
    }

    /**
     * Abime le vélo, quelque soit son état d'origine.
     */
    public void abimer() {
        this.estAbime = true;
    }

    /**
     * Indique si le vélo est en bon état ou abimé.
     * @return true si le vélo est abimé, false sinon.
     */
    public boolean estAbime() {
        return this.estAbime;
    }

    /**
     * Permet de réviser le vélo lorsque celui-ci est décroché. Cette action a pour effet
     * de réinitialiser le décompte des kilomètres à parcourir avant la prochaine révision.
     * Si le vélo était abimé, la révision a pour effet de le réparer.
     * @return  0 si la révision a pu être effectuée,
     *          -1 sinon (le vélo est encore accroché).
     */
    public int reviser() {
        if(estDecroche) {
            this.kilometrageDerniereRevision = this.kilometrage;
            this.estAbime = false;
            return 0;
        }
        return -1;
    }

    /**
     * Permet de réparer un vélo. La réparation s'effectue sur un vélo abimé qui n'est pas accroché.
     * @return  0 si le vélo a pu être réparé,
     *          -1 si le vélo est accroché,
     *          -2 si le vélo est décroché, mais qu'il n'est pas abimé.è
     */
    public int reparer() {
        if(estDecroche) {
            if(estAbime) {
                return 0;
            } else {
                return -2;
            }
        }
        return -1;
    }

    /**
     * Tarif horaire pour le vélo.
     * @return Le tarif de location pour une heure de vélo (positif ou nul)
     */
    public abstract double tarif();

    /**
     * Génère une chaîne de caractères décrivant le vélo.
     * @return une chaîne décrivant le vélo.
     */
    public abstract String toString();

}
