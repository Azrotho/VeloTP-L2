package fr.l2info.oprog;

public class VeloMusculaire extends Velo{

    @Override
    public double tarif() {
        return 2.0d;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vélo ");
        sb.append("musculaire");
        sb.append(" - ");
        sb.append(String.format("%.1f", super.kilometrage()));
        sb.append(" km");
        if((this.prochaineRevision() <= 0)) {
            sb.append(" (révision nécessaire)");
        }
        return sb.toString();
    }
}
