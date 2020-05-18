import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    // put your code here
    val u1 = scanner.nextDouble()
    val u2 = scanner.nextDouble()
    val v1 = scanner.nextDouble()
    val v2 = scanner.nextDouble()
    val cosTheta = (u1 * v1 + u2 * v2) / (Math.sqrt(u1 * u1 + u2 * u2) * Math.sqrt(v1 * v1 + v2 * v2))
    println(Math.acos(cosTheta) * 180.0 / Math.PI)
}
