package com.jkschneider;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.TreeVisitingPrinter;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaType;
import org.openrewrite.java.tree.TypeUtils;
import org.openrewrite.marker.SearchResult;

public class FindStringMethods extends Recipe {

    @Override
    public String getDisplayName() {
        return "Find string methods";
    }

    @Override
    public String getDescription() {
        return "All string methods.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
//        MethodMatcher methodMatcher = new MethodMatcher("java.lang.String *(..)");

        return new JavaIsoVisitor<ExecutionContext>() {
            @Override
            public J.MethodInvocation visitMethodInvocation(J.MethodInvocation method, ExecutionContext ctx) {
                J.MethodInvocation m = method;
                // super.visitMethodInvocation(method, ctx);

                JavaType.Method methodType = m.getMethodType();
                if (methodType != null && TypeUtils.isOfClassType(methodType.getDeclaringType(), "java.lang.String")) {
                    m = SearchResult.found(m);
                }

                return m;
            }
        };
    }
}
