# Robocode Robot Template

## Project Import

As a Gradle project, this project can be imported into most IDEs. To allow
indexing of the Robocode dependency, Robocode needs to be downloaded first. This
can be done by running the Gradle task `robocodeDownload`. 

## Create Robot

Change the name of the class located at `src/main/kotlin/template/TemplateRobot`
and relocate to the desired package. Update `build.gradle.kts` to match the name
and classpath of the robot.

## Run Robocode

To run Robocode, use the Gradle task `robocodeRun`. To build the project so that
Robocode can use any Robots in the project, use the Gradle task `robotBin`. This
task can be executed with Gradle continuous mode (`--continuous`) to make the 
development process easier.
