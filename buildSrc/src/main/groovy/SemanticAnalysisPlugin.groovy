import org.gradle.api.Plugin
import org.gradle.api.Project

public class SemanticAnalysisPlugin implements Plugin<Project> {
    void apply(Project project) {

        // Create field
        project.extensions.create("semanticAnalyzer", SemanticAnalysisPluginExtension)

        // After evaluating the build file, add jvm args
        project.afterEvaluate {
            def values = []
            values << "-Deasycc.semantic.grammar=" + project.semanticAnalyzer.grammarPath
            project.run.jvmArgs = values
        }
    }
}

class SemanticAnalysisPluginExtension {
    String grammarPath
}