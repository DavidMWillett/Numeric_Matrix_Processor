import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    // put your code here
    val a = scanner.nextInt()
    val b = scanner.nextInt()
    val c = scanner.nextInt()
    val p = (a + b + c) / 2.0
    println(Math.sqrt(p * (p - a) * (p - b) * (p -c)))
}
