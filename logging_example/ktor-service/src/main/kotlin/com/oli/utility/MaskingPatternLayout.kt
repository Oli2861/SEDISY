package com.oli.utility

import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.spi.ILoggingEvent
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.stream.Collectors
import java.util.stream.IntStream


class MaskingPatternLayout : PatternLayout() {
    private var multilinePattern: Pattern? = null
    private val maskPatterns: MutableList<String> = ArrayList()

    fun addMaskPattern(maskPattern: String) {
        maskPatterns.add(maskPattern)
        multilinePattern = Pattern.compile(maskPatterns.stream().collect(Collectors.joining("|")), Pattern.MULTILINE)
    }

    override fun doLayout(event: ILoggingEvent?): String? {
        return maskMessage(super.doLayout(event))
    }

    private fun maskMessage(message: String): String {
        multilinePattern?.let { multilinePattern ->
            val sb = StringBuilder(message)
            val matcher: Matcher = multilinePattern.matcher(sb)

            while (matcher.find()) {
                IntStream.rangeClosed(1, matcher.groupCount()).forEach { group ->
                    if (matcher.group(group) != null) {
                        IntStream.range(matcher.start(group), matcher.end(group))
                            .forEach { i -> sb.setCharAt(i, '*') }
                    }
                }
            }
            return sb.toString()
        }
        return message
    }

}