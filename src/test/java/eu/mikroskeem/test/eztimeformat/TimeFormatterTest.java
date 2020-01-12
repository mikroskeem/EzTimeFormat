/*
 * This file is part of project EzTimeFormat, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2020 Mark Vainomaa <mikroskeem@mikroskeem.eu>
 * Copyright (c) Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package eu.mikroskeem.test.eztimeformat;

import eu.mikroskeem.eztimeformat.EzTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

/**
 * @author Mark Vainomaa
 */
final class TimeFormatterTest {
    @Test
    void testZeroTime() {
        Duration zero = Duration.ZERO;
        Duration underOneSecondOne = Duration.ofMillis(999);
        Duration underOneSecondTwo = Duration.ofMillis(5);
        Duration underOneSecondThree = Duration.ofMillis(1);

        Assertions.assertEquals("0 {seconds}", EzTimeFormatter.formatDuration(zero));
        Assertions.assertEquals("0 {seconds}", EzTimeFormatter.formatDuration(underOneSecondOne));
        Assertions.assertEquals("0 {seconds}", EzTimeFormatter.formatDuration(underOneSecondTwo));
        Assertions.assertEquals("0 {seconds}", EzTimeFormatter.formatDuration(underOneSecondThree));
    }

    @Test
    void testEvenTimes() {
        Duration oneSecond = Duration.ofSeconds(1);
        Duration twoSeconds = Duration.ofSeconds(2);
        Duration oneWeek = Duration.ofDays(7);

        Assertions.assertEquals("1 {second}", EzTimeFormatter.formatDuration(oneSecond));
        Assertions.assertEquals("2 {seconds}", EzTimeFormatter.formatDuration(twoSeconds));
        Assertions.assertEquals("7 {days}", EzTimeFormatter.formatDuration(oneWeek));
    }

    @Test
    void testNegativeTime() {
        Duration negativeOneMinute = Duration.ofMinutes(-1);

        Assertions.assertEquals("1 {minute}", EzTimeFormatter.formatDuration(negativeOneMinute));
    }
}
