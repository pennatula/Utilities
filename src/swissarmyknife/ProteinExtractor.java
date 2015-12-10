/**
 * 
 */
package swissarmyknife;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author anwesha
 * 
 */
public class ProteinExtractor {

	private static String protein = "";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Set<String> proteins = new HashSet<String>();
		File protFile = new File(
				"/home/anwesha/Reactome/ProtsMets_5Sep2015/allProts_V2015_08/allProts/uniprot_sprot.dat");
		try (BufferedReader br = new BufferedReader(new FileReader(protFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				
						if (line.startsWith("AC")) {
							protein = line.replaceFirst("AC", "");
					//							if (line.startsWith("OS")) {
//								proteins.add(line.replaceFirst("OS", ""));
//							}
						}	
						if(line.startsWith("OS")){
							line = line.replaceFirst("OS   ", "");
							
							if(line.equalsIgnoreCase("Homo sapiens (Human).")){
								System.out.println(line);
								proteins.add(protein);
							}
							}
				
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File allProts = new File(
				"/home/anwesha/Reactome/ProtsMets_5Sep2015/allProts_V2015_08/allProts/allProtsHuman.txt");
		// check IOException in method signature
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(allProts));
			Iterator it = proteins.iterator(); 
			while(it.hasNext()) {
			    out.write(it.next()+"\n");
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
