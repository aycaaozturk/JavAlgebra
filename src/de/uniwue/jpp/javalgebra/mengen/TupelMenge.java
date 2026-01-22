package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;
import de.uniwue.jpp.javalgebra.Tupel;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Stream;

public class TupelMenge<T> implements Menge<Tupel<T>> {

    Menge<T> menge;
    Set<Tupel<T>> tupelMengeElements = new HashSet<>();

    public TupelMenge(Menge<T> menge) {
        //örn (2,3) -> (2,2), (2,3), (3,2), (3,3)
        if (menge.getSize().isPresent() == false) {
            throw new IllegalArgumentException("!");
        } else {
            menge.getElements().forEach(tupelFirst -> menge
                    .getElements()
                    .forEach(tupelSecond -> tupelMengeElements
                            .add(new Tupel<>(tupelFirst, tupelSecond))));
        }
        this.menge=menge;

    }

    @Override
    public Stream<Tupel<T>> getElements() {
        return tupelMengeElements.stream();
    }

    @Override
    public boolean contains(Tupel<T> element) {
        return getElements()
                .anyMatch(currentElement -> currentElement.equals(element));
    }

    @Override
    public Optional<Integer> getSize() {
        if (menge == null || menge.getSize().isEmpty())  {
            return Optional.empty();
        } else {
           return Optional.of(menge.getSize().get() * menge.getSize().get());

        }


    }
}
