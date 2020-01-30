package com.company;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Terminal obj = new Terminal();
        Scanner input = new Scanner(System.in);
        String command="";


        Parser parserObj = new Parser();
        while (true) {
            command = input.nextLine();
            if(!parserObj.parse(command))
            {
                break;
            }

            if (parserObj.getCmd().equals("mkdir")) {
                    obj.mkdir(parserObj.getArguments());

                }
            else if (parserObj.getCmd().equals("ls")) {
                obj.ls((parserObj.getArguments()[0]));
            }
            else if
            (parserObj.getCmd().equals("cd")) {

                    obj.cd(parserObj.getArguments()[0]);
                }
                else if(parserObj.getCmd().equals("pwd"))
            {
                obj.pwd();
            }
                else if(parserObj.getCmd().equals("esc"))
            {
                obj.esc();
            }
                else if(parserObj.getCmd().equals("cp"))
            {
                obj.cp(parserObj.getArguments()[0],parserObj.getArguments()[1]);
            }
                else if(parserObj.getCmd().equals("date"))
            {
                obj.date();
            }
                else if(parserObj.getCmd().equals("clear"))
            {
                obj.clear();
            }
                else if(parserObj.getCmd().equals("help"))
            {
                obj.help();
            }
                else if(parserObj.getCmd().equals("args"))
            {
                obj.args(parserObj.getArguments()[0]);
            }
                else if(parserObj.getCmd().equals("cat"))
            {
                obj.cat(parserObj.getArguments());
            }
                else if(parserObj.getCmd().equals("rm"))
            {
                obj.rm(parserObj.getArguments()[0]);
            }
                else if(parserObj.getCmd().equals("rmdir"))
            {
                obj.rmdir(parserObj.getArguments()[0]);
            }
                else if(parserObj.getCmd().equals(">"))
            {
                obj.insertion(parserObj.getArguments(),parserObj.getFileName());
            }
                else if(parserObj.getCmd().equals(">>"))
            {
                obj.doubleInsertion(parserObj.getArguments(),parserObj.getFileName());

            }
                else if(parserObj.getCmd().equals("more"))
            {
                obj.more(parserObj.getArguments()[0]);
            }


        }

    }
}
