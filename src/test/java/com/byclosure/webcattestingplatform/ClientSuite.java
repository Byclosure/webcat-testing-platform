package com.byclosure.webcattestingplatform;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestDownloaderInitialization.class,
                    TestRemoveHeaderFromBase64FileUtilsMethod.class})
public class ClientSuite {
}
