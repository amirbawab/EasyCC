import org.gradle.api.Plugin
import org.gradle.api.Project

import java.lang.reflect.Field

public class LexicalAnalysisPlugin implements Plugin<Project> {
    void apply(Project project) {

        // Create field
        project.extensions.create("lexicalAnalyzer", LexicalAnalysisPluginExtension)

        // After evaluating the build file, add jvm args
        project.afterEvaluate {
            def values = []
            values << "-Deasycc.machine=" + project.lexicalAnalyzer.DFAPath
            values << "-Deasycc.config=" + project.lexicalAnalyzer.configPath
            project.run.jvmArgs = values
        }
    }
}

class LexicalAnalysisPluginExtension {
    String DFAPath
    String configPath
}