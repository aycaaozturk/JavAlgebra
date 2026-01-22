package de.uniwue.jpp.javalgebra;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StrukturMitEinerVerknuepfung<T> {

    Menge<T> menge;
    Abbildung<Tupel<T>, T> verknuepfung;
    //Abbildung: definition<Tupel<T>>, ziel<T>, funktion


    public StrukturMitEinerVerknuepfung(Menge<T> menge, Abbildung<Tupel<T>, T> verknuepfung) {
        if (menge.getSize().isEmpty()) {
            throw new IllegalArgumentException("invalidA");
        }
        Set<T> elements = menge.getElements().collect(Collectors.toSet());
        Set<T> werte = verknuepfung.zielmenge.getElements().collect(Collectors.toSet());
        Set<Tupel<T>> def = verknuepfung.definitionsmenge.getElements().collect(Collectors.toSet());

        for (T element1 : elements) {
            for (T element2 : elements) {
                Tupel<T> pair = new Tupel<>(element1, element2);
                T result = verknuepfung.apply(pair);
                if (menge.contains(result) == false) {
                    throw new IllegalArgumentException("f(a,b)= y,  y is not an element of menge");    //(a,b)=y degeri döndü, y degeri
                                                                       //mengede yoksa->false
                }
            }
        }

        this.menge = menge;
        this.verknuepfung = verknuepfung;
    }

    public T apply(T t1, T t2) {
        if (menge.contains(t1) == false || menge.contains(t2) == false) {
            throw new IllegalArgumentException("!");
        }
        Tupel<T> input = new Tupel<>(t1, t2);
        return verknuepfung.apply(input);


    }

    public boolean isHalbgruppe() {
        //  also für alle a, b, c aus menge gilt, dass (a+b)+c = a+(b+c)
        Set<T> elements = menge.getElements().collect(Collectors.toSet());
        for (T a : elements) {
            for (T b : elements) {
                for (T c : elements) {
                    Tupel<T> ab = new Tupel<>(a, b);
                    Tupel<T> bc = new Tupel<>(b, c);
                    T AB = apply(a, b);
                    T BC = apply(b, c);
                    T AplusBthenC = apply(AB, c);
                    T AthenBplusC = apply(a, BC);
                    if (AplusBthenC.equals(AthenBplusC) == false) {
                        return false;
                    }
                }
            }
        }
        return true;


    }

    public boolean isMonoid() {
//    Es handelt sich um einen Monoid genau dann, wenn die Struktur eine Halbgruppe ist
//    und zusätzlich ein neutrales Element e existiert, das für jedes a aus menge erfüllt: e+a = a+e = a.
//    Verknüpfungen müssen nicht kommutativ sein, deswegen muss geprüft werden,
//    dass e neutrales Element bei Verknüpfung sowohl von links als auch von rechts ist.
//    Existiert ein neutrales Element, so ist dieses eindeutig.
//    Überprüfen Sie also, ob es ein Element in menge gibt, dass neutral bezüglich der Verknüfung ist.
        boolean neutralExists = false;
        Set<T> elements = menge.getElements().collect(Collectors.toSet());
        for (T e : elements) {
            if (isItNeutral(e) == true) {
                neutralExists = true;
            }
        }
        if (isHalbgruppe() && neutralExists) {
            return true;
        } else {
            return false;
        }


    }

    public T getNeutralesElement() {
        if (isMonoid() == false) {
            throw new UnsupportedOperationException("!");
        }
        Set<T> elements = menge.getElements().collect(Collectors.toSet());
        T neutral = null;
        for (T e : elements) {
            if (isItNeutral(e) == true) {
                neutral = e;
            }


        }
        return neutral;
    }

    public boolean isItNeutral(T neutral) {
        Set<T> elements = menge.getElements().collect(Collectors.toSet());
        boolean isNeutral = true;
        for (T e : elements) {
            T result1 = apply(neutral, e);
            T result2 = apply(e, neutral);
            boolean isItNeutral = result1.equals(result2) && result1.equals(e) && result2.equals(result1) && result2.equals(e);
            if (isItNeutral == false) {

                isNeutral = false;
            }
        }
        return isNeutral;

    }

    public boolean isGruppe() {
// Es handelt sich um eine Gruppe genau dann, wenn die Struktur ein Monoid ist
// und zusätzlich jedes Element ein Inverses besitzt.
//  Wenn e das neutrale Element der Verknüpfung ist, dann muss also zu jedem a aus menge ein b aus menge existieren mit
//  a+b = b+a = e.
//  Überprüfen Sie diese Eigenschaft.
        if (isMonoid() && thereIsInverseForAll()) {
            return true;
        } else {
            return false;
        }

    }

    public T getInverse(T elem) {
        T inverse = null;
        Set<T> Elements = menge.getElements().collect(Collectors.toSet());
        for (T e : Elements) {
            T aPlusb = apply(elem, e);
            T bPlusa = apply(e, elem);
            T neutral = getNeutralesElement();
            if (aPlusb.equals(bPlusa) && aPlusb.equals(neutral) && bPlusa.equals(neutral)) {
                inverse = e;

            }
        }
        return inverse;
    }

    public boolean thereIsInverseForAll() {
        Set<T> Elements = menge.getElements().collect(Collectors.toSet());
        boolean hasInverse = true;
        for (T e : Elements) {
            if (getInverse(e) == null) {
                hasInverse = false;
            }
        }
        return hasInverse;
    }

    public boolean isKommutativ() {
// Eine Verknüpfung ist kommutativ, wenn für beliebige a, b aus menge gilt, dass a+b = b+a ist.
// Überprüfen Sie dies.
        boolean kommutativ = true;
        Set<T> Elements = menge.getElements().collect(Collectors.toSet());
        for (T a : Elements) {
            for (T b : Elements) {
                T aPlusb = apply(a, b);
                T bPlusa = apply(b, a);
                if (aPlusb.equals(bPlusa) == false) {
                    kommutativ = false;
                }
            }
        }
        return kommutativ;

    }

    public boolean isAbelscheGruppe() {
//        Es handelt sich um eine abelsche Gruppe, wenn die Struktur eine kommutative Gruppe ist.
//        Überprüfen Sie dies.
        if (isGruppe() && isKommutativ()) {
            return true;
        } else {
            return false;
        }
    }
}
