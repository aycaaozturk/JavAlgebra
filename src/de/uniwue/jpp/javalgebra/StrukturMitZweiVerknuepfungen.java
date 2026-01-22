package de.uniwue.jpp.javalgebra;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StrukturMitZweiVerknuepfungen<T> {

    Menge<T> menge;
    Abbildung<Tupel<T>, T> plus;
    Abbildung<Tupel<T>, T> mal;


    public StrukturMitZweiVerknuepfungen(Menge<T> menge, Abbildung<Tupel<T>, T> plus, Abbildung<Tupel<T>, T> mal) {
        if (menge == null || menge.getSize().isEmpty()) {
            throw new IllegalArgumentException("invalidC");
        }
        Set<T> Elements = menge.getElements().collect(Collectors.toSet());
        for (T a : Elements) {
            for (T b : Elements) {
                Tupel<T> ab = new Tupel<>(a, b);
                T resultPlus = plus.apply(ab);
                T resultMal = mal.apply(ab);
                if (Elements.contains(resultPlus) == false || Elements.contains(resultMal) == false) {
                    throw new IllegalArgumentException("invalidD");
                }

            }
        }
        this.menge = menge;
        this.plus = plus;
        this.mal = mal;

    }

    public T applyPlus(T t1, T t2) {

        Set<T> Elements = menge.getElements().collect(Collectors.toSet());
        if (Elements.contains(t1) == false || Elements.contains(t2) == false) {
            throw new IllegalArgumentException("invalid2");
        }
        Tupel<T> paar = new Tupel<>(t1, t2);
        T result = plus.apply(paar);
        return result;
    }

    public T applyMal(T t1, T t2) {

        Set<T> Elements = menge.getElements().collect(Collectors.toSet());
        if (Elements.contains(t1) == false || Elements.contains(t2) == false) {
            throw new IllegalArgumentException("invalid1");
        }
        Tupel<T> paar = new Tupel<>(t1, t2);
        T result = mal.apply(paar);
        return result;
    }

    public boolean isDistributiv() {
//  Eine Struktur ist distributiv genau dann, wenn für beliebige a, b, c aus menge die beiden Distributivgesetze gelten:
//      a*(b+c)=a*b + a*c
//      (a+b)*c=a*c + b*c
        Set<T> Elements = menge.getElements().collect(Collectors.toSet());
        boolean dist = true;
        for (T a : Elements) {
            for (T b : Elements) {
                for (T c : Elements) {
                    T bPLUSc = applyPlus(b, c);
                    T aMALb = applyMal(a, b);
                    T aMALc = applyMal(a, c);
                    T leftFirstRule = applyMal(a, bPLUSc);
                    T rightFirstRule = applyPlus(aMALb, aMALc);
                    T aPLUSb = applyPlus(a, b);
                    T bMALc = applyMal(b, c);
                    T leftSecondRule = applyMal(aPLUSb, c);
                    T rightSecondRule = applyPlus(aMALc, bMALc);
                    if (!leftFirstRule.equals(rightFirstRule) || !leftSecondRule.equals(rightSecondRule)) {
                        dist = false;
                    }
                }
            }
        }
        return dist;


    }

    public boolean isRing() {
//        Eine Struktur ist ein Ring genau dann, wenn menge mit der Verknüpfung plus eine abelsche Gruppe ist,
//                wenn menge mit mal eine Halbgruppe ist und Distributivität gegeben ist.
        StrukturMitEinerVerknuepfung MAL = new StrukturMitEinerVerknuepfung(menge, mal);
        StrukturMitEinerVerknuepfung PLUS = new StrukturMitEinerVerknuepfung(menge, plus);

        if (PLUS.isAbelscheGruppe() && MAL.isHalbgruppe() && isDistributiv()) {
            return true;
        } else {
            return false;
        }


    }

    public T getNull() {
//  Wenn es sich bei der Struktur um einen Ring handelt, ist insbesondere menge mit plus eine abelsche Gruppe.
//  Das neutrale Element bezüglich der plus-Verknüpfung wird Nullelement genannt.
//  Falls die Struktur kein Ring ist, werfen Sie eine UnsupportedOperationException mit aussagekräftiger Nachricht.
//  Ansonsten geben Sie das Nullelement zurück.
        if (isRing() == false) {
            throw new UnsupportedOperationException("!");
        }
        StrukturMitEinerVerknuepfung PLUS = new StrukturMitEinerVerknuepfung(menge, plus);
        T nüll = (T) PLUS.getNeutralesElement();
        return nüll;


    }

    public boolean isKoerper() {
        if(isRing()==false){
            return false;
        }
// Es handelt sich um einen Körper genau dann, wenn die Struktur ein Ring ist und
// zusätzlich menge ohne das Nullelement zusammen mit mal eine abelsche Gruppe ist.
        Set<T> Elements = menge.getElements().collect(Collectors.toSet());
        T nüll = getNull();
        if(nüll != null){
            Elements.remove(nüll);
        }

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

        Menge<T> OhneNullElement = new SimpleMenge<>(Elements);   //mengenin elemanlari, null elemani olmadan
        Set<Tupel<T>> setset = mal.definitionsmenge.getElements().collect(Collectors.toSet());

        Set<T> zielziel = mal.zielmenge.getElements().collect(Collectors.toSet());
        Set<Tupel<T>> setsetNEw = new HashSet<>(setset);

        Set<T> zielzielNEw = new HashSet<>(zielziel);

        for(Tupel<T> ab : setset){
            if(ab.getFirst()==getNull() || ab.getSecond()==getNull()){


                                             //null elemaninin verdigi sonucu cikardik
               setsetNEw.remove(ab);           //null elemanli girdileri cikardik (0,2), (5,0) (0.0) gibi
            }
        }        //sonuc olarak mal yapisindan 0 girdilerini ve 0 girdilerinin ürettgi ciktilar gitti
                 //su anki hata:
                 //(a,b)=y degeri döndü, y degeri
                 //mengede yoksa->false

        zielzielNEw.remove(nüll);
        Menge<Tupel<T>> setRemoved = new SimpleMenge<>(setsetNEw);
        Menge<T> zielRemoved = new SimpleMenge<>(zielzielNEw);


        Abbildung<T,T> malNew;
        StrukturMitEinerVerknuepfung MALohneNULL;
        try {
            malNew = new Abbildung(setRemoved, zielRemoved, mal.abbVorschrift);
            MALohneNULL = new StrukturMitEinerVerknuepfung(OhneNullElement, malNew);
        } catch (Exception e) {
            return false;
        }




        if (isRing() && MALohneNULL.isAbelscheGruppe()) {
            return true;
        } else {
            return false;
        }

    }
//    Wenn sich bei der Struktur um einen Körper handelt, ist insbesondere menge ohne Null mit mal eine abelsche Gruppe.
//    Das neutrale Element bezüglich mal wird Einselement genannt.
//    Falls die Struktur kein Körper ist, werfen Sie eine UnsupportedOperationException mit aussagekräftiger Nachricht.
//    Ansonsten geben Sie das Einselement zurück.
//

    public T getEins() {
        if(isKoerper()==false){
            throw new UnsupportedOperationException("!");
        }
        StrukturMitEinerVerknuepfung malmal = new StrukturMitEinerVerknuepfung(menge, mal);
        return (T) malmal.getNeutralesElement();

    }
}
