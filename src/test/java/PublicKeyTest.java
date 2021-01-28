import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PublicKeyTest {
    @Test
    public void testConstructort(){
        PublicKey publicKey = new PublicKey();
        System.out.println(publicKey);
        Assertions.assertThat(publicKey.getN())
                .isEqualTo(publicKey.getP().multiply(publicKey.getQ()));
    }
}
