package com.byclosure.webcattestingplatform;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({DownloaderClass.class,
                    TestRemoveHeaderFromBase64FileUtilsMethod.class,
                    NavigationServiceWithDummyPageObject.class})
public class ClientSuite {
}
