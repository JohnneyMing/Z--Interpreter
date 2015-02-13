import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This is a interpreter to generate final results of different
 * variables by reading the Z+- program.
 * 
 * All code is original 
 * @author MINGWEI ZHONG
 *
 */

public class Interpreter {
	
	public static String fileName = "prog5.zpm";
	public static HashMap<String,Integer>value = new HashMap<String,Integer>();
	
	//Number of times of looping
	public static int numLoop;
	
	
	//Started point of body of loop;
	public static int startLoop;
	
	//End point of body of loop;
	public static int endLoop;

	//Right hand side of "=", "+=", "-=", "*="
	public static String rightHandSideEqual;
	
	//The side of variable.
	public static int size;
	
	//Used to read file and store each line of code
	public static ArrayList<String> list = new ArrayList<String>();
	
	//Array-list to store variables.
	public static ArrayList<String> variable = new ArrayList<String>();
	
	//Body of the loop
	public static String loopBody = "";
	
	
	/**
	 * Driver
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[]args) throws IOException
	{	
		readFile();
		createHashmap();
		mainCases();
	}
	
	/**
	 * This method is used to check if a String is integer.
	 * 
	 * 
	 * @param s a String
	 * @return return true if it's a integer.
	 */
	public static boolean isInteger(String s)
	{
		try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	/**
	 * When only "=" sign appears, assign a value to that variable.
	 * 
	 * @param output one line of Z+- code.
	 * @param c a variable.
	 */
	public static void assignVariable(String output,String c)
	{
		 String o = "";

		  for(int k = output.indexOf("=")+1;k<output.indexOf(";")-1;k++)
          {                      
             o = o + Character.toString(output.charAt(k));          
          }
          
          rightHandSideEqual = o.trim();	
      	if(isInteger(rightHandSideEqual))
      	{
      		int a = Integer.parseInt(rightHandSideEqual);            		
      		value.put(c, a);
      	}
      	else
      	{
      		int b = value.get(rightHandSideEqual);
      	    value.put(c, b);
      	}
      	System.out.println(value.entrySet());
	}
	
	
	/**
	 * When only "*=" sign appears, assign a value to that variable.
	 * 
	 * @param output one line of Z+- code.
	 * @param c a variable.
	 */
	public static void multiplyEqual(String output,String c)
	{	
		String o = "";
	
		for(int k = output.indexOf("=")+1;k<output.indexOf(";")-1;k++)
        {                      
           o = o + Character.toString(output.charAt(k));          
        }
        
        rightHandSideEqual = o.trim();	
        
    	if(isInteger(rightHandSideEqual))
    	{
    		int a = Integer.parseInt(rightHandSideEqual);     
    		int b = value.get(c);
    		
    		value.put(c, a*b);
    	}
    	else
    	{
    		int a = value.get(c);
    		int b = value.get(rightHandSideEqual);
    	    value.put(c, a*b);
    	}
    	System.out.println(value.entrySet());	
	}
	
	/**
	 * When only "+=" sign appears, assign a value to that variable.
	 * 
	 * @param output one line of Z+- code.
	 * @param c a variable.
	 */
	public static void plusEqual(String output,String c)
	{

		String o = "";
		 for(int k = output.indexOf("=")+1;k<output.indexOf(";")-1;k++)
         {                      
            o = o + Character.toString(output.charAt(k));          
         }
         
         rightHandSideEqual = o.trim();	
     
     	if(isInteger(rightHandSideEqual))
     	{
     		int a = Integer.parseInt(rightHandSideEqual);     
     		int b = value.get(c);
     		value.put(c, a+b);
     	}
     	else
     	{
     		int a = value.get(c);
     		int b = value.get(rightHandSideEqual);
     	    value.put(c, a+b);
     	}
     	System.out.println(value.entrySet());
     }
	
	/**
	 * When only "-=" sign appears, assign a value to that variable.
	 * 
	 * @param output one line of Z+- code.
	 * @param c a variable.
	 */
	public static void minusEqual(String output,String c)
	{	 
		String o = "";
		 for(int k = output.indexOf("=")+1;k<output.indexOf(";")-1;k++)
         {                      
            o = o + Character.toString(output.charAt(k));          
         }
         
         rightHandSideEqual = o.trim();	
     	if(isInteger(rightHandSideEqual))
     	{
     		int a = Integer.parseInt(rightHandSideEqual);     
     		int b = value.get(c);
     		value.put(c, a-b);
     	}
     	else
     	{
     		int a = value.get(c);
     		int b = value.get(rightHandSideEqual);
     	    value.put(c, a-b);
     	}
     	System.out.println(value.entrySet());
     }
	
	/**
	 * If a line of Z+- code represents loop, get the number of looping.
	 * 
	 * 
	 * @param output a line of Z+- code
	 */
	public static void getNumberOflooping(String output)
	{
		int index = output.indexOf("R")+2;
		String temp = "";

		temp = temp + Character.toString(output.charAt(index));
		if(output.charAt(index+1) != ' ')
		{
			index = index + 1;
			temp = temp + Character.toString(output.charAt(index));			
		}
		numLoop = Integer.parseInt(temp);
		startLoop = index + 2;
	 
		int end = output.indexOf("ENDFOR");
		endLoop = end-4;

	}
	
	/**
	 * Divide the body of the loop into 2 parts
	 * 
	 * 
	 * @param output the body of loop
	 */
	public static void getBodyofLoop(String output)
	{
		String temp = "";
		String firstPart = "";
		String secondPart = "";
		
		
		for(int i = startLoop;i<= (endLoop)+2;i++)
		{
             temp = temp + Character.toString(output.charAt(i));            
		}
		
		loopBody = temp;

		for(int i = 0;i<numLoop;i++){
		
		firstPart = separateLoopfirst(loopBody);
		String c = Character.toString(firstPart.charAt(0));
		checkfirstpart(firstPart, c);
		
		secondPart = separateLoopSecond(loopBody);
		String d = Character.toString(secondPart.charAt(0));
		checksecondpart(secondPart, d);
		}
	}
	
	/**
	 * To check the first part of loop
	 * 
	 * @param firstPart first part of loop
	 * @param c a variable
	 */
	public static void checkfirstpart(String firstPart, String c)
	{
		if(  (value.containsKey(c)) && (firstPart.indexOf("=") != -1) 
				
        		&& (firstPart.indexOf("*=") == -1)  
        		&& (firstPart.indexOf("+=") != -1)
        		&& (firstPart.indexOf("-=") == -1)){
        	plusEqual(firstPart,c);
		}
		else if(  (value.containsKey(c)) && (firstPart.indexOf("=") != -1) 
        		&& (firstPart.indexOf("*=") != -1)  
        		&& (firstPart.indexOf("+=") == -1)
        		&& (firstPart.indexOf("-=") == -1)){
        	multiplyEqual(firstPart,c);            
        }
		else if(  (value.containsKey(c)) && (firstPart.indexOf("=") != -1) 
        		&& (firstPart.indexOf("*=") == -1)  
        		&& (firstPart.indexOf("+=") == -1)
        		&& (firstPart.indexOf("-=") != -1)){
        	minusEqual(firstPart,c);            
        }
	}
	
	
	/**
	 * To check the second part of loop
	 * 
	 * @param secondpPart second part of loop
	 * @param c a variable
	 */
	public static void checksecondpart(String secondPart,String d)
	{
		
        if(  (value.containsKey(d)) && (secondPart.indexOf("=") != -1) 
				
        		&& (secondPart.indexOf("*=") == -1)  
        		&& (secondPart.indexOf("+=") != -1)
        		&& (secondPart.indexOf("-=") == -1)){
        	
        	plusEqual(secondPart,d);
		}
        else if(  (value.containsKey(d)) && (secondPart.indexOf("=") != -1) 
        		&& (secondPart.indexOf("*=") != -1)  
        		&& (secondPart.indexOf("+=") == -1)
        		&& (secondPart.indexOf("-=") == -1)){
        	multiplyEqual(secondPart,d);            
        }
        else if(  (value.containsKey(d)) && (secondPart.indexOf("=") != -1) 
        		&& (secondPart.indexOf("*=") == -1)  
        		&& (secondPart.indexOf("+=") == -1)
        		&& (secondPart.indexOf("-=") != -1)){
        	minusEqual(secondPart,d);            
        }
	}
	
   /**
    * This method is used to separate the body of loop into 2 parts according to
    * the position of ";"
    * 
    */

	public static String separateLoopfirst(String f)
	{
		   
	    	String ret = "";
	    	ret = f.substring(0, f.indexOf(";")+1);
		    return ret;
	}
	
	/**
	    * This method is used to separate the body of loop into 2 parts according to
	    * the position of ";"
	    * 
	    */
	public static String separateLoopSecond(String l)
	{
		   String ret = "";
		   ret = l.substring(l.indexOf(";")+2);
		   return ret;
	}

	
	
	/**
	 * This method is used to present different operation
	 * 
	 */
	public static void mainCases()
	{
		
		for(int i = 0;i<list.size();i++)
		{
			    String output = list.get(i).trim();
                String c =  Character.toString(output.charAt(0));
                //Checking only for with "="
                       
           if( (list.get(i).indexOf("FOR") == -1) && (list.get(i).indexOf("ENDFOR") == -1)) {     
                if(  (value.containsKey(c)) && (list.get(i).indexOf("=") != -1) 
                		&& (list.get(i).indexOf("*=") == -1)  
                		&& (list.get(i).indexOf("+=") == -1)
                		&& (list.get(i).indexOf("-=") == -1)){
                	assignVariable(output,c);
                  
                }
                else if(  (value.containsKey(c)) && (list.get(i).indexOf("=") != -1) 
                		&& (list.get(i).indexOf("*=") != -1)  
                		&& (list.get(i).indexOf("+=") == -1)
                		&& (list.get(i).indexOf("-=") == -1)){
                	multiplyEqual(output,c);            
                }
                
                //*****check for "+="
                else if(  (value.containsKey(c)) && (list.get(i).indexOf("=") != -1) 
                		&& (list.get(i).indexOf("*=") == -1)  
                		&& (list.get(i).indexOf("+=") != -1)
                		&& (list.get(i).indexOf("-=") == -1)){
                	plusEqual(output,c);
                }
                
                else if(  (value.containsKey(c)) && (list.get(i).indexOf("=") != -1) 
                		&& (list.get(i).indexOf("*=") == -1)  
                		&& (list.get(i).indexOf("+=") == -1)
                		&& (list.get(i).indexOf("-=") != -1)){
                	minusEqual(output,c);
                   
                }
           }
           else
           {
        	   getNumberOflooping(output);
        	   getBodyofLoop(output);
           }      
		}
	}
   
	/**
	 * Read the Z+- file.
	 * 
	 * @throws IOException
	 */
	public static void readFile() throws IOException
	{
		
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line;
		while( (line = reader.readLine()) != null  )
		{
			 if(!(line.equals("") && line.indexOf("FOR") == -1)){
		     list.add(line.trim());	
			 }
		}
		
		reader.close();
		
		for(int i = 0;i<list.size();i++)
		{	
			if( !(list.get(i).equals("")) && (list.get(i).substring(0,3).equals("DEF") )){
			    variable.add( (list.get(i).substring(4,5) ));
			}
		}
	}
	
	/**
	 * Data structure hash-map used to store keys and variables.
	 * 
	 */
	public static void createHashmap()
	{	
		for(int i = 0;i<variable.size();i++)
		{
			value.put(variable.get(i), 0);
		}
		
	}
}
