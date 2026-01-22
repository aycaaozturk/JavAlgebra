package de.uniwue.jpp.javalgebra;

public class Tupel<T> {

    T first;
    T second;

    public Tupel(T first, T second) {
        this.first=first;
        this.second=second;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Aynı referans mı?
        if (o == null || getClass() != o.getClass()) return false; // Tipleri farklı mı?

        Tupel<?> tupel = (Tupel<?>) o;

        if (first != null ? !first.equals(tupel.first) : tupel.first != null) return false;
        return second != null ? second.equals(tupel.second) : tupel.second == null;

    }
    @Override
    public int hashCode() {
        // Object sinifinin bir metodu
        // iki obje esitse, yani equals() true döndürüyorsa -> hashCode'lari ayni
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }

    public String toStringFirst(){
        return first.toString();
    }
    public String toStringSecond(){
        return second.toString();
    }

    @Override
    public String toString() {
//   Gibt einen String im Format "(toStringOfFirst, toStringOfSecond)" zurück.
//   Zum Beispiel soll new Tupel<Integer>(42, 69).toString() sein: "(42, 69)" (testen!).
        StringBuilder st = new StringBuilder();
        String fs = toStringFirst();
        String sn = toStringSecond();
        String all = "("+fs+", "+sn+")";
        return all;


    }
}
