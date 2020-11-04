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
        duplicates = new HashMap<>();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if(attrs.isRegularFile()) {

            long fileSize = attrs.size();
            LinkedList<Path> files;
            if (null == duplicates.get(fileSize)) {
                files = new LinkedList<>();
            } else {
                files = duplicates.get(fileSize);
            }

            files.add(file);
            duplicates.put(fileSize, files);
        }

        return CONTINUE;
    }
    
    public void printDuplicates(){
        duplicates.forEach((size, paths)->{

            if ( paths.size() >= 2 ) {
                System.out.format("\n****** Files with size: (%s) bytes *****\n", size);
                for (Path path : paths) {
                    System.out.println(path);
                }
            }
        });
    }
}
