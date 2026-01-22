package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class RestklassenMenge implements Menge<Integer> {

    private final List<Integer> Reste = new ArrayList<>();
    int mod;

    public RestklassenMenge(int mod) {
        if(mod<1){
            throw new IllegalArgumentException("Mod invalid!");
        }
        else{
            for(int i=0; i<mod; i++){
                Reste.add(i);
            }
        }
        this.mod=mod;
    }

    @Override
    public Stream<Integer> getElements() {
        return Reste.stream();
    }

    @Override


    //örn: mod = 7   element=7    ->yok
    // icerdigi sayilar: 0, 1, 2, ,3, 4, 5, ,6
     //örn: element= 0, n=1
    public boolean contains(Integer element) {
        if(element<mod && element>=0){
            return true;
        }
        else{ return false;}
    }

    @Override
    public Optional<Integer> getSize() {
        return Optional.of(mod);
    }
}
