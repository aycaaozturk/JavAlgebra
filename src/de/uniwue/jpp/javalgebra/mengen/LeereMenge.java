package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;

import java.util.stream.Stream;

public class LeereMenge<T> implements Menge<T> {

    @Override
    public Stream<T> getElements() {
        return Stream.empty();
        //null mi olmali empty mi?
    }
}
