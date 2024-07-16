package com.jkschneider;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.TreeVisitingPrinter;
import org.openrewrite.java.tree.J;
import org.openrewrite.marker.SearchResult;

public class FindLiterals extends Recipe {

    @Override
    public String getDisplayName() {
        return "Find integer literals";
    }

    @Override
    public String getDescription() {
        return "And show me what their values are.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaIsoVisitor<ExecutionContext>() {
            @Override
            public J.Literal visitLiteral(J.Literal literal, ExecutionContext ctx) {
                J.Literal l = super.visitLiteral(literal, ctx);

                if (l.getValue() instanceof Integer) {
                    l = SearchResult.found(l, l.getValue().toString());
                }

                return l;
            }
        };
    }
}
