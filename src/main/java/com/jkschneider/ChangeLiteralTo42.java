package com.jkschneider;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;

import static java.util.Collections.emptyList;

public class ChangeLiteralTo42 extends Recipe {

    @Override
    public String getDisplayName() {
        return "Change literal to forty-two";
    }

    @Override
    public String getDescription() {
        return "Because 42 is the answer to " +
               "life, the universe, and everything.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        emptyList();

        return new JavaIsoVisitor<ExecutionContext>() {
            @Override
            public J.Literal visitLiteral(J.Literal literal, ExecutionContext ctx) {
                J.Literal l = super.visitLiteral(literal, ctx);

                if (l.getValue() instanceof Integer &&
                    (Integer) l.getValue() != 42) {
                    l = l.withValue(42).withValueSource("42");
                }

                return l;
            }
        };
    }
}
