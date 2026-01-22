package de.uniwue.jpp.javalgebra;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Relation<T> implements Menge<Tupel<T>> {


    //BiFunction, 2 girdi alan, bir cikti dönen bir fonksiyondur,
    //BiFunction<girdi1, girdi2, cikti> seklindedir
//    apply() metodu ile iki girdi üzerinde işlem yapılır ve sonuç döndürülür.
//    andThen() metodu, işlem sonrası ek bir işlev yürütmek için kullanılır.


//    BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
//
//    int result = add.apply(10, 20);
//
//System.out.println(result); // Çıktı: 30

//yani lambda fonksiyonu seklinde fonksiyonu tanimla
//apply() metodu ile bu tanimi uygulamaya gecir


    Menge<T> menge;                                // T tipindeki elemanlardan olusuyor, (a,b,c,d) gibi
    BiFunction<T, T, Boolean> isInRelation;
    Set<Tupel<T>> Relationen = new HashSet<>();   //T tipindeki tupel'lerden olusan bir set,yani iliski icinde bulunanlar
    //örnegin simetri iliskisiyse ( (a,a), (b,b) ) gibi bir set

    public Relation(Menge<T> menge, BiFunction<T, T, Boolean> isInRelation) {
        if (menge.getSize().isEmpty()) {
            throw new IllegalArgumentException("menge invalid!");
        }
        //bir tupel mengesi aliyor, yani ((1,2), (3,7), (6,1), (8,4)) gibi
        else {
            this.menge = menge;
            this.isInRelation = isInRelation;

            this.Relationen = menge.getElements()     //kümenin elemanlari -> stream'e dönüstü
                    .flatMap(fst -> menge.getElements().map(snd -> new Tupel<>(fst, snd)))  //menge a,b,c ise ab ac ba ca bc cb gibi hepsini ikili cift halinde yazdi
                    .filter(t -> isInRelation.apply(t.getFirst(), t.getSecond()))
                    .collect(Collectors.toSet());
        } //yani isInRelation sartini saglayan tüm ciftler Relationen setinin icerisinde
    }

    @Override
    public Stream<Tupel<T>> getElements() {
        return Relationen.stream();
    }

    @Override
    public boolean contains(Tupel<T> element) {
    //    Ein Tupel ist genau dann in der Relation,
    //    wenn die beiden Werte in menge sind und isInRelation für die beiden true zurück gibt.
        Set<T> MENGE = menge.getElements().collect(Collectors.toSet());
        if(MENGE.contains(element.getFirst()) && MENGE.contains(element.getSecond()) && isInRelation.apply(element.getFirst(), element.getSecond())==true  ){
            return true;
        }
        else{ return false;

    }}

    public boolean isReflexiv() {  //relation set icerisinde ( aa bb cc ab bc ac) : aa bb cc oldugu icin reflexiv
        Set<Tupel<T>> RelationenSet = getElements().collect(Collectors.toSet());
        Set<T> MengeSet = menge.getElements().collect(Collectors.toSet());
        //menge icerisindeki elemanlar: a,b,c -> RelationenSet aa bb cc icermeli

        for (T element : MengeSet) {
            Tupel<T> reflexivElement = new Tupel<>(element, element);
            if (RelationenSet.contains(reflexivElement) == false) {
                return false;
            }


        }
        return true;

    }

    public boolean isIrreflexiv() {
        Set<Tupel<T>> RelationenSet = getElements().collect(Collectors.toSet());
        Set<T> MengeSet = menge.getElements().collect(Collectors.toSet());
        //menge icerisindeki elemanlar: a,b,c -> RelationenSet aa bb cc icermeli

        for (T element : MengeSet) {
            Tupel<T> reflexivElement = new Tupel<>(element, element);
            if (RelationenSet.contains(reflexivElement) == true) {
                return false;
            }


        }
        return true;
    }

    public boolean isSymmetrisch() {
        Set<Tupel<T>> RelationenSet = getElements().collect(Collectors.toSet());

        //menge icerisindeki elemanlar: a,b,c -> RelationenSet aa bb cc icermeli

        for (Tupel<T> tupelInRelation : RelationenSet) {
            Tupel<T> t1 = new Tupel<>(tupelInRelation.getFirst(), tupelInRelation.getSecond());
            Tupel<T> t2 = new Tupel<>(tupelInRelation.getSecond(), tupelInRelation.getFirst());
            if ((RelationenSet.contains(t1) && RelationenSet.contains(t2)) == false) {
                return false;

            }

        }
        return true;
    }

    public boolean isAsymmetrisch() {
        Set<Tupel<T>> RelationenSet = getElements().collect(Collectors.toSet());

        //menge icerisindeki elemanlar: a,b,c -> RelationenSet aa bb cc icermeli

        for (Tupel<T> tupelInRelation : RelationenSet) {
            Tupel<T> t1 = new Tupel<>(tupelInRelation.getFirst(), tupelInRelation.getSecond());
            Tupel<T> t2 = new Tupel<>(tupelInRelation.getSecond(), tupelInRelation.getFirst());
            if ((RelationenSet.contains(t1) && RelationenSet.contains(t2)) == true) {
                return false;

            }

        }
        return true;
    }

    public boolean isAntisymmetrisch() {
        // ab ve ba varsa -> a=b olmali
        // ab var, ba var, a!=b ise: false
        for(Tupel<T> tupel : Relationen){
            T t1 = tupel.getFirst();
            T t2 =tupel.getSecond();
            Tupel<T> tupel1 = new Tupel<>(t1,t2);
            Tupel<T> tupel2 =new Tupel<>(t2,t1);
            if(t1.equals(t2)==false && Relationen.contains(tupel1) && Relationen.contains(tupel2)){
                return false;
            }

        }
        return true;


    }

    public boolean isTransitiv() { //ab var, bc var ise: ac de olmali  tupel1: ab , tupel2: bc
        Set<T> MENGE = menge.getElements().collect(Collectors.toSet());
        for (Tupel<T> tupel1 : Relationen) {
            for (Tupel<T> tupel2 : Relationen) {
                T b1 = tupel1.getSecond();
                T b2 = tupel2.getFirst();
                if (b1.equals(b2)) {
                    Tupel<T> transitiv = new Tupel<>(tupel1.getFirst(), tupel2.getSecond());
                    if (Relationen.contains(transitiv) == false) {
                        return false;
                    }

                }

            }
        }
        return true;
    }
    public boolean isTotal() {  // nicht(a oder b) = nicht a und nicht b
                                //a:AB, b:BA
        Set<T> MENGE =menge.getElements().collect(Collectors.toSet());
        for(T element1 : MENGE){
            for(T element2 : MENGE){
                Tupel<T> AB = new Tupel<>(element1, element2);
                Tupel<T> BA = new Tupel<>(element2, element1);
                if(Relationen.contains(AB)==false && Relationen.contains(BA)==false){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isAequivalenzrelation() {
        if(isReflexiv() && isSymmetrisch() && isTransitiv()){
            return true;
        }
        else return false;
    }

    public Set<Set<T>> getAequivalenzklassen() {
        if(isAequivalenzrelation()==false){
            throw new UnsupportedOperationException("keine äquivalenzrelation");
        }
        Set<Set<T>> classSet = new HashSet<>();
        for (Tupel<T> t: Relationen) {

            if (isInRelation.apply(t.getFirst(), t.getSecond()) == isInRelation.apply( t.getSecond(),t.getFirst())) {
                Set<T> found1 = null;
                Set<T> found2 = null;
                for (Set<T> partition: classSet) {
                    if (partition.contains(t.getFirst())) {
                        found1 = partition;
                    }
                    if (partition.contains(t.getSecond())) {
                        found2 = partition;
                    }
                }

                if (found1 == null && found2 == null) {
                    Set<T> tupelSet = new HashSet<>();
                    tupelSet.add(t.getFirst());
                    tupelSet.add(t.getSecond());
                    classSet.add(tupelSet);
                } else if (found1 == found2) {

                }
                else if (found1 != null && found2 != null) {
                    found1.addAll(found2);
                    Set<Set<T>> classSet2 = new HashSet<>();
                    for (Set<T> partition: classSet) {
                        if (!partition.equals(found2)) {
                            classSet2.add(partition);
                        }

                    }
                    classSet = classSet2;
                } else if (found1 != null ) {
                    found1.add(t.getSecond());
                } else if (found2 != null ) {
                    found2.add(t.getFirst());
                }



            }
        }

        return classSet;

    }

    public boolean isTotalordnung() {
        //  Eine Relation ist genau dann eine Totalordnung, wenn sie reflexiv, antisymmetrisch, transitiv und total ist.
        if (isReflexiv() && isAntisymmetrisch() && isTransitiv() && isTotal()){
            return true;
        }
        else return false;

    }
    public List<T> getElementsInOrder(){

//   Ist die Relation keine Totalordnung, dann werfen Sie eine UnsupportedOperationException mit aussagekräftiger Nachricht.
//    Ansonsten geben Sie eine Liste zurück,
//    die die Elemente der Menge in der durch die Relation definierten geordneten Reihenfolge zurück.

// menge'nin elemanlarindan bir liste dönücek
//Relationen: Tupel<T>

        if (!isTotalordnung()) {
            throw new UnsupportedOperationException("!");
        }
        Set<T> flatSet = new HashSet<>();
        for (Tupel<T> element : Relationen) {
            flatSet.add(element.getFirst());
            flatSet.add(element.getSecond());
        }
        List<T> flatList = new ArrayList<>();

        while (flatSet.size() != flatList.size()) {
            for (T e1 : flatSet) {
                boolean tot = true;
                for (T e2 : flatSet) {
                    if (!e1.equals(e2) && !flatList.contains(e2)) {
                        if (!isInRelation.apply(e1, e2)) {
                            tot = false;
                        }
                    }
                }
                if (tot) {
                    if (!flatList.contains(e1))
                        flatList.add(e1);
                }
            }
        }


        return flatList;

    }
}
