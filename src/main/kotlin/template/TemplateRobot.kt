package template

import robocode.AdvancedRobot
import robocode.RobotDeathEvent
import robocode.ScannedRobotEvent
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Point2D
import kotlin.math.cos
import kotlin.math.sin

class TemplateRobot : AdvancedRobot() {
    companion object {
        private val otherRobots = mutableMapOf<String, Point2D>()
    }

    override fun run() {
        // Change you robot colors!
        setBodyColor(Color(0xf6, 0x9a, 0x2b))
        setGunColor(Color(0x04, 0x62, 0xaf))
        setRadarColor(Color(0xff, 0xff, 0xff))

        while (true) {
            // Move parts of you robot
            setTurnRadarRightRadians(Double.POSITIVE_INFINITY)
            setTurnGunRightRadians(Double.NEGATIVE_INFINITY)
            setTurnLeftRadians(Double.POSITIVE_INFINITY)
            setAhead(Double.NEGATIVE_INFINITY)

            // Fire as fast as possible!
            setFire(0.1)

            // Tell Robocode to execute your set actions
            execute()

            // Log things to the console for debugging
            for ((name, location) in otherRobots) {
                println("$name is located at $location")
            }
        }
    }

    override fun onPaint(g: Graphics2D) {
        // Draw things to the battle field for debugging
        g.drawString("Hello, Robocode!", battleFieldHeight.toInt() / 2, battleFieldWidth.toInt() / 2)
    }

    override fun onScannedRobot(e: ScannedRobotEvent) {
        // When your radar scans another robot
        if (e.name !in otherRobots)  println("Hello, ${e.name}! What country are you from?")
        otherRobots[e.name] = e.toPoint()
    }

    override fun onRobotDeath(e: RobotDeathEvent) {
        // When another robot dies
        otherRobots.remove(e.name)
    }

    private fun ScannedRobotEvent.toPoint(): Point2D {
        val angle = this@TemplateRobot.headingRadians + bearingRadians
        return Point2D.Double(x + sin(angle) * distance, y + cos(angle) * distance)
    }
}
