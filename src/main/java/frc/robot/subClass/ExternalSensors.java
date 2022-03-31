package frc.robot.subClass;


import edu.wpi.first.cameraserver.CameraServer;

public class ExternalSensors {
    CameraServer driveCamera;
    
    public ExternalSensors() {

    }

    /**
     * 外部のセンサーの値を読む。
     */
    public void readExternalSensors() {
        driveCamera.startAutomaticCapture();

    }
}
