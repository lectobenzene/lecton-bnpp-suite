package corecode

/**
 * Created by Saravana on 07/02/14.
 */
class Replacer {

    def fileList = ['AndroidManifest.xml', 'libs', 'res']

    def void replaceFiles(srcFolderPath, destFolderPath) {
        //debug
        println "SourcePath = $srcFolderPath ::: DestFolder = $destFolderPath"

        def srcFolder = new File(srcFolderPath)
        def destFolder = new File(destFolderPath)

        fileList.each {
            def eachFile = new File(srcFolder, "$it")
            def movedFile = new File(destFolder, "$it")

            //debug
            println "EachFile = ${eachFile.absolutePath} ::: MovedFile = ${movedFile.absolutePath}"

            /*
            Remove the file or folder if it already exists.
            */
            if (movedFile.exists()) {
                if (movedFile.isDirectory()) {
                    movedFile.deleteDir()
                } else {
                    movedFile.delete()
                }
            }

            boolean result = eachFile.renameTo(movedFile)

            //debug
            println "SrcPathName : ${eachFile.absolutePath} ::: Moved? : $result"
        }

        def classesFile = new File(srcFolder, 'classes.jar')
        classesFile.renameTo(new File(destFolder, "libs/${classesFile.name}"))

        //Remove GEN folder
        def genFile = new File(destFolder, 'gen')
        genFile.deleteDir()

        //Remove all content in SRC folder
        def srcFile = new File(destFolder, 'src')
        srcFile.deleteDir()
        srcFile.mkdir()

        // self destruct. Delete the TEMP folder
        srcFolder.deleteDir()
    }
}
