package corecode

/**
 * Created by Saravana on 07/02/14.
 */
class CentralController {
    def INPUT_ZIP_FILE_PATH = "/Users/Saravana/Desktop/shell-sdk-3.1.1.20140203.aar"
    def OUTPUT_FOLDER_PATH = "/Users/Saravana/Documents/Workspace/TrialLibraryProject"
    private static final String TEMP_FOLDER = 'TEMP'

    def aarPath;
    def libProjectPath;

    CentralController(aarPath, libProjectPath) {
        this.aarPath = aarPath;
        this.libProjectPath = libProjectPath;
    }

    static main(args) {
        def INPUT_ZIP_FILE_PATH = "/Users/Saravana/Desktop/shell-sdk-3.1.1.20140203.aar"
        def OUTPUT_FOLDER_PATH = "/Users/Saravana/Documents/Workspace/TrialLibraryProject"

        def decomposer = new Decomposer()
        decomposer.unZip(INPUT_ZIP_FILE_PATH, "${OUTPUT_FOLDER_PATH}/${TEMP_FOLDER}")

        def replacer = new Replacer()
        replacer.replaceFiles("${OUTPUT_FOLDER_PATH}/${TEMP_FOLDER}", OUTPUT_FOLDER_PATH)
    }

    def updateProject() {
        def decomposer = new Decomposer()
        decomposer.unZip(aarPath, "${libProjectPath}/${TEMP_FOLDER}")

        def replacer = new Replacer()
        replacer.replaceFiles("${libProjectPath}/${TEMP_FOLDER}", libProjectPath)
    }
}
