package com.jkschneider.table;

import lombok.Value;
import org.openrewrite.Column;
import org.openrewrite.DataTable;
import org.openrewrite.Recipe;

public class ProjectDependencyRelationships extends DataTable<ProjectDependencyRelationships.Row> {

    public ProjectDependencyRelationships(Recipe recipe) {
        super(
                recipe,
                "Project dependency relationships",
                "A table of project dependencies."
        );
    }

    @Value
    public static class Row {
        @Column(displayName = "From project",
                description = "The project that depends on another project.")
        String fromProject;

        @Column(displayName = "To project",
                description = "The project that is depended on by another project.")
        String toProject;
    }
}
