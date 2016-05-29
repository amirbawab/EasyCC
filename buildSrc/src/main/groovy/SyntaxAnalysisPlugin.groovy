import org.gradle.api.Plugin
import org.gradle.api.Project

public class SyntaxAnalysisPlugin implements Plugin<Project> {
    void apply(Project project) {

        // Create field
        project.extensions.create("syntaxAnalyzer", SyntaxAnalysisPluginExtension)

        // After evaluating the build file, add jvm args
        project.afterEvaluate {
            def values = []
            values << "-Deasycc.syntax.grammar=" + project.syntaxAnalyzer.grammarPath
            values << "-Deasycc.syntax.parseStrategy=" + project.syntaxAnalyzer.parseStrategy
            values << "-Deasycc.syntax.messages=" + project.syntaxAnalyzer.messagesPath
            project.run.jvmArgs = values
        }
    }
}

class SyntaxAnalysisPluginExtension {
    String grammarPath
    String parseStrategy
    String messagesPath
}