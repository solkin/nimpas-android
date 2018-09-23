/*
 * Copyright 2015 - 2018 Michael Rapp
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.tomclaw.bottomsheet.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.lang.reflect.Constructor;

/**
 * An utility class, which provides static methods, which allow to ensure, that variables and
 * objects fulfill certain conditions. If a condition is not violated, an exception is thrown by
 * each of these methods.
 *
 * @author Michael Rapp
 * @since 1.0.0
 */
public final class Condition {

    /**
     * Creates a new utility class, which provides static methods, hwich allows to ensure, that
     * variables and objects fulfill certain conditions.
     */
    private Condition() {

    }

    /**
     * Throws a specific runtime exception. The exception is instantiated using reflection and must
     * provide a constructor, which expects exactly one {@link String} parameter. If instantiating
     * the exception fails, a {@link RuntimeException} is thrown instead.
     *
     * @param exceptionMessage The message of the exception, which should be thrown, as a {@link String} or null, if
     *                         the exception should not have a message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, as an instance of the
     *                         class {@link Class}. The class may not be null
     */
    private static void throwException(@Nullable final String exceptionMessage,
                                       @NonNull final Class<? extends RuntimeException> exceptionClass) {
        RuntimeException exception;

        try {
            Constructor<? extends RuntimeException> constructor =
                    exceptionClass.getConstructor(String.class);
            exception = constructor.newInstance(exceptionMessage);
        } catch (Exception e) {
            exception = new RuntimeException(exceptionMessage);
        }

        throw exception;
    }

    /**
     * Ensures that an object is not null. Otherwise a {@link NullPointerException} with a specific
     * message is thrown.
     *
     * @param object           The object, which should be checked, as an instance of the class {@link Object}
     * @param exceptionMessage The message of the exception, which is thrown, if the given object is null, as a
     *                         {@link String} or null, if the exception should not have a message
     */
    public static void ensureNotNull(final Object object, @Nullable final String exceptionMessage) {
        ensureNotNull(object, exceptionMessage, NullPointerException.class);
    }

    /**
     * Ensures that an object is not null. Otherwise a specific {@link RuntimeException} is thrown.
     *
     * @param object           The object, which should be checked, as an instance of the class {@link Object}
     * @param exceptionMessage The message of the exception, which is thrown, if the given object is null, as a
     *                         {@link String} or null, if the exception should not have a message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given object is
     *                         null, as an instance of the class {@link Class}. The class may not be null
     */
    public static void ensureNotNull(final Object object, @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (object == null) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a text is not empty. Otherwise an {@link IllegalArgumentException} with a
     * specific message is thrown.
     *
     * @param text             The text, which should be checked, as an instance of the type {@link CharSequence}
     * @param exceptionMessage The message of the exception, which is thrown, if the given text is empty, as a
     *                         {@link String} or null, if the exception should not have a message
     */
    public static void ensureNotEmpty(final CharSequence text,
                                      @Nullable final String exceptionMessage) {
        ensureNotEmpty(text, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a text is not empty. Otherwise a specific {@link RuntimeException} is thrown.
     *
     * @param text             The text, which should be checked, as an instance of the type {@link CharSequence}
     * @param exceptionMessage The message of the exception, which is thrown, if the given text is empty, as a
     *                         {@link String} or null, if the exception should not have a message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given text is
     *                         empty, as an instance of the class {@link Class}. The class may not be null
     */
    public static void ensureNotEmpty(final CharSequence text,
                                      @Nullable final String exceptionMessage,
                                      @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (TextUtils.isEmpty(text)) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Short} value is at least as great as a reference value. Otherwise an
     * {@link IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Short} value
     * @param referenceValue   The reference value, the given value must be at least as great as, as a {@link Short}
     *                         value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     */
    public static void ensureAtLeast(final short value, final short referenceValue,
                                     @Nullable final String exceptionMessage) {
        ensureAtLeast(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Short} value is at least as great as a reference value. Otherwise a
     * specific {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Short} value
     * @param referenceValue   The reference value, the given value must be at least as great as, as a {@link Short}
     *                         value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         smaller as the reference value, as an instance of the class {@link Class}. The class
     *                         may not be null
     */
    public static void ensureAtLeast(final short value, final short referenceValue,
                                     @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value < referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that an {@link Integer} value is at least as great as a reference value. Otherwise
     * an {@link IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as an {@link Integer} value
     * @param referenceValue   The reference value, the given value must be at least as great as, as an {@link
     *                         Integer} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     */
    public static void ensureAtLeast(final int value, final int referenceValue,
                                     @Nullable final String exceptionMessage) {
        ensureAtLeast(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that an {@link Integer} value is at least as great as a reference value. Otherwise a
     * specific {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as an {@link Integer} value
     * @param referenceValue   The reference value, the given value must be at least as great as, as an {@link
     *                         Integer} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         smaller as the reference value, as an instance of the class {@link Class}. The class
     *                         may not be null
     */
    public static void ensureAtLeast(final int value, final int referenceValue,
                                     @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value < referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Long} value is at least as great as a reference value. Otherwise an
     * {@link IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Long} value
     * @param referenceValue   The reference value, the given value must be at least as great as, as a {@link Long}
     *                         value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     */
    public static void ensureAtLeast(final long value, final long referenceValue,
                                     @Nullable final String exceptionMessage) {
        ensureAtLeast(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Long} value is at least as great as a reference value. Otherwise a
     * specific {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Long} value
     * @param referenceValue   The reference value, the given value must be at least as great as, as a {@link Long}
     *                         value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         smaller as the reference value, as an instance of the class {@link Class}. The class
     *                         may not be null
     */
    public static void ensureAtLeast(final long value, final long referenceValue,
                                     @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value < referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Float} value is at least as great as a reference value. Otherwise an
     * {@link IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Float} value
     * @param referenceValue   The reference value, the given value must be at least as great as, as a {@link Float}
     *                         value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     */
    public static void ensureAtLeast(final float value, final float referenceValue,
                                     @Nullable final String exceptionMessage) {
        ensureAtLeast(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Float} value is at least as great as a reference value. Otherwise a
     * specific {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Float} value
     * @param referenceValue   The reference value, the given value must be at least as great as, as a {@link Float}
     *                         value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         smaller as the reference value, as an instance of the class {@link Class}. The class
     *                         may not be null
     */
    public static void ensureAtLeast(final float value, final float referenceValue,
                                     @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value < referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Double} value is at least as great as a reference value. Otherwise an
     * {@link IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Double} value
     * @param referenceValue   The reference value, the given value must be at least as great as, as a {@link
     *                         Double} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     */
    public static void ensureAtLeast(final double value, final double referenceValue,
                                     @Nullable final String exceptionMessage) {
        ensureAtLeast(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Double} value is at least as great as a reference value. Otherwise a
     * specific {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Double} value
     * @param referenceValue   The reference value, the given value must be at least as great as, as a {@link
     *                         Double} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         smaller as the reference value, as an instance of the class {@link Class}. The class
     *                         may not be null
     */
    public static void ensureAtLeast(final double value, final double referenceValue,
                                     @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value < referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Short} value is at maximum as great as a reference value. Otherwise an
     * {@link IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Short} value
     * @param referenceValue   The reference value, the given value must be at maximum as great as, as a {@link
     *                         Short} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     */
    public static void ensureAtMaximum(final short value, final short referenceValue,
                                       @Nullable final String exceptionMessage) {
        ensureAtMaximum(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Short} value is at maximum as great as a reference value. Otherwise a
     * specific {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Short} value
     * @param referenceValue   The reference value, the given value must be at maximum as great as, as a {@link
     *                         Short} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         greater as the reference value, as an instance of the class {@link Class}. The class
     *                         may not be null
     */
    public static void ensureAtMaximum(final short value, final short referenceValue,
                                       @Nullable final String exceptionMessage,
                                       @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value > referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that an {@link Integer} value is at maximum as great as a reference value. Otherwise
     * an {@link IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as an {@link Integer} value
     * @param referenceValue   The reference value, the given value must be at maximum as great as, as an {@link
     *                         Integer} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     */
    public static void ensureAtMaximum(final int value, final int referenceValue,
                                       @Nullable final String exceptionMessage) {
        ensureAtMaximum(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that an {@link Integer} value is at maximum as great as a reference value. Otherwise
     * a specific {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as an {@link Integer} value
     * @param referenceValue   The reference value, the given value must be at maximum as great as, as a {@link
     *                         Short} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         greater as the reference value, as an instance of the class {@link Class}. The class
     *                         may not be null
     */
    public static void ensureAtMaximum(final int value, final int referenceValue,
                                       @Nullable final String exceptionMessage,
                                       @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value > referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Long} value is at maximum as great as a reference value. Otherwise an
     * {@link IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Long} value
     * @param referenceValue   The reference value, the given value must be at maximum as great as, as a {@link
     *                         Long} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     */
    public static void ensureAtMaximum(final long value, final long referenceValue,
                                       @Nullable final String exceptionMessage) {
        ensureAtMaximum(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Long} value is at maximum as great as a reference value. Otherwise a
     * specific {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Long} value
     * @param referenceValue   The reference value, the given value must be at maximum as great as, as a {@link
     *                         Long} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         greater as the reference value, as an instance of the class {@link Class}. The class
     *                         may not be null
     */
    public static void ensureAtMaximum(final long value, final long referenceValue,
                                       @Nullable final String exceptionMessage,
                                       @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value > referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Float} value is at maximum as great as a reference value. Otherwise an
     * {@link IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Float} value
     * @param referenceValue   The reference value, the given value must be at maximum as great as, as a {@link
     *                         Float} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     */
    public static void ensureAtMaximum(final float value, final float referenceValue,
                                       @Nullable final String exceptionMessage) {
        ensureAtMaximum(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Float} value is at maximum as great as a reference value. Otherwise a
     * specific {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Float} value
     * @param referenceValue   The reference value, the given value must be at maximum as great as, as a {@link
     *                         Float} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         greater as the reference value, as an instance of the class {@link Class}. The class
     *                         may not be null
     */
    public static void ensureAtMaximum(final float value, final float referenceValue,
                                       @Nullable final String exceptionMessage,
                                       @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value > referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Double} value is at maximum as great as a reference value. Otherwise
     * an {@link IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Double} value
     * @param referenceValue   The reference value, the given value must be at maximum as great as, as a {@link
     *                         Double} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     */
    public static void ensureAtMaximum(final double value, final double referenceValue,
                                       @Nullable final String exceptionMessage) {
        ensureAtMaximum(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Double} value is at maximum as great as a reference value. Otherwise a
     * specific {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Double} value
     * @param referenceValue   The reference value, the given value must be at maximum as great as, as a {@link
     *                         Double} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater as the
     *                         reference value, as a {@link String} or null, if the exception should not have a
     *                         message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         greater as the reference value, as an instance of the class {@link Class}. The class
     *                         may not be null
     */
    public static void ensureAtMaximum(final double value, final double referenceValue,
                                       @Nullable final String exceptionMessage,
                                       @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value > referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Short} value is greater as a reference value. Otherwise an {@link
     * IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Short} value
     * @param referenceValue   The reference value, the given value must be greater as, as a {@link Short} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     */
    public static void ensureGreater(final short value, final short referenceValue,
                                     @Nullable final String exceptionMessage) {
        ensureGreater(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Short} value is greater as a reference value. Otherwise a specific
     * {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Short} value
     * @param referenceValue   The reference value, the given value must be greater as, as a {@link Short} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         smaller or equal as the reference value, as an instance of the class {@link Class}.
     *                         The class may not be null
     */
    public static void ensureGreater(final short value, final short referenceValue,
                                     @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value <= referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that an {@link Integer} value is greater as a reference value. Otherwise an {@link
     * IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as an {@link Integer} value
     * @param referenceValue   The reference value, the given value must be greater as, as an {@link Integer} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     */
    public static void ensureGreater(final int value, final int referenceValue,
                                     @Nullable final String exceptionMessage) {
        ensureGreater(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that an {@link Integer} value is greater as a reference value. Otherwise a specific
     * {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as an {@link Integer} value
     * @param referenceValue   The reference value, the given value must be greater as, as an {@link Integer} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         smaller or equal as the reference value, as an instance of the class {@link Class}.
     *                         The class may not be null
     */
    public static void ensureGreater(final int value, final int referenceValue,
                                     @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value <= referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Long} value is greater as a reference value. Otherwise an {@link
     * IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Long} value
     * @param referenceValue   The reference value, the given value must be greater as, as a {@link Long} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     */
    public static void ensureGreater(final long value, final long referenceValue,
                                     @Nullable final String exceptionMessage) {
        ensureGreater(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Long} value is greater as a reference value. Otherwise a specific
     * {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Long} value
     * @param referenceValue   The reference value, the given value must be greater as, as a {@link Long} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         smaller or equal as the reference value, as an instance of the class {@link Class}.
     *                         The class may not be null
     */
    public static void ensureGreater(final long value, final long referenceValue,
                                     @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value <= referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Float} value is greater as a reference value. Otherwise an {@link
     * IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Float} value
     * @param referenceValue   The reference value, the given value must be greater as, as a {@link Float} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     */
    public static void ensureGreater(final float value, final float referenceValue,
                                     @Nullable final String exceptionMessage) {
        ensureGreater(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Float} value is greater as a reference value. Otherwise a specific
     * {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Float} value
     * @param referenceValue   The reference value, the given value must be greater as, as a {@link Float} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         smaller or equal as the reference value, as an instance of the class {@link Class}.
     *                         The class may not be null
     */
    public static void ensureGreater(final float value, final float referenceValue,
                                     @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value <= referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Double} value is greater as a reference value. Otherwise an {@link
     * IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Double} value
     * @param referenceValue   The reference value, the given value must be greater as, as a {@link Double} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     */
    public static void ensureGreater(final double value, final double referenceValue,
                                     @Nullable final String exceptionMessage) {
        ensureGreater(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Double} value is greater as a reference value. Otherwise a specific
     * {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Double} value
     * @param referenceValue   The reference value, the given value must be greater as, as a {@link Double} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is smaller or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         smaller or equal as the reference value, as an instance of the class {@link Class}.
     *                         The class may not be null
     */
    public static void ensureGreater(final double value, final double referenceValue,
                                     @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value <= referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Short} value is smaller as a reference value. Otherwise an {@link
     * IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Short} value
     * @param referenceValue   The reference value, the given value must be smaller as, as a {@link Short} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     */
    public static void ensureSmaller(final short value, final short referenceValue,
                                     @Nullable final String exceptionMessage) {
        ensureSmaller(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Short} value is smaller as a reference value. Otherwise a specific
     * {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Short} value
     * @param referenceValue   The reference value, the given value must be smaller as, as a {@link Short} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         greater or equal as the reference value, as an instance of the class {@link Class}.
     *                         The class may not be null
     */
    public static void ensureSmaller(final short value, final short referenceValue,
                                     @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value >= referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that an {@link Integer} value is smaller as a reference value. Otherwise an {@link
     * IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as an {@link Integer} value
     * @param referenceValue   The reference value, the given value must be smaller as, as an {@link Integer} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     */
    public static void ensureSmaller(final int value, final int referenceValue,
                                     @Nullable final String exceptionMessage) {
        ensureSmaller(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that an {@link Integer} value is smaller as a reference value. Otherwise a specific
     * {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as an {@link Integer} value
     * @param referenceValue   The reference value, the given value must be smaller as, as an {@link Integer} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         greater or equal as the reference value, as an instance of the class {@link Class}.
     *                         The class may not be null
     */
    public static void ensureSmaller(final int value, final int referenceValue,
                                     @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value >= referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Long} value is smaller as a reference value. Otherwise an {@link
     * IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Long} value
     * @param referenceValue   The reference value, the given value must be smaller as, as a {@link Long} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     */
    public static void ensureSmaller(final long value, final long referenceValue,
                                     @Nullable final String exceptionMessage) {
        ensureSmaller(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Long} value is smaller as a reference value. Otherwise a specific
     * {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Long} value
     * @param referenceValue   The reference value, the given value must be smaller as, as a {@link Long} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         greater or equal as the reference value, as an instance of the class {@link Class}.
     *                         The class may not be null
     */
    public static void ensureSmaller(final long value, final long referenceValue,
                                     @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value >= referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Float} value is smaller as a reference value. Otherwise an {@link
     * IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Float} value
     * @param referenceValue   The reference value, the given value must be smaller as, as a {@link Float} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     */
    public static void ensureSmaller(final float value, final float referenceValue,
                                     @Nullable final String exceptionMessage) {
        ensureSmaller(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Float} value is smaller as a reference value. Otherwise a specific
     * {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Float} value
     * @param referenceValue   The reference value, the given value must be smaller as, as a {@link Float} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         greater or equal as the reference value, as an instance of the class {@link Class}.
     *                         The class may not be null
     */
    public static void ensureSmaller(final float value, final float referenceValue,
                                     @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value >= referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Double} value is smaller as a reference value. Otherwise an {@link
     * IllegalArgumentException} with a specific message is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Double} value
     * @param referenceValue   The reference value, the given value must be smaller as, as a {@link Double} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     */
    public static void ensureSmaller(final double value, final double referenceValue,
                                     @Nullable final String exceptionMessage) {
        ensureSmaller(value, referenceValue, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Double} value is smaller as a reference value. Otherwise a specific
     * {@link RuntimeException} is thrown.
     *
     * @param value            The value, which should be checked, as a {@link Double} value
     * @param referenceValue   The reference value, the given value must be smaller as, as a {@link Double} value
     * @param exceptionMessage The message of the exception, which is thrown, if the given value is greater or equal
     *                         as the reference value, as a {@link String} or null, if the exception should not have
     *                         a message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given value is
     *                         greater or equal as the reference value, as an instance of the class {@link Class}.
     *                         The class may not be null
     */
    public static void ensureSmaller(final double value, final double referenceValue,
                                     @Nullable final String exceptionMessage,
                                     @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (value >= referenceValue) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

    /**
     * Ensures, that a {@link Iterable} is not empty. Otherwise an {@link IllegalArgumentException}
     * with a specific message is thrown.
     *
     * @param iterable         The iterable, which should be checked, as an instance of the type {@link Iterable}
     * @param exceptionMessage The message of the exception, which is thrown, if the given iterable is empty, as a
     *                         {@link String} or null, if the exception should not have a message
     */
    public static void ensureNotEmpty(final Iterable<?> iterable,
                                      @Nullable final String exceptionMessage) {
        ensureNotEmpty(iterable, exceptionMessage, IllegalArgumentException.class);
    }

    /**
     * Ensures, that a {@link Iterable} is not empty. Otherwise a specific {@link RuntimeException}
     * is thrown.
     *
     * @param iterable         The iterable, which should be checked, as an instance of the type {@link Iterable}
     * @param exceptionMessage The message of the exception, which is thrown, if the given iterable is empty, as a
     *                         {@link String} or null, if the exception should not have a message
     * @param exceptionClass   The class of the runtime exception, which should be thrown, if the given iterable is
     *                         empty, as an instance of hte class {@link Class}. The class may not be null
     */
    public static void ensureNotEmpty(final Iterable<?> iterable,
                                      @Nullable final String exceptionMessage,
                                      @NonNull final Class<? extends RuntimeException> exceptionClass) {
        if (!iterable.iterator().hasNext()) {
            throwException(exceptionMessage, exceptionClass);
        }
    }

}