import kotlinx.coroutines.*

fun newJobFunc() = runBlocking<Unit> {
    val job = launch {
        launch(Job()){
            println(coroutineContext[Job])
            println("launch1: ${Thread.currentThread().name}")
            delay(1000L)
            println("3!")
        }

        launch {
            println(coroutineContext[Job])
            println("launch2 : ${Thread.currentThread().name}")
            delay(1000L)
            println("1!")
        }
    }

    delay(500L)
    job.cancelAndJoin()
    delay(1000L)
}//기본적으로 laucn로 코루틴을 생성할 때 job은 자신을 둘러싼 코루틴을 부모 코루틴으로 인식하여 자동적으로 생선된다. 하지만, 명시적으로 Job() 객체와 함께 launch를 사용하면 부모 객체를 인식하지 못해 부모가 취소되어도 취소되지 않고 끝까지 코드를 실행하는 것을 볼 수 있다.


@OptIn(ExperimentalStdlibApi::class)
fun coroutineElementFunc() = runBlocking<Unit> {
    launch {
        launch(Dispatchers.IO + CoroutineName("launch1")) {//코루틴 엘리먼트를 한번에 사용 가능. + 연산을 사용해 엘리먼트를 합칠 수 있다. 자식 코루틴의 컨텍스트 = 부모 코루틴 컨텍스트 + 자식 코루틴 컨텍스트(코루틴 엘리먼트)
            println("launch1: ${Thread.currentThread().name}")
            println(coroutineContext[CoroutineDispatcher])
            println(coroutineContext[CoroutineName])
            delay(5000L)
        }
        launch(Dispatchers.Default + CoroutineName("launch2")) {
            println("launch2: ${Thread.currentThread().name}")
            println(coroutineContext[CoroutineDispatcher])
            println(coroutineContext[CoroutineName])
            delay(5000L)
        }
    }
}
//fun main() = coroutineElementFunc()