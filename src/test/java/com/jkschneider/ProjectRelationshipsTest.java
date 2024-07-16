package com.jkschneider;

import com.jkschneider.table.ProjectDependencyRelationships;
import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openrewrite.java.Assertions.*;
import static org.openrewrite.maven.Assertions.pomXml;
import static org.openrewrite.yaml.Assertions.yaml;

public class ProjectRelationshipsTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new ProjectRelationships("com.google*"));
    }

    @Test
    void projectRelationships() {
        rewriteRun(
          spec -> spec.dataTable(ProjectDependencyRelationships.Row.class, rows -> {
              assertThat(rows).hasSizeGreaterThan(0);
              assertThat(rows.stream()
                .map(row -> row.getFromProject() + "->" + row.getToProject())
              ).contains("com.example:explicit-deps-app:0.0.1-SNAPSHOT->com.google.guava:guava:28.0-jre");
          }),
          mavenProject("core",
            //language=xml
            pomXml(
              """
                <project>
                    <groupId>com.example</groupId>
                    <artifactId>explicit-deps-app</artifactId>
                    <version>0.0.1-SNAPSHOT</version>
                    <dependencies>
                        <dependency>
                            <groupId>io.dropwizard.metrics</groupId>
                            <artifactId>metrics-annotation</artifactId>
                            <version>RELEASE</version>
                        </dependency>
                        <dependency>
                            <groupId>com.google.guava</groupId>
                            <artifactId>guava</artifactId>
                            <version>28.0-jre</version>
                        </dependency>
                    </dependencies>
                </project>
                """
            )
          )

        );
    }
}
