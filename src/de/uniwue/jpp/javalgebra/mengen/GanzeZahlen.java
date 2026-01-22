package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;

import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Stream;

public class GanzeZahlen implements Menge<BigInteger> {


    //0, -1, -2 diye gidiyor, hem pozitif hem negatif olucak
    @Override
    public Stream<BigInteger> getElements() {  //OLMUYO

        return Stream.iterate(BigInteger.ZERO, n -> {  // 0dan basliyor, n-> : n ile yapilacak islemler

            if (n.compareTo(BigInteger.ZERO) == 0) {  // n=0 ise
                //n= 1 oldu
                return BigInteger.ONE;
                //  0 1 -1 2 -2
            }
            if (n.compareTo(BigInteger.ZERO) > 0) {   //n, 0dan büyükse
                return n.negate();

            } else {  //n, 0dan kücükse

                return n.negate().add(BigInteger.ONE);


            }
        });

    }

    @Override
    public boolean contains(BigInteger element) {

        return true;
    }

    @Override
    public Optional<Integer> getSize() {
        return Optional.empty();
    }
}
