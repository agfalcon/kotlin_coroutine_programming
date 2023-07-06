import kotlinx.coroutines.*

//runBlocking과 다른 코루틴 스코프를 만드는 스코프 빌더 : coroutineScope
//launch는 Job 객체를 반환하며 이를 통해 종료될 때까지 기다리거나 취소할 수 있다.
//Job 객체에 대해 join()을 하면 해당 객체 코드 블럭이 끝날 때 까지 기다린다.
//Job 객체에 대해 cancel()을 하면 해당 객체 코드 블럭 실행을 취소한다.

suspend fun doOneTwoThree() = coroutineScope {//this: 부모 코루틴
    val job = launch { // this : 자식 코루틴 1
        println("launch1: ${Thread.currentThread().name}");
        delay(1000L) //suspension point
        println("3!")
    }
    job.join() // suspension point

    launch { // this : 자식 코루틴 2
        println("launch2 : ${Thread.currentThread().name}")
        println("1!")
    }
    launch { // this : 자식 코루틴 3
        println("launch3 : ${Thread.currentThread().name}")
        delay(500L) //suspension point
        println("2!")
    }
    println("4!")
}

suspend fun cancelOneTwoThree() = coroutineScope {
    val job1 = launch {
        println("launch1 : ${Thread.currentThread().name}")
        delay(1000L)
        println("1!")
    }
    val job2 = launch {
        println("launch2 : ${Thread.currentThread().name}")
        println("2!")
    }
    val job3 = launch {
        println("launch3 : ${Thread.currentThread().name}")
        delay(500L)
        println("3!")
    }
    delay(800L)
    job1.cancel()
    job2.cancel()
    job3.cancel()
    println("4!")
}

suspend fun doCountDone() = coroutineScope {
    val job1 = launch(Dispatchers.Default){
        var i = 1
        var nextTime = System.currentTimeMillis() + 100L

        while(i<=10){
            val currentTime = System.currentTimeMillis()
            if(currentTime>=nextTime) {
                println(i)
                nextTime = currentTime + 100L
                i++
            }
        }
    }

    delay(200L)
    job1.cancelAndJoin()
    println("doCount Done!")
}



//runBlocking vs coroutineScope
//runBlocking은 현재 쓰레드를 멈추게 만들고, 기다리지만
//coroutineScope는 현재 쓰레드를 멈추게 하지 않고 다른 누군가가 일을 하려고 하면 일을 할 수 있다.

fun main() = runBlocking {
    doCountDone()
}