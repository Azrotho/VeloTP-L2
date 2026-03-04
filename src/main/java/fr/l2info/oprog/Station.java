package fr.l2info.oprog;

import java.util.Set;

public class Station {

    private final String nom;
    private final double latitude;
    private final double longitude;
    private final int capacite;
    private Velo[] bornes;

    private IRegistre registre = null;

    public Station(String nom, double latitude, double longitude, int capacite) {
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacite = capacite;
        this.bornes = new Velo[capacite];
    }

    public void setRegistre(IRegistre registre) {
        this.registre = registre;
    }

    public String getNom() {
        return this.nom;
    }

    public int capacite() {
        return this.capacite;
    }

    public Velo veloALaBorne(int b) {
        if (b > capacite)
            return null;
        if (b <= 0)
            return null;
        return bornes[b - 1];
    }

    public int nbBornesLibres() {
        int count = 0;
        for (int i = 0; i < capacite; i++) {
            if (bornes[i] == null) {
                count++;
            }
        }
        return count;
    }

    public Velo emprunterVelo(Abonne a, int b) {
        if (registre == null)
            return null;
        if (a.estBloque())
            return null;
        if (b > capacite)
            return null;
        if (b <= 0)
            return null;
        if (bornes[b - 1] == null)
            return null;
        Velo veloaemprunter = bornes[b - 1];
        int resultDecro = veloaemprunter.decrocher();
        if (resultDecro != 0)
            return null;
        int result = registre.emprunter(a, veloaemprunter, maintenant());
        if (result != 0)
            return null;
        bornes[b - 1] = null;
        return veloaemprunter;
    }

    public int arrimerVelo(Velo v, int b) {
        if (v == null)
            return -1;
        if (b > capacite)
            return -1;
        if (b <= 0)
            return -1;

        if (registre == null)
            return -2;
        if (bornes[b - 1] != null)
            return -2;

        int result = v.arrimer();
        if (result != 0)
            return -3;

        result = registre.retourner(v, maintenant());
        bornes[b - 1] = v;
        if (result != 0)
            return -4;

        return 0;
    }

    public void equilibrer(Set<Velo> velos) {
        int moitie = (int) Math.ceil(((double) capacite) / 2.0d);
        for (int i = 0; i < capacite; i++) {
            Velo v = bornes[i];
            if (v != null && (v.estAbime() || v.prochaineRevision() <= 0.0d)) {
                velos.add(v);
                bornes[i] = null;
            }
        }
        int counter = 0;
        while (capacite - nbBornesLibres() > moitie) {
            if (bornes[counter] != null) {
                velos.add(bornes[counter]);
                bornes[counter] = null;
            }
            counter++;
        }

        int nbvelosneuf = (int) velos
                .stream()
                .filter(velo -> {
                    return !velo.estAbime() && velo.prochaineRevision() > 0.0d;
                })
                .count();

        int nbareviser = (int) (velos.stream().filter(velo -> {
            return !velo.estAbime() && velo.prochaineRevision() <= 0.0d;
        }).count());

        int manquants = moitie - (capacite - nbBornesLibres());
        int nbreintegrer = Math.max(0, manquants - (int) nbvelosneuf);
        nbreintegrer = Math.min(nbreintegrer, nbareviser);

        for (int i = 0; i < capacite && nbreintegrer > 0; i++) {
            if (bornes[i] == null) {
                Velo vReviser = velos.stream().filter(velo -> !velo.estAbime() && velo.prochaineRevision() <= 0.0d)
                        .findFirst().orElse(null);
                if (vReviser == null)
                    break;
                velos.remove(vReviser);
                vReviser.arrimer();
                bornes[i] = vReviser;
                nbreintegrer--;
            }
        }

        for (int i = 0; i < capacite; i++) {
            if (capacite - nbBornesLibres() >= moitie)
                break;
            if (bornes[i] != null)
                continue;
            Velo remplacement = velos.stream().filter(velo -> !velo.estAbime() && velo.prochaineRevision() > 0.0d)
                    .findFirst().orElse(null);
            if (remplacement == null)
                break;
            velos.remove(remplacement);
            remplacement.arrimer();
            bornes[i] = remplacement;
        }

    }

    public double distance(Station s) {
        double radius = 6371;
        double lat1 = Math.toRadians(this.latitude);
        double lat2 = Math.toRadians(s.latitude);
        double o = lat2 - lat1;
        double lambda = Math.toRadians(s.longitude - this.longitude);

        double a = Math.sin(o/2.0d) * Math.sin(o/2.0d) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(lambda/2.0d) * Math.sin(lambda/2.0d);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return radius * c;
    }

    public long maintenant() {
        return System.currentTimeMillis();
    }

}
