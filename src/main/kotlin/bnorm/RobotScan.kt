package bnorm

import bnorm.geo.Vector

data class RobotScan(
    val name: String,
    val time: Long,
    val location: Vector.Cartesian,
    val velocity: Vector.Polar,
) {
    var prev: RobotScan? = null
        set(value) {
            value?.next = this
            field = value
        }
    var next: RobotScan? = null
}
