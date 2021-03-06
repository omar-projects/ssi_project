

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

public class PublicKey implements Serializable {

//    p, q, n, m, e
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger m;
    private int e;

    /**
     * Constructeur qui crée une clé publique
     * n = p*q
     * m = (p-1)*(q-1)
     */
    public PublicKey() {
        this.p = BigInteger.probablePrime(1999, new Random());
        this.q = BigInteger.probablePrime(1999, new Random());
        // si p est égale à q on régénére q
        while(p.equals(q)){
            this.q = BigInteger.probablePrime(1999, new Random());
        }
        this.n = this.p.multiply(q); // n = p*q
        this.m = (this.p.subtract(BigInteger.ONE)).multiply(this.q.subtract(BigInteger.ONE)); // m = (p-1)(q-1)
        // génération de e : un nombre premier avec m
        this.e = new Random().nextInt();
        // tant que e est pair ou il n'est pas premier avec m on lui donne une nouvelle valeure, on verifie aussi que p est impair et positif
        while(this.e % 2 == 0 || !this.m.gcd(BigInteger.valueOf(this.e)).equals(BigInteger.ONE) || e < 0){
            this.e = new Random().nextInt();
        }
    }

    public PublicKey(BigInteger p, BigInteger q, BigInteger n, BigInteger m, int e) {
        this.p = p;
        this.q = q;
        this.n = n;
        this.m = m;
        this.e = e;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("p = " + this.p + "\n")
                .append("q = " + this.q + "\n")
                .append("n = " + this.n + "\n")
                .append("m = " + this.m + "\n")
                .append("e = " + this.e + "\n");
        return stringBuilder.toString();
    }


    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getM() {
        return m;
    }

    public int getE() {
        return e;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public void setM(BigInteger m) {
        this.m = m;
    }

    public void setE(int e) {
        this.e = e;
    }
}
