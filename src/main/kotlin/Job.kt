import kotlinx.coroutines.*

//runBlocking과 다른 코루틴 스코프를 만드는 스코프 빌더 : coroutineScope

suspend fun doOneTwoThree() = coroutineScope {//this: 부모 코루틴
    launch { // this : 자식 코루틴 1
        println("launch1: ${Thread.currentThread().name}");
        delay(1000L) //suspension point
        println("3!")
    }
    launch { // this : 자식 코루틴 2
        println("launch2 : ${Thread.currentThread().name}")
        delay(1000L) //suspension point
        println("1!")
    }
    launch { // this : 자식 코루틴 3
        println("launch3 : ${Thread.currentThread().name}")
        delay(500L) //suspension point
        println("2!")
    }
    println("4!")
}

//runBlocking vs coroutineScope
//runBlocking은 현재 쓰레드를 멈추게 만들고, 기다리지만
//coroutineScope는 현재 쓰레드를 멈추게 하지 않고 다른 누군가가 일을 하려고 하면 일을 할 수 있다.

fun main() = runBlocking {
    doOneTwoThree() //suspension point
    println("runBlocking: ${Thread.currentThread().name}")
    println("5")
    doOneTwoThree() //suspension point
}