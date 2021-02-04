import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PublicKeyTest {
    @Test
    public void testConstructort(){
        PublicKey publicKey = new PublicKey();
        boolean pIsDifferentToQ = publicKey.getQ().equals(publicKey.getP());
        System.out.println(publicKey);
        // on test si n = p*q
        Assertions.assertThat(publicKey.getN())
                .isEqualTo(publicKey.getP().multiply(publicKey.getQ()));
        // on test p != q
        Assertions.assertThat(pIsDifferentToQ).isEqualTo(false);
    }
}
