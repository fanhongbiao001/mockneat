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

import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.CallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

public final class LoopsUtils {

    private static final Logger logger = LoggerFactory.getLogger(LoopsUtils.class);

    private LoopsUtils() {}

    public static void loop(int cycles, CallBack callBack) {
        for (int i = 0; i < cycles; i++) {
            callBack.call();
        }
    }

    public static void loop(int cycles, MockNeat[] array, Consumer<MockNeat> consumer) {
        loop(cycles, () -> Arrays.stream(array).forEach(consumer));
    }

    public static <T> void loop(int cycles, MockNeat[] array, Function<MockNeat, T> map, Consumer<T> consume) {
        loop(cycles, () -> Arrays.stream(array).map(map).forEach(consume));
    }

    public static <T> void loop(boolean dbg, int cycles, MockNeat[] array, Function<MockNeat, T> map, Consumer<T> consume) {
        loop(cycles,
                () -> Arrays.stream(array)
                        .map(r -> {
                            T o = map.apply(r);
                            if (dbg) {
                                logger.info(null == o ? "" : o.toString());
                            }
                            return o;
                        })
                        .forEach(consume));

    }
}