import java.math.BigInteger;

public class RSAEncryptionService {
    /**
     * cette fonction permet de crypter un message
     * @param msg
     * @param publicKey
     * @return message encrypté
     */
    public static String encrypt(String msg, PublicKey publicKey){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < msg.length(); i++) {
            int c = (int) msg.charAt(i);
            // s = s pow e mod n | ceci est rélisé grace à modPow de BigInteger
            BigInteger s = BigInteger.valueOf(c).modPow(BigInteger.valueOf(publicKey.getE()), publicKey.getN());
            stringBuilder.append(s.toString()).append(" ");
        }
        return stringBuilder.toString();
    }

    /**
     * cette fonction permet de décrypter un message avec la clée privée
     * @param privateKey
     * @param msg    
     * @return message décrypté
     */
    public static String decryption(PrivateKey privateKey, String msg){
        String[] wordTab = msg.split(" "); // msg : 386 737 970 204 1858
        String decryptResult = "";
        String tmp_str;
        BigInteger val_char;

        //Déchiffrement
        for(int i = 0; i < wordTab.length; ++i){
            val_char = new BigInteger(wordTab[i]);
            val_char = val_char.modPow(privateKey.getU(), privateKey.getN()); // S u i mod n la clé privée (n, u)
            tmp_str = val_char + "";
            decryptResult += (char)Integer.parseInt(tmp_str); // chiffre to lettre
        }
        return decryptResult;
    }

}
