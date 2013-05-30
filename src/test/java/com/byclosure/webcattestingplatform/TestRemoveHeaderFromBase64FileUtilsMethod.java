package com.byclosure.webcattestingplatform;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 */
public class TestRemoveHeaderFromBase64FileUtilsMethod {

    @Test
    public void itShould_NotReturnContentType_InTheBeginOfFile() {
        String base64String = "data:application/octet-stream;base64,ThisIsAMockOfABase64String";

        Assert.assertFalse(Utils.removeHeaderFromBase64File(base64String)
                .startsWith("data:application/octet-stream;base64,"));
    }

    @Test
    public void itShould_NotRemoveContentTypeString_IfInMiddleOfFile() {
        String base64String = "ThisIsAMockOfdata:application/octet-stream;base64,ABase64String";

        Assert.assertTrue(Utils.removeHeaderFromBase64File(base64String).equals(base64String));
    }
}
