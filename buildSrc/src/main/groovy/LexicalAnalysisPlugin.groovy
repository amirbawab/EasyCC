import org.gradle.api.Plugin
import org.gradle.api.Project

public class LexicalAnalysisPlugin implements Plugin<Project> {
    void apply(Project project) {

        // Create field
        project.extensions.create("lexicalAnalyzer", LexicalAnalysisPluginExtension)

        // After evaluating the build file, add jvm args
        project.afterEvaluate {
            def values = []
            values << "-Deasycc.lexical.machine=" + project.lexicalAnalyzer.machinePath
            values << "-Deasycc.lexical.config=" + project.lexicalAnalyzer.configPath
            project.run.jvmArgs = values
        }
    }
}

class LexicalAnalysisPluginExtension {
    String machinePath
    String configPath
}