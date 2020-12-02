package net.andreinc.mockneat.unit.objects;

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

import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockUnit;
import net.andreinc.mockneat.abstraction.MockUnitBase;
import net.andreinc.mockneat.abstraction.MockUnitDouble;
import net.andreinc.mockneat.abstraction.MockValue;
import net.andreinc.mockneat.types.Pair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static java.math.BigDecimal.valueOf;
import static net.andreinc.mockneat.abstraction.MockConstValue.constant;
import static net.andreinc.mockneat.abstraction.MockUnitValue.unit;
import static net.andreinc.mockneat.utils.ValidationUtils.*;

public class Probabilities<T> extends MockUnitBase implements MockUnit<T> {

    private final List<Pair<BigDecimal, MockValue>> probs = new ArrayList<>();
    private final MockUnitDouble mud;
    private final Class<T> cls;

    public static <T> Probabilities<T> probabilities(Class<T> cls) {
        return MockNeat.threadLocal().probabilites(cls);
    }

    protected Probabilities(Class<T> cls) {
        this(MockNeat.threadLocal(), cls);
    }

    public Probabilities(MockNeat mockNeat, Class<T> cls) {
        super(mockNeat);
        this.mud = mockNeat.doubles().range(0, 1.0);
        this.cls = cls;
    }

    @Override
    public Supplier<T> supplier() {
        return this::getMock;
    }

    public Probabilities<T> add(Double prob, MockUnit<T> mock) {
        notNull(prob, "prob");
        isTrue(prob.compareTo(0.0)>0, PROBABILITY_NOT_NEGATIVE, "prob", prob);
        BigDecimal probInternal = valueOf(prob);
        BigDecimal lastVal = lastVal();
        BigDecimal toAdd = lastVal.add(probInternal);
        isTrue(toAdd.compareTo(valueOf(1.0)) <= 0, PROBABILITIES_SUM_BIGGER);
        probs.add(Pair.of(toAdd, unit(mock)));
        return this;
    }

    public Probabilities<T> add(Double prob, T obj) {
        notNull(prob, "prob");
        isTrue(prob.compareTo(0.0)>0, PROBABILITY_NOT_NEGATIVE, "prob", prob);
        BigDecimal probInternal = valueOf(prob);
        BigDecimal lastVal = lastVal();
        BigDecimal toAdd = lastVal.add(probInternal);
        isTrue((toAdd.compareTo(valueOf(1.0)) <= 0), PROBABILITIES_SUM_BIGGER);
        probs.add(Pair.of(toAdd, constant(obj)));
        return this;
    }

    private BigDecimal lastVal() {
        return (probs.isEmpty()) ? valueOf(0.0) : probs.get(probs.size()-1).getFirst();
    }

    private T getMock() {
        isTrue((probs.get(probs.size()-1).getFirst().compareTo(valueOf(1.0))) == 0, PROBABILITIES_SUM_NOT_1);
        BigDecimal rVal = mud.map(BigDecimal::valueOf).val();
        int i = 0;
        while(probs.get(i).getFirst().compareTo(rVal) < 0) {
            i++;
        }
        return cls.cast(probs.get(i).getSecond().get());
    }
}