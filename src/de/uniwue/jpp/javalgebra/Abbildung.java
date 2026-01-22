package de.uniwue.jpp.javalgebra;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Abbildung<T, S> {

    Menge<T> definitionsmenge;
    Menge<S> zielmenge;
    Function<T, S> abbVorschrift; //fonksiyon kurali


    public Abbildung(Menge<T> definitionsmenge, Menge<S> zielmenge, Function<T, S> abbVorschrift) {

        if (definitionsmenge.getSize().isEmpty() || zielmenge.getSize().isEmpty()) {
            throw new IllegalArgumentException("invalid3");
        }
        Set<T> definition = definitionsmenge.getElements().collect(Collectors.toSet());
        Set<S> ziel = zielmenge.getElements().collect(Collectors.toSet());

        //bir eleman def te varsa ve karsiligi zielmengede yoksa -> exception

        for (T elements : definition) {
            S output = abbVorschrift.apply(elements);
            if (ziel.contains(output) == false) {
                throw new IllegalArgumentException("invalid4");  //x degerinden bir y degeri döndü ama bu deger
                                                                 // y kümesinde (zielmenge) yok -> false
            }

        }
        this.definitionsmenge = definitionsmenge;
        this.zielmenge = zielmenge;
        this.abbVorschrift = abbVorschrift;
    }

    public S apply(T t) {
        if (definitionsmenge.contains(t) == false) {
            throw new IllegalArgumentException("!");
        }
        S output = abbVorschrift.apply(t);
        return output;
    }

    public Menge<S> getBildVon(Menge<T> m) {
//  Wenn m eine Untermenge von definitionsmenge ist, dann ist das Bild von m eine Untermenge von zielmenge,
//  die genau die Elemente enthält, auf die die Elemente von m abgebildet werden.
//  Überprüfen Sie also zunächst, ob m eine Untermenge von definitionsmenge ist,
//  ansonsten werfen Sie eine IllegalArgumentException mit aussagekräftiger Nachricht.
//  Dann geben Sie das Bild als eine Menge<S> zurück. Es bietet sich an eine der zuvor implementierten Mengen zu verwenden.

        Set<T> MENGE = m.getElements().collect(Collectors.toSet());

        for (T element : MENGE) {
            if (definitionsmenge.contains(element) == false) {
                throw new IllegalArgumentException("!");
            }
        }

        Set<S> resultSet = new HashSet<>();

        for (T element : MENGE) {
            S result = apply(element);

            resultSet.add(result);
        }
        class SimpleMenge<S> implements Menge<S> { //cok havali oldu \m/

            private Set<S> elements;

            public SimpleMenge(Set<S> elements) {
                this.elements = elements;
            }

            @Override
            public Stream<S> getElements() {
                return elements.stream();
            }


        }
        Menge<S> bild = new SimpleMenge<>(resultSet);
        return bild;

    }

    public Menge<T> getUrbildVon(Menge<S> m) {  //ciktilar verilmis, girdilerini istiyor
        //kendim yazdim!!!!!!!!!!!!!
        Set<S> MENGE = m.getElements().collect(Collectors.toSet());

        for (S element : MENGE) {
            if (zielmenge.contains(element) == false) {
                throw new IllegalArgumentException("!");
            }

        }

        //tüm defMengeyi fonksiyona koy, ciktilardan m ile eslesenleri al, onlar icin koydugun degerlerden bir set yap
        List<T> sameXvalues = new ArrayList<>();

        Map<S, ArrayList<T>> inputOutput = new HashMap<>();
        Map<T, S> Funktion = new HashMap<>();

        Set<T> def = definitionsmenge.getElements().collect(Collectors.toSet());

//        for(T input : def){                   //input: definition
//            S output = apply(input);          //output: ziel
//            inputOutput                       // key: y degeri    value: x degeri
//                                             // birden fazla x degeri ayni y degerine gittiyse bunlari ayri ayri ver
//                                             // örn: f(x) = x hoch 2
//                                             // f(2)=4,   f(-2)=4
//
//        }
        for (T xValue : def) {
            S yValue = apply(xValue);
            Funktion.put(xValue, yValue);
        }

        List<Map.Entry<T, S>> FunktionList = new ArrayList<>(Funktion.entrySet());

        Set<T> xValuesOfGivenSet = new HashSet<>();
        //MENGE: bize verilen S tipinde küme, y nin altkümesi

        for (Map.Entry<T, S> XYpair : FunktionList) {
            if (MENGE.contains(XYpair.getValue()) && XYpair.getKey() != null) {
                xValuesOfGivenSet.add(XYpair.getKey());
            } else continue;
        }

        class SimpleMenge<S> implements Menge<S> {

            private Set<S> elements;

            public SimpleMenge(Set<S> elements) {
                this.elements = elements;
            }

            @Override
            public Stream<S> getElements() {
                return elements.stream();
            }


        }
        Menge<T> Urbild = new SimpleMenge<>(xValuesOfGivenSet);
        return Urbild;
    }

    public boolean isInjektiv() {  //iki farkli x degeri ayni y degerine gidemez, gidiyorsa x1=x2
        Set<T> xValues = definitionsmenge.getElements().collect(Collectors.toSet());
        Set<S> yValues = zielmenge.getElements().collect(Collectors.toSet());

        Set<S> Results = new HashSet<>();



        for (T xValue : xValues) {
            S yValue = apply(xValue);
            if (Results.add(yValue) == false) {
                return false;
            }

        }
        return true;


    }

    public boolean isSurjektiv() {
//        Eine Abbildung ist surjektiv genau dann,
//        wenn es für jedes Element s aus der zielmenge mindestens ein Element aus der definitionsmenge gibt,
//        das auf s abbildet.
//        yani tüm y degerleri icin en az bir x var
        class SimpleMenge<T> implements Menge<T> {

            private Set<T> elements;

            public SimpleMenge(Set<T> elements) {
                this.elements = elements;
            }
            @Override
            public Stream<T> getElements() {
                return elements.stream();
            }
        }
        class SimpleMenge2<S> implements Menge<S> {

            private Set<S> elements;

            public SimpleMenge2(Set<S> elements) {
                this.elements = elements;
            }
            @Override
            public Stream<S> getElements() {
                return elements.stream();
            }
        }
        Set<T> Def = definitionsmenge.getElements().collect(Collectors.toSet());
        Set<S> Ziel = zielmenge.getElements().collect(Collectors.toSet());
        Set<T> XValues = new HashSet<>();
        Set<S> YValues = new HashSet<>();

        for(S yValue : Ziel){
            YValues.add(yValue);
            Menge<S> YvaluesMenge = new SimpleMenge2<>(YValues);
            Menge<T> UrbildVonYValue = getUrbildVon(YvaluesMenge);
            if(UrbildVonYValue.isEmpty() || UrbildVonYValue==null){
                return false;
            }
            else {
                YValues.remove(yValue);
            }
        } return true;
    }


    public boolean isBijektiv() {
        if (isInjektiv() && isSurjektiv()) {
            return true;
        } else return false;
    }

    public Abbildung<S, T> getUmkehrabbildung() {
        if(isBijektiv()==false){
            throw new UnsupportedOperationException("nicht bijektiv!");
        }
        // attribute:     Menge<T> definitionsmenge;
        //               Menge<S> zielmenge;
        //               Function<T, S> abbVorschrift;
        class SimpleMenge<T> implements Menge<T> {

            private Set<T> elements;

            public SimpleMenge(Set<T> elements) {
                this.elements = elements;
            }
            @Override
            public Stream<T> getElements() {
                return elements.stream();
            }
        }
        class SimpleMenge2<S> implements Menge<S> {

            private Set<S> elements;

            public SimpleMenge2(Set<S> elements) {
                this.elements = elements;
            }
            @Override
            public Stream<S> getElements() {
                return elements.stream();
            }
        }

        Menge<S> DefVonUmkehr =zielmenge;
        Menge<T> ZielVonUmkehr= definitionsmenge;

        //weil es bijektiv ist, hat getUrbildVon immer ein Element, also Typ von T

        Function<S,T> reversedFunction = S-> getUrbildvonY(S);
         return new Abbildung<>(DefVonUmkehr, ZielVonUmkehr, reversedFunction);

    }
    public T getUrbildvonY(S yWert) {
        class SimpleMenge<T> implements Menge<T> {

            private Set<T> elements;

            public SimpleMenge(Set<T> elements) {
                this.elements = elements;
            }
            @Override
            public Stream<T> getElements() {
                return elements.stream();
            }
        }
        class SimpleMenge2<S> implements Menge<S> {

            private Set<S> elements;

            public SimpleMenge2(Set<S> elements) {
                this.elements = elements;
            }
            @Override
            public Stream<S> getElements() {
                return elements.stream();
            }
        }
     Set<S> yValue= new HashSet<>();
     T X=null;

     yValue.add(yWert);
     Menge<S> yMenge = new SimpleMenge2<>(yValue);
     Menge<T> xMenge = getUrbildVon(yMenge);
     Set<T> xValue = xMenge.getElements().collect(Collectors.toSet());
     for(T element: xValue) {
         X=element;
     }
     return X;



    }


}
