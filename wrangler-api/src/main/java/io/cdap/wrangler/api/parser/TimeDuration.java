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

 import com.google.gson.JsonElement;
 import com.google.gson.JsonObject;
 
 import java.util.Locale;
 
 /**
  * Token for parsing time durations like "100ms", "5s", "2m", "1h".
  */
 public class TimeDuration implements Token {
   private final String original;
   private final long milliseconds;
 
   public TimeDuration(String value) {
     this.original = value;
     String val = value.trim().toLowerCase(Locale.ENGLISH);
 
     if (val.endsWith("ms")) {
       milliseconds = Long.parseLong(val.replace("ms", "").trim());
     } else if (val.endsWith("s")) {
       milliseconds = Long.parseLong(val.replace("s", "").trim()) * 1000L;
     } else if (val.endsWith("m")) {
       milliseconds = Long.parseLong(val.replace("m", "").trim()) * 60_000L;
     } else if (val.endsWith("h")) {
       milliseconds = Long.parseLong(val.replace("h", "").trim()) * 3_600_000L;
     } else {
       throw new IllegalArgumentException("Unsupported time format: " + value);
     }
   }
 
   public long getMilliseconds() {
     return milliseconds;
   }
 
   @Override
   public Object value() {
     return milliseconds;
   }
 
   @Override
   public TokenType type() {
     return TokenType.TIME_DURATION;
   }
 
   @Override
   public JsonElement toJson() {
     JsonObject json = new JsonObject();
     json.addProperty("type", "TIME_DURATION");
     json.addProperty("original", original);
     json.addProperty("milliseconds", milliseconds);
     return json;
   }
 }
