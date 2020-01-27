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

package eu.mikroskeem.eztimeformat;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.Duration;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.LongFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mark Vainomaa
 */
public final class EzTimeParser {
    private static final Pattern PATTERN = Pattern.compile("(([0-9]+)([a-z]+))"); // <num><type>
    private static final Map<String, LongFunction<Duration>> DATE_MAPPING;

    // TODO: match unmatched
    @NonNull
    public static Duration parseTime(@NonNull String timeString) {
        Duration collected = Duration.ZERO;
        Matcher matcher = PATTERN.matcher(timeString);

        while (matcher.find()) {
            long num = Long.parseLong(matcher.group(2));
            String type = matcher.group(3);

            LongFunction<Duration> parser = DATE_MAPPING.get(type);
            if (parser == null) {
                throw new UnknownTimeTypeException(timeString, type);
            }

            Duration parsed = parser.apply(num);
            collected = collected.plus(parsed);
        }

        if (!collected.isZero()) {
            String replaced = timeString.replaceAll(PATTERN.pattern(), "");
            if (!replaced.isEmpty()) {
                throw new TrailingUnparsedTimeException(timeString, replaced);
            }
        }

        return collected;
    }

    public abstract static class TimeParseException extends RuntimeException {
        protected final String rawString;

        public TimeParseException(@NonNull String rawString, @NonNull String reason) {
            super("Invalid time string '" + rawString + "': " + reason);
            this.rawString = rawString;
        }

        @NonNull
        public final String getRawString() {
            return rawString;
        }
    }

    public static class UnknownTimeTypeException extends TimeParseException {
        private final String type;

        public UnknownTimeTypeException(@NonNull String rawString, @NonNull String type) {
            super(rawString, "unknown type '" + type + "'");
            this.type = type;
        }

        @NonNull
        public final String getType() {
            return type;
        }
    }

    public static class TrailingUnparsedTimeException extends TimeParseException {
        private final String trailing;

        public TrailingUnparsedTimeException(@NonNull String rawString, @NonNull String trailing) {
            super(rawString, "trailing unparsed '" + trailing + "'");
            this.trailing = trailing;
        }

        @NonNull
        public final String getTrailing() {
            return trailing;
        }
    }

    static {
        Map<String, LongFunction<Duration>> mapping = new HashMap<>();
        mapping.put("ms", Duration::ofMillis);
        mapping.put("s", Duration::ofSeconds);
        mapping.put("m", Duration::ofMinutes);
        mapping.put("h", Duration::ofHours);
        mapping.put("d", Duration::ofDays);

        // I guess...?
        mapping.put("w", num -> Duration.ofDays(num * 7));
        mapping.put("M", num -> Duration.ofDays(num * 30));
        mapping.put("y", num -> Duration.ofDays(num * 365));

        DATE_MAPPING = Collections.unmodifiableMap(mapping);
    }
}
