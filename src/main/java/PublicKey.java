import java.math.BigInteger;

public class PublicKey {
    /**
     * n = p * q
     */
    private BigInteger n;
    
    /**
     * m = (p-1) * (q-1)
     */
    private BigInteger m;

    /**
     * e = exposant public
     */
    private BigInteger e;
    
    
    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }
    
    public BigInteger getM() {
        return m;
    }

    public void setM(BigInteger m) {
        this.m = m;
    }

    public BigInteger getE() {
        return e;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }

}
