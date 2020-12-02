package net.andreinc.mockneat.types.enums;

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
 OTHERWISE, ARISING FROM, OUT OF OR PARAM CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS PARAM THE SOFTWARE.
 */

import net.andreinc.mockneat.types.Pair;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

@SuppressWarnings("ImmutableEnumChecker")
public enum UserNameType {

    ADJECTIVE_NOUN(
            unmodifiableList(asList(
                    new Pair<>(DictType.EN_ADJECTIVE_1SYLL, DictType.EN_NOUN_1SYLL),
                    new Pair<>(DictType.EN_ADJECTIVE_1SYLL, DictType.EN_NOUN_2SYLL),
                    new Pair<>(DictType.EN_ADJECTIVE_2SYLL, DictType.EN_NOUN_1SYLL)
            ))
    ),

    ADVERB_VERB(
            unmodifiableList(asList(
                    new Pair<>(DictType.EN_ADVERB_1SYLL, DictType.EN_VERB_1SYLL),
                    new Pair<>(DictType.EN_ADVERB_2SYLL, DictType.EN_VERB_1SYLL),
                    new Pair<>(DictType.EN_ADVERB_1SYLL, DictType.EN_VERB_2SYLL)
            ))
    ),

    ADJECTIVE_FIRST_NAME(
            unmodifiableList(asList(
                    new Pair<>(DictType.EN_ADJECTIVE_1SYLL, DictType.FIRST_NAME_MALE_AMERICAN),
                    new Pair<>(DictType.EN_ADJECTIVE_1SYLL, DictType.FIRST_NAME_FEMALE_AMERICAN)
            ))
    ),

    ADJECTIVE_LAST_NAME(
            unmodifiableList(asList(
                    new Pair<>(DictType.EN_ADJECTIVE_1SYLL, DictType.LAST_NAME_AMERICAN),
                    new Pair<>(DictType.EN_ADJECTIVE_2SYLL, DictType.LAST_NAME_AMERICAN)
            ))
    ),

    ADJECTIVE_FIRST_NAME_MALE(
            Collections.singletonList(
                    new Pair<>(DictType.EN_ADJECTIVE_1SYLL, DictType.FIRST_NAME_MALE_AMERICAN)
            )
    ),

    ADJECTIVE_FIRST_NAME_FEMALE(
            Collections.singletonList(
                    new Pair<>(DictType.EN_ADJECTIVE_1SYLL, DictType.FIRST_NAME_FEMALE_AMERICAN)
            )
    );

    private final List<Pair<DictType, DictType>> dictCombos;

    UserNameType(List<Pair<DictType, DictType>> dictCombos) {
        this.dictCombos = dictCombos;
    }

    public List<Pair<DictType, DictType>> getDictCombos() {
        return dictCombos;
    }
}
