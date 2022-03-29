package frc.robot.subClass;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.State;

public class Util {

    //不感帯処理
    public static double deadbandProcessing(double value) {
        return Math.abs(value) > Const.Other.Deadband ? value : 0;
    }

    public static boolean deadbandCheck(double value) {
        return Math.abs(value) > Const.Other.Deadband;
    }


    public static void sendConsole(String key, String text) {
        // System.out.println(key + ":" + text);
        SmartDashboard.putString(key, text);
    }

    public static void sendConsole(String key, double number) {
        // System.out.println(key + ":" +number);
        SmartDashboard.putNumber(key, number);
    }
    public static void sendConsole(String key, Boolean which){
        // System.out.println(key + ":" +which);
        SmartDashboard.putBoolean(key, which);
    }

    public static void allSendConsole(){
        sendConsole( "Mode", State.mode.toString());
        sendConsole("DriveState", State.driveState.toString());
        sendConsole("ClimbState", State.climbArmState.toString());
        sendConsole("ConveyorState", State.conveyorState.toString());
        sendConsole("driveXSpeed", State.driveXSpeed);
        sendConsole("driveZRotation", State.driveZRotation);
        sendConsole("extendSpeed", State.intakeExtendSpeed);
        sendConsole("1stSolenoidOpen", State.is_firstSolenoidOpen);
        sendConsole("2ndSolenoidOpen", State.is_secondSolenoidOpen);
        sendConsole("climbSolenoidUp", State.is_climbSolenoidOpen);
        sendConsole("climbArmSpeed", State.climbArmSpeed);
        sendConsole("compressorOn", State.is_compressorEnabled);
        sendConsole("driveRightMeter", State.driveRightFrontPositionMeter);
        sendConsole("driveLeftMeter", State.driveLeftFrontPositionMeter);
        sendConsole("climbArmAngle", State.climbArmAngle);
        sendConsole("alliance", State.alliance.toString());
        sendConsole("gameMessage", State.gameSpecificMessage);
        sendConsole("ballQuantity", State.ballQuantity.getBallQuantity());
        sendConsole("shooterSpeed",State.shooterSpeed);
        sendConsole("climbMotorNEO", State.is_climbArmMotorNEO);
        sendConsole("extendOpen", State.is_intakeExtendOpen);
        sendConsole("robotDirection",State.currentDirection);
        sendConsole("isTurnFinished", State.reachTurn);
    }

    /**
     * 角度がその範囲にあるか。
     * min <= max, max >= 0, min <= 360, max - min < 360を前提としている。
     * angleには条件はない。
     * 
     * <ul>
     * <li><b>例:</b>
     *  <ul>
     *    <li> min=0, max=225, angle=10 => true </li>
     *    <li> min=-40, max=100, angle=350 => true </li>
     *    <li> min=-40, max=100, angle=-60 => false </li>
     *    <li> min=200, max=450, angle=-60 => true </li>
     *    <li> min=200, max=450, angle=600 => true </li>
     *    <li> min=200, max=450, angle=500 => false </li>
     *  </ul>
     * </li>
     * </ul>
     * 
     * @param min 最小角度(degree)
     * @param max 最大角度(degree)
     * @param angle 対象角度(degree)
     * @return angleがmin以上max以下か
     */
    public static boolean is_angleInRange(double min, double max, double angle) {
        angle = mod(angle, Const.Calculation.FullTurnAngle);
    
        if(min < 0) {
          return angle <= max || mod(min, Const.Calculation.FullTurnAngle) <= angle; 
        } else if(max >= Const.Calculation.FullTurnAngle) {
          return min <= angle || angle <= mod(max, Const.Calculation.FullTurnAngle);
        } else {
          return min <= angle && angle <= max;
        }
    }
    
    /**
     * aをbで割ったあまりを返す。
     * あまりrは0 <= r < b。
     * b > 0を仮定している。
     *  <ul>
     * <li><b>例:</b>
     *  <ul>
     *    <li> a=5, b=2 => 1 </li>
     *    <li> a=-1000, b=360 => 80 </li>
     *  </ul>
     * </li>
     * </ul>
     * @param a
     * @param b
     * @return aをbで割ったあまり
     */
    public static double mod(double a, double b) {
        if(a < 0) {
            a += ((int)(-a / b) + 1) * b;
        }
        return a % b;
    }

    // 時計回りに回るかを、反時計回りに回るかを、曲がりたい方向の大きさによって決める。
    public static double determineDirection(double a) {
        double k = mod(a, 360);
        if(k>180){
            return(k-360);
        } else{
            return(k);
        }
    }
}
