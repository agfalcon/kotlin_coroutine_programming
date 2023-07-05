import kotlinx.coroutines.*

//runBlocking은 코루틴을 만들고 코드 블록이 수행이 끝날 떄까지 runBlocking 다음의 코드를 수행하지 못하게 막는다.
//코루틴을 만드는 함수를 코루틴 빌더라고 한다.

fun main() = hierarchyFunc()

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

//이처럼 코드가 잠드는 부분을 suspension point라고 부르고, 코루틴은 이처럼 여러 코루틴이 단일 쓰레드 내에서도 협조하면서 잘 활용될 수 있다.


fun delayFunc1() = runBlocking{
    launch{
        println("launch1 : ${Thread.currentThread().name}")
        delay(1000L)
        println("3!")
    }
    launch{
        println("launch2 : ${Thread.currentThread().name}")
        println("1!")
    }
    println("runBlocking : ${Thread.currentThread().name}")
    delay(500L)
    println("2!")
}

//위의 코드는 2개의 launch와 runBlocking 코루틴이 있고 이를 delay를 통해 순서를 정하는 모습이다. 순서를 예상해보면
//1.runBlocking 코드가 실행된다.
//2.delay함수에서 suspend 되어 launch1 코드 블럭이 실행된다.
//3.delay함수에서 suspend 되어 launch2의 코드가 모두 실행된다.
//4.runBlocking의 나머지 코드가 실행된다.
//5.launch1 나머지 코드가 실행된다.

fun sleepFunc() = runBlocking{
    launch{
        println("launch : ${Thread.currentThread().name}")
        println("World")
    }
    println("runBlocking: ${Thread.currentThread().name}")
    Thread.sleep(500L) // sleep은 delay와 같이 코드를 쉬어가지만 thread를 독점하고 있기 때문에 runBlocking 코드 블록 후에 launch가 실행된다.
    println("Hello")
}



//코루틴은 기본적으로 계층적이고, 상위 코루틴은 하위 코루틴을 끝까지 책임진다.
fun hierarchyFunc(){
    runBlocking{
        launch{
            println("launch1 : ${Thread.currentThread().name}")
            delay(1000L)
            println("3!")
        }
        launch{
            println("launch2 : ${Thread.currentThread().name}")
            println("1!")
        }
        println("runBlocking : ${Thread.currentThread().name}")
        delay(500L)
        println("2!")
    }
    println("4!")
}

//runBlocking은 두 launch가 속해 있는데 계층화되어 구조적이다.
//runBlocking은 그 속에 포함된 launch가 다 끝나기 전까지 종료되지 않는다.