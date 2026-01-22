package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;

import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Stream;

public class NatuerlicheZahlen implements Menge<BigInteger> {

    @Override
    public Stream<BigInteger> getElements() {
        Stream<BigInteger> Endless = Stream.iterate(BigInteger.ONE, a -> a.add(BigInteger.ONE));
        return Endless;

    }

    @Override
    public boolean contains(BigInteger element) {
        if(element.compareTo(BigInteger.ZERO) > 0){  //element 0dan büyükse true döner
            return true;                             //compareTo, 2 BigInteger degerini karsilastirir
            //bu bize int cikti döner:
            //-1: element kücük
            //0: esit
            //1: element büyük
            //yani 0la karsilastirarak büyük mü oldugunu verify ediyoruz
        }
        else {
            return false;
        }    }

    @Override
    public Optional<Integer> getSize() {

        return Optional.empty();
    }
}
