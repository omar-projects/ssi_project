import java.math.BigInteger;

public class RSAEncryptionService {
    public static String encrypt(String msg, PublicKey publicKey){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < msg.length(); i++) {
            int c = (int) msg.charAt(i);
            BigInteger s = BigInteger.valueOf(c).modPow(BigInteger.valueOf(publicKey.getE()), publicKey.getN());
            stringBuilder.append(s.toString()).append(" ");
        }
        return stringBuilder.toString();
    }
}
