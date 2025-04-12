/*
 * Copyright © 2025 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

 package io.cdap.wrangler.api.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ByteSize {
    private static final Pattern PATTERN = Pattern.compile("(\\d+(?:\\.\\d+)?)\\s*(B|KB|MB|GB|TB|PB|EB)", Pattern.CASE_INSENSITIVE);

    public static long parse(String size) {
        if (size == null || size.trim().isEmpty()) {
            throw new IllegalArgumentException("Byte size string is null or empty");
        }

        Matcher matcher = PATTERN.matcher(size.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid byte size format: " + size);
        }

        double value = Double.parseDouble(matcher.group(1));
        String unit = matcher.group(2).toUpperCase();

        switch (unit) {
            case "B":  return (long) value;
            case "KB": return (long) (value * 1024);
            case "MB": return (long) (value * 1024 * 1024);
            case "GB": return (long) (value * 1024 * 1024 * 1024);
            case "TB": return (long) (value * 1024 * 1024 * 1024 * 1024L);
            case "PB": return (long) (value * 1024 * 1024 * 1024 * 1024L * 1024);
            case "EB": return (long) (value * 1024 * 1024 * 1024 * 1024L * 1024 * 1024);
            default:
                throw new IllegalArgumentException("Unknown unit: " + unit);
        }
    }
}
