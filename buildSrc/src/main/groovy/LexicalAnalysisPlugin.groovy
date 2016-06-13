import org.gradle.api.Plugin
import org.gradle.api.Project

public class LexicalAnalysisPlugin implements Plugin<Project> {
    void apply(Project project) {

        // Create field
        project.extensions.create("lexicalAnalyzer", LexicalAnalysisPluginExtension)

        // After evaluating the build file, add jvm args
        project.afterEvaluate {
            project.run.jvmArgs = project.lexicalAnalyzer.getValues()
        }
    }
}

class LexicalAnalysisPluginExtension {
    String machinePath
    String tokensPath
    String messagesPath

    def getValues() {
        def values = []
        values << "-Deasycc.lexical.machine=\"$machinePath\""
        values << "-Deasycc.lexical.tokens=\"$tokensPath\""
        values << "-Deasycc.lexical.messages=\"$messagesPath\""
        return values
    }
}