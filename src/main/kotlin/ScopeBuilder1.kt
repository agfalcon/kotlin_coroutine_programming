import kotlinx.coroutines.*

//runBlocking은 코루틴을 만들고 코드 블록이 수행이 끝날 떄까지 runBlocking 다음의 코드를 수행하지 못하게 막는다.
//코루틴을 만드는 함수를 코루틴 빌더라고 한다.

fun main() = launchBuilderFunc()

fun runBlockingFunc(){
    runBlocking {
        println(coroutineContext)// 코루틴 스코프는 코루틴을 제대로 처리하기 위한 정보, coroutineContext를 가지고 있다. coroutineContext는 여러가지 정보를 가지고 있음.
        println(this) //runBlocking 안에서 this를 수행하면 코루틴이 수신 객체인 것을 알 수 있다. 즉 코드 블런 안에서 모든 코루틴 기능 사용 가능
        println(Thread.currentThread().name)
        println("Hello")
    }
    println("World")
}
//BlockingCoroutine은 CoroutineScope의 자식.
//코틀린 코루틴을 쓰는 모든 곳에는 코루틴 스코프가 있다고 생각하면 된다.
//즉, 코루틴의 시작은 코루틴 스코프다.

fun launchBuilderFunc() = runBlocking{
    launch{
        println("launch: ${Thread.currentThread().name}")
        delay(500L) // 이곳에 똑같이 delay함수를 주면 launch 블록 코드도 잠들게 되면서 먼저 꺠어난 runBlocking 코드가 먼저 thread를 선점해서 실행된다.
        println("World!")
    }
    println("runBlocking: ${Thread.currentThread().name}")
    delay(500L) // delay함수는 해당 시간(ms) 동안 쉬는데, thread를 해제하고 쉰다. 이 코드에서는 runBlocking이 쉬면서 main thread를 해제하기 때문에 launch가 블록안의 코드들이 먼저 실행된다.
    println("Hello")
}

//launch -> 코루틴 빌더
//launch의 특징은 "할 수 있다면 다른 코루틴 코드를 같이 수행"
//2개의 코루틴 빌더가 있는데 runBlocking이 먼저 실행되고 launch는 runBlocking이 끝날 때까지 기다린 후 실행

//이처럼 코드가 잠드는 부분을 suspension point라고 부르고, 코루틴은 이처럼 여러 코루틴이 협조하면서 활용할 수 있다.