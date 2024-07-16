package com.jkschneider;

import com.jkschneider.table.ProjectDependencyRelationships;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Option;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.maven.MavenIsoVisitor;
import org.openrewrite.maven.tree.MavenResolutionResult;
import org.openrewrite.maven.tree.ResolvedDependency;
import org.openrewrite.maven.tree.ResolvedGroupArtifactVersion;
import org.openrewrite.xml.tree.Xml;

import java.util.HashSet;
import java.util.Set;

@Value
@EqualsAndHashCode(callSuper = false)
public class ProjectRelationships extends Recipe {
    transient ProjectDependencyRelationships relationships =
            new ProjectDependencyRelationships(this);

    @Option(displayName = "Group ID",
            description = "The group ID of the dependencies to match on.")
    String groupId;

    @Override
    public String getDisplayName() {
        return "Discover the relationships between dependent projects";
    }

    @Override
    public String getDescription() {
        return "So we can build a graph of the project dependencies.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        Set<ResolvedGroupArtifactVersion> gavs = new HashSet<>();

        return new MavenIsoVisitor<ExecutionContext>() {
            @Override
            public Xml.Document visitDocument(Xml.Document document, ExecutionContext ctx) {
                MavenResolutionResult mrr = getResolutionResult();
                for (ResolvedDependency dependency : findDependencies(groupId, "*")) {
                    if (gavs.add(dependency.getGav())) {
                        relationships.insertRow(ctx, new ProjectDependencyRelationships.Row(
                                mrr.getPom().getGav().toString(),
                                dependency.getGav().toString()
                        ));
                    }
                }
                return super.visitDocument(document, ctx);
            }
        };
    }
}
