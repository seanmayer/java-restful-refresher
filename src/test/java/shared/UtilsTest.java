package shared;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UtilsTest {

 @BeforeEach
 void setUp() throws Exception {
 }

 @Test
 void testGenerateUserId() {
  // fail("Not yet implemented");

 }

 @Test
 @Disabled
 void testHasTokenExpired() {
  fail("Not yet implemented");
 }
 
}
