import org.gradle.api.Plugin
import org.gradle.api.Project

public class SemanticAnalysisPlugin implements Plugin<Project> {
    void apply(Project project) {

        // Create field
        project.extensions.create("semanticAnalyzer", SemanticAnalysisPluginExtension)

        // After evaluating the build file, add jvm args
        project.afterEvaluate {
            project.run.jvmArgs = project.semanticAnalyzer.getValues()
        }
    }
}

class SemanticAnalysisPluginExtension {
    String messagesPath

    def getValues() {
        def values = []
        values << "-Deasycc.semantic.messages=$messagesPath"
        return values
    }
}