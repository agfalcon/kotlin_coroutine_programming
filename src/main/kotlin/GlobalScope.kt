import kotlin.system.*
import kotlinx.coroutines.*
import kotlin.random.Random
suspend fun printRandom() {
    delay(500L)
    println(Random.nextInt(0, 500))
}

fun main() {
    val job = GlobalScope.launch(Dispatchers.IO){
        printRandom()
    }
    Thread.sleep(1000L)
}