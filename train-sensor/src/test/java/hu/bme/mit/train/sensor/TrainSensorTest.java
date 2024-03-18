package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.sensor.TrainSensorImpl;

public class TrainSensorTest {

    TrainSensor sensor;
    TrainController mockController;
    TrainUser mockUser;


    @Before
    public void before() {
        this.mockController = mock(TrainController.class);
        this.mockUser = mock(TrainUser.class);
        this.sensor = new TrainSensorImpl(this.mockController, this.mockUser);
    }

    @Test
    public void RelativeLimitBelowTest() {
        when(mockController.getReferenceSpeed()).thenReturn(200);
        this.sensor.overrideSpeedLimit(99);
        verify(mockUser, times(1)).setAlarmState(true);
    }

    @Test
    public void RelativeLimitAboveTest() {
        when(mockController.getReferenceSpeed()).thenReturn(200);
        this.sensor.overrideSpeedLimit(101);
        verify(mockUser, never()).setAlarmState(true);
    }


    @Test
    public void AbsoluteLimitBelowTest() {
        this.sensor.overrideSpeedLimit(501);
        verify(mockUser, times(1)).setAlarmState(true);
    }

    @Test
    public void AbsoluteLimitAboveTest() {
        this.sensor.overrideSpeedLimit(-1);
        verify(mockUser, times(2)).setAlarmState(true);
    }
}
