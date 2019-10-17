package co.urbi.http;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.text.Normalizer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.ZoneOffset.UTC;
import static java.util.Arrays.asList;
import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

@UtilityClass
public class U {

    public static String encodeBase64(String str) {
        return encodeBase64(str.getBytes(UTF_8));
    }

    public static String encodeBase64(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes), UTF_8);
    }

    public static String encodeBase64Url(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes), UTF_8);
    }

    public static String decodeBase64Url(String str) {
        return new String(Base64.getUrlDecoder().decode(str), UTF_8);
    }

    public static String decodeBase64(String str) {
        return decodeBase64(str.getBytes(UTF_8));
    }

    public static String decodeBase64(byte[] bytes) {
        return new String(Base64.getDecoder().decode(bytes), UTF_8);
    }

    public static String md5(String str) {
        return md5(str.getBytes());
    }

    @SneakyThrows
    public static String md5(byte[] bytes) {
        return format("%032x", new BigInteger(1, MessageDigest.getInstance("MD5").digest(bytes)));
    }

    @SneakyThrows
    public static String encodeUrl(String str) {
        return URLEncoder.encode(str, "UTF-8");
    }

    public static <T> T coalesce(T a, T b) {
        return a == null ? b : a;
    }

    @SuppressWarnings("unchecked")
    public static <T> T coalesce(T... array) {
        for (T a : array)
            if (a != null) return a;
        return null;
    }

    public static String padLeft(String input, int padUpTo) {
        StringBuilder sb = new StringBuilder();
        for (int toPrepend = padUpTo - input.length(); toPrepend > 0; toPrepend--)
            sb.append(' ');
        return sb.append(input).toString();
    }

    public static String padRight(String input, int padUpTo) {
        StringBuilder sb = new StringBuilder(input);
        for (int toPrepend = padUpTo - input.length(); toPrepend > 0; toPrepend--)
            sb.append(' ');
        return sb.toString();
    }

    /**
     * Returns the argument string if not <tt>null</tt>, the
     * empty string otherwise.
     *
     * @param s the string to check against <tt>null</tt>
     * @return the string itself, or <tt>""</tt> if <tt>null</tt>
     */
    public static String emptyIfNull(String s) {
        return coalesce(s, "");
    }

    public static String nullIfEmpty(String s) {
        return isNullOrEmpty(s) ? null : s;
    }

    // Files

    @SneakyThrows
    public static void delete(Path path) {
        Files.delete(path);
    }

    @SneakyThrows
    public static void createDirectory(Path dir) {
        Files.createDirectory(dir);
    }

    @SneakyThrows
    public static void createDirectories(Path dir) {
        Files.createDirectories(dir);
    }

    @SneakyThrows
    public static void deleteDirectory(Path dir) {
        try (Stream<Path> stream = Files.walk(dir)) {
            stream.sorted(reverseOrder()).forEach(U::delete);
        }
    }

    @SneakyThrows
    public static long size(Path path) {
        return Files.size(path);
    }

    @SneakyThrows
    public static boolean isEmpty(Path localDir) {
        try (Stream<Path> list = Files.list(localDir)) {
            return !list.findFirst().isPresent();
        }
    }

    /**
     * Returns the file's lines as a stream.
     * IMPORTANT: remember to close the stream (or use try-with-resources) to
     * avoid a file leak
     *
     * @param path the file to read
     * @return the String stream
     */
    @SneakyThrows
    public static Stream<String> lines(Path path) {
        return Files.lines(path);
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> asSet(T... items) {
        return new HashSet<>(asList(items));
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            // Ignore and return
        }
    }

    /**
     * Calls the argument <tt>function</tt> if <tt>arg</tt> is not <tt>null</tt>.
     *
     * @param arg      the arg that will be passed to {@link Consumer#accept(Object)}
     * @param function the function to call
     * @param <T>      the type of parameter
     */
    public static <T> void callIfArgNotNull(T arg, Consumer<T> function) {
        if (arg != null)
            function.accept(arg);
    }

    /**
     * Returns the return value of the argument function applied to the first
     * argument, or <tt>null</tt> if the first argument is <tt>null</tt>.
     *
     * @param arg      the object to be passed to the function
     * @param function the function to call on the argument, if not <tt>null</tt>
     * @param <T>      the type of of argument accepted by the argument function
     * @param <R>      the type of values returned by the argument function
     * @return the result of calling {@link Function#apply(Object)} on <tt>arg</tt>,
     * or <tt>null</tt> if <tt>arg</tt> is <tt>null</tt>
     */
    public static <T, R> R nullSafeCall(T arg, Function<T, R> function) {
        return arg == null ? null : function.apply(arg);
    }

    /**
     * Parses a date in ISO format (which may or may not contain time data).
     *
     * @param isoDate the date to format
     * @return the parsed date, or <tt>null</tt> if a date couldn't be parsed
     */
    public static LocalDate parseIsoDate(String isoDate) {

        try {
            if (isoDate != null && isoDate.length() >= 10) {
                return LocalDate.parse(isoDate.substring(0, 10));
            }
        } catch (DateTimeParseException e) {
            // ignore and return null
            System.err.println("invalid date format for " + isoDate);
        }

        return null;
    }

    /**
     * Returns whether all of the argument objects are non-<tt>null</tt>.
     *
     * @param objects the objects to check
     * @return <tt>true</tt> if all of the argument objects are not <tt>null</tt>
     */
    public static boolean isNoneNull(Object... objects) {
        for (Object o : objects)
            if (o == null)
                return false;
        return true;
    }

    /**
     * Converts a sequence of escaped unicode codes to a String.
     * <p>
     * <p>
     * Examples:
     * <ul>
     * <li><tt>"\u0048\u0065\u006C\u006C\u006F"</tt> becomes <tt>"Hello"</tt></li>
     * <li><tt>"\U0048"</tt> becomes <tt>"H"</tt></li>
     * </ul>
     *
     * @param escapedUnicode the string to unescape
     * @return a string with the decoded representation of all unicode escapes
     */
    public static String unicodeEscapeToString(String escapedUnicode) {

        escapedUnicode = escapedUnicode.trim().toLowerCase().replace("\\", "").replaceFirst("u", "");
        String[] characters = escapedUnicode.split("u");
        StringBuilder builder = new StringBuilder();

        for (String character : characters) {
            int hexVal = Integer.parseInt(character, 16);
            builder.append((char) hexVal);
        }

        return builder.toString();
    }

    /**
     * Returns whether the argument string is <tt>null</tt> or empty.
     *
     * @param s the string to check
     * @return <tt>true</tt> if <tt>s</tt> is <tt>null</tt> or empty
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isNullOrEmpty(Collection c) {
        return c == null || c.isEmpty();
    }

    /**
     * A String.join() which skips nulls, empty strings, or strings with only whitespaces (ie, empty once trimmed).
     *
     * @return The joined string, or <tt>null</tt> if the resulting string is empty.
     */
    public static String cleanJoin(String delimiter, String... strings) {

        if (strings == null || strings.length == 0) return null;
        if (delimiter == null) delimiter = "";

        List<String> cleanList = new ArrayList<>();
        for (String string : strings) {
            String trimmed = string == null ? "" : string.trim();
            if (!trimmed.isEmpty())
                cleanList.add(trimmed);
        }

        return cleanList.size() == 0 ? null : join(delimiter, cleanList.toArray(new String[0]));

    }

    /**
     * <p>Capitalizes a String changing the first character to title case as
     * per {@link Character#toTitleCase(int)}. No other characters are changed.</p>
     * <p>Taken from apache commons-lang</p>
     * <pre>
     * StringUtils.capitalize(null)  = null
     * StringUtils.capitalize("")    = ""
     * StringUtils.capitalize("cat") = "Cat"
     * StringUtils.capitalize("cAt") = "CAt"
     * StringUtils.capitalize("'cat'") = "'cat'"
     * </pre>
     *
     * @param str the String to capitalize, may be null
     * @return the capitalized String, {@code null} if null String input
     * @since 2.0
     */
    public static String capitalize(final String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        final int firstCodepoint = str.codePointAt(0);
        final int newCodePoint = Character.toTitleCase(firstCodepoint);
        if (firstCodepoint == newCodePoint) {
            // already capitalized
            return str;
        }

        final int[] newCodePoints = new int[strLen]; // cannot be longer than the char array
        int outOffset = 0;
        newCodePoints[outOffset++] = newCodePoint; // copy the first codepoint
        for (int inOffset = Character.charCount(firstCodepoint); inOffset < strLen; ) {
            final int codepoint = str.codePointAt(inOffset);
            newCodePoints[outOffset++] = codepoint; // copy the remaining ones
            inOffset += Character.charCount(codepoint);
        }
        return new String(newCodePoints, 0, outOffset);
    }

    /**
     * Returns a message in the form "errorName at className:lineNumber" using
     * the top element in the stack trace for the argument throwable
     * (and where <tt>errorName</tt> is the result of calling <tt>error.toString()</tt>).
     *
     * @param error the error to get the line and class for
     * @return the message to log
     */
    public static String getThrowableDescription(Throwable error) {

        if (error == null)
            return "unknown cause";

        StackTraceElement[] stackTrace = error.getStackTrace();
        if (stackTrace.length > 0) {
            StackTraceElement top = stackTrace[0];
            return format("%s at %s.%s:%d", error, top.getClassName(), top.getMethodName(), top.getLineNumber());
        } else
            return format("%s (missing stacktrace info)", error);
    }

    public <T> List<List<T>> splitInBatches(List<T> list, int batchSize) {
        return range(0, list.size()).boxed()
                                    .collect(groupingBy(index -> index / batchSize)).values().stream()
                                    .map(indices -> indices.stream().map(list::get).collect(toList()))
                                    .collect(toList());
    }

    public static byte[] utf8(String key) {
        return coalesce(key, "").getBytes(UTF_8);
    }

    public static Instant fromEpochSecondOrNull(Integer epochSecond) {
        return epochSecond == null ? null : Instant.ofEpochSecond(epochSecond);
    }

    /**
     * Wraps a function in a try/catch, so that it can be called in a stream.
     * <p>
     * If the function throws an exception of one of the specified types,
     * <tt>null</tt> is returned. If no types are specified, any exception is
     * swallowed by this method, and <tt>null</tt> is returned.
     *
     * @param function          the function to wrap
     * @param allowedExceptions exceptions that are allowed to be thrown by
     *                          the function for <tt>null</tt> to be thrown.
     *                          If no exception is passed, any exception will
     *                          be swallowed by the wrapper
     * @param <T>               the input type for the function
     * @param <R>               the return type for the function
     * @return the value returned by <tt>function.apply()</tt>, or <tt>null</tt>
     * if <tt>apply</tt> threw an exception
     */
    @SuppressWarnings("unchecked")
    public static <T, R> Function<T, R> safeFunction(Function<T, R> function, Class... allowedExceptions) {

        return i -> {
            try {
                return function.apply(i);
            } catch (Exception e) {
                if (allowedExceptions.length > 0) {
                    for (Class c : allowedExceptions) {
                        if (c.isAssignableFrom(e.getClass()))
                            return null;
                    }
                    throw e;
                }
                return null;
            }
        };
    }

    /**
     * Converts accented letters into non-accented equivalents (i.e., ì -> i),
     * and sets the string to lowercase.
     *
     * @param s the string to normalize
     * @return the input string in lowercase, with accented letters replaced by regular
     */
    public static String normalize(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFKD).toLowerCase();
        return s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

    @SneakyThrows
    public static XMLGregorianCalendar toXMLGregorianCalendar(LocalDate date) {

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(Date.from(date.atStartOfDay().toInstant(UTC)));

        return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    }

    public static Instant fromXMLGregorianCalendar(XMLGregorianCalendar calendar, TimeZone timeZone) {

        if (calendar == null || timeZone == null) return null;

        Instant instant = Instant.ofEpochMilli(calendar.toGregorianCalendar(timeZone, null, null).getTimeInMillis());

        return instant.getEpochSecond() < 0 ? null : instant;
    }

    /*
     * Taken from http://www.icosaedro.it/cf-pi/
     */
    public static void checkItalianSSNValidity(String cf) {

        int i, s, c;
        String cf2;
        int[] setdisp = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };

        if (cf.length() != 16)
            throw new IllegalArgumentException("error.cf.length");

        cf2 = cf.toUpperCase();

        for (i = 0; i < 16; i++) {
            c = cf2.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z'))
                throw new IllegalArgumentException("error.cf.character");
        }

        s = 0;
        for (i = 1; i <= 13; i += 2) {
            c = cf2.charAt(i);
            if (c >= '0' && c <= '9')
                s = s + c - '0';
            else
                s = s + c - 'A';
        }

        for (i = 0; i <= 14; i += 2) {
            c = cf2.charAt(i);
            if (c >= '0' && c <= '9') c = c - '0' + 'A';
            s = s + setdisp[c - 'A'];
        }

        if (s % 26 + 'A' != cf2.charAt(15))
            throw new IllegalArgumentException("error.cf.check");
    }

    /**
     * Escapes some JavaScript code to make it suitable for inclusion in
     * inline scripts. Basically replaces "/" with "\/"
     *
     * @param jsCode the JavaScript code to escape
     * @return the escaped code
     */
    public static String escapeJsForInlineScript(String jsCode) {
        if (jsCode == null) {
            return null;
        }
        return jsCode.replace("/", "\\/");
    }

    /**
     * Reads the provided InputStream into a String, using the charset.
     * The InputStream is not closed, so it's the caller's responsibility to close it
     *
     * @param is      the input stream
     * @param charset the charset
     * @return the text String extracted from the input
     */
    public static String toString(InputStream is, Charset charset) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(charset.name());
    }

    /**
     * Checks if a date is in a given range. FromDate and toDate can be null,
     * which makes the range open ended on either/both sides.
     *
     * @param date     the date to test
     * @param fromDate earliest date for the check, inclusive
     * @param toDate   latest date for the check, exclusive
     * @return true if the date is in the range, false otherwise
     */
    public static boolean isBetween(
            LocalDate date,
            LocalDate fromDate,
            LocalDate toDate
    ) {
        if (fromDate != null && date.isBefore(fromDate)) {
            return false;
        }
        return toDate == null || date.isBefore(toDate);
    }

    /**
     * Checks if an instant is in a given range. FromInstant and toInstant can be null,
     * which makes the range open ended on either/both sides.
     *
     * @param instant     the instant to test
     * @param fromInstant earliest instant for the check, inclusive
     * @param toInstant   latest instant for the check, exclusive
     * @return true if the date is in the range, false otherwise
     */
    public static boolean isBetween(
            Instant instant,
            Instant fromInstant,
            Instant toInstant
    ) {
        if (fromInstant != null && instant.isBefore(fromInstant)) {
            return false;
        }
        return toInstant == null || instant.isBefore(toInstant);
    }

    /**
     * Converts a glob-style pattern to a regular expression, e.g.
     * <code>*.urbi.co</code> becomes <code>.*\.urbi\.co</code>
     *
     * @param glob the glob pattern convert
     * @return the regex
     */
    public static String globToRegex(String glob) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < glob.length(); ++i) {
            final char c = glob.charAt(i);
            switch (c) {
                case '*':
                    sb.append(".*");
                    break;
                case '?':
                    sb.append(".");
                    break;
                case '.':
                    sb.append("\\.");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    public String[] splitLines(String text) {
        if (text == null) {
            return new String[0];
        }
        return text.split("\\r?\\n");
    }

    public String normaliseNewLines(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\r\n", "\n");
    }

    public static double truncate(double d, int decimals) {
        return BigDecimal.valueOf(d).setScale(decimals, BigDecimal.ROUND_DOWN).doubleValue();
    }

}