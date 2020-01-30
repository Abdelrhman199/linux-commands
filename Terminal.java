package com.company;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Terminal {
    private boolean isInsertionNeeded = false;
    private static String insertionDataFile;
    private static String userPath = "";
    private final static String defultPath = "E:\\";

    public void cp(String sourcePath, String destinationPath) {
        Path srcPath = null;
        Path distPath = null;
        if (sourcePath.contains("\\")) {
            srcPath = Paths.get(sourcePath);
        } else {
            if (userPath.isEmpty()) {
                srcPath = Paths.get(defultPath + "\\" + sourcePath);
            } else srcPath = Paths.get(userPath + "\\" + sourcePath);
        }
        if (destinationPath.contains("\\")) {
            distPath = Paths.get(destinationPath);
        } else {
            if (userPath.isEmpty()) {
                distPath = Paths.get(defultPath + "\\" + destinationPath);
            } else distPath = Paths.get(userPath + "\\" + destinationPath);
        }
        try (BufferedReader inputFile = Files.newBufferedReader(srcPath); OutputStream outputFile = new BufferedOutputStream(Files.newOutputStream(distPath))) {
            String inputFileData;
            while ((inputFileData = inputFile.readLine()) != null) {
                outputFile.write(inputFileData.getBytes(), 0, inputFileData.length());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void mv(String sourcePath, String destinationPath) {


        Path srcPath;
        Path destPath;
        if(!sourcePath.contains("/"))
        {
            if(userPath.isEmpty())
            {
                srcPath= Paths.get(defultPath + "/" + sourcePath);
            }
            else
            {
                srcPath=Paths.get(userPath+"/"+sourcePath);
            }
        }
        else srcPath=Paths.get(sourcePath);
        if(!destinationPath.contains("/"))
        {
            if(userPath.isEmpty())
            {
                destPath= Paths.get(defultPath + "/" + destinationPath);
            }
            else
            {
                destPath=Paths.get(userPath+"/"+destinationPath);
            }
        }
        else destPath=Paths.get(destinationPath);
        Path move = null;
        try {
            move = Files.move(srcPath,destPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(move!=null)
        {
            System.out.println("file moved successfully");
        }
        else System.out.println("failed to move file");

    }

    public void rmdir(String dirPath) {
        if (!dirPath.contains("\\")) {
            dirPath = userPath + "\\" + dirPath;
        }
        File file = new File(dirPath);
        if (file.delete()) {
            System.out.println("Directory has been deleted");
        } else {
            System.out.println("Directory is not empty or doesn't exist");
        }
    }

    public void rm(String sourcePath) {
        Path srcPath;
        if (sourcePath.contains("\\")) {
            srcPath = Paths.get(sourcePath);
        } else {
            srcPath = Paths.get(userPath + "\\" + sourcePath);
        }
        File file = null;
        try {
            file = new File(String.valueOf(srcPath));
            if (file.delete()) {
                System.out.println("File has been deleted");
            } else {
                System.out.println("File Dose'nt Exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pwd() {

        if (!userPath.isEmpty()) {
            if (isInsertionNeeded) {

                insertionDataFile = userPath;
            }
            else
            {

                System.out.println(userPath);
            }
        } else {
            if(isInsertionNeeded){
                insertionDataFile = defultPath;
            }
            else {
                System.out.println(defultPath);
            }
        }
    }

    public void cat(String[] paths) {
        StringBuilder input = new StringBuilder();

        if (paths.length == 1 && paths[0].isEmpty()) {
            Scanner inputData = new Scanner(System.in);
            String tempData;
            System.out.println("entered");
            while (true) {
                tempData = inputData.nextLine();
                if (tempData.equals("^Z")) {
                    break;
                }

                input.append(tempData).append("\n");

            }


            if (isInsertionNeeded) {
                insertionDataFile = input.toString();
            } else {
                System.out.println(input);
            }
        } else {
            Scanner readFromFile = null;
            for (int i = 0; i < paths.length; i++) {

                try {
                    if (!paths[i].contains("\\")) {
                        if (userPath.isEmpty()) {
                            readFromFile = new Scanner(new File(defultPath + "\\" + paths[i]));

                        } else {
                            readFromFile = new Scanner(new File(userPath + "\\" + paths[i]));
                        }
                    } else {
                        readFromFile = new Scanner(new File(paths[i]));
                    }
                    while (readFromFile.hasNext()) {
                        input.append(readFromFile.nextLine()).append("\n");
                    }
                    input.append("\n");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    readFromFile.close();
                }

            }
            if (isInsertionNeeded) {
                insertionDataFile = input.toString();
            } else {
                System.out.println(input);
            }
        }
    }

    public void cd(String cdPath) {
        if (cdPath.isEmpty()) {
            userPath = "";
        } else if (!cdPath.contains("\\")) {

            if (userPath.isEmpty()) {
                userPath = defultPath + "\\" + cdPath;
            } else {
                if (cdPath.equals("..")) {
                    String directedPath = "";

                    Path path = Paths.get(userPath);
                    if (!(String.valueOf(path.getParent()).equals("null"))) {
                        userPath = String.valueOf(path.getParent());
                    }


                } else {
                    userPath = userPath + "\\" + cdPath;
                }
            }
        } else if (cdPath.contains("\\")) {
            userPath = cdPath;
        }


    }


    public void date() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/mm/dd hh:mm:ss");
        LocalDateTime time = LocalDateTime.now();
    if(isInsertionNeeded)
    {
        insertionDataFile=formatter.format(time);
    }
    else {
        System.out.println(formatter.format(time));
    }
    }

    public void mkdir(String[] directories) {
        for (int i = 0; i < directories.length; i++) {
            new File(defultPath + "\\" + directories[i]).mkdir();
        }
    }

    public void ls(String lsPath) {
        File file;

        if (lsPath.contains("\\")) {

            file = new File(lsPath);
        } else {

            if (userPath.isEmpty()) {
                file = new File(defultPath + "\\" + lsPath);
            } else {
                file = new File(userPath + "\\" + lsPath);
            }

        }


        try {

            String[] files = file.list();
            StringBuilder tempInsertionDataFile = new StringBuilder();
            for (int i = 0; i < files.length; i++) {

                if (isInsertionNeeded) {
                    tempInsertionDataFile.append(files[i]).append("\n");

                } else {
                    System.out.println(files[i]);
                }


            }
            if (isInsertionNeeded) {
                insertionDataFile = tempInsertionDataFile.toString();
            }
        } catch (NullPointerException e) {
            System.out.println("Path is not Existed");

        }
    }

    public void esc() {
        System.exit(0);
    }

    public void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }

    }

    public void help() {
        if(isInsertionNeeded) {
            insertionDataFile=("cd : change the current working directory"+"\n");
            insertionDataFile+=("ls : list all files and directories in the current or a selected directory "+"\n");
            insertionDataFile+=("cp : copy the content of a file to another file"+"\n");
            insertionDataFile+=("cat : "+"\n");
            insertionDataFile+=("mkdir : make new directory"+"\n");
            insertionDataFile+=("args : list all command arguments"+"\n");
            insertionDataFile+=("date : print the current date & time"+"\n");
            insertionDataFile+=("pwd  : print the current working directory"+"\n");
            insertionDataFile+=("help : print all the available commands and there functions"+"\n");
            insertionDataFile+=("rm : delete or remove files or directories"+"\n");
            insertionDataFile+=("mv : move files or directories from one place to another"+"\n");
            insertionDataFile+=("rmdir : used to remove empty directories"+"\n");
            insertionDataFile+=("clear : clear the cmd window"+"\n");
            insertionDataFile+=("more : used to view the text files in the command prompt"+"\n");
        }
        else
        {
            System.out.println("cd : change the current working directory");
            System.out.println ("ls : list all files and directories in the current or a selected directory ");
            System.out.println("cp : copy the content of a file to another file");
            System.out.println("cat : ");
            System.out.println("mkdir : make new directory");
            System.out.println("args : list all command arguments");
            System.out.println("date : print the current date & time");
            System.out.println("pwd  : print the current working directory");
            System.out.println("help : print all the available commands and there functions");
            System.out.println("rm : delete or remove files or directories");
            System.out.println("mv : move files or directories from one place to another");
            System.out.println("rmdir : used to remove empty directories");
            System.out.println("clear : clear the cmd window");
            System.out.println("more : used to view the text files in the command prompt");

        }
    }

    public void args(String command) {

        if (!isInsertionNeeded) {
            switch (command) {
                case "cd":
                    System.out.println("arg1: directory path");
                    break;
                case "ls":
                    System.out.println("arg1: directory path or no argument");
                    break;
                case "help":
                    System.out.println("no arguments");
                    break;
                case "date":
                    System.out.println("no arguments");
                    break;
                case "pwd":
                    System.out.println("no arguments");
                    break;
                case "clear":
                    System.out.println("no arguments");
                    break;
                case "args":
                    System.out.println("arg1:command");
                    break;

                case "rm":
                    System.out.println("arg1: source path");
                    break;
                case "mv":
                    System.out.println("arg1: source path arg2: destination path");
                    break;
                case "rmdir":
                    System.out.println("arg1: directory path");
                    break;
                case "mkdir":
                    System.out.println("arg1: array of directories");
                    break;
                case "more":
                    System.out.println("arg1: file name");
                    break;
                case "car":
                    System.out.println("arg1: array of paths");
                    break;
                case "cp":
                    System.out.println("arg1: source path arg2: destination path");
                    break;

            }
        } else {
            switch (command) {
                case "cd":
                    insertionDataFile = "arg1: directory path";
                    break;
                case "ls":
                    insertionDataFile = "arg1: directory path or no argument";
                    break;
                case "help":
                    insertionDataFile = "no arguments";
                    break;
                case "date":
                    insertionDataFile = "no arguments";
                    break;
                case "pwd":
                    insertionDataFile = "no arguments";
                    break;
                case "clear":
                    insertionDataFile = "no arguments";
                    break;
                case "args":
                    insertionDataFile = "arg1:command";
                    break;

                case "rm":
                    insertionDataFile = "arg1: source path";
                    break;
                case "mv":
                    insertionDataFile = "arg1: source path arg2: destination path";
                    break;
                case "rmdir":
                    insertionDataFile = "arg1: directory path";
                    break;
                case "mkdir":
                    insertionDataFile = "arg1: array of directories";
                    break;
                case "more":
                    insertionDataFile = "arg1: file name";
                    break;
                case "car":
                    insertionDataFile = "arg1: array of paths";
                    break;
                case "cp":
                    insertionDataFile = "arg1: source path arg2: destination path";
                    break;
            }
        }
    }

    public void insertion(String[] command, String fileName) {
        String[] tempCommand;
        isInsertionNeeded = true;
        if (command.length > 1) {
            tempCommand = new String[command.length - 1];
        } else {
            tempCommand = new String[]{""};
        }


        for (int i = 1; i < command.length; i++) {
            tempCommand[i - 1] = command[i];
        }
        switch (command[0]) {

            case "ls":
                ls(tempCommand[0]);
                break;
            case "pwd":
                pwd();
                break;
            case "date":
                date();
                break;
            case "args":
                args(command[0]);

                break;
            case "help":
            help();
                break;
            case "cat":

                cat(tempCommand);

                break;
            default:
                isInsertionNeeded = false;
                return;
        }
        PrintWriter outputFile = null;
        if (fileName.contains("\\")) {
            try {
                outputFile = new PrintWriter(fileName, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (userPath.isEmpty()) {
                    outputFile = new PrintWriter(defultPath + "\\" + fileName, "UTF-8");
                } else {
                    outputFile = new PrintWriter(userPath + "\\" + fileName, "UTF-8");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {


            outputFile.println(insertionDataFile);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            outputFile.close();
        } catch (NullPointerException b) {
            b.printStackTrace();
        }

        isInsertionNeeded = false;

    }

    public void doubleInsertion(String[] command, String fileName)
    {
        String[] tempCommand;
        isInsertionNeeded = true;
        if (command.length > 1) {
            tempCommand = new String[command.length - 1];
        } else {
            tempCommand = new String[]{""};
        }


        for (int i = 1; i < command.length; i++) {
            tempCommand[i - 1] = command[i];
        }
        switch (command[0]) {

            case "ls":
                ls(tempCommand[0]);
                break;
            case "pwd":
                pwd();
                break;
            case "date":
                date();
                break;
            case "args":
                args(command[0]);
                break;
            case "help":
                help();
                break;
            case "cat":
                cat(tempCommand);
                break;
            default:
                isInsertionNeeded = false;
                return;
        }
        Path outputFile = null;
        if (fileName.contains("\\")) {
            outputFile = Paths.get(fileName);
        } else {
            if (userPath.isEmpty()) {
                outputFile =Paths.get(defultPath + "\\" + fileName);
            } else {
                outputFile = Paths.get(userPath + "\\" + fileName);

            }
        }
        try {


           Files.write(outputFile,insertionDataFile.getBytes(), StandardOpenOption.APPEND);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
        try {

        } catch (NullPointerException b) {
            b.printStackTrace();
        }

        isInsertionNeeded = false;

    }
    public void more( String fileName)
    {

        ArrayList<String>data=new ArrayList<>();
        int moreFlag;
        Scanner input=new Scanner(System.in);
        Scanner readFromFile= null;
        if(!fileName.contains("\\"))
        {
            if(userPath.isEmpty())
            {
                try {
                    readFromFile = new Scanner(new File(String.valueOf(Paths.get(defultPath+"\\"+fileName))));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                try {
                    readFromFile = new Scanner(new File(String.valueOf(Paths.get(userPath+"\\"+fileName))));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
        else
        {
            try {
                readFromFile = new Scanner(new File(String.valueOf(Paths.get(fileName))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        int counter = 0;
        while (readFromFile.hasNext())
        {
            data.add(readFromFile.nextLine());
        }

        do {

            for(int i=counter;i<counter+5 && i<data.size();i++)
            {
                System.out.println(data.get(i));
            }
            counter += 5;
        }while ((moreFlag=input.nextInt())==1 );

    }


}
