import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class RSAEncryptionServiceTest {
    final Logger logger = LoggerFactory.getLogger(RSAEncryptionServiceTest.class);

    @Test
    public void testEncrypt(){
        PublicKey publicKey = new PublicKey();
        publicKey.setE(7);
        publicKey.setN(BigInteger.valueOf(5141));
        System.out.println(publicKey);
        System.out.println(RSAEncryptionService.encrypt("Bonjour !",publicKey));
        Assertions.assertThat(RSAEncryptionService.encrypt("Bonjour !",publicKey))
                .isEqualTo("386 1858 2127 2809 1858 1774 737 3675 244 ");
    }
}
