package fr.l2info.oprog;

import com.opencsv.CSVReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Lecture des stations à partir d'un fichier CSV.
 */
public class StationReader {

    private List<Station> stationList = new ArrayList<>();

    public StationReader(File f) throws IOException, FileFormatException {

        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));

        int l = 0;
        for (String[] line : reader) {
            l++;
            if (line.length != 4) {
                throw new FileFormatException(l, FileFormatException.WRONG_FIELD_NUMBER);
            }
        }
    }

    public Station[] getStations() {
        return (Station[]) stationList.stream().filter(Objects::nonNull).toArray();
    }
}


class FileFormatException extends Exception {

    public final static String
            WRONG_FIELD_NUMBER = "Nombre de champs incorrect",
            EMPTY_FIELD = "Champ vide",
            WRONG_NAME_FORMAT = "Format de nom incorrect",
            INCORRECT_GPS_DATA = "Données GPS invalides",
            INCORRECT_CAPACITY_VALUE = "Valeur de capacité incorrecte",
            DUPLICATE_STATION = "Station déjà existante";

    /** Ligne contenant l'erreur */
    int line;
    /** Cause de l'erreur */
    String reason;

    public FileFormatException(int l, String r) {
        super("Erreur ligne " + l + " : " + r);
        this.line = l;
        this.reason = r;
    }

    public int getLine() {
        return line;
    }

    public String getReason() {
        return reason;
    }

}
