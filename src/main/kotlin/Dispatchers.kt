import kotlinx.coroutines.*

//fun main() = unConfinedDispatcherFunc()

fun dispatchersFunc() = runBlocking<Unit> {
    launch{
        println("context of parent -> ${Thread.currentThread().name}")
    }

    launch(Dispatchers.Default) {
        println("Default -> ${Thread.currentThread().name}")
    }

    launch(Dispatchers.IO){
        println("IO -> ${Thread.currentThread().name}")
    }

    launch(Dispatchers.Unconfined) {
        println("Unconfined -> ${Thread.currentThread().name}")
    }

    launch(newSingleThreadContext("KGB")){
        println("newSingleThreadContext -> ${Thread.currentThread().name}")
    }
}
//Default는 코어 수에 비례하는 스레드 풀에서 수행
//IO는 코어 수 보다 훨씬 많은 스레드를 가지는 스레드 풀이다. IO 작업은 CPU를 덜 소모하기 떄문이다.
//Unconfined는 어디에도 속하지 않는다. 지금 시점에는 부모의 스레드에서 수행될 것이다.
//newSingleThreadContext는 항상 새로운 스레드를 만든다.


fun unConfinedDispatcherFunc() = runBlocking<Unit> {
    async(Dispatchers.Unconfined){
        repeat(30){
            println("Unconfined -> ${Thread.currentThread().name}")
            delay(100L)
        }
    }
}//Unconfined는 중단점 이후에 어느 dispatcher에서 수행될지 에측하기 어렵다. 가능하면 확실한 dispatcher을 사용하는 것이 좋다.