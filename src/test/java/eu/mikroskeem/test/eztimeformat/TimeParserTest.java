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

import eu.mikroskeem.eztimeformat.EzTimeParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

/**
 * @author Mark Vainomaa
 */
final class TimeParserTest {
    @Test
    void simpleParseTest() {
        Duration expected = Duration.ofSeconds(198300);

        Assertions.assertEquals(expected, EzTimeParser.parseTime("2d7h5m"));
    }

    @Test
    void invalidParseTest() {
        EzTimeParser.UnknownTimeTypeException exception = Assertions.assertThrows(EzTimeParser.UnknownTimeTypeException.class, () -> {
            EzTimeParser.parseTime("5yyy");
        });

        Assertions.assertEquals("yyy", exception.getType());
    }

    @Test
    void trailingCharactersTest() {
        EzTimeParser.TrailingUnparsedTimeException exception = Assertions.assertThrows(EzTimeParser.TrailingUnparsedTimeException.class, () -> {
            EzTimeParser.parseTime("5d555555");
        });

        Assertions.assertEquals("555555", exception.getTrailing());
    }
}
