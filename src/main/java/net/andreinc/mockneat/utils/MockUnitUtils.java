package net.andreinc.mockneat.utils;

/**
 * Copyright 2017, Andrei N. Ciobanu

 Permission is hereby granted, free of charge, to any user obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. PARAM NO EVENT SHALL THE AUTHORS OR
 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER PARAM AN ACTION OF CONTRACT, TORT OR
 OTHERWISE, ARISING FROM, FREE_TEXT OF OR PARAM CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS PARAM THE SOFTWARE.
 */

import net.andreinc.mockneat.abstraction.MockUnit;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.andreinc.aleph.AlephFormatter.str;
import static net.andreinc.mockneat.utils.ValidationUtils.*;

public final class MockUnitUtils {

    private MockUnitUtils() {}

    public static <T> void add(Class<? extends Collection> collectionClass, Collection<T> result, Supplier<T> supplier) {
        T value = supplier.get();
        try {
            result.add(value);
        } catch (Exception e) {
            String msg = str(CANNOT_ADD_VALUE_TO_COLLECTION)
                            .arg("val", value)
                            .arg("cls", collectionClass)
                            .fmt();
            throw new IllegalArgumentException(msg, e);
        }
    }
    public static <T> void add(Class<? extends List> listClass, List<T> result, Supplier<T> supplier) {
        T value = supplier.get();
        try {
            result.add(value);
        } catch (Exception e) {
            String msg = str(CANNOT_ADD_VALUE_TO_LIST).args("value", value, "cls", listClass).fmt();
            throw new IllegalArgumentException(msg, e);
        }
    }
    public static <T> void add(Class<? extends Set> setClass, Set<T> result, Supplier<T> supplier) {
        T value = supplier.get();
        try {
            result.add(value);
        } catch (Exception e) {
            String msg = str(CANNOT_ADD_VALUE_TO_SET).args("value", value, "cls", setClass).fmt();
            throw new IllegalArgumentException(msg, e);
        }
    }
    public static <T, R> void put(Class<? extends Map> mapClass, Map<T, R> map, Supplier<T> keySupplier, Supplier<R> valueSupplier) {
        T keyVal = keySupplier.get();
        R valVal =  valueSupplier.get();
        try {
            map.put(keyVal, valVal);
        } catch(Exception e ) {
            String msg = str(CANNOT_PUT_VALUES_TO_MAP)
                            .args("key", keyVal, "val", valVal, "cls", mapClass.getSimpleName())
                            .fmt();
            throw new IllegalArgumentException(msg, e);
        }
    }
    public static <T, R> void put(Class<? extends Map> mapClass, Map<T, R> map, T key, R value) {
        try {
            map.put(key, value);
        } catch (Exception e) {
            String msg = str(CANNOT_PUT_VALUES_TO_MAP)
                            .args("key", key, "val", value, "cls", mapClass.getSimpleName())
                            .fmt();
            throw new IllegalArgumentException(msg, e);
        }
    }

    public static Object mockOrObject(Object obj) {
        if (null == obj) {
            return null;
        }
        if (obj instanceof MockUnit<?>) {
            return ((MockUnit<?>) obj).val();
        }
        return obj;
    }

    public static String listTypes(Object[] objects) {
        final StringBuilder buff = new StringBuilder("(");
        Arrays.stream(objects).forEach(obj -> {
            if (null != obj) { buff.append(obj.getClass().getName()); }
            else { buff.append("null"); }
            buff.append(',');
        });
        buff.deleteCharAt(buff.length()-1);
        buff.append(')');
        return buff.toString();
    }

    public static <T, R> Supplier<R> ifSupplierNotNullDo(Supplier<T> supplier, Function<T, R> function) {
        return () -> {
            T val = supplier.get();
            if (null == val)
                return null;
            return function.apply(val);
        };
    }

}
