import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.system.*


suspend fun getRandom1(): Int {
    delay(1000L)
    return Random.nextInt(0,500)
}

suspend fun getRandom2(): Int {
    delay(1000L)
    return Random.nextInt(0,500)
}

fun ex1() = runBlocking {
    val elapsedTime = measureTimeMillis {
        val value1 = getRandom1()
        val value2 = getRandom2()
        println("$value1 + $value2 = ${value1 + value2}")
    }
    println(elapsedTime)
} // 하나의 코루틴에서 getRandom1, getRandom2가 실행되어 2035ms가 소요됨
//여러 개의 코루틴을 만든다면 suspend 함수를 한번에 수행할 수 있음.
//async 키워드를 이용하면 동시에 다른 블록을 수행 가능. launch와 비슷한 코루틴 빌더지만 async는 수행 결과를 await 키워드를 통해 받을 수 있다는 차이가 있음.
//await 키워드를 만나는 경우 async 블록이 수행이 끝났는지 확인하고 아직 끝나지 않았다면 suspend 되었다 나중에 다시 깨어나고 반환값을 받아옴.

fun ex2() = runBlocking{
    val elapsedTime = measureTimeMillis {
        val value1 = async{
            getRandom1()
        }
        val value2 = async {
            getRandom2()
        }
        println("${value1.await()} + ${value2.await()} = ${value1.await() + value2.await()}")
    }
    println(elapsedTime)
}// async를 이용해 코루틴을 새로 생성하고 await를 이용해 값의 결과를 기다렸다가 출력한다. 1033ms가 소요됨.

fun layAsync() = runBlocking{
    val elapsedTime = measureTimeMillis {
        val value1 = async(start = CoroutineStart.LAZY) {
            getRandom1()
        }
        val value2 = async(start = CoroutineStart.LAZY) {
            getRandom2()
        }

        value1.start()
        value2.start()
        println("${value1.await()} + ${value2.await()} = ${value1.await() + value2.await()}")
    }
    println(elapsedTime)
}// async 키워드를 사용하는 순간 코드 블록이 수행을 준비하는데, async(start = CoroutineStart.LAZY)로 인자를 전달하면 우리가 원하는 순간 수행을 준비하게 할 수 있고, start 메서드를 이용해 수행을 할 수 있다.

suspend fun getRandom1WithException(): Int {
    try{
        delay(1000L)
        return Random.nextInt(0, 500)
    }
    finally {
        println("getRandom1 is cancelled.")
    }
}

suspend fun getRandom2WithException(): Int {
    delay(500L)
    throw  IllegalStateException()
}


suspend fun structuralAsync() = coroutineScope {//부모 코루틴
    val value1 = async {//자식 코루틴1
        getRandom1WithException()
    }
    val value2 = async {//자식 코루틴2
        getRandom2WithException()
    }
    try{
        println("${value1.await()}  + ${value2.await()} = ${value1.await() + value2.await()}")
    }
    finally {
        println("structuralAcsync is cancelled.")
    }
}// 여러가지를 함께 수행할 때 한가지에 문제가 생기면 다른 것들도 취소해야하는 경우 계층적으로 묶어서 한번에 취소하면 된다.

//fun main() = runBlocking {
//    try{
//        structuralAsync()
//    }
//    catch(e: IllegalStateException){
//        println("IllegalStateException failed: $e")
//    }
//}