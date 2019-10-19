@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson3.task1.digitNumber
import lesson3.task1.isPrime
import java.io.File.separator
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var number = 0.0
    for (element in v) {
        number += sqr(element)
    }
    return sqrt(number)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    return if (list.isEmpty()) 0.0 else list.sum() / list.size
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val average = mean(list)
    for (i in 0 until list.size) list[i] -= average
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var c = 0
    for (i in 0 until a.size) c += a[i] * b[i]
    return c
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var px = 0
    var x1 = 1
    for (i in 0 until p.size) {
        px += p[i] * x1
        x1 *= x
    }
    return px
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    var sum = 0
    for (i in 0 until list.size) {
        sum += list[i]
        list[i] = sum
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    val result = mutableListOf<Int>()
    var n1 = n
    var number = 2
    while (n1 != 1) {
        while (n1 % number == 0) {
            result.add(number)
            n1 /= number
        }
        number += 1
    }
    return result
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val result = mutableListOf<Int>()
    var n1 = n
    do {
        result.add(0, n1 % base)
        n1 /= base
    } while (n1 >= base)
    if (n1 > 0) result.add(0, n1 % base)
    return result
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    return convert(n, base).joinToString(separator = "") { if (it < 10) "$it" else (it + 87).toChar().toString() }
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var count = 0
    var dgr = 1
    for (i in (digits.size - 1) downTo 0) {
        count += digits[i] * dgr
        dgr *= base
    }
    return count
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    var el: String
    var number = 0
    var dgr = 1
    for (i in str.length - 1 downTo 0) {
        el = if (str[i].toInt() < 97) str[i].toString() else (str[i].toInt() - 87).toString()
        number += el.toInt() * dgr
        dgr *= base
    }
    return number
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var n1 = n
    var s = ""
    when {
        (n1 % 10) in 1..3 -> for (i in 1 .. n1 % 10) s += "I"
        (n1 % 10) == 4 -> s += "VI"
        (n1 % 10) == 5 -> s += "V"
        (n1 % 10) in 6..8 -> {for (i in 1 .. n1 % 10 - 5) s += "I"; s += "V"}
        (n1 % 10) == 9 -> s += "XI"
        else -> s += ""
    }
    n1 /= 10

    when {
        (n1 % 10) in 1..3 -> for (i in 1 .. n1 % 10) s += "X"
        (n1 % 10) == 4 -> s += "LX"
        (n1 % 10) == 5 -> s += "L"
        (n1 % 10) in 6..8 -> {for (i in 1 .. n1 % 10 - 5) s += "X"; s += "L"}
        (n1 % 10) == 9 -> s += "CX"
        else -> s += ""
    }
    n1 /= 10

    when {
        (n1 % 10) in 1..3 -> for (i in 1 .. n1 % 10) s += "C"
        (n1 % 10) == 4 -> s += "DC"
        (n1 % 10) == 5 -> s += "D"
        (n1 % 10) in 6..8 -> {for (i in 1 .. n1 % 10 - 5) s += "C"; s += "D"}
        (n1 % 10) == 9 -> s += "MC"
        else -> s += ""
    }
    n1 /= 10
    for (i in 1 .. n1) s += "M"
    return s.reversed()
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun units(n: Int): String {
    return when (n) {
        1 -> "один "
        2 -> "два "
        3 -> "три "
        4 -> "четыре "
        5 -> "пять "
        6 -> "шесть "
        7 -> "семь "
        8 -> "восемь "
        9 -> "девять "
        else -> " "
    }
}

fun unitsforthous(n: Int): String {
    return when (n) {
        1 -> "одна "
        2 -> "две "
        else -> units(n)
    }
}

fun dozens(n: Int): String {
    return when (n / 10 % 10) {
        1 -> when (n % 10) {
            1 -> "одиннадцать"
            2 -> "двенадцать"
            3 -> "тринадцать"
            4 -> "четырнадцать"
            5 -> "пятнадцать"
            6 -> "шестнадцать"
            7 -> "семнадцать"
            8 -> "восемнадцать"
            9 -> "девятнадцать"
            else -> ""
        }
        2 -> "двадцать " + units(n % 10)
        3 -> "тридцать " + units(n % 10)
        4 -> "сорок " + units(n % 10)
        in 5 .. 8 -> units(n / 10 % 10).trim() + "десят " + units(n % 10)
        9 -> "девяносто " + units(n % 10)
        else  -> units(n % 10) + " "
    }
}

fun dozensforthous(n: Int): String {
    return when (n / 10 % 10) {
        1 -> when (n % 10) {
            1 -> "одиннадцать "
            2 -> "двенадцать "
            3 -> "тринадцать "
            4 -> "четырнадцать "
            5 -> "пятнадцать "
            6 -> "шестнадцать "
            7 -> "семнадцать "
            8 -> "восемнадцать "
            9 -> "девятнадцать "
            else -> " "
        }
        2 -> "двадцать " + unitsforthous(n % 10)
        3 -> "тридцать " + unitsforthous(n % 10)
        4 -> "сорок " + unitsforthous(n % 10)
        in 5 .. 8 -> units(n / 10 % 10).trim() + "десят " + units(n % 10)
        9 -> "девяносто " + unitsforthous(n % 10)
        else  -> unitsforthous(n % 10)
    }
}

fun hund(n: Int): String {
    return when (n / 100 % 10) {
        1 -> "сто " + dozens(n % 100)
        2 -> "двести " + dozens(n % 100)
        3 -> "триста " + dozens(n % 100)
        4 -> "четыреста " + dozens(n % 100)
        in 5..9 -> units(n / 100 % 10).trim() + "сот " + dozens(n % 100)
        else -> dozens(n % 100)
    }
}

fun hundforthous(n: Int): String {
    return when (n / 100 % 10) {
        1 -> "сто " + dozensforthous(n % 100)
        2 -> "двести " + dozensforthous(n % 100)
        3 -> "триста " + dozensforthous(n % 100)
        4 -> "четыреста " + dozensforthous(n % 100)
        in 5..9 -> units(n / 100 % 10).trim() + "сот " + dozensforthous(n % 100)
        else -> dozensforthous(n % 100)
    }
}

fun thousands(n: Int): String {
    return when (n / 10 % 10) {
        1 -> "тысяч "
        else  -> when (n % 10) {
            1 -> "тысяча "
            in 2 .. 4 -> "тысячи "
            in 5 .. 9 -> "тысяч "
            else -> "тысяч "
        }
    }
}

fun russian(n: Int): String {
    return if (digitNumber(n) <= 3) hund(n)
    else (hundforthous(n / 1000) + thousands(n / 1000 % 100) + hund(n % 1000)).trim()
}
