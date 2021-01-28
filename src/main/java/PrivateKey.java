import java.math.BigInteger;


public class PrivateKey {
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
	public PrivateKey generatePrivateKey(PublicKey publicKey){
    	PrivateKey privateKey = new PrivateKey();
    	
		BigInteger a = publicKey.getE(); // a = e
		BigInteger b = publicKey.getM(); // b = m			 
	
		
		//BigInteger r; // r = a * u + b * v = pgcd(a, b) 
	
		//Initialisation
		BigInteger r0 = a, r1 = b;
		BigInteger u0 = BigInteger.ONE, u1 = BigInteger.ZERO;  
		BigInteger v0 = BigInteger.ZERO, v1 = BigInteger.ONE; 
		
		BigInteger q;									//quotient entier
		BigInteger rs, us, vs;							//variables de stockages    	
	
		while(r1 != BigInteger.ZERO){
			q = r0.divide(r1);
			rs = r0; us = u0; vs = v0;
			r0 = r1; u0 = u1; v0 = v1;
			r1 = rs.subtract(q.multiply(r1)); 
			u1 = us.subtract(q.multiply(u1)); 
			v1 = vs.subtract(q.multiply(v1));
		}
	
		BigInteger k = BigInteger.valueOf(-1);
		
		while(u0.compareTo(BigInteger.valueOf(2)) != 1 || u0.compareTo(b) != -1 ){
			u0 = u0.subtract(k.multiply(b));
			k = k.subtract(BigInteger.valueOf(-1));
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
