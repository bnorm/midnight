package bnorm

import bnorm.geo.Vector
import bnorm.geo.theta
import robocode.AdvancedRobot
import robocode.DeathEvent
import robocode.RobotDeathEvent
import robocode.ScannedRobotEvent
import robocode.WinEvent
import robocode.util.Utils
import java.awt.Color
import java.awt.Graphics2D
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sign
import kotlin.math.sin

class Midnight : AdvancedRobot() {
    companion object {
        private val otherRobots = mutableMapOf<String, RobotScan>()
    }

    override fun run() {
        setBodyColor(Color.black)
        setGunColor(Color.white)
        setRadarColor(Color.black)

        isAdjustGunForRobotTurn = true
        isAdjustRadarForGunTurn = true
        setTurnRadarRightRadians(Double.POSITIVE_INFINITY)

        while (true) {
            val location = Vector.Cartesian(x, y)
            val enemy = otherRobots.values.singleOrNull()
            if (enemy != null) {
                val bearing = Utils.normalRelativeAngle(location.theta(enemy.location) - radarHeadingRadians)
                setTurnRadarRightRadians(bearing + mul(bearing) * PI / 10)
            }
            execute()
        }
    }

    override fun onPaint(g: Graphics2D) {
        g.color = Color.red
        for ((_, scan) in otherRobots) {
            generateSequence(scan) { it.prev }.take(25).forEach { g.fillOval(it.location, 4.0) }
        }
    }

    override fun onScannedRobot(e: ScannedRobotEvent) {
        val newScan = e.toRobotScan()
        newScan.prev = otherRobots.put(e.name, newScan)
    }

    override fun onRobotDeath(e: RobotDeathEvent) {
        otherRobots.remove(e.name)
    }

    override fun onDeath(e: DeathEvent) {
        otherRobots.clear()
        println("It'll take a lot more than a bullet to the brain, lungs, heart, back and balls to kill Michael Scarn!")
    }

    override fun onWin(e: WinEvent) {
        println("Clean up on Aisle 5.")
    }

    private fun ScannedRobotEvent.toRobotScan(): RobotScan {
        val angle = this@Midnight.headingRadians + bearingRadians
        return RobotScan(
            name = name,
            time = time,
            location = Vector.Cartesian(x + sin(angle) * distance, y + cos(angle) * distance),
            velocity = Vector.Polar(headingRadians, velocity),
        )
    }
}

fun mul(x: Double): Double {
    val sign = sign(x)
    return if (sign == 0.0) 1.0 else sign
}

fun Graphics2D.fillOval(location: Vector.Cartesian, radius: Double) {
    fillOval((location.x - radius / 2).toInt(), (location.y - radius / 2).toInt(), radius.toInt(), radius.toInt())
}
