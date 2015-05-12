
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

**
 * This is a interpreter to generate final results of different
 * variables by reading the Z+- program.
 * 
 * All code is original 
 * @author MINGWEI ZHONG
 *
 */
public class Interpreter {
    Scanner scan;
    String fileName;
    Map<String,Integer> variables;
    Z(String fileName)
    {
        this.fileName = fileName;
        try
        {
        scan = new Scanner(new File(fileName));
        }
        catch(Exception ex)
        {
        }
        variables = new HashMap<String,Integer>();
    }
    void processLine(String line)
    {
        
        if(line.indexOf("DEF")>=0)
        {
            String[] token = line.split(" ");
            variables.put(token[1], 0);
        }
        else if(line.indexOf("FOR")>=0)
        {
            String[] token = line.split(" ");
            int loop = Integer.parseInt(token[1].trim());
            line = line.substring(line.indexOf(" ", 4)).trim();
            for(int i = 0;i<loop;i++)
            {
                String[] statements = line.split(";");
                for(String statement:statements)
                {
                    if(!statement.trim().equals("ENDFOR"))
                        processLine(statement.trim());
                }
            }
        }
        else if(line.indexOf("+=")>=0)
        {
            String[] token = line.split(" ");
            try
            {
                variables.put(token[0], variables.get(token[0])+Integer.parseInt(token[2]));
            }
            catch(Exception ex)
            {
                variables.put(token[0], variables.get(token[0])+variables.get(token[2]));
            }
        }
        else if(line.indexOf("-=")>=0)
        {
            String[] token = line.split(" ");
            try
            {
                variables.put(token[0], variables.get(token[0]) - Integer.parseInt(token[2]));
            }
            catch(Exception ex)
            {
                variables.put(token[0], variables.get(token[0]) - variables.get(token[2]));
            }
        }
        else if(line.indexOf("*=")>=0)
        {
            String[] token = line.split(" ");
            try
            {
                variables.put(token[0], variables.get(token[0])*Integer.parseInt(token[2]));
            }
            catch(Exception ex)
            {
                variables.put(token[0], variables.get(token[0])*variables.get(token[2]));
            }
        }
        else if(line.indexOf("=")>=0)
        {
            String[] token = line.split(" ");
            try
            {
                variables.put(token[0], Integer.parseInt(token[2]));
            }
            catch(Exception ex)
            {
                variables.put(token[0], variables.get(token[2]));
            }
        }
    }
    void interpreter()
    {
        while(scan.hasNext())
        {
            String line = scan.nextLine().trim();
            processLine(line);
        }
        for(Map.Entry<String,Integer> entry:variables.entrySet())
        {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
    }
    public static void main(String args[])
    {
    	long startTime = System.currentTimeMillis();
        Z z = new Z("prog5.txt");
        z.interpreter();
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println((double)totalTime/1000 + " seconds");
    }
}
