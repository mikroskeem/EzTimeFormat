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

/**
 * @author Mark Vainomaa
 */
public final class EzTimeFormatter {
    public static final String DAY_PLACEHOLDER = "{day}";
    public static final String DAYS_PLACEHOLDER = "{days}";
    public static final String HOUR_PLACEHOLDER = "{hour}";
    public static final String HOURS_PLACEHOLDER = "{hours}";
    public static final String MINUTE_PLACEHOLDER = "{minute}";
    public static final String MINUTES_PLACEHOLDER = "{minutes}";
    public static final String SECOND_PLACEHOLDER = "{second}";
    public static final String SECONDS_PLACEHOLDER = "{seconds}";

    @NonNull
    public static String formatDuration(@NonNull Duration duration) {
        return formatDuration(duration, true);
    }

    @NonNull
    public static String formatDuration(@NonNull Duration duration, boolean spacingAfterNumber) {
        StringBuilder builder = new StringBuilder();
        if (duration.isNegative()) {
            duration = duration.negated();
        }

        long days = duration.toDays();
        duration = duration.minusDays(days);
        if (buildNumber(builder, days, spacingAfterNumber, DAY_PLACEHOLDER, DAYS_PLACEHOLDER)) {
            builder.append(' ');
        }

        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        if (buildNumber(builder, hours, spacingAfterNumber, HOUR_PLACEHOLDER, HOURS_PLACEHOLDER)) {
            builder.append(' ');
        }

        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);
        if (buildNumber(builder, minutes, spacingAfterNumber, MINUTE_PLACEHOLDER, MINUTES_PLACEHOLDER)) {
            builder.append(' ');
        }

        long seconds = duration.getSeconds();
        //duration = duration.minusSeconds(seconds);
        if (buildNumber(builder, seconds, spacingAfterNumber, SECOND_PLACEHOLDER, SECONDS_PLACEHOLDER)) {
            builder.append(' ');
        }

        // Delete leading space
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }

    private static boolean buildNumber(@NonNull StringBuilder builder, long number,
                                       boolean spacingAfterNumber,
                                       @NonNull String singularForm, @NonNull String pluralForm) {
        if (number >= 1) {
            builder.append(number);
            if (spacingAfterNumber) {
                builder.append(' ');
            }
            builder.append(number == 1 ? singularForm : pluralForm);
            return true;
        }
        return false;
    }
}