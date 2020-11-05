package com.joas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileInfo {
    private long size;
    private final StringBuilder hash;
    private int intHash;

    public FileInfo(Path file) throws IOException, NoSuchAlgorithmException {
        hash = new StringBuilder();
        byte[] fileBytes = Files.readAllBytes(file);
        size = fileBytes.length;
        byte[] hash = MessageDigest.getInstance("MD5").digest(fileBytes);

        for (byte b : hash) {
            this.hash.append( Integer.toString((b & 0xff) + 0x100, 16).substring(1) );
            this.intHash += b &0xFF +0x100;
        }
    }

    @Override
    public boolean equals(Object o){
        return getClass()==o.getClass() && equals((FileInfo)o);
    }

    public boolean equals(FileInfo info){
        return info.size == size &&
                info.hash.toString().equals(this.hash.toString());
    }

    public long getSize() {
        return size;
    }

    public String getHash() {
        return hash.toString();
    }

    @Override
    public int hashCode() {
        return intHash;
    }
}
