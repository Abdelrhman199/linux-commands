package com.company;

import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    private String[] args;
    private String cmd;
    private String fileName;


    public boolean parse(String input) {
        String[] temp = input.split(" ");
        int index;
       if( (index=Arrays.asList(temp).indexOf(">"))!=-1)
       {    args=new String[temp.length-2];
           for(int i=0;i<index;i++)
           {
               args[i]=temp[i];
           }
           fileName=temp[temp.length-1];
           cmd=">";
       }
       else if((index=Arrays.asList(temp).indexOf(">>"))!=-1)
       {
           args=new String[temp.length-2];
           for(int i=0;i<index;i++)
           {
               args[i]=temp[i];
           }
           fileName=temp[temp.length-1];
           cmd=">>";
       }
       else {
           switch (temp[0]) {
               case "ls":
                   cmd = temp[0];
                   if (temp.length > 2) {
                       return false;
                   } else if (temp.length == 2) {
                       args = new String[]{temp[1]};
                   } else if (temp.length == 1) {
                       args = new String[]{""};
                   }

                   break;
               case "mkdir":
                   cmd = temp[0];
                   if (temp.length == 1) {
                       System.out.print("Dirctory name needed");
                       return false;
                   }
                   args = new String[temp.length - 1];
                   for (int i = 1; i < temp.length; i++) {
                       args[i - 1] = temp[i];
                   }

                   break;

               case "cd":
                   cmd = temp[0];
                   if (temp.length == 1) {
                       args = new String[]{""};
                       break;
                   } else if (temp.length > 2) {
                       System.out.println("too many arguments!");
                       return false;
                   }
                   args = new String[]{temp[1]};
                   break;
               case "pwd":
                   cmd = temp[0];
                   if (temp.length > 1) {
                       System.out.println("too many arguments");
                       return false;
                   }
                   break;
               case "esc":
                   cmd = temp[0];
                   if (temp.length > 1) {
                       System.out.println("too many arguments");
                       return false;
                   }
                   break;
               case "cp":
                   cmd = temp[0];
                   if (temp.length < 3) {
                       System.out.println("missing arguments");
                       return false;
                   } else {
                       args = new String[]{temp[1], temp[2]};
                   }
                   break;
               case "date":
                   cmd = temp[0];
                   if (temp.length > 1) {
                       System.out.println("too many arguments");
                       return false;
                   }
                   break;
               case "cat":
                   cmd = temp[0];
                   if (temp.length > 1) {
                       // System.arraycopy(temp, 1, args, 0, temp.length );
                       args = new String[temp.length - 1];
                       for (int i = 1; i < temp.length; i++) {
                           args[i - 1] = temp[i];
                       }
                   } else {
                       args = new String[1];
                   }
                   break;
               case "clear":
                   cmd = temp[0];
                   if (temp.length > 1) {
                       System.out.println("too many arguments");
                       return false;
                   }
                   break;
               case "help":
                   cmd = temp[0];
                   if (temp.length > 1) {
                       System.out.println("too many arguments");
                       return false;
                   }
                   break;
               case "args":
                   cmd = temp[0];
                   if (temp.length > 2) {
                       System.out.println("too many arguments");
                       return false;
                   } else if (temp.length < 2) {
                       System.out.println("missing arguments");
                       return false;
                   } else {
                       args = new String[]{temp[1]};
                   }
                   break;
               case "rm":
               case "rmdir":
                   cmd = temp[0];
                   if (temp.length > 2) {
                       return false;
                   }
                   args = new String[]{temp[1]};
                   break;
               case "more":
                   cmd=temp[0];
                   if(temp.length<2)
                   {
                       return false;
                   }
                   args=new String[]{temp[1]};
                   break;


               default:
                   return false;
           }
       }


        return true;
    }

   public String getCmd()
    {
        return cmd;
    }
    public String[] getArguments()
    {
        return args;
    }
    public String getFileName()
    {
        return fileName;
    }
}
