package com.jkschneider;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import java.util.ArrayList;

import static org.openrewrite.java.Assertions.java;

public class FindStringMethodsTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new FindStringMethods());
    }

    @Test
    void showMeHowToReadOctal() {
        rewriteRun(
          //language=java
          java(
            """
              class Test {
                String n = "hi".substring(
                  "hi".length() - 1);
              }
              """,
            """
              class Test {
                String n = /*~~>*/"hi".substring(
                  /*~~>*/"hi".length() - 1);
              }
              """
          )
        );
    }
}
