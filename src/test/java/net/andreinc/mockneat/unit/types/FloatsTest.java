package net.andreinc.mockneat.unit.types;

/**
 * Copyright 2017, Andrei N. Ciobanu

 Permission is hereby granted, free of charge, to any user obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 OTHERWISE, ARISING FROM, FREE_TEXT OF OR PARAM CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS PARAM THE SOFTWARE.
 */

import net.andreinc.mockneat.Constants;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static net.andreinc.mockneat.utils.LoopsUtils.loop;
import static org.apache.commons.lang3.ArrayUtils.toObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FloatsTest {

    @Test
    public void testNextFloatInCorrectRange() {
        loop(Constants.FLOATS_CYCLES,
                Constants.MOCKS,
                r -> r.floats().val(),
                n -> assertTrue(n >= 0.0 && n < 1.0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNextFloatZeroNotBound() {
        Constants.M.floats().bound(0.0f).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nextFloat_NegativeNotBound() {
        Constants.M.floats().bound(-5.0f).val();
    }

    @Test
    public void testNextFloatInCorrectRange2() {
        float bound = 0.01f;
        loop(Constants.FLOATS_CYCLES,
                Constants.MOCKS,
                r -> r.floats().bound(bound).val(),
                n -> assertTrue(n >= 0.0 && n < bound));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNextFloatNaNNotBound() {
        Constants.M.floats().bound(Float.NaN).val();
    }

    @Test(expected = NullPointerException.class)
    public void nextFloat_NullNotBound() {
        Float bound = null;
        Constants.M.floats().bound(bound).val();
    }

    @Test
    public void testNextFloatInCorrectRange3() {
        Float lowerBound = 1.1987f;
        float upperBound = Float.MAX_VALUE;

        loop(Constants.FLOATS_CYCLES,
                Constants.MOCKS,
                r -> r.floats().range(lowerBound, upperBound).val(),
                n -> assertTrue(n >= lowerBound && n < upperBound));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNextFloatBoundsNotEqual() {
        Constants.M.floats().range(0.01f, 0.01f).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNextFloatNaNNotBound2() {
        Constants.M.floats().range(Float.NaN, 10.0f).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNextFloatNaNNotBound3() {
        Constants.M.floats().range(10.0f, Float.NaN).val();
    }

    @Test(expected =  NullPointerException.class)
    public void testNextFloatNullNotBound2() {
        Float f = null;
        Constants.M.floats().range(10.0f, f).val();
    }

    @Test(expected =  NullPointerException.class)
    public void testNextFloatNullNotBound3() {
        Float f = null;
        Constants.M.floats().range(f, 10.0f).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNextFloatLesserUpperBound() {
        Constants.M.floats().range(10.0f, 8.0f).val();
    }

    @Test
    public void testNextCorrectValues() {
        float[] floats = { 1.0f, 5.0f, 10.0f, 15.0f, 20.52f };
        Set<Float> values = new HashSet<>(asList(toObject(floats)));
        loop(Constants.FLOATS_CYCLES,
                Constants.MOCKS,
                r -> r.floats().from(floats).val(),
                num -> assertTrue(values.contains(num)));
    }

    @Test
    public void testNextCorrectValues2() {
        float[] floats = { 0.0f };
        loop(Constants.FLOATS_CYCLES,
                Constants.MOCKS,
                r -> r.floats().from(floats).val(),
                num -> assertEquals(num, floats[0], 0.0));
    }

    @Test(expected = NullPointerException.class)
    public void testNextNullNotAlphabet() {
        float[] alphabet = null;
        Constants.M.floats().from(alphabet).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNextEmptyArrayNotAlphabet() {
        Constants.M.floats().from(new float[]{}).val();
    }

    @Test
    public void testDoubleStreams() {
        loop(
                Constants.FLOATS_CYCLES,
                Constants.MOCKS,
                m -> m.floats()
                        .range(0, 100)
                        .doubleStream()
                        .val()
                        .limit(100),
                stream -> stream
                            .forEach(num -> assertTrue(0f <= num && num < 100f))
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFloatsArrayNegativeSize() {
        loop(
                Constants.FLOATS_CYCLES,
                Constants.MOCKS,
                m -> m.floats()
                        .bound(10f)
                        .array(-1)
                        .val()
        );
    }

    @Test
    public void testFloatsArrayZeroSize() {
        loop(
                Constants.FLOATS_CYCLES,
                Constants.MOCKS,
                m -> m.floats()
                        .bound(10f)
                        .array(0)
                        .val(),
                arr -> assertEquals(0, arr.length)
        );
    }

    @Test
    public void testFloatsArrayCorrectSize() {
        loop(
                Constants.FLOATS_CYCLES,
                Constants.MOCKS,
                m -> {
                    int size = m.ints().bound(100).val();

                    Float[] floats = m.floats()
                                        .range(0f, 1f)
                                        .array(size)
                                        .val();

                    assertEquals(floats.length, size);
                    stream(floats).forEach(f -> assertTrue(0f <= f && f< 1f));
                }
        );
    }
}
