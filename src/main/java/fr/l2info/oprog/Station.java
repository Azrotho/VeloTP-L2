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
        if(b > capacite) return null;
        if(b <= 0) return null;
        return bornes[b-1];
    }

    public int nbBornesLibres() {
        int count = 0;
        for(int i = 0; i < capacite; i++) {
            if(bornes[i] == null) {
                count++;
            }
        }
        return count;
    }

    public Velo emprunterVelo(Abonne a, int b){
        if(registre == null) return null;
        if(a.estBloque()) return null;
        if(b > capacite) return null;
        if(b <= 0) return null;
        if(bornes[b-1] == null) return null;
        Velo veloaemprunter = bornes[b-1];
        int resultDecro = veloaemprunter.decrocher();
        if(resultDecro != 0) return null;
        int result = registre.emprunter(a, veloaemprunter, maintenant());
        if(result != 0) return null;
        bornes[b-1] = null;
        return veloaemprunter;
    }

    public int arrimerVelo(Velo v, int b) {
        if(v == null) return -1;
        if(b > capacite) return -1;
        if(b <= 0) return -1;

        if(registre == null) return -2;
        if(bornes[b-1] != null) return -2;

        int result = v.arrimer();
        if(result != 0) return -3;

        result = registre.retourner(v, maintenant());
        if(result != 0) return -4;

        bornes[b-1] = v;
        return 0;
    }

    public void equilibrer(Set<Velo> velos) {
        int moitie = (int) Math.ceil(((double) capacite) / 2.0d);
        for(int i = 0; i < capacite; i++) {
            Velo v = bornes[i];
            if (v.estAbime() || v.prochaineRevision() <= 0.0d) {
                velos.add(v);
                bornes[i] = null;
            }
        }
        int counter = 0;
        while(capacite - nbBornesLibres() > moitie) {
            Velo v = bornes[counter];
            velos.add(v);
            bornes[counter] = null;
        }

        long nbvelosneuf = velos.stream().filter(velo -> {
            return !velo.estAbime() && velo.prochaineRevision() > 0.0d;
        }).count();

        long nbveloreviser = nbvelosneuf - velos.stream().filter(velo -> {
            return !velo.estAbime();
        }).count();


    }

    public double distance(Station s) {
        return 0.0d;
    }

    public long maintenant() {
        return System.currentTimeMillis();
    }


}
