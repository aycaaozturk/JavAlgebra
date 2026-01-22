package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EndlicheMenge<T> implements Menge<T> {
    Set<T> objects;

    public EndlicheMenge(Set<T> objects) {
        this.objects=objects;
    }

    @Override
    public Stream<T> getElements() {
        return objects.stream();
    }

    public de.uniwue.jpp.javalgebra.mengen.EndlicheMenge<T> createUntermenge(Predicate<T> filter) {
        Set<T> Unter = getElements().filter(filter).collect(Collectors.toSet());
        de.uniwue.jpp.javalgebra.mengen.EndlicheMenge<T> UnterMenge = new de.uniwue.jpp.javalgebra.mengen.EndlicheMenge<>(Unter);
        return UnterMenge;

    }
}
