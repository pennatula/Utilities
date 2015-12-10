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
public class removeInvalIds {

	

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		File allIds = new File(args[0]);
		File idsToRmv = new File(args[1]);
		File selIds = new File(args[2]);

		Set<String> finalIds = new HashSet<String>();
		boolean add = true;
		try (BufferedReader br1 = new BufferedReader(new FileReader(allIds))) {
			BufferedReader br2 = new BufferedReader(new FileReader(idsToRmv));
			String line1;
			String line2;
			while ((line1 = br1.readLine()) != null) {
				line1 = line1.trim();
			
				//System.out.println("line1"+line1);
				while ((line2 = br2.readLine()) != null) {
					line2 = line2.trim();
					//System.out.println(line2);
					if (line2.matches(line1)) {
						System.out.println("matches");
						add  = false;
						System.out.println(line2);
					}
				}
				if (add){
					finalIds.add(line1);
				}
			}
			br1.close();
			br2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(selIds));
			Iterator<String> it = finalIds.iterator();
			while (it.hasNext()) {
				out.write(it.next() + "\n");
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}