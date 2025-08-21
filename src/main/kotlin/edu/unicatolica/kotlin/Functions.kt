package edu.unicatolica.kotlin

//
// KotlinClassPractice.kt
// Practical class content demonstrating core Kotlin concepts:
// - Variables and types
// - Data types (Kotlin vs Java primitives/boxed commentary)
// - Conditionals as expressions
// - Loops: for / while / do-while (and when to use them)
// - Functions and Unit
// - Lambdas.
// - Functional programming basics
// - Higher-order functions: advantages and applications
// - Generics (types and functions)
// - Extension functions (concept + examples)
//
// How to run (JVM):
// kotlinc KotlinClassPractice.kt -include-runtime -d app.jar
// java -jar app.jar
//

import kotlin.math.pow
import kotlin.random.Random
import kotlin.math.max

// ===== Utility to print section headers =====
fun section(title: String) {
    println("\n" + "=".repeat(72))
    println(title)
    println("=".repeat(72))
}

// ===== 1) Variables and Types =====
fun variablesAndTypes() {
    section("1) Variables and Types")

    // val = immutable reference; var = mutable reference
    val lang: String = "Kotlin"      // explicit type
    var counter = 0                   // type inference (Int)

    println("val lang = $lang")
    println("var counter = $counter")
    counter += 3
    println("counter after += 3 => $counter")

    // Kotlin exposes only reference types at language level (Int, Double, String...)
    // The compiler/VM may optimize to primitives when possible.
    val i: Int = 42
    val nullableI: Int? = i // nullable forces boxing
    println("Int::class.javaPrimitiveType = ${Int::class.javaPrimitiveType} (mapped primitive)")
    println("nullable Int is boxed at runtime? class = ${nullableI!!::class.java.name}")
}

// ===== 2) Conditionals as expressions & 'when' =====
fun conditionalsAsExpressions(n: Int) {
    section("2) Conditionals as Expressions & when")

    // 'if' returns a value -> can be assigned directly
    val parity = if (n % 2 == 0) "even" else "odd"
    println("n=$n is $parity")

    // 'when' is a powerful switch-like expression
    val description = when {
        n == 0 -> "zero"
        n in 1..9 -> "one-digit positive"
        n < 0 -> "negative"
        else -> "positive (>= 10)"
    }
    println("Description: $description")
}

// ===== 3) Loops: for / while / do-while (and when to use them) =====
fun loopsDemo() {
    section("3) Loops: for / while / do-while")

    // for: best for ranges/collections
    print("for 1..5: ")
    for (i in 1..5) print("$i ")
    println()

    val list = listOf("a", "b", "c")
    print("for over list: ")
    for (x in list) print("$x ")
    println()

    // while: condition-checked-before style
    var x = 3
    print("while x>0: ")
    while (x > 0) {
        print("$x ")
        x--
    }
    println()

    // do-while: runs at least once (condition checked after)
    var y = 0
    print("do-while y<1: ")
    do {
        print("$y ")
        y++
    } while (y < 1)
    println()

    // When to use: for (collections/ranges), while (unknown iterations), do-while (ensure first run)
}

// ===== 4) Functions and Unit =====
fun greet(name: String): String = "Hello, $name"
fun logLine(msg: String): Unit { // Unit can be omitted; kept for demonstration
    println("[LOG] $msg")
}

fun functionsAndUnit() {
    section("4) Functions and Unit")

    val message = greet("James")
    println("greet(...) returned: $message")

    // Every function returns something. If nothing explicit, it returns Unit.
    val unitValue: Unit = logLine("This function returns Unit")
    println("Captured Unit value: $unitValue (prints 'kotlin.Unit')")

    // Single-expression functions are concise and still typed:
    fun square(x: Int) = x * x
    println("square(5) = ${square(5)}")
}

// ===== 5) Lambdas =====
fun lambdasDemo() {
    section("5) Lambdas")

    // A lambda is an anonymous function value
    val double: (Int) -> Int = { it * 2 }       // 'it' used when only one parameter
    val power: (Double) -> Double = { x -> x.pow(2.0) } // explicit param name

    println("double(7) = ${double(7)}")
    println("power(3.0) = ${power(3.0)}")

    // Multi-parameter lambda example with reduce
    val sum = listOf(1, 2, 3, 4).reduce { acc, n -> acc + n }
    println("reduce sum = $sum")
}

// ===== 6) Functional Programming basics =====
fun functionalProgrammingDemo() {
    section("6) Functional Programming Basics")

    val nums = listOf(1, 2, 3, 4, 5, 6)
    val evenSquares = nums
        .filter { it % 2 == 0 }     // keep even numbers
        .map { it * it }            // square them
    println("evenSquares from $nums = $evenSquares")

    // Declarative style helps readability and testing
    val productOfOdds = nums
        .filter { it % 2 == 1 }
        .fold(1) { acc, n -> acc * n }
    println("productOfOdds = $productOfOdds")
}

// ===== 7) Higher-Order Functions (HOF): receive/return functions =====
fun <T> applyTwice(x: T, f: (T) -> T): T = f(f(x))

fun operate(x: Int, y: Int, f: (Int, Int) -> Int): Int = f(x, y)

// Retry example: tries up to N times until op succeeds (no exception)
fun <T> retry(times: Int, op: () -> T): T {
    require(times > 0) { "times must be > 0" }
    var lastError: Throwable? = null
    repeat(times) {
        try { return op() } catch (t: Throwable) { lastError = t }
    }
    throw lastError ?: IllegalStateException("retry failed without error?")
}

fun higherOrderFunctionsDemo() {
    section("7) Higher-Order Functions (advantages + applications)")

    // Advantages: reuse, abstraction of behavior, clearer APIs, composition
    val inc = { n: Int -> n + 1 }
    val result = applyTwice(10, inc) // (((10 + 1) + 1)) = 12
    println("applyTwice(10, inc) = $result")

    val max = operate(3, 7, ::max)
    val sum = operate(3, 7) { a, b -> a + b }
    println("operate max = $max, operate sum = $sum")

    // Applications: retry logic, strategies, pipelines
    var attempts = 0
    val value = retry(3) {
        attempts++
        if (attempts < 3) error("Transient failure #$attempts")
        "Success on attempt $attempts"
    }
    println("retry result: $value")
}

// ===== 8) Generics (types and functions) =====
class Box<T>(val value: T) {
    fun get(): T = value
}

fun <T> pairOf(a: T, b: T) = Pair(a, b)

fun <T> headOr(default: T, list: List<T>): T = if (list.isNotEmpty()) list.first() else default

fun genericsDemo() {
    section("8) Generics: types and functions")

    val intBox = Box(10)
    val strBox = Box("hello")
    println("intBox.get() = ${intBox.get()} | strBox.get() = ${strBox.get()}")

    val p1 = pairOf(1, 2)
    val p2 = pairOf("A", "B")
    println("pairOf Ints = $p1 | pairOf Strings = $p2")

    println("headOr(default=99, listOf()) = ${headOr(99, listOf<Int>())}")
    println("headOr(default=\"X\", listOf(\"A\",\"B\")) = ${headOr("X", listOf("A","B"))}")
}

// ===== 9) Extension Functions =====
fun String.lastWord(): String =
    trim().split(Regex("\\s+")).lastOrNull() ?: ""

fun List<Int>.averageOrZero(): Double =
    if (isEmpty()) 0.0 else average()

fun String.isEmail(): Boolean =
    Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(this)

fun extensionFunctionsDemo() {
    section("9) Extension Functions")

    val phrase = "Hola mundo Kotlin"
    println("lastWord(\"$phrase\") = ${phrase.lastWord()}")

    val empty = emptyList<Int>()
    val some = listOf(10, 20, 30)
    println("empty.averageOrZero() = ${empty.averageOrZero()} | some.averageOrZero() = ${some.averageOrZero()}")

    val email = "user@example.com"
    val bad = "not-an-email"
    println("$email.isEmail() = ${email.isEmail()} | $bad.isEmail() = ${bad.isEmail()}")
}

// ===== 10) Mini practicals that touch multiple topics at once =====
fun miniPracticals() {
    section("10) Mini Practicals (multi-topic)")

    // A) Sum of evens vs product of odds (loops + conditionals as expressions)
    val data = listOf(1, 2, 3, 4, 5)
    var sumEvens = 0
    var productOdds = 1
    for (n in data) {
        if (n % 2 == 0) sumEvens += n else productOdds *= n
    }
    val moreEvensOrOdds = if (data.count { it % 2 == 0 } >= data.count { it % 2 != 0 }) "evens" else "odds"
    println("sumEvens=$sumEvens, productOdds=$productOdds, majority=$moreEvensOrOdds")

    // B) Simple pipeline (FP + lambdas + HOF)
    val raw = listOf("kotlin", "is", "awesome", "and", "fun")
    val grouped = raw
        .filter { it.length > 2 }
        .map { it.uppercase() }
        .groupBy { it.first() }
    println("pipeline grouped = $grouped")

    // C) Strategy via higher-order function (choose discount policy)
    val regular = { price: Double -> price }
    val student = { price: Double -> price * 0.9 }
    val vip = { price: Double -> price * 0.8 }

    fun priceWith(policy: (Double) -> Double, price: Double) = policy(price)
    println("price regular(100) = ${priceWith(regular, 100.0)}")
    println("price student(100) = ${priceWith(student, 100.0)}")
    println("price vip(100)     = ${priceWith(vip, 100.0)}")
}

// ===== main =====
fun main() {
    variablesAndTypes()
    conditionalsAsExpressions(n = 7)
    loopsDemo()
    functionsAndUnit()
    lambdasDemo()
    functionalProgrammingDemo()
    higherOrderFunctionsDemo()
    genericsDemo()
    extensionFunctionsDemo()
    miniPracticals()

    section("Done ✅")
}
