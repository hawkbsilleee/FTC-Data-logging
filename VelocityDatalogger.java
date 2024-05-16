/*
This sample FTC OpMode uses methods of the Datalogger class to specify and
collect robot data to be logged in a CSV file, ready for download and charting.

For instructions, see the tutorial at the FTC Wiki:
https://github.com/FIRST-Tech-Challenge/FtcRobotController/wiki/Datalogging


The Datalogger class is suitable for FTC OnBot Java (OBJ) programmers.
Its methods can be made available for FTC Blocks, by creating myBlocks in OBJ.

Android Studio programmers can see instructions in the Datalogger class notes.

Credit to @Windwoes (https://github.com/Windwoes).

*/


package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name = "Concept Datalogger v01", group = "Datalogging")
public class VelocityDatalogger extends LinearOpMode
{
    Datalog datalog;
    DcMotorEx intakeMotor; 
    
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    static final double INTAKE_SPEED = 0.9;
    static final double DRIVE_SPEED = 0.9;

    @Override
    public void runOpMode() throws InterruptedException
    {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        frontRight = hardwareMap.get(DcMotor.class, "FR");
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");
        
        intakeMotor.setDirection(DcMotorEx.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        // Initialize the datalog
        datalog = new Datalog("Velocity_Drive");

        datalog.opModeStatus.set("INIT");

        telemetry.setMsTransmissionInterval(50);

        waitForStart();
        
        intakeMotor.setPower(INTAKE_SPEED);
        frontLeft.setPower(DRIVE_SPEED);
        frontRight.setPower(DRIVE_SPEED);
        backLeft.setPower(DRIVE_SPEED);
        backRight.setPower(DRIVE_SPEED);

        datalog.opModeStatus.set("RUNNING");

        for (int i = 0; opModeIsActive(); i++)
        {
            // Note that the order in which we set datalog fields
            // does *not* matter! The order is configured inside
            // the Datalog class constructor.

            datalog.loopCounter.set(i);
            // datalog.battery.set(battery.getVoltage());

            // Orientation orientation = imu.getAngularOrientation();

            // datalog.yaw.set(orientation.firstAngle);
            // datalog.pitch.set(orientation.secondAngle);
            // datalog.roll.set(orientation.thirdAngle);
            datalog.motorEncoder.set(intakeMotor.getVelocity());

            // The logged timestamp is taken when writeLine() is called.
            datalog.writeLine();

            // Datalog fields are stored as text only; do not format here.
            // telemetry.addData("Yaw", datalog.yaw);
            // telemetry.addData("Pitch", datalog.pitch);
            // telemetry.addData("Roll", datalog.roll);
            // telemetry.addLine();
            telemetry.addData("OpMode Status", datalog.opModeStatus);
            telemetry.addData("Loop Counter", datalog.loopCounter);
            telemetry.addData("Intake", datalog.motorEncoder);

            telemetry.update();

            sleep(20);
        }

        /*
         * The datalog is automatically closed and flushed to disk after 
         * the OpMode ends - no need to do that manually :')
         */
    }

    /*
     * This class encapsulates all the fields that will go into the datalog.
     */
    public static class Datalog
    {
        // The underlying datalogger object - it cares only about an array of loggable fields
        private final Datalogger datalogger;

        // These are all of the fields that we want in the datalog.
        // Note that order here is NOT important. The order is important in the setFields() call below
        public Datalogger.GenericField opModeStatus = new Datalogger.GenericField("OpModeStatus");
        public Datalogger.GenericField loopCounter  = new Datalogger.GenericField("Loop Counter");
        
        // public Datalogger.GenericField yaw          = new Datalogger.GenericField("Yaw");
        // public Datalogger.GenericField pitch        = new Datalogger.GenericField("Pitch");
        // public Datalogger.GenericField roll         = new Datalogger.GenericField("Roll");
        // public Datalogger.GenericField battery      = new Datalogger.GenericField("Battery");
        public Datalogger.GenericField motorEncoder = new Datalogger.GenericField("Intake");

        public Datalog(String name)
        {
            // Build the underlying datalog object
            datalogger = new Datalogger.Builder()

                    // Pass through the filename
                    .setFilename(name)

                    // Request an automatic timestamp field
                    .setAutoTimestamp(Datalogger.AutoTimestamp.DECIMAL_SECONDS)

                    // Tell it about the fields we care to log.
                    // Note that order *IS* important here! The order in which we list
                    // the fields is the order in which they will appear in the log.
                    .setFields(
                            opModeStatus,
                            loopCounter,
                            motorEncoder
                    )
                    .build();
        }

        // Tell the datalogger to gather the values of the fields
        // and write a new line in the log.
        public void writeLine()
        {
            datalogger.writeLine();
        }
    }
}
