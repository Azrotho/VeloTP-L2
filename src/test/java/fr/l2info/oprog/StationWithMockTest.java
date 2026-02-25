package fr.l2info.oprog;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class StationWithMockTest {
    @Test
    public void testArimerAbonne() {
        Abonne mockAbonne = Mockito.mock(Abonne.class);

        Station s = new Station("Feur Station", 4,4, 42);
        VeloMusculaire veloMusculaireMock = Mockito.mock(VeloMusculaire.class);
        Mockito.when(veloMusculaireMock.arrimer()).thenReturn(0);


        int result = s.arrimerVelo(veloMusculaireMock, 1);
        Assert.assertEquals(-2, result);
    }
}
