package net.andreinc.mockneat.abstraction;

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
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static net.andreinc.mockneat.utils.LoopsUtils.loop;
import static org.apache.commons.lang3.ArrayUtils.toObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MockFromFunctionsTest {

    private static class TestModel {
        private final String x1;
        private final Integer y1;

        public TestModel(String x1, Integer y1) {
            this.x1 = x1;
            this.y1 = y1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestModel testModel = (TestModel) o;

            if (!Objects.equals(x1, testModel.x1)) return false;
            return Objects.equals(y1, testModel.y1);

        }

        @Override
        public int hashCode() {
            int result = x1 != null ? x1.hashCode() : 0;
            result = 31 * result + (y1 != null ? y1.hashCode() : 0);
            return result;
        }

        public static TestModel[] getTestArray() {
            return new TestModel[]{
                    new TestModel("a", 1),
                    new TestModel("b", 2),
                    new TestModel("c", 3),
                    new TestModel("d", 4),
                    new TestModel("xa", 100),
                    new TestModel("ha", 200),
                    new TestModel("bla", 300),
                    new TestModel("Hohoho", 25)
            };
        }

        public static List<TestModel> getTestList() {
            return asList(getTestArray());
        }
    }

    private enum TestEnum { TEST1, TEST2, TEST3 }

    private enum TestEnumEmpty {}

    @Test
    public void testFromArray() {
        TestModel[] array = TestModel.getTestArray();
        Set<TestModel> possibleValues = new HashSet<>(asList(array));
        loop(Constants.OBJS_CYCLES,
                Constants.MOCKS,
                r -> r.from(array).val(),
                tm -> assertTrue(possibleValues.contains(tm)));
    }

    @Test
    public void testFromArraySingleElement() {
        String[] array = { "a" };
        loop(Constants.OBJS_CYCLES,
                Constants.MOCKS,
                r -> r.from(array).val(),
                s -> assertEquals(s, array[0]));
    }

    @Test(expected = NullPointerException.class)
    public void testFromNullArray() {
        TestModel[] array = null;
        Constants.M.from(array).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromEmptyArray() {
        TestModel[] array = new TestModel[]{};
        Constants.M.from(array).val();
    }

    @Test
    public void testFromList() {
        List<TestModel> list = TestModel.getTestList();
        Set<TestModel> possibleValues = new HashSet<>(list);
        loop(Constants.OBJS_CYCLES,
                Constants.MOCKS,
                r -> r.from(list).val(),
                tm -> assertTrue(possibleValues.contains(tm)));
    }

    @Test
    public void testFrom1ElementList() {
        List<String> list = Collections.singletonList("a");
        loop(Constants.OBJS_CYCLES,
                Constants.MOCKS,
                r -> r.from(list).val(),
                s -> assertEquals(s, list.get(0)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromNullList() {
        List<TestModel> array = null;
        Constants.M.from(array).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromEmptyList() {
        List<TestModel> list = new ArrayList<>();
        Constants.M.from(list).val();
    }

    @Test
    public void testEnum() {
        Set<TestEnum> possible = new HashSet<>(asList(TestEnum.values()));
        loop(Constants.OBJS_CYCLES,
                Constants.MOCKS,
                r -> r.from(TestEnum.class).val(),
                tm -> assertTrue(possible.contains(tm)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnumEmpty() {
        Constants.M.from(TestEnumEmpty.class).val();
    }

    @Test(expected = NullPointerException.class)
    public void testEnumNull() {
        Class<? extends Enum<?>> cls = null;
        Constants.M.from(cls).val();
    }

    @Test
    public void testFromKeys() {
        Map<Integer, Integer> map = Constants.M.ints().mapKeys(25, Constants.M.ints()::val).val();
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromKeys(map).val(),
                x -> Assert.assertTrue(map.containsKey(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromKeysNullMap() {
        Map<Integer, Integer> intMap = null;
        Constants.M.fromKeys(intMap).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromKeysEmptyMap() {
        Map<Integer, Integer> intMap = new HashMap<>();
        Constants.M.fromKeys(intMap).val();
    }

    @Test
    public void testFromValues() {
        Map<Integer, Integer> map = Constants.M.ints().mapKeys(25, Constants.M.ints()::val).val();
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromValues(map).val(),
                x -> assertTrue(map.containsValue(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromValuesNullMap() {
        Map<Integer, Integer> intMap = null;
        Constants.M.fromValues(intMap).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromValuesEmptyMap() {
        Map<Integer, Integer> intMap = new HashMap<>();
        Constants.M.fromValues(intMap).val();
    }

    /**********************
     * fromInts
     ***********************/

    @Test
    public void testFromIntsInteger() {
        Integer[] array = Constants.M.ints().array(25).val();
        Set<Integer> arrayValues = new HashSet<>(asList(array));
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromInts(array).val(),
                x -> assertTrue(arrayValues.contains(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromIntsIntegerNullArray() {
        Integer[] array = null;
        Constants.M.fromInts(array).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromIntsIntegerEmptyArray() {
        Integer[] array = new Integer[0];
        Constants.M.fromInts(array).val();
    }

    @Test
    public void testFromIntsInt() {
        int[] array = Constants.M.ints().arrayPrimitive(25).val();
        Set<Integer> arrayValues = new HashSet<>(asList(toObject(array)));
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromInts(array).val(),
                x -> assertTrue(arrayValues.contains(x)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromIntsIntEmtpyArray() {
        int[] array = new int[0];
        Constants.M.fromInts(array).val();
    }

    @Test(expected = NullPointerException.class)
    public void testFromIntsIntNullArray() {
        int[] array = null;
        Constants.M.fromInts(array).val();
    }

    @Test
    public void testFromIntsList() {
        List<Integer> list = Constants.M.ints().range(0,5).list(10).val();
        Set<Integer> listValues = new HashSet<>(list);
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromInts(list).val(),
                x -> assertTrue(listValues.contains(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromIntsListNullList() {
        List<Integer> list = null;
        Constants.M.fromInts(list).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromIntsListNotEmpty() {
        List<Integer> list = new ArrayList<>();
        Constants.M.fromInts(list).val();
    }

    @Test
    public void testFromIntsValues() {
        Map<Character, Integer> map = Constants.M.chars().letters().mapVals(25, Constants.M.ints()::val).val();
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromIntsValues(map).val(),
                x -> assertTrue(map.containsValue(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromIntsValuesNullMap() {
        Map<Collection<?>, Integer> map = null;
        Constants.M.fromIntsValues(map).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromIntsValuesEmptyMap() {
        Map<String, Integer> map = new HashMap<>();
        Constants.M.fromIntsValues(map).val();
    }

    @Test
    public void testFromIntsKeys() {
        Map<Integer, Character> map = Constants.M.chars().letters().mapKeys(25, Constants.M.ints()::val).val();
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromIntsKeys(map).val(),
                x -> assertTrue(map.containsKey(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromIntKeysNullMap() {
        Map<Integer, ?> map = null;
        Constants.M.fromIntsKeys(map).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromIntKeysEmptyMap() {
        Map<Integer, ?> map = new HashMap<>();
        Constants.M.fromIntsKeys(map).val();
    }

    /**********************
     * fromDoubles
     ***********************/

    @Test
    public void testFromDoublesDouble() {
        Double[] array = Constants.M.doubles().array(25).val();
        Set<Double> arrayValues = new HashSet<>(asList(array));
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromDoubles(array).val(),
                x -> assertTrue(arrayValues.contains(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromDoublesDoubleNullArray() {
        Double[] array = null;
        Constants.M.fromDoubles(array).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDoublesDoubleEmptyArray() {
        Double[] array = new Double[0];
        Constants.M.fromDoubles(array).val();
    }

    @Test
    public void testFromDoublesDoublePrim() {
        double[] array = Constants.M.doubles().arrayPrimitive(25).val();
        Set<Double> arrayValues = new HashSet<>(asList(toObject(array)));
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromDoubles(array).val(),
                x -> assertTrue(arrayValues.contains(x)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDoublesDoublePrimEmtpyArray() {
        double[] array = new double[0];
        Constants.M.fromDoubles(array).val();
    }

    @Test(expected = NullPointerException.class)
    public void testFromDoublesDoublePrimNullArray() {
        double[] array = null;
        Constants.M.fromDoubles(array).val();
    }

    @Test
    public void testFromDoubleList() {
        List<Double> list = Constants.M.doubles().range(0,5).list(10).val();
        Set<Double> listValues = new HashSet<>(list);
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromDoubles(list).val(),
                x -> assertTrue(listValues.contains(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromDoubleListNullList() {
        List<Double> list = null;
        Constants.M.fromDoubles(list).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDoublesListNotEmpty() {
        List<Double> list = new ArrayList<>();
        Constants.M.fromDoubles(list).val();
    }

    @Test
    public void testFromDoublesValues() {
        Map<Character, Double> map = Constants.M.chars().letters().mapVals(25, Constants.M.doubles()::val).val();
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromDoublesValues(map).val(),
                x -> assertTrue(map.containsValue(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromDoublesValuesNullMap() {
        Map<Collection<?>, Double> map = null;
        Constants.M.fromDoublesValues(map).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDoublesValuesEmptyMap() {
        Map<String, Double> map = new HashMap<>();
        Constants.M.fromDoublesValues(map).val();
    }

    @Test
    public void testFromDoublesKeys() {
        Map<Double, Character> map = Constants.M.chars().letters().mapKeys(25, Constants.M.doubles()::val).val();
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromDoublesKeys(map).val(),
                x -> assertTrue(map.containsKey(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromDoubleKeysNullMap() {
        Map<Double, ?> map = null;
        Constants.M.fromDoublesKeys(map).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDoublesKeysEmptyMap() {
        Map<Double, ?> map = new HashMap<>();
        Constants.M.fromDoublesKeys(map).val();
    }

    /**********************
     * fromLongs
     ***********************/

    @Test
    public void testFromLongsLong() {
        Long[] array = Constants.M.longs().array(25).val();
        Set<Long> arrayValues = new HashSet<>(asList(array));
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromLongs(array).val(),
                x -> assertTrue(arrayValues.contains(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromLongsLongNullArray() {
        Long[] array = null;
        Constants.M.fromLongs(array).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromLongsLongEmptyArray() {
        Long[] array = new Long[0];
        Constants.M.fromLongs(array).val();
    }

    @Test
    public void testFromLongsLongPrim() {
        long[] array = Constants.M.longs().arrayPrimitive(25).val();
        Set<Long> arrayValues = new HashSet<>(asList(toObject(array)));
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromLongs(array).val(),
                x -> assertTrue(arrayValues.contains(x)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromLongsLongPrimEmtpyArray() {
        long[] array = new long[0];
        Constants.M.fromLongs(array).val();
    }

    @Test(expected = NullPointerException.class)
    public void testFromLongsLongPrimNullArray() {
        long[] array = null;
        Constants.M.fromLongs(array).val();
    }

    @Test
    public void testFromLongsList() {
        List<Long> list = Constants.M.longs().range(0,5).list(10).val();
        Set<Long> listValues = new HashSet<>(list);
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromLongs(list).val(),
                x -> assertTrue(listValues.contains(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromLongsLongNullList() {
        List<Long> list = null;
        Constants.M.fromLongs(list).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromLongsListNotEmpty() {
        List<Double> list = new ArrayList<>();
        Constants.M.fromDoubles(list).val();
    }

    @Test
    public void testFromLongsValues() {
        Map<Character, Long> map = Constants.M.chars().letters().mapVals(25, Constants.M.longs()::val).val();
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromLongsValues(map).val(),
                x -> assertTrue(map.containsValue(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromLongsValuesNullMap() {
        Map<Collection<?>, Long> map = null;
        Constants.M.fromLongsValues(map).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromLongsValuesEmptyMap() {
        Map<String, Long> map = new HashMap<>();
        Constants.M.fromLongsValues(map).val();
    }

    @Test
    public void testFromLongsKeys() {
        Map<Long, Character> map = Constants.M.chars().letters().mapKeys(25, Constants.M.longs()::val).val();
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromLongsKeys(map).val(),
                x -> assertTrue(map.containsKey(x)));

    }

    @Test(expected = NullPointerException.class)
    public void testFromLongsKeysNullMap() {
        Map<Long, ?> map = null;
        Constants.M.fromLongsKeys(map).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromLongsKeysEmptyMap() {
        Map<Long, ?> map = new HashMap<>();
        Constants.M.fromLongsKeys(map).val();
    }

    /**********************
     * fromStrings
     ***********************/

    @Test
    public void testFromStrings() {
        String[] array = {"a", "b", "c"};
        Set<String> arrayValues = new HashSet<>(asList(array));
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromStrings(array).val(),
                x -> assertTrue(arrayValues.contains(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromStringNullArray() {
        String[] array = null;
        Constants.M.fromStrings(array).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringsEmptyArray() {
        String[] array = new String[0];
        Constants.M.fromStrings(array).val();
    }

    @Test
    public void testFromStringList() {
        List<String> list = new ArrayList<>(asList("a","b","c"));
        Set<String> listValues = new HashSet<>(list);
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromStrings(list).val(),
                x -> assertTrue(listValues.contains(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromStringNullList() {
        List<String> list = null;
        Constants.M.fromStrings(list).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromLStringsListNotEmpty() {
        List<String> list = new ArrayList<>();
        Constants.M.fromStrings(list).val();
    }

    @Test
    public void testFromStringValues() {
        Map<Character, String> map = Constants.M.chars().letters().mapVals(25, Constants.M.names()::val).val();
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromStringsValues(map).val(),
                x -> assertTrue(map.containsValue(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromStringValuesNullMap() {
        Map<Collection<?>, String> map = null;
        Constants.M.fromStringsValues(map).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringValuesEmptyMap() {
        Map<String, String> map = new HashMap<>();
        Constants.M.fromStringsValues(map).val();
    }

    @Test
    public void testFromStringKeys() {
        Map<String, Character> map = Constants.M.chars().letters().mapKeys(25, Constants.M.names()::val).val();
        loop(Constants.MOCK_CYCLES,
                Constants.MOCKS,
                r -> r.fromStringsKeys(map).val(),
                x -> assertTrue(map.containsKey(x)));
    }

    @Test(expected = NullPointerException.class)
    public void testFromStringKeysNullMap() {
        Map<String, ?> map = null;
        Constants.M.fromStringsKeys(map).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringKeysEmptyMap() {
        Map<String, ?> map = new HashMap<>();
        Constants.M.fromStringsKeys(map).val();
    }
}
