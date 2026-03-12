package fr.l2info.oprog;

import com.opencsv.CSVReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Lecture des stations à partir d'un fichier CSV.
 */
public class StationReader {

    private List<Station> stationList = new ArrayList<>();
    private boolean valid = false;

    public StationReader(File f) throws IOException, FileFormatException {
        if(f == null) {
            throw new IOException("Le fichier est null");
        }

        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));

        int l = 0;
        for (String[] line : reader) {
            l++;
            if(line.length == 1 && line[0].trim().isEmpty()) {
                continue;
            }
            if (line.length != 4) {
                throw new FileFormatException(l, FileFormatException.WRONG_FIELD_NUMBER);
            }

            if(Arrays.stream(line).anyMatch(target -> target.trim().isEmpty()))
                throw new FileFormatException(l, FileFormatException.EMPTY_FIELD);

            line[0] = line[0].trim();
            line[1] = line[1].trim();
            line[2] = line[2].trim();
            line[3] = line[3].trim();

            String nom = line[0];
            if(!nom.matches("[A-Z][a-z -'À-ÿA-Z0-9]+")) throw new FileFormatException(l, FileFormatException.WRONG_NAME_FORMAT);

            if(!isDouble(line[1])) throw new FileFormatException(l, FileFormatException.INCORRECT_GPS_DATA);
            if(!isDouble(line[2])) throw new FileFormatException(l, FileFormatException.INCORRECT_GPS_DATA);
            double latitude = Double.parseDouble(line[1]);
            double longitude = Double.parseDouble(line[2]);
            if(latitude < -90.0d || latitude > 90.0d) throw new FileFormatException(l, FileFormatException.INCORRECT_GPS_DATA);
            if(longitude < -180.0d || longitude > 180.0d) throw new FileFormatException(l, FileFormatException.INCORRECT_GPS_DATA);

            if(!isInteger(line[3])) throw new FileFormatException(l, FileFormatException.INCORRECT_CAPACITY_VALUE);
            int capacity = Integer.parseInt(line[3]);
            if(capacity <= 0) throw new FileFormatException(l, FileFormatException.INCORRECT_CAPACITY_VALUE);

            if(stationList.stream().anyMatch(station -> {
                return station.getNom().equalsIgnoreCase(line[0]);
            })) throw new FileFormatException(l, FileFormatException.DUPLICATE_STATION);

            Station station = new Station(nom, latitude, longitude, capacity);
            if(stationList.stream().anyMatch(stationTarget -> {
                return stationTarget.distance(station) < 0.02d; // 20m mais la valeur de distance est en km
            })) throw new FileFormatException(l, FileFormatException.DUPLICATE_STATION);

            stationList.add(station);
            this.valid = true;
        }
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Station[] getStations() {
        if(!valid) return null;
        Station[] stations = new Station[stationList.size()];
        stationList.forEach(station -> {
            stations[stationList.indexOf(station)] = station;
        });
        return stations;
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
