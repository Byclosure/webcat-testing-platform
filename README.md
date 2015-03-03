This library provides an implementation of the Navigation Service Pattern.
It eases the development of Page Objects by implementing an engine for navigating between them.

This library is part of the [Webcat Project](http://www.webcat.byclosure.com/).

#Installation#
To install this library, add the following to your `pom.xml`.

```
<dependency>
    <groupId>com.byclosure.webcat</groupId>
    <artifactId>webcat-testing-platform</artifactId>
    <version>0.0.11</version>
</dependency>
```

#Usage
See the section [What are page objects and how to write them?](http://www.webcat.byclosure.com/user-manual.html#what-page-objects) in the Webcat Project website, to know how to use the `NavigationService`.

###Automated steps
This library also provides a set of steps already automated for you to start writing features right away. These are the implemented steps:

**Browsing steps:**
```cucumber
Given I am on "[url]"
When I go to "[url]"
When I follow "[link text, name, id, inner image alt text or CSS selector]"
Then I should see "[text]"
Then I should be on "[url]"
```

**Form steps:**
```cucumber
When I fill in "[field id, name, text or CSS selector]" with "[value]"
Then the "[field id, name, text or CSS selector]" field (within [field CSS selector])* should contain "[value]"
When I press [button text, id, title, value or CSS selector]
```
`*` () means it's optional

To use these steps in your features:
1. add `com.byclosure.webcat.stepdefinitions` to your `CucumberOptions` glue, e.g.:
```
@CucumberOptions(format = {"pretty", "junit:target/junit-report.xml"},
        monochrome = true, glue={"com.byclosure.webcat.stepdefinitions", "[the package where you have your step implementations]"})
```
2. Add this to your `pom.xml`, in the end of the `<dependencies>` section:
```
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>2.39.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>info.cukes</groupId>
    <artifactId>cucumber-guice</artifactId>
    <version>1.1.2</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>com.google.inject</groupId>
    <artifactId>guice</artifactId>
    <version>3.0</version>
    <scope>test</scope>
</dependency>
```
3. Create a Guice Module, to configure the WebDriver binding, example:
```java
public class ConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    WebDriver getWebDriver(){
        // create an instance of selenium WebDriver
        return driver;
    }
}
```
For more information about Guice, go to [its website](https://github.com/google/guice).
4. Create a `cucumber-guice.properties` file in yout test resources folder (typically `src/test/resources`), with the following content:
```
guiceModule=[path to your Guice Module]
```
5. Profit!

#Contributing#
We are happy to accept contributions.
To contribute to this library, do the following:

1. Fork the repository
2. Create a new branch
3. Write your code in the new branch (tests included)
4. Submit a pull request

#Copyright#
Copyright (c) 2015 Byclosure. See LICENSE for details.
