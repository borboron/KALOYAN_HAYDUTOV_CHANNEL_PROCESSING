import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ChannelProcessingTest {

    @Test
    void correctValueOfYGreaterThan1(){

        ChannelProcessing channelProcessing = new ChannelProcessing();
        Double[] Y = new Double[]{3.0, 5.0, 7.0};
        Double[] X = new Double[]{1.0, 2.0, 3.0};

        assertArrayEquals(Y,channelProcessing.FindY(2.0,1.0,X));

    }

    @Test
    void correctValueOfYLessThan1(){
        ChannelProcessing channelProcessing = new ChannelProcessing();
        Double[] Y = new Double[]{1.2, 1.6, 2.6};
        Double[] X = new Double[]{0.1, 0.3, 0.8};

        assertArrayEquals(Y,channelProcessing.FindY(2.0,1.0,X));


    }

    @Test
    void correctValueOfA(){
        ChannelProcessing channelProcessing = new ChannelProcessing();
        Double[] A = new Double[]{0.2, 0.14285714285714285, 0.125};
        Double[] X = new Double[]{5.0, 7.0, 8.0};

        assertArrayEquals(A,channelProcessing.FindA(X));

    }

    @Test
    void correctValueOfC(){
        ChannelProcessing channelProcessing = new ChannelProcessing();
        Double[] A = new Double[]{12.0, 15.0, 20.0};
        Double[] X = new Double[]{7.0, 10.0, 15.0};

        assertArrayEquals(A,channelProcessing.FindC(X,5.0));

    }

    @Test
    void correctValueOfB() throws IOException {
        ChannelProcessing channelProcessing = new ChannelProcessing();
        Double[] A = new Double[]{18.0, 15.0, 20.0};
        Double[] Y = new Double[]{12.0, 10.0, 15.0};

        assertEquals(30,channelProcessing.FindB(A,Y));

    }





}