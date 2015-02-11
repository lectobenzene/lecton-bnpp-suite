package corecode

import java.util.zip.ZipInputStream

class Decomposer {

    /**
     * Extracts the source zip file and puts them into the destination folder
     *
     * @param srcZipFile
     *            input zip file
     * @param output
     *            zip file output folder
     */
    def unZip(srcZipFile, destinationFolder) {

        def buffer = new byte[1024]

        //create output directory is not exists
        def folder = new File(destinationFolder)
        if (!folder.exists()) {
            folder.mkdir()
        }

        //get the zip file content
        def zipInpStream = new ZipInputStream(new FileInputStream(srcZipFile))

        //get the zipped file list entry
        def zipEntry = zipInpStream.nextEntry

        while (zipEntry != null) {

            def fileName = zipEntry.name
            def newFile = new File(destinationFolder + File.separator + fileName)

            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            def dir = new File(newFile.parent)
            dir.mkdirs()

            if (!zipEntry.isDirectory()) {
                def fos = new FileOutputStream(newFile)
                def len
                while ((len = zipInpStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, len)
                }
                fos.close()
            }

            zipEntry = zipInpStream.nextEntry
        }

        zipInpStream.closeEntry()
        zipInpStream.close()

        println 'Done'

    }
}


