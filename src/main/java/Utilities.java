import java.math.BigInteger;


public class Utilities {
	
    /**
     * Dechiffre un texte avec une clé privée
     * @return le texte déchiffré
     */
	public String decryption(PrivateKey privateKey, String txt){		
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
