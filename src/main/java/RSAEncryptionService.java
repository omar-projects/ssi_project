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
            BigInteger s = BigInteger.valueOf(c).modPow(BigInteger.valueOf(publicKey.getE()), publicKey.getN());
            stringBuilder.append(s.toString()).append(" ");
        }
        return stringBuilder.toString();
    }

    /**
     * Dechiffre un texte avec une clé privée
     * @return le texte déchiffré
     */
    public static String decryption(PrivateKey privateKey, String txt){
        String[] wordTab = txt.split(" ");
        String decryptResult = "";
        int tmp_int;
        String tmp_str;
        BigInteger val_char;

        //Déchiffrement
        for(int i = 0; i < wordTab.length; ++i){
            val_char = new BigInteger(wordTab[i]);
            val_char = val_char.modPow(privateKey.getU(), privateKey.getN());
            tmp_str = val_char + "";
            tmp_int = Integer.parseInt(tmp_str);
            decryptResult += (char)tmp_int;
        }
        return decryptResult;
    }

}
