package fr.l2info.oprog;

import java.io.File;
import java.io.IOException;
import java.time.chrono.JapaneseEra;
import java.util.*;

/**
 * Entité représentant une ville.
 */
public class Ville {

    // Liste des vélos dans toute la ville
    List<Velo> velosStock = new ArrayList<>();

    List<Abonne> abonnes = new ArrayList<>();

    JRegistre jRegistre = new JRegistre();

    /** Stations présentes dans la ville */
    private Station[] stations = new Station[0];

    /**
     * Constructeur d'une ville à partir d'un ensemble de stations.
     * @param f un fichier CSV de description de stations.
     */
    public Ville(File f) {
        try {
            StationReader sr = new StationReader(f);
            stations = sr.getStations();
            Arrays.stream(stations).forEach(station -> station.setRegistre(jRegistre));
        }
        catch (IOException e) {
            // ignore erreur
        }
        catch (FileFormatException e) {
            // ignore erreur
        }
    }

    /**
     * Création d'un abonné et ajout à l'ensemble des abonnés de la ville.
     * @param nom le nom de l'abonné.
     * @param RIB le RIB de l'abonné.
     * @return l'abonné ainsi créé.
     */
    public Abonne creerAbonne(String nom, String RIB) {
        try {
            Abonne abonne = new Abonne(nom, RIB);
            if(abonne.estBloque()) {
                return null;
            }
            abonnes.add(abonne);
            return abonne;
        } catch (IncorrectNameException e) {
            return null;
        }
    }

    /**
     * Réalise l'acquisition de vélos dont les quantités sont données en paramètre.
     * Ces vélos sont ajoutés au stock de la ville.
     * @param nbVM nombre de vélos musculaires
     * @param nbVE nombre de vélos électriques
     */
    public void acquerirVelos(int nbVM, int nbVE) {
        for(int i = 0; i < nbVM; i++) {
            velosStock.add(new VeloMusculaire());
        }
        for(int i = 0; i < nbVE; i++) {
            velosStock.add(new VeloElectrique());
        }
    }

    /**
     * Révise et révise les vélos qui sont dans le stock.
     */
    public void entretenirVelos() {
        velosStock.stream().filter(Velo::estAbime).forEach(Velo::reparer);
        velosStock.stream().filter(velo -> velo.prochaineRevision() <= 0.0d).forEach(Velo::reviser);
    }

    /**
     * Récupère la station dont le nom est demandé.
     * @param nom le nom de la station à récupérer
     * @return l'objet Station correspondant, null si aucune station avec ce nom n'existe.
     */
    public Station station(String nom) {
        return Arrays
                .stream(stations)
                .filter(station -> station.getNom().equals(nom))
                .findFirst()
                .orElse(null);
    }

    /**
     * Réalise une tournée de ré-équilibrage des stations au départ de la première station de la ville.
     * La tournée réalise un circuit qui réalise le plus court chemin. Le camion réalisant le parcours
     * est initialement chargé avec les nombres de vélos électriques et musculaires (en bon état, issus
     * du stock) demandés en paramètre. S'il n'y a pas assez de vélos disponibles d'un type donné, on
     * partira avec moins de vélos.
     * Les vélos ramenés par le camion à l'issue de la tournée sont ensuite remis dans le stock.
     * @param nbVM nombre de vélos musculaires à emporter pour la tournée
     * @param nbVE nombre de vélos électrique à emporter pour la tournée
     */
    public void reapprovisionner(int nbVM, int nbVE) {
        Set<Velo> camion = new HashSet<>();
        for(int i = 0; i < nbVM; i++) {
            Velo veloM = velosStock.stream()
                    .filter(velo -> !velo.estAbime())
                    .filter(velo -> velo.prochaineRevision() <= 0d)
                    .filter(velo -> velo instanceof VeloMusculaire)
                    .findFirst()
                    .orElse(null);

            if(veloM == null) break;
            velosStock.remove(veloM);
            camion.add(veloM);
        }

        for(int i = 0; i < nbVE; i++) {
            Velo veloE = velosStock.stream()
                    .filter(velo -> !velo.estAbime())
                    .filter(velo -> velo.prochaineRevision() <= 0d)
                    .filter(velo -> velo instanceof VeloElectrique)
                    .findFirst()
                    .orElse(null);

            if(veloE == null) break;
            velosStock.remove(veloE);
            camion.add(veloE);
        }

        Arrays.stream(stations).forEach(station -> station.equilibrer(camion));
        // Déchargement du camion
        velosStock.addAll(camion);
    }

    /**
     * Extrait un ensemble de statistiques sur les vélos à l'instant T.
     * Dans le stock :
     * - nombre de vélos en bon état : "stock-OK"
     * - nombre de vélos à réviser : "stock-aReviser"
     * - nombre de vélos abîmés : "stock-abimes"
     * Aux différentes stations :
     * - nombre de vélos en bon état : "stations-OK"
     * - nombre de vélos à réviser : "stations-aReviser"
     * - nombre de vélos abîmés : "stations-abimes"
     * Globalement :
     * - nombre de vélos électriques : "total-elec"
     * - nombre de vélos musculaires : "total-muscu"
     * Les vélos actuellement en cours d'emprunt ne sont pas comptabilisés.
     * @return un mapping associant aux clés ci-dessus les valeurs correspondantes.
     */
    public Map<String,Integer> statistiques() {
        HashMap<String, Integer> stats = new HashMap<>();

        stats.put(
            "stock-OK", (int) velosStock.stream()
                .filter(velo -> !(velo.estAbime()))
                .filter(velo -> velo.prochaineRevision() > 0)
                .count()
        );

        stats.put(
            "stock-aReviser", (int) velosStock.stream()
                .filter(velo -> !(velo.estAbime()))
                .filter(velo -> velo.prochaineRevision() <= 0)
                .count()
        );

        stats.put(
            "stock-abimes", (int) velosStock.stream()
                .filter(Velo::estAbime)
                .count()
        );

        List<Velo> velosStation = new ArrayList<>();
        Arrays.stream(stations).filter(Objects::nonNull).forEach(station -> {
            for(int i = 1; i < station.capacite()+1; i++) {
                Velo velo = station.veloALaBorne(i);
                if(velo != null) velosStation.add(velo);
            }
        });

        stats.put(
            "stations-OK", (int) velosStation.stream()
                .filter(velo -> !(velo.estAbime()))
                .filter(velo -> velo.prochaineRevision() > 0)
                .count()
        );

        stats.put(
            "stations-aReviser", (int) velosStation.stream()
                .filter(velo -> !(velo.estAbime()))
                .filter(velo -> velo.prochaineRevision() <= 0)
                .count()
        );

        stats.put(
            "stations-abimes", (int) velosStation.stream()
                .filter(Velo::estAbime)
                .count()
        );

        stats.put(
            "total-elec", (int) velosStation.stream().filter(velo -> velo instanceof VeloElectrique).count() +
                (int) velosStock.stream().filter(velo -> velo instanceof VeloElectrique).count()
        );

        stats.put(
            "total-muscu", (int) velosStation.stream().filter(velo -> velo instanceof VeloMusculaire).count() +
                (int) velosStock.stream().filter(velo -> velo instanceof VeloMusculaire).count()
        );
        return stats;
    }

    /**
     * Calcul de la facturation mensuelle des abonnés pour le mois et l'année
     * passés en paramètre (du 1er jour du mois à minuit, jusqu'au dernier jour
     * du mois suivant, juste avant minuit).
     * On fera l'hypothèse que l'année et le mois représentent une date valide.
     * @param annee l'année considérée
     * @param mois le mois considéré (entre 1 et 12)
     * @return un mapping associant à chaque abonné
     */
    public Map<Abonne,Double> facturation(int mois, int annee) {
        Map<Abonne, Double> facturer = new HashMap<>();
        // Considéré les emprunts du premier jour du mois à 0h00 pile jusqu'au dernier jour du mois à 23h59
        Calendar dateDebut = GregorianCalendar.getInstance();
        dateDebut.set(Calendar.YEAR, annee);
        dateDebut.set(Calendar.MONTH, mois);
        dateDebut.set(Calendar.DAY_OF_MONTH, 1);
        dateDebut.set(Calendar.HOUR_OF_DAY, 0);
        dateDebut.set(Calendar.MINUTE, 0);
        dateDebut.set(Calendar.SECOND, 0);
        dateDebut.set(Calendar.MILLISECOND, 0);

        Calendar dateFin = GregorianCalendar.getInstance();
        dateFin.set(Calendar.YEAR, annee);
        dateFin.set(Calendar.MONTH, mois);
        dateFin.set(Calendar.DAY_OF_MONTH, dateFin.getMaximum(Calendar.DAY_OF_MONTH));
        dateFin.set(Calendar.HOUR_OF_DAY, dateFin.getMaximum(Calendar.HOUR_OF_DAY));
        dateFin.set(Calendar.MINUTE, 59);
        dateFin.set(Calendar.SECOND, 59);
        dateFin.set(Calendar.MILLISECOND, 999);

        abonnes.stream()
                .filter(Objects::nonNull)
                .forEach(abonne -> facturer.put(abonne, jRegistre.facturation(abonne, dateDebut.getTimeInMillis(), dateFin.getTimeInMillis())));


        return null;
    }

    /**
     * Calcule un parcours de la ville, à partir d'un point de départ, en rejoignant
     * à chaque étape la prochaine station la plus proche.
     * @return un itérateur de la liste des stations à parcourir
     */
    public Iterator<String> circuit() {
        List<String> todo = new ArrayList<>();
        Station current = stations[0];
        Station closest = null;
        while (todo.size() < stations.length) {
            double min = Double.MAX_VALUE;
            for (Station s : stations) {
                if (!todo.contains(s.getNom()) && current.distance(s) < min) {
                    closest = s;
                    min = current.distance(s);
                }
            }
            if(closest != null) {
                todo.add(closest.getNom());
                current = closest;
            }
        }
        return todo.iterator();
    }

}

