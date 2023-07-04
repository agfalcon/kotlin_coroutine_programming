import kotlinx.coroutines.*

//runBlocking은 코루틴을 만들고 코드 블록이 수행이 끝날 떄까지 runBlocking 다음의 코드를 수행하지 못하게 막는다.
//코루틴을 만드는 함수를 코루틴 빌더라고 한다.

fun main() = runBlocking {
    println(this) //runBlocking 안에서 this를 수행하면 코루틴이 수신 객체인 것을 알 수 있다. 즉 코드 블런 안에서 모든 코루틴 기능 사용 가능
    println(Thread.currentThread().name)
    println("Hello")
}

//코틀린 코루틴을 쓰는 모든 곳에는 코루틴 스코프가 있다고 생각하면 된다.
//즉, 코루틴의 시작은 코루틴 스코프다.