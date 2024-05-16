# FTC-Data-logging
Coding project for Avenues Robotics which uses the FtcRobotController datalogging library to analyze Oscar's intake speed under different drivetrain power draws.

## Overview
<img width="806" alt="Screen Shot 2024-05-15 at 10 45 56 PM" src="https://github.com/hawkbsilleee/FTC-Data-logging/assets/43685920/8064e7cf-bc17-47a9-ae28-3ef7cccc4d31">

One of the problems we faced during the early part of the '23-'24 Center Stage season was that our robot often stalled when trying to intake pixels. We noticed that there was a high amount of friction when the first set of compliant wheels made contact with the pixel, causing the intake system to lose a ton of velocity. Even after we addressed this issue by swapping out the compliant wheels for even more compliant surgical tubing "rollers," we noticed that our ability to successfully intake pixels was reduced when the robot was driving (as opposed to getting "fed" pixels when stationary).  

This project attempts to use the FTC data logging library to quantitatively describe the velocity drop in the intake when the robot is driving versus being stationary.  

## Results
The velocity of the intake motor was recorded for approximately 10 seconds while the robot remained stationary and then for another ~10 seconds while it spun in a circle at full power. Both times the intake motor was run at 70% of its max power.   

The maximum velocity of the intake motor decreased by approximately 7% when the drivetrain was run, which confirms our original observation/hypothesis that the robot struggled to intake pixels while moving due to a reduced maximum motor velocity because of power draw limitations from the battery.    

## File Structure
* Datalogger.java -> contains the Java class that handles all details of storing the robot data in a datalog file.
* ConceptDatalogger.java -> is the OpMode that's run from the Driver Station. It specifies and collects the robot data to be logged and ultimately charted. The OpMode uses, or calls, commands/methods provided in the Datalogger class.
* something.txt -> raw .csv file that records the robot data when the OpMode is run.
* Robot Datalogging Analysis.csv -> processed data. 
