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
} //GlobalScope은 어떤 계층에도 속하지 않고 영원히 동작하는 전역 스코프. 프로그래밍에서 전역 객체를 잘 사용하지 않는 것 처럼
//GlobalScope도 잘 사용하지 않는다.