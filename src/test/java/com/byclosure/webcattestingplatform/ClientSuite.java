package com.byclosure.webcattestingplatform;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestDownloaderInitialization.class,
                    TestUtilsMethods.class})
public class ClientSuite {
}
