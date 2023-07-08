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

fun main() = newJobFunc()