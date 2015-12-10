
package swissarmyknife;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Maps Gene ids to the corresponding Uniprot IDs
 * 
 * @author anwesha
 * 
 */
public class matcher {
	String[] ids;
	private static Map<String, String> STRING_UNIPROT_IDMapper;
	
	public matcher()
			 {
		ids = new String[2];
		STRING_UNIPROT_IDMapper = new HashMap<String, String>();
			}

	public static void main(String args[]) {
		if (args.length == 2) {
			String inputFile = args[0];
			String outputFile = args[1];
			matcher wr = new matcher();
				/*
				 * Find WP Reactome pairs
				 * 
				 */
				wr.createStringUniprotMapper(inputFile);
				wr.getUnprotIds(outputFile);
			
		} else {
			System.out.println("Not enough args");
		}
	}

	private void createStringUniprotMapper(String inputFile) {
		BufferedReader brInput;
		try {
			brInput = new BufferedReader(new FileReader(inputFile));
			String line = null;
			while ((line = brInput .readLine()) != null) {
				ids = line.split("\t");
	            STRING_UNIPROT_IDMapper.put(ids[0],ids[1]);
//	            System.out.println("mapper"+"\t"+ids[0]+"\t"+ids[1]);
			}
		 
			brInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		    
	}
	private void getUnprotIds(String outputFile) {
		BufferedReader brOutput;
		try {
			brOutput = new BufferedReader(new FileReader(outputFile));
			String line = null;
			while ((line = brOutput .readLine()) != null) {
//				System.out.println(line);
	           if(STRING_UNIPROT_IDMapper.containsKey(line)){
	        	  	String id = STRING_UNIPROT_IDMapper.get(line);
		            System.out.println(id);	
	            }else{
	            	System.out.println("absent");	
	            }
	            
				}
			brOutput.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
}
}