import org.gradle.api.Plugin
import org.gradle.api.Project

public class SyntaxAnalysisPlugin implements Plugin<Project> {
    void apply(Project project) {

        // Create field
        project.extensions.create("syntaxAnalyzer", SyntaxAnalysisPluginExtension)

        // After evaluating the build file, add jvm args
        project.afterEvaluate {
            project.run.jvmArgs = project.syntaxAnalyzer.getValues()
        }
    }
}

class SyntaxAnalysisPluginExtension {
    String grammarPath
    String parseStrategy
    String messagesPath

    def getValues() {
        def values = []
        values << "-Deasycc.syntax.grammar=\"$grammarPath\""
        values << "-Deasycc.syntax.parseStrategy=\"$parseStrategy\""
        values << "-Deasycc.syntax.messages=\"$messagesPath\""
        return values
    }
}