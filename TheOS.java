import java.io.*;
import java.util.*;

public class TheOS {

    static String[][] memory;
    static int pointer;
    static Queue<String> readyQueue;



    public TheOS(){

        memory = new String[75][2];
        readyQueue = new LinkedList<>();
    }

    public static void interpret (String[] code,int vars) throws IOException {

        switch (code[0]) {
            case "assign":
                if (code[2].equals("input")) {
                    String s= input();
                    assign(code[1],s,vars);
                }
                else if(code[2].equals ("readFile")){
                    String s = readFile(code[3], vars);
                    assign (code[1],s, vars);//the output of the readfile method

                } break;
            case "print":
                print (code[1],vars);
                break;
            case "add" :
                add(code[1],code[2],vars);break;
            case "writeFile"   :
               writeFile(code[1],code[2],vars);break;



           default: System.out.println("wrong code");break;
        }
    }
    public static void add (String a, String b, int vars){
        int newVars= vars;
        int memA=vars;
        int A=-1;
        int B=-1;
        while (memory[newVars][0]!=null&& (newVars-vars<=10)) {
            if (memory[newVars][0].equals(a)){
                 A= Integer.parseInt(memory[newVars][1]);
                System.out.println("The memory is accessed at index "+newVars+" and "+ memory[newVars][0]+" is read: "+ memory[newVars][1]);
                memA=newVars;
            }
            if (memory[newVars][0].equals(b)){
                B= Integer.parseInt(memory[newVars][1]);
                System.out.println("The memory is accessed at index "+newVars+" and "+ memory[newVars][0]+" is read: "+ memory[newVars][1]);


            }
            newVars++;}

        int result=A+B;

         memory[memA][1]=""+result;
        System.out.println("The memory is accessed at index "+memA+" and "+ memory[memA][0]+" is written to: "+ memory[memA][1]);
    }
    public static void print(String x,int vars){
        int newVars= vars;
        while (memory[newVars][0]!=null&& (newVars-vars<=10)) {
            if (memory[newVars][0].equals(x)){
                System.out.println("The memory is accessed at index "+newVars+" and "+ memory[newVars][0]+" is read: "+ memory[newVars][1]);
                System.out.println((memory[newVars][1]));
            return;}
                newVars++;}
        System.out.println(x);

    }
    public static String input( ){
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream.
        String str= sc.nextLine(); //reads string.
        return str;

    }
    public static void assign (String varName ,String value,int vars){
        int newVars=vars;
        while (memory[newVars][0]!=null&& (newVars-vars<=10)) {
            newVars++;}
        memory[newVars][0]= varName;
        memory[newVars][1]= value;
        System.out.println("The memory is accessed at index "+newVars+" and "+ memory[newVars][0]+" written to: "+ memory[newVars][1]);
    }



    public static void writeFile(String a, String b, int vars)throws IOException {
        int newVars= vars;

        String A="";
        String B="";
        while (memory[newVars][0]!=null&& (newVars-vars<=10)) {
            if (memory[newVars][0].equals(a)){
                A= memory[newVars][1];
                System.out.println("The memory is accessed at index "+newVars+" and "+ memory[newVars][0]+" is read: "+ memory[newVars][1]);


            }
            if (memory[newVars][0].equals(b)){
                B= memory[newVars][1];
                System.out.println("The memory is accessed at index "+newVars+" and "+ memory[newVars][0]+" is read: "+ memory[newVars][1]);

            }
            newVars++;}
        BufferedWriter Writer = new BufferedWriter(new FileWriter(A ,true));
        Writer.write(B);
        Writer.close();


    }

    public static String readFile (String a, int vars )throws  FileNotFoundException, IOException{
        String output="";
        int newVars = vars;
        String A = "";
        while (memory[newVars][0]!=null&& (newVars-vars<=10)) {
            if (memory[newVars][0].equals(a)){
                A= memory[newVars][1];
                System.out.println("The memory is accessed at index "+newVars+" and "+ memory[newVars][0]+" is read: "+ memory[newVars][1]);
            }
            newVars++;}

        BufferedReader Reader = new BufferedReader(new FileReader(A));
        String row="";
        while ((row = Reader.readLine()) != null) {
            output+=" "+row;

        }
        return output;

    }
    public static void readToMemory(String filepath) throws IOException {
     int thePointer = pointer;
     String pid = filepath.substring(4,filepath.length());
        Vector<String>theProcess =   parse(filepath);
        memory[pointer][0]= "pid";
        System.out.println("The memory is accessed at index "+pointer+" and pid is added with value: "+pid);
        memory[pointer++][1]= pid;
        memory[pointer][0]= "state";

        System.out.println("The memory is accessed at index "+pointer+" and state is added with value: not running");
        memory[pointer++][1]= "not running";
        memory[pointer][0]= "PC";
        int pc =thePointer+5;
        System.out.println("The memory is accessed at index "+pointer+" and PC is added with value: "+pc);
        memory[pointer++][1]=""+pc;

        memory[pointer][0]= "BeginBoundary";
        System.out.println("The memory is accessed at index "+pointer+" and BeginBoundary is added with value: "+thePointer);
        memory[pointer++][1]=""+thePointer;
        memory[pointer][0]= "endBoundary";
        int endprocess= thePointer+25;
        System.out.println("The memory is accessed at index "+pointer+" and endBoundary is added with value: "+endprocess);
        memory[pointer++][1]=""+endprocess;
        for(int i=0 ; i<theProcess.size();i++){
            memory[pointer][0] ="i"+i;
            System.out.println("The memory is accessed at index "+pointer+" and instruction "+i+" is added with value: "+theProcess.get(i));
            memory[pointer++][1]=theProcess.get(i);}
         pointer= thePointer+25;
        readyQueue.add(pid);
    }


    public static Vector<String> parse(String filepath) throws IOException {
        BufferedReader Reader = new BufferedReader(new FileReader(filepath));
        //returns true if there is another line to read
        String s =" ";
        Vector<String> readProgram = new Vector<String>();

        while ((s=Reader.readLine()) != null){
            readProgram.add(s);
        }
        return readProgram;
    }

    public static void executeSlice () throws IOException {
        String pid = readyQueue.remove();
        System.out.println("The scheduler chose "+pid);
        int i;
        for(i=0; i<75;i+=25){

            if (memory[i][1].equals(pid))
                break; }
        memory[i+1][1]= "running";
        System.out.println("The memory is accessed at index "+(i+1)+" and state content changed to: running");
       int pc= Integer.parseInt(memory[i+2][1]);
        System.out.println("The memory is accessed at index "+(i+2)+" and pc content read: "+ pc);


        System.out.println("The memory is accessed at index "+pc+" and instruction is read: "+ memory[pc][1]);
        interpret(memory[pc++][1].split(" ", 0),i+15);
        memory[i+2][1]=""+pc;
        System.out.println("The memory is accessed at index "+(i+2)+" and PC content changed to: "+ pc);
        if (memory[pc][1]!= null){
            System.out.println("The memory is accessed at index "+pc+" and instruction is read: "+ memory[pc][1]);
            interpret( memory[pc][1].split(" ", 0),i+15);
            int temp = pc+1;
            memory[i+2][1]=""+temp;
            System.out.println("The memory is accessed at index "+(i+2)+" and pc content changed to: "+ temp);
            memory[i+1][1]= "not running";
            System.out.println("The memory is accessed at index "+(i+1)+" and state content changed to: not running");
            System.out.println("The scheduler was executing "+pid+" for 2 Quantas"+'\n');
            if (memory[++pc][1]!= null){
                readyQueue.add(pid);
            }
        }
        else{
            memory[i+1][1]= "not running";
            System.out.println("The memory is accessed at index "+(i+1)+" and state content changed to: not running");
            System.out.println("The scheduler was executing "+pid+" for 1 Quantas"+'\n');}
    }
    public static void RR ( ) throws IOException {
        while (!(readyQueue.isEmpty())){
              executeSlice();
        }
    }


    public static void main (String [] args) throws IOException {

        TheOS os =new TheOS();
        readToMemory("src/Program1");
        readToMemory("src/Program2");
        readToMemory("src/Program3");
        RR();




    }
}