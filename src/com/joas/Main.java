package com.joas;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args){
        if(args.length == 0){
            System.out.println("please specify path");
            System.exit(1);
        }

        Path startingDir = Path.of(args[0]);
        FindDuplicates findDuplicates = new FindDuplicates();

        System.out.format("Printing files with same size in %s\n", startingDir);
        try {
            Files.walkFileTree(startingDir, findDuplicates);
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Printing duplicates ...");
        findDuplicates.printDuplicates();
    }
}
