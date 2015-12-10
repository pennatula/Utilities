/**
 * 
 */
package swissarmyknife;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
public class GOTermExtractor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		File oboFile = new File(args[0]);
		File outputFile = new File(args[1]);
		
		Set<String> goTermsHuman = new HashSet<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(oboFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("GO:")) {
					int begin = line.indexOf("GO:");
					int end = begin + 11;
					goTermsHuman.add(line.substring(begin, end));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(outputFile));
			Iterator<String> it = goTermsHuman.iterator(); 
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
