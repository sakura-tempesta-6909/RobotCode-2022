package frc.robot.component;

import frc.robot.subClass.State;

public interface Component {
    /**
     * autonomous時の初期化
     * @param objects
     */
    void autonomousInit();

    /**
     * teleop時の初期化
     * @param objects
     */
    void teleopInit();

    /**
     * disable時の初期化
     * @param objects
     */
    void disabledInit();

    /**
     * test時の初期化
     * @param objects
     */
    void testInit();

    /**
     * stateを適用する
     * @param state
     */
    void applyState(final State state);
}