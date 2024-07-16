package com.jkschneider;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

public class FindLiteralsTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new FindLiterals());
    }

    @Test
    void showMeHowToReadOctal() {
        rewriteRun(
          //language=java
          java(
            """
              class Test {
                int n = 0123;
              }
              """,
            """
              class Test {
                int n = /*~~(83)~~>*/0123;
              }
              """
          )
        );
    }
}
