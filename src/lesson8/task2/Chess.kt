@file:Suppress("UNUSED_PARAMETER")

package lesson8.task2

import lesson8.task3.Graph
import java.lang.IllegalArgumentException
import kotlin.math.abs

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String {
        val colNumbers = listOf(1, 2, 3, 4, 5, 6, 7, 8)
        if (column !in colNumbers || row !in colNumbers) return ""
        val columns = listOf("a", "b", "c", "d", "e", "f", "g", "h")
        for (i in colNumbers.indices) {
            if (colNumbers[i] == column) {
                val x = columns[i]
                return "$x$row"
            }
        }
        return ""
    }
}

/**
 * Простая
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square {
    if (notation.length != 2) throw IllegalArgumentException()
    val columns = listOf("a", "b", "c", "d", "e", "f", "g", "h")
    val colNumbers = listOf(1, 2, 3, 4, 5, 6, 7, 8)
    if (notation[0].toString() !in columns || notation[1].toString().toIntOrNull() !in colNumbers)
        throw IllegalArgumentException()
    return Square(columns.indexOf(notation[0].toString()) + 1, notation[1].toString().toInt())
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int {
    val colNumbers = listOf(1, 2, 3, 4, 5, 6, 7, 8)
    if (start.column !in colNumbers || start.row !in colNumbers || end.column !in colNumbers || end.row !in colNumbers)
        throw IllegalArgumentException()
    return if (start == end) 0
    else if (start.row == end.row || start.column == end.column) 1
    else 2
}

/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> {
    val result = mutableListOf<Square>(start)
    if (start == end) return result
    else if (start.row == end.row || start.column == end.column) return result + end
    else result += Square(start.column, end.row)
    return result + end
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int {
    val colNumbers = listOf(1, 2, 3, 4, 5, 6, 7, 8)
    if (start.column !in colNumbers || start.row !in colNumbers || end.column !in colNumbers || end.row !in colNumbers)
        throw IllegalArgumentException()
    if ((start.row + start.column) % 2 != (end.row + end.column) % 2) return -1
    return if (start == end) 0
    else if (abs(start.row - end.row) == abs(start.column - end.column)) 1
    else 2
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun bishopTrajectory(start: Square, end: Square): List<Square> {
    if ((start.row + start.column) % 2 != (end.row + end.column) % 2) return listOf()
    val result = mutableListOf(start)
    if (start == end) return result
    else if (abs(start.row - end.row) == abs(start.column - end.column)) return result + end
    for (i in 1..8) {
        for (j in 1..8) {
            if (abs(start.row - i) == abs(start.column - j) && abs(end.row - i) == abs(end.column - j)) {
                result.add(Square(j, i))
                return result + end
            }
        }
    }
    return result + end
}

/**
 * Средняя
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int {
    if (!checkSquare(start) || !checkSquare(end)) throw IllegalArgumentException()
    return kingTrajectory(start, end).size - 1
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> {
    val result = mutableListOf(start)
    var checkSquare = start
    while (end.row != checkSquare.row) {
        if (end.row > checkSquare.row) {
            checkSquare = if (end.column > checkSquare.column) Square(checkSquare.column + 1, checkSquare.row + 1)
            else if (end.column < checkSquare.column) Square(checkSquare.column - 1, checkSquare.row + 1)
            else Square(checkSquare.column, checkSquare.row + 1)
        } else if (end.row < checkSquare.row) {
            checkSquare = if (end.column > checkSquare.column) Square(checkSquare.column + 1, checkSquare.row - 1)
            else if (end.column < checkSquare.column) Square(checkSquare.column - 1, checkSquare.row - 1)
            else Square(checkSquare.column, checkSquare.row - 1)
        }
        result.add(checkSquare)
    }
    while (end.column != checkSquare.column) {
        checkSquare = if (end.column < checkSquare.column) Square(checkSquare.column - 1, checkSquare.row)
        else Square(checkSquare.column + 1, checkSquare.row)
        result.add(checkSquare)
    }
    return result
}

/**
 * Сложная
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
fun checkSquare(square: Square): Boolean {
    val colNumbers = listOf(1, 2, 3, 4, 5, 6, 7, 8)
    if (square.column !in colNumbers || square.row !in colNumbers) return false
    return true
}

fun knightMoveNumber(start: Square, end: Square): Int {
    if (!checkSquare(start) || !checkSquare(end)) throw IllegalArgumentException()
    return knightTrajectory(start, end).size - 1
}

/**
 * Очень сложная
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun knightTrajectory(start: Square, end: Square): List<Square> {
    val result = listOf(start)
    if (start == end) return result
    val letters = mutableListOf("a", "b", "c", "d", "e", "f", "g", "h")
    val g = Graph()
    for (i in 1..8) {
        for (j in 1..8) {
            val x = letters[i - 1]
            g.addVertex("$x$j")
        }
    }
    for (i in 1..8) {
        for (j in 1..8) {
            val k = letters[i - 1]
            if (checkSquare(Square(i + 2, j + 1))) {
                val x = letters[i + 2 - 1]
                val y = j + 1
                g.connect("$k$j", "$x$y")
            }

            if (checkSquare(Square(i + 1, j + 2))) {
                val x = letters[i + 1 - 1]
                val y = j + 2
                g.connect("$k$j", "$x$y")
            }

            if (checkSquare(Square(i - 1, j + 2))) {
                val x = letters[i - 1 - 1]
                val y = j + 2
                g.connect("$k$j", "$x$y")
            }

            if (checkSquare(Square(i - 2, j + 1))) {
                val x = letters[i - 2 - 1]
                val y = j + 1
                g.connect("$k$j", "$x$y")
            }

            if (checkSquare(Square(i - 2, j - 1))) {
                val x = letters[i - 2 - 1]
                val y = j - 1
                g.connect("$k$j", "$x$y")
            }

            if (checkSquare(Square(i - 1, j - 2))) {
                val x = letters[i - 1 - 1]
                val y = j - 2
                g.connect("$k$j", "$x$y")
            }

            if (checkSquare(Square(i + 1, j - 2))) {
                val x = letters[i + 1 - 1]
                val y = j - 2
                g.connect("$k$j", "$x$y")
            }

            if (checkSquare(Square(i + 2, j - 1))) {
                val x = letters[i + 2 - 1]
                val y = j - 1
                g.connect("$k$j", "$x$y")
            }
        }
    }
    return g.way(start.notation(), end.notation()).map { square(it.name) }
}