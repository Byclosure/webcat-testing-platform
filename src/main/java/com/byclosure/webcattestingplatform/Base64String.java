package com.byclosure.webcattestingplatform;

import org.apache.commons.codec.binary.Base64;

import java.io.*;

public class Base64String {

    private final String b64String;

    public Base64String(String s) {
        b64String = s;
    }

    public void saveToFile(String filename) {
        DataOutputStream out = null;
        try {
            File f = new File(filename);
            out = new DataOutputStream(new FileOutputStream(f));
            out.write(this.decodeAsByteArray());
        } catch(FileNotFoundException fileNotFound) {
            throw new RuntimeException(fileNotFound);
        } catch(IOException io) {
            throw new RuntimeException(io);
        } finally {
            try {
                if(out != null) out.close();
            } catch(IOException io) {
                throw new RuntimeException(io);
            }
        }
    }

    public String decodeAsString() {
        return new String(this.decodeAsByteArray());
    }

    public byte[] decodeAsByteArray() {
        if(b64String == null) return new byte[]{};

        return Base64.decodeBase64(b64String);
    }

    @Override
    public String toString() {
        return this.b64String;
    }

}
