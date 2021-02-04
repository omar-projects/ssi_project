import java.io.Serializable;
import java.math.BigInteger;


public class PrivateKey implements Serializable {
    /**
     * Coefficient de Bézout
     */
    private BigInteger u;
    
    /**
     * Coefficient de Bézout
     */
    private BigInteger v;
    
    /**
     * n = p * q, clé publique
     */
    private BigInteger n;
    
    
    /**
     * Génèrer une clé privée
     * @return une clé privée
     */
	public static PrivateKey generatePrivateKey(PublicKey publicKey){
    	PrivateKey privateKey = new PrivateKey();
    	
		BigInteger a = BigInteger.valueOf(publicKey.getE()); // a = e
		BigInteger b = publicKey.getM(); // b = m			 
	
		//Initialisation
		BigInteger r0 = a, r1 = b; 
		BigInteger u0 = BigInteger.ONE, u1 = BigInteger.ZERO; // u 0 = 1 & u 1 = 0
		BigInteger v0 = BigInteger.ZERO, v1 = BigInteger.ONE; // v 0 = 0 & v 1 = 1
		
		BigInteger q; // quotient
		BigInteger rs, us, vs; // rs = r i + 1 & us = u i + 1 & vs = v i + 1
	
		while(r1 != BigInteger.ZERO){ // Arrêt si r = 0
			q = r0.divide(r1); // r i – 1 / r i
			rs = r0; us = u0; vs = v0; 
			r0 = r1; u0 = u1; v0 = v1;
			r1 = rs.subtract(q.multiply(r1)); // r i – 1 - (r i – 1 / r i ) × r i 
			u1 = us.subtract(q.multiply(u1)); // u i – 1 - (r i – 1 / r i ) × u i 
			v1 = vs.subtract(q.multiply(v1)); // v i – 1 - (r i – 1 / r i ) × v i
		}
	
		BigInteger k = BigInteger.valueOf(-1); // k = -1
		
		while(u0.compareTo(BigInteger.valueOf(2)) != 1 || u0.compareTo(b) != -1 ){
			u0 = u0.subtract(k.multiply(b)); // u - k × m avec b=m
			k = k.subtract(BigInteger.valueOf(-1)); // k (-1, -2...)
		}
	
		privateKey.setN(publicKey.getN());
		privateKey.setU(u0);
		privateKey.setV(v0);
    	
        return privateKey;
    }
    
    
    /**
     * Getter & Setter
     */
    public BigInteger getU() {
        return u;
    }

    public void setU(BigInteger u) {
        this.u =u;
    }
    
    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n =n;
    }
    
    public BigInteger getV() {
        return v;
    }

    public void setV(BigInteger v) {
        this.v = v;
    }
}
