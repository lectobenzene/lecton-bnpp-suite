package com.mobility.bnpp.buildautomation.corecode;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.zip.ZipEntry;

public class ZipFolderFilter extends SimpleFileVisitor<Path>{

	Path sourcePath;
	
	
	int i = 0;
	ArrayList<Path> dirPathsNotNeededArrayList;
	
	String relativeFileName;
	ZipEntry entry;
	InputStream inputStream;
	
	int length;
	byte buffer[] = new byte[1024];
	
	public ZipFolderFilter(Path sourcePath) {
		this.sourcePath = sourcePath;
	}

	
	
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

		relativeFileName = sourcePath.relativize(file).toString();
		
		entry = new ZipEntry(relativeFileName);
		PackageCopier.zipOutputStream.putNextEntry(entry);
		
		inputStream = Files.newInputStream(file, StandardOpenOption.READ);
		
        //byte buffer[] = new byte[1024];
       
		while ((length = inputStream.read(buffer)) > 0) {
        	PackageCopier.zipOutputStream.write(buffer, 0, length);
        }
        inputStream.close();
        PackageCopier.zipOutputStream.closeEntry();
		
		return FileVisitResult.CONTINUE;
	}

	
	
	
	
	
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		while (i == 0) {
			i++;
			
			// folders to skip from being copied.
			Path[] dirPathsNotNeeded = new Path[] {Paths.get("bin")};
			dirPathsNotNeededArrayList = new ArrayList<Path>();
			for (Path path : dirPathsNotNeeded) {
				dirPathsNotNeededArrayList.add(path);
			}
		}
		
		// Certain folders are skipped from being copied.
		if (Files.isHidden(dir) || (dirPathsNotNeededArrayList.contains(dir.getFileName()) && dir.getParent().equals(sourcePath))) {
			return FileVisitResult.SKIP_SUBTREE;
		}

		return FileVisitResult.CONTINUE;
	}
	
	
	
}
