package shared;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import com.appsdeveloperblog.app.ws.shared.Utils;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Utils.class})
public class UtilsTest {

 @Autowired
 Utils utils;

 @BeforeEach
 void setUp() {
 }

 @Test
 void testGenerateUserId() {
  String userId = utils.generatedUserId(30);
  String userId2 = utils.generatedUserId(30);
  assertNotNull(userId);
  assertNotNull(userId2);
  assertNotNull(userId.length() == 30);
  assertNotNull(userId2.length() == 30);
  assertNotNull(!userId.equalsIgnoreCase(userId2));
 }

 @Test
 @Disabled
 void testHasTokenExpired() {
  fail("Not yet implemented");
 }
 
}
