package com.byclosure.webcat.stepdefinitions.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectorHelpers {

    public static String selectorFor(String locator) {
        if("the page".equals(locator)) {
            return "html > body";
        } else if("table's header".equals(locator)) {
            return "table tbody > tr th";
        } else if(Pattern.matches("^paragraphs?$", locator)) {
            return "p";
        } else if(Pattern.matches("^\"(.+)\"$", locator)) {
            final Pattern p = Pattern.compile("^\"(.+)\"$");
            final Matcher m = p.matcher(locator);
            if(m.find()) {
                return m.group(1);
            }
        }

        String message = String.format("Can't find mapping from \"%s\" to a selector.", locator);
        throw new IllegalArgumentException(message);
    }
}
