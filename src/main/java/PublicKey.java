import java.math.BigInteger;
import java.util.Random;

public class PublicKey {


//    p, q, n, m, e
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger m;
    private BigInteger e;

    /**
     * Crée une clef publique
     * n = p*q
     * m = (p-1)*(q-1)
     */
    public PublicKey() {
        this.p = BigInteger.probablePrime(1999, new Random());
        this.q = BigInteger.probablePrime(1999, new Random());
        while(p.equals(q)){
            this.q = BigInteger.probablePrime(1999, new Random());
        }
        this.n = this.p.multiply(q);
        this.m = (this.p.subtract(BigInteger.ONE)).multiply(this.q.subtract(BigInteger.ONE));
        // génération de e : un nombre premier avec m
        int varE = new Random().nextInt();         
        // tant que e est pair ou il n'est pas premier avec m on lui donne une nouvelle valeure
        while(varE % 2 == 0 || !this.m.gcd(BigInteger.valueOf(varE)).equals(BigInteger.ONE)){
            this.e = BigInteger.valueOf(new Random().nextLong());
        }
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

    public BigInteger getE() {
        return e;
    }

}