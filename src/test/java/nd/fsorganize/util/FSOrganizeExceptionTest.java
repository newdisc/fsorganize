package nd.fsorganize.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FSOrganizeExceptionTest {
    @Test
    public void ctortest() {
        final String message = "Test Message";
        FSOrganizeException fsox = new FSOrganizeException(message);
        Assert.assertNotNull(fsox);
        fsox = new FSOrganizeException(fsox);
        Assert.assertNotNull(fsox);
        fsox = new FSOrganizeException(message, fsox);
        Assert.assertNotNull(fsox);
        fsox = new FSOrganizeException(message, fsox, true, true);
        Assert.assertNotNull(fsox);
    }
}
