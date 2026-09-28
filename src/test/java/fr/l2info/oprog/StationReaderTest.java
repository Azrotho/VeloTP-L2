package fr.l2info.oprog;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StationReaderTest {
    private static final String PREFIX = "target/classes/data/";

    @Test
    public void testFileOK() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesancon.csv"));
    }

    @Test(expected = FileNotFoundException.class)
    public void testFileKO() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconZEFjnkeZBJNLGHIULZgerZBHIJULREZHUIz.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileDuppCoord() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconDupplicateCoordinate.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileDuppName() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconDupplicateName.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileInvalidCapacityInt() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconInvalidCapacityInt.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileNulCapacityInt() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconNulCapacityNumber.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileInvalidLatitude() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconInvalidLatNumber.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileInvalidLongitude() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconInvalidLonNumber.csv"));
    }

     @Test(expected = FileFormatException.class)
    public void testFileInvalidLongitude2() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconInvalidLonInt.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileInvalidLatitude2() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconInvalidLatInt.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileInvalidLongitude3() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconInvalidLatInt2.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileInvalidLatitude3() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconInvalidLonInt2.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileMissingField() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconMissingField.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileWithTooManyFields() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconTooManyField.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileWithEmptyField() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconEmptyField1.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileWithEmptyField2() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconEmptyField2.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileWithEmptyField3() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconEmptyField3.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileWithEmptyField4() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconEmptyField4.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileWithEmptyFieldSpace() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconEmptyField1Space.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileWithEmptyField2Space() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconEmptyField2Space.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileWithEmptyField3Space() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconEmptyField3Space.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testFileWithEmptyField4Space() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconEmptyField4Space.csv"));
    }

    @Test
    public void testTrim1() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconTrim1.csv"));
    }

    @Test
    public void testTrim2() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconTrim2.csv"));
    }

    @Test
    public void testTrim3() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconTrim3.csv"));
    }

    @Test
    public void testTrim4() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconTrim4.csv"));
    }

    @Test
    public void testTrim5() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconTrim5.csv"));
    }

    @Test
    public void testTrim6() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconTrim6.csv"));
    }

    @Test
    public void testTrim7() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconTrim7.csv"));
    }

    @Test
    public void testTrim8() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconTrim8.csv"));
    }

    @Test
    public void testTrim9() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconTrim9.csv"));
    }

    @Test
    public void testTrim10() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconTrim10.csv"));
    }

    @Test
    public void testLineEmpty() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconEmptyLine.csv"));
    }

    @Test
    public void testGetStationList() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesancon.csv"));
        Assert.assertEquals(33, r.getStations().length);
    }

    @Test(expected = IOException.class)
    public void testFileNull() throws FileFormatException, IOException {
        StationReader r = new StationReader(null);
    }

    @Test(expected = FileFormatException.class)
    public void testFileInvalidNom() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconInvalidNom.csv"));
    }

    @Test(expected = FileFormatException.class)
    public void testNomExistCase() throws FileFormatException, IOException {
        StationReader r = new StationReader(new File(PREFIX + "velociteBesanconDupplicateNameCase.csv"));
    }

    @Test
    public void testFileDuppCoordDetail() throws FileFormatException, IOException {
        try{
            new StationReader(new File(PREFIX + "velociteBesanconDupplicateCoordinate.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(34, e.getLine());
            Assert.assertEquals(FileFormatException.DUPLICATE_STATION, e.getReason());
        }
    }

    @Test
    public void testFileDuppNameDetail() throws FileFormatException, IOException {
        try{
            new StationReader(new File(PREFIX + "velociteBesanconDupplicateName.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(34, e.getLine());
            Assert.assertEquals(FileFormatException.DUPLICATE_STATION, e.getReason());
        }
    }

    @Test
    public void testFileInvalidCapacityIntDetail() throws FileFormatException, IOException {
        try{
            new StationReader(new File(PREFIX + "velociteBesanconInvalidCapacityInt.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.INCORRECT_CAPACITY_VALUE, e.getReason());
        }
    }

    @Test
    public void testFileNulCapacityIntDetail() throws FileFormatException, IOException {
        try{
            new StationReader(new File(PREFIX + "velociteBesanconNulCapacityNumber.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.INCORRECT_CAPACITY_VALUE, e.getReason());
        }
    }

    @Test
    public void testFileInvalidLatitudeDetail() throws FileFormatException, IOException {
        try{
            new StationReader(new File(PREFIX + "velociteBesanconInvalidLatNumber.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.INCORRECT_GPS_DATA, e.getReason());
        }
    }

    @Test
    public void testFileInvalidLongitudeDetail() throws FileFormatException, IOException {
         try {
             new StationReader(new File(PREFIX + "velociteBesanconInvalidLonNumber.csv"));
         } catch (FileFormatException e) {
             Assert.assertEquals(33, e.getLine());
             Assert.assertEquals(FileFormatException.INCORRECT_GPS_DATA, e.getReason());
         }
    }

    @Test
    public void testFileInvalidLongitude2Detail() throws FileFormatException, IOException {
         try {
             new StationReader(new File(PREFIX + "velociteBesanconInvalidLonInt.csv"));
         } catch (FileFormatException e) {
             Assert.assertEquals(33, e.getLine());
             Assert.assertEquals(FileFormatException.INCORRECT_GPS_DATA, e.getReason());
         }
    }

    @Test
    public void testFileInvalidLatitude2Detail() throws FileFormatException, IOException {
        try {
            new StationReader(new File(PREFIX + "velociteBesanconInvalidLatInt.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.INCORRECT_GPS_DATA, e.getReason());
        }
    }

    @Test
    public void testFileInvalidLongitude3Detail() throws FileFormatException, IOException {
        try {
            new StationReader(new File(PREFIX + "velociteBesanconInvalidLatInt2.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.INCORRECT_GPS_DATA, e.getReason());
        }
    }

    @Test
    public void testFileInvalidLatitude3Detail() throws FileFormatException, IOException {
        try {
            new StationReader(new File(PREFIX + "velociteBesanconInvalidLonInt2.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.INCORRECT_GPS_DATA, e.getReason());
        }
    }

    @Test
    public void testFileMissingFieldDetail() throws FileFormatException, IOException {
        try {
            new StationReader(new File(PREFIX + "velociteBesanconMissingField.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.WRONG_FIELD_NUMBER, e.getReason());
        }
    }

    @Test
    public void testFileWithTooManyFieldsDetail() throws FileFormatException, IOException {
        try {
            new StationReader(new File(PREFIX + "velociteBesanconTooManyField.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.WRONG_FIELD_NUMBER, e.getReason());
        }
    }

    @Test
    public void testFileWithEmptyFieldDetail() throws FileFormatException, IOException {
        try {
            new StationReader(new File(PREFIX + "velociteBesanconEmptyField1.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.EMPTY_FIELD, e.getReason());
        }
    }

    @Test
    public void testFileWithEmptyField2Detail() throws FileFormatException, IOException {
        try {
            new StationReader(new File(PREFIX + "velociteBesanconEmptyField2.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.EMPTY_FIELD, e.getReason());
        }
    }

    @Test
    public void testFileWithEmptyField3Detail() throws FileFormatException, IOException {
        try {
            new StationReader(new File(PREFIX + "velociteBesanconEmptyField3.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.EMPTY_FIELD, e.getReason());
        }
    }

    @Test
    public void testFileWithEmptyField4Detail() throws FileFormatException, IOException {
        try {
            new StationReader(new File(PREFIX + "velociteBesanconEmptyField4.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.EMPTY_FIELD, e.getReason());
        }
    }

    @Test
    public void testFileWithEmptyFieldSpaceDetail() throws FileFormatException, IOException {
        try {
            new StationReader(new File(PREFIX + "velociteBesanconEmptyField1Space.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.EMPTY_FIELD, e.getReason());
        }
    }

    @Test
    public void testFileWithEmptyField2SpaceDetail() throws FileFormatException, IOException {
        try {
            new StationReader(new File(PREFIX + "velociteBesanconEmptyField2Space.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.EMPTY_FIELD, e.getReason());
        }
    }

    @Test
    public void testFileWithEmptyField3SpaceDetail() throws FileFormatException, IOException {
        try {
            new StationReader(new File(PREFIX + "velociteBesanconEmptyField3Space.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.EMPTY_FIELD, e.getReason());
        }
    }

    @Test
    public void testFileWithEmptyField4SpaceDetail() throws FileFormatException, IOException {
        try {
            new StationReader(new File(PREFIX + "velociteBesanconEmptyField4Space.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.EMPTY_FIELD, e.getReason());
        }
    }

    @Test
    public void testFileInvalidNomDetail() throws FileFormatException, IOException {
        try {
            new StationReader(new File(PREFIX + "velociteBesanconInvalidNom.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(33, e.getLine());
            Assert.assertEquals(FileFormatException.WRONG_NAME_FORMAT, e.getReason());
        }
    }

    @Test
    public void testNomExistCaseDetail() throws FileFormatException, IOException {
        try {
            new StationReader(new File(PREFIX + "velociteBesanconDupplicateNameCase.csv"));
        } catch (FileFormatException e) {
            Assert.assertEquals(34, e.getLine());
            Assert.assertEquals(FileFormatException.DUPLICATE_STATION, e.getReason());
        }
    }

    // TODO:
    //- même nom a été déclarée précédemment (indépendamment de la casse)
    //- ce type de fichiers, les éventuels espaces avant ou après les séparateurs (virgules)
    //- De même, le format autorise que des lignes du fichier soient vides.
}
