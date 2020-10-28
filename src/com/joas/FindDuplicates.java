package com.joas;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static java.nio.file.FileVisitResult.*;

public class FindDuplicates
        extends SimpleFileVisitor<Path> {

    private Map<Long, LinkedList<Path>> duplicates;

    public FindDuplicates(){
        duplicates = new HashMap<Long, LinkedList<Path>>();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        long fileSize = attrs.size();

        if (attrs.isSymbolicLink()) {
            System.out.format("Symbolic link: %s ", file);
        } else if (attrs.isRegularFile()) {
            System.out.format("Regular file: %s ", file);
        } else {
            System.out.format("Other: %s ", file);
        }
        System.out.println("(" + fileSize + "bytes)");

        LinkedList<Path> files;
        if(null == duplicates.get(fileSize)){
            files = new LinkedList<Path>();
        }else {
            files = duplicates.get(fileSize);
        }

        files.add(file);
        duplicates.put(fileSize, files);

        return CONTINUE;
    }
    
    public void printDuplicates(){
        duplicates.forEach((size, paths)->{

            if ( paths.size() >= 2 ) {
                System.out.format("****** Files with size: (%s) bytes *****\n", size);
                for (Path path : paths) {
                    System.out.println(path);
                }
            }
        });
    }
}
