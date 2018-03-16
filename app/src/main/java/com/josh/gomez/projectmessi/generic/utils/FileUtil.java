package com.josh.gomez.projectmessi.generic.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by Joshua Gomez on 18/01/2018.
 */

public class FileUtil {

    public static void writeToFile(String fileName, String data, Context context) {
        File path = getPath();
        File file = new File(path, fileName);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            stream.write(data.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static File getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Mess mate/backup/";
        File folder = new File(path);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            // Do something on success
            return folder;
        } else {
            return new File(Environment.getExternalStorageDirectory().toString());
            // Do something else on failure
        }
    }

    public static String readFromFile(String fileName, Context context) {
        File path = getPath();
        File file = new File(path, fileName);
        BufferedInputStream bin = null;
        try {
            bin = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[(int) file.length()];
            bin.read(buffer);
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
