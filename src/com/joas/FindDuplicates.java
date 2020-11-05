package com.joas;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static java.nio.file.FileVisitResult.*;

public class FindDuplicates
        extends SimpleFileVisitor<Path> {

    private final Map<Long, LinkedList<Path>> fileSizesMap;
    private final Map<FileInfo, LinkedList<Path>> fileDuplicates;

    public FindDuplicates(){
        fileSizesMap = new HashMap<>();
        fileDuplicates = new HashMap<>();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (attrs.isRegularFile()) {
            long fileSize = attrs.size();
            LinkedList<Path> files;

            if (fileSizesMap.containsKey(fileSize) ) {
                files = fileSizesMap.get(fileSize);
            } else {
                files = new LinkedList<>();
            }
            files.add(file);
            fileSizesMap.put(fileSize, files);
        }
        return CONTINUE;
    }


    public void printDuplicates(){
        fileSizesMap.forEach((fileSize, paths)->{

            if ( paths.size() >= 2 ) {

                for (Path path : paths) {
                    //System.out.format("\n **** Files with size: %s Bytes ***\n", fileSize);
                    try {
                        FileInfo fileInfo = new FileInfo(path);
                        LinkedList<Path> files;
                        if(fileDuplicates.containsKey(fileInfo)){
                            files = fileDuplicates.get(fileInfo);
                        }else{
                            files = new LinkedList<>();
                        }
                        files.add(path);
                        fileDuplicates.put(fileInfo, files);

                    } catch (IOException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        fileDuplicates.forEach((fileInfo, paths)->{
            if(paths.size() >= 2) {
                System.out.format("\n **** Files duplicated with size: %d Bytes ***\n",
                        fileInfo.getSize());
                for (Path path : paths) {
                    System.out.println(path);
                }
            }
        });
    }
}
