import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class RSAEncryptionServiceTest {
    final Logger logger = LoggerFactory.getLogger(RSAEncryptionServiceTest.class);

    @Test
    public void testEncrypt(){
        PublicKey publicKey = new PublicKey(BigInteger.valueOf(53), BigInteger.valueOf(97), BigInteger.valueOf(5141), BigInteger.valueOf(4992), 7);
        System.out.println(publicKey);
        String cryptedMsg = RSAEncryptionService.encrypt("Bonjour !",publicKey);
        System.out.println(cryptedMsg);
        Assertions.assertThat(cryptedMsg)
                .isEqualTo("386 1858 2127 2809 1858 1774 737 3675 244 ");
    }

    @Test
    public void testDecrypt(){
        PublicKey publicKey = new PublicKey(BigInteger.valueOf(53), BigInteger.valueOf(97), BigInteger.valueOf(5141), BigInteger.valueOf(4992), 7);
        PrivateKey privateKey = new PrivateKey();
        privateKey = privateKey.generatePrivateKey(publicKey);
        System.out.println(RSAEncryptionService.decryption(privateKey, "386 1858 2127 2809 1858 1774 737 3675 244 "));
        Assertions.assertThat(RSAEncryptionService.decryption(privateKey, "386 1858 2127 2809 1858 1774 737 3675 244 "))
                .isEqualTo("Bonjour !");
    }
}
