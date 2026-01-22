package de.uniwue.jpp.javalgebra;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Menge<T> {

    Stream<T> getElements();

    default boolean contains(T element) {
        return getElements()
                .anyMatch(currentElement -> currentElement.equals(element));
    }

    default Optional<Integer> getSize(){

    long sizeOfSet = getElements().count();
        return Optional.of((int) sizeOfSet);
        }

    default boolean isEmpty() {
        Optional<Integer> numberOfElements = getSize();  //Optional nesnesi yarattik
        if(numberOfElements.isPresent() && numberOfElements.get()==0){

            //isPresent= optional nesnesi icinde deger varsa true döner
            // get= Optional icindeki degeri alir
            //bos oldugu durumu yaptik
            return true;
        }
        else { return false;
    }}

    default String asString(int maxDisplay) {
        //en fazla, maxDisplay sayisi kadar elemani gösterecek, getElements'teki sirasiyla
        //String halinde
        //eger maxDisplayden daha az ya da esit sayida eleman varsa: hepsini display yap  size<=max
        //eger maxDisplayden daha fazla eleman varsa: ilk o sayidaki elemani yazdir       size>max

        if (maxDisplay <= 0) {
            throw new IllegalArgumentException("0: invalid");
        } else {
            if(getSize().isPresent() ==true && getSize().get() <=maxDisplay){  //
                  //asString();
                return getElements()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ", "[", "]"));



            }else{
                Stream<T> Elements = getElements().limit(maxDisplay);
                String E = Elements.map(String :: valueOf)
                        .collect(Collectors.joining(", ", "[", "]"));


                return getElements().limit(maxDisplay)
                        .map(String :: valueOf)
                        .collect(Collectors.joining(", ", "[", ", ...]" ));

            }
        }
    }

    default String asString() {
        if(getSize().isEmpty() && this!=null){
            return getElements()
                    .limit(10)
                    .map(String :: valueOf)
                    .collect(Collectors.joining(", " , "[", ", ...]"));
        }
        else {
            return getElements()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "[", "]"));


        }
    }
}


//    Stream<T> Elements = getElements();
//    String E = Elements.map(String :: valueOf)
//            .collect(Collectors.joining(", ", "[", "]"));
//        return E;