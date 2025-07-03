import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class BCryptPasswordEncoderTest {

  @Test
  void encode_and_matches_test() {
    // given
    String rawPassword = "joy250321~!";
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // when
    String encodedPassword = encoder.encode(rawPassword);

    // then
    System.out.println("Encoded password: " + encodedPassword);
    assertThat(encoder.matches(rawPassword, encodedPassword)).isTrue();
  }
}
