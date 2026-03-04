package fr.l2info.oprog;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *  Classe implantant un registre simple en Java.
 */
public class JRegistre implements IRegistre {

    private Map<Abonne, Set<Emprunt>> empruntsParAbonne;
    private Map<Velo, Set<Emprunt>> empruntsParVelo;

    protected JRegistre() {
        empruntsParAbonne = new HashMap<>();
        empruntsParVelo = new HashMap<>();
    }

    @Override
    public int emprunter(Abonne a, Velo v, long d) {
        if (a == null || v == null) {
            return -1;
        }
        if (empruntsParVelo.containsKey(v)) {
            for (Emprunt em : empruntsParVelo.get(v)) {
                if (em.estEnCours() || em.contientDate(d)) {
                    return -2;
                }
            }
        }
        if (! empruntsParAbonne.containsKey(a)) {
            empruntsParAbonne.put(a, new HashSet<Emprunt>());
        }
        if (! empruntsParVelo.containsKey(v)) {
            empruntsParVelo.put(v, new HashSet<Emprunt>());
        }
        Emprunt em = new Emprunt(a, v, d);
        empruntsParAbonne.get(a).add(em);
        empruntsParVelo.get(v).add(em);
        return 0;
    }

    @Override
    public int retourner(Velo v, long d) {
        if (v == null) {
            return -1;
        }
        if (! empruntsParVelo.containsKey(v)){
            return -2;
        }

        Emprunt emEnCours = null;
        for (Emprunt em : empruntsParVelo.get(v)) {
            if (em.estEnCours()) {
                emEnCours = em;
                break;
            }
        }
        if (emEnCours == null){
            return -2;
        }

        for (Emprunt em : empruntsParVelo.get(v)) {
            if (! em.estEnCours() && em.contientDate(d)) {
                return -3;
            }
            if(! em.estEnCours() && emEnCours.debut < em.debut && d > em.fin) {
                return -3;
            }
        }
        if (! emEnCours.termine(d)){
            return -3;
        }
        return 0;
    }

    @Override
    public int nbEmpruntsEnCours(Abonne a) {
        int nb = 0;
        if (empruntsParAbonne.containsKey(a)) {
            for (Emprunt e : empruntsParAbonne.get(a)) {
                if (e.estEnCours()) {
                    nb++;
                }
            }
        }
        return nb;
    }

    @Override
    public double facturation(Abonne a, long debut, long fin) {
        double facture = 0.0;
        if (empruntsParAbonne.get(a) != null) {
            for (Emprunt e : empruntsParAbonne.get(a)) {
                if (e.finitEntre(debut, fin)) {
                    facture += e.cout();
                }
            }
        }
        return facture;
    }


    private class Emprunt {

        private Velo velo;
        private long debut, fin;
        private boolean enCours;
        private Abonne abo;

        public Emprunt(Abonne a, Velo v, long d) {
            abo = a;
            velo = v;
            debut = d;
            fin = d - 1;
            enCours = true;
        }

        public boolean estEnCours() {
            return enCours;
        }

        public boolean termine(long f) {
            if (enCours && f >= debut) {
                fin = f;
                enCours = false;
                return true;
            }
            return false;
        }

        public boolean contientDate(long d) {
            return d >= debut && d <= fin;
        }

        public boolean finitEntre(long d, long f) {
            return fin >= d && fin <= f;
        }

        public double cout() {
            if (enCours)
                return 0;
            long delta = Math.abs(fin - debut);
            long nbMin = delta / (60 * 1000);
            return (velo.tarif() * nbMin) / 60;
        }
    }
}


