@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import kotlin.system.exitProcess

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
//fun main() {
//}
/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val x = listOf<String>(
        "января", "февраля", "марта", "апреля", "мая", "июня", "июля",
        "августа", "сентября", "октября", "ноября", "декабря"
    )
    val parts = str.split(" ")
    if (parts.size != 3) return ""
    val date = parts[0].toInt()
    var month = parts[1]
    val year = parts[2].toInt()
    for (i in 0 until x.size) if (month == x[i]) month = (i + 1).toString()
    if (month == parts[1]) return ""
    return when {
        ((month == "1") || (month == "3") || (month == "5") || (month == "7") || (month == "8") ||
                (month == "10") || (month == "12")) && (date > 31) -> ""
        ((month == "4") || (month == "6") || (month == "9") || (month == "11")) && (date > 30) -> ""
        ((month == "2") && (year % 400 == 0 ||
                (year % 100 != 0 && year % 4 == 0)) && (date > 29)) -> ""
        ((month == "2") && (year % 400 != 0) && (year % 4 != 0 || year % 100 == 0) && (year != 0) && (date > 28)) -> ""
        date == 0 -> ""
        else -> "${twoDigitStr(date)}.${twoDigitStr(month.toInt())}.$year"
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val dateInMonths = mapOf<String, String>(
        "января" to "01", "февраля" to "02",
        "марта" to "03", "апреля" to "04", "мая" to "05", "июня" to "06", "июля" to "07", "августа" to "08",
        "сентября" to "09", "октября" to "10", "ноября" to "11", "декабря" to "12"
    )
    val parts = digital.split(".")
    if (parts.size != 3) return ""
    val date = parts[0]
    var month = parts[1]
    for ((names, dates) in dateInMonths) if (month == dates) month = names
    if (month == parts[1]) return ""
    val year = parts[2]
    return when {
        ((month == "января") || (month == "марта") || (month == "мая") || (month == "июля") ||
                (month == "августа") || (month == "октября") || (month == "декабря")) && (date.toInt() > 31) -> ""
        ((month == "апреля") || (month == "июня") || (month == "сентября") ||
                (month == "ноября")) && (date.toInt() > 30) -> ""
        ((month == "февраля") && (year.toInt() % 400 == 0 ||
                (year.toInt() % 100 != 0 && year.toInt() % 4 == 0)) && (date.toInt() > 29)) -> ""
        ((month == "февраля") && (year.toInt() % 400 != 0) && (year.toInt() % 4 != 0 || year.toInt() % 100 == 0) &&
                (year.toInt() != 0) && (date.toInt() > 28)) -> ""
        date.toInt() == 0 -> ""
        else -> "${date.toInt()} " + month + " ${year.toInt()}"
    }
}

fun main() {
    println(dateDigitToStr("29.02.8758800"))
}


/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    var result = ""
    val x = listOf<String>("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", " ", "+", "-", ")", "(")
    for (i in 0 until phone.length) {
        val y = phone[i].toString()
        if (y !in x) return ""
        if (phone.contains("()")) return ""
        if ((x.contains(y)) && (y != " ") && (y != "-") && (y != "(") && (y != ")")) result += y
    }
    return result
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (jumps == "") return -1
    var result = Int.MIN_VALUE
    val x = mutableSetOf<Char>()
    val str = "0123456789"
    for (chars in str) x.add(chars)
    val y = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-", "%", " ")
    for (chars in jumps) if (!y.contains(chars.toString())) return -1
    val parts = jumps.split(" ")
    for (part in parts) {
        if ((x.containsAll(part.toSet())) && (part.toInt() > result)) result = part.toInt()
    }
    return if (result == Int.MIN_VALUE) -1 else result
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    var result = Int.MIN_VALUE
    val x = mutableSetOf<Char>()
    val str = "0123456789"
    for (chars in str) x.add(chars)
    val y = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-", "%", "+", " ")
    for (chars in jumps) if (!y.contains(chars.toString())) return -1
    val parts = jumps.split(" ")
    for (i in 0 until parts.size - 1) {
        if ((x.containsAll(parts[i].toSet())) && (parts[i + 1].contains("+")) && (parts[i].toInt() > result))
            result = parts[i].toInt()
    }
    return result
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    var n = 1
    val x = mutableSetOf<Char>()
    val str = "0123456789"
    for (chars in str) x.add(chars)
    val y = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-", "+", " ")
    val parts = expression.split(" ")
    if (parts[0].startsWith("+") || parts[0].startsWith("-")) throw IllegalArgumentException()
    var result = 0
    if (parts[0].toIntOrNull() == null) throw IllegalArgumentException() else result = parts[0].toInt()
    for (i in 1 until parts.size) {
        if (i % 2 == 1) {
            if (parts[i] == "+") n = 1
            else if (parts[i] == "-") n = -1
        } else if (x.containsAll(parts[i].toSet())) result += parts[i].toInt() * n
        else throw IllegalArgumentException()

    }
    return result
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    var word = ""
    val parts = str.split(" ")
    for (i in 0 until parts.size - 1) {
        if (parts[i].toLowerCase() == parts[i + 1].toLowerCase()) {
            word = parts[i] + " " + parts[i + 1]
            break
        }
    }
    return if (word == "") -1 else str.indexOf(word)
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    var result = ""
    var max1 = Int.MIN_VALUE.toDouble()
    val parts = description.split("; ")
    val products = mutableMapOf<String, Double>()
    for (part in parts) {
        val prod = part.split(" ")
        if (prod.size != 2) return ""
        if (prod[1].toDoubleOrNull() == null) return ""
        else products.put(prod[0], prod[1].toDouble())
    }
    for ((name, cost) in products) {
        if (cost > max1) {
            max1 = cost
            result = name
        }
    }
    return result
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    if (roman == "") return -1
    var roman1 = roman
    val arab = listOf(1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000)
    val rom = listOf(
        "I", "IV", "V", "IX", "X", "XL", "L",
        "XC", "C", "CD", "D", "CM", "M"
    )
    var result = 0
    for (i in 0 until rom.size) {
        while ((roman1.endsWith(rom[i])) && (roman1.length > 0)) {
            result += arab[i]
            roman1 = roman1.substring(0, roman1.length - rom[i].length)
        }
    }
    return if (roman1 == "") result else -1
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    var checklimit = 1
    val list = mutableListOf<Int>()
    var sk1 = 0
    var sk2 = 0
    var ind = cells / 2
    var i = 0
    for (i in 0 until cells) list.add(0)
    val simbols = listOf(">", "<", "+", "-", " ", "[", "]")
    for (i in 0 until commands.length) {
        if (commands[i].toString() !in simbols) throw IllegalArgumentException()
        if (commands[i].toString() == "[") sk1 += 1
        if (commands[i].toString() == "]") sk2 += 1
    }
    if (sk1 != sk2) throw IllegalArgumentException()
    while ((checklimit <= limit) && (i != commands.length)) {
        when (commands[i].toString()) {
            "+" -> list[ind] += 1
            "-" -> list[ind] -= 1
            ">" -> ind += 1
            "<" -> ind -= 1
            " " -> ind = ind
            "[" -> if (list[ind] == 0) i = closebracketsInd(commands, i)
            "]" -> if (list[ind] != 0) i = openbracketsInd(commands, i)
            else -> throw IllegalArgumentException()
        }
        if ((ind > list.size - 1) || (ind < 0)) throw IllegalStateException()
        checklimit += 1
        i += 1
    }
    return list
}

fun closebracketsInd(str: String, ind: Int): Int {
    var openbr = 0
    for (i in ind + 1 until str.length) {
        if ((str[i].toString() == "]") && (openbr == 0)) return i
        if ((str[i].toString() == "]") && (openbr != 0)) openbr -= 1
        if (str[i].toString() == "[") openbr += 1
    }
    return 0
}

fun openbracketsInd(str: String, ind: Int): Int {
    var closebr = 0
    for (i in ind - 1 downTo 0) {
        if ((str[i].toString() == "[") && (closebr == 0)) return i
        if ((str[i].toString() == "[") && (closebr != 0)) closebr -= 1
        if (str[i].toString() == "]") closebr += 1
    }
    return 0
}