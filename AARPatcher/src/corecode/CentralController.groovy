package corecode

/**
 * CentralClass to update a LibraryProject with .aar file.<br />
 *
 * Created by Saravana on 07/02/14.
 * @author Saravana T.
 */
class CentralController {
    private static final String TEMP_FOLDER = 'TEMP'

    def aarPath;
    def libProjectPath;

    /**
     * Constructor
     *
     * @param aarPath location of the .aar file
     * @param libProjectPath location of the AndroidLibrary Project that is to be updated
     */
    CentralController(aarPath, libProjectPath) {
        this.aarPath = aarPath;
        this.libProjectPath = libProjectPath;
    }

    /**
     * Updates the LibraryProject with .aar file
     */
    def updateProject() {
        def decomposer = new Decomposer()
        decomposer.unZip(aarPath, "${libProjectPath}/${TEMP_FOLDER}")

        def replacer = new Replacer()
        replacer.replaceFiles("${libProjectPath}/${TEMP_FOLDER}", libProjectPath)
    }
}
