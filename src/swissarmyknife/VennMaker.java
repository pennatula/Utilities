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
public class VennMaker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		File uniprotFile = new File(args[0]);
		File wpFile = new File(args[1]);
		File rFile = new File(args[2]);
		
		Set<String> Uni = new HashSet<String>();
		Set<String> WP = new HashSet<String>();
		Set<String> WP_h = new HashSet<String>();
		Set<String> Re = new HashSet<String>();
		Set<String> Re_h = new HashSet<String>();
		Set<String> onlyWP = new HashSet<String>();
		Set<String> onlyRe = new HashSet<String>();
		Set<String> WPnRe = new HashSet<String>();
		/*
		 * Read ids
		 */
		try (BufferedReader br = new BufferedReader(new FileReader(uniprotFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				Uni.add(line);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new FileReader(wpFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				WP.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new FileReader(rFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				Re.add(line);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * Calculate
		 */
		if (Uni.containsAll(WP)) {
			  // do something if needs be
			}
		if (Uni.containsAll(Re)) {
			  // do something if needs be
			}
		
		/*
		 * Only
		 */
		Set<String> one = WP;
		Set<String> two = Re;
		one.removeAll(Re);
		two.removeAll(WP);
		
		
//		Iterator<String> itWP = WP.iterator(); 
//		while(itWP.hasNext()) {
//		    if(Uni.contains(itWP.next())){
//		    	WP_h.add(itWP.next());
//		    }
//		}
//		
//		Iterator<String> itRe = Re.iterator(); 
//		while(itRe.hasNext()) {
//		    if(Uni.contains(itRe.next())){
//		    	Re_h.add(itRe.next());
//		    	}
//		}
		
//		if(WP_h.contains(itRe.next())){
//    		WPnRe.add(itRe.next());
//    	}else{
//    		onlyRe.add(itRe.next());
//    	}
				
//		Iterator<String> itWPonly = WP.iterator(); 
//		while(itWPonly.hasNext()) {
//		    if(Re_h.contains(itWPonly.next())){
//		  	    }else{
//		  	    	if(!itWPonly.next().isEmpty())
//		    	onlyWP.add(itWPonly.next());	
//		    }
//		}
		
		System.out.println("All human proteins = "+Uni.size());
		System.out.println("All WP proteins = "+WP.size());
		System.out.println("All Re proteins = "+Re.size());
		System.out.println("Proteins in both WP and R = "+WPnRe.size());
		System.out.println("Proteins only in WP = "+one.size());
		System.out.println("Proteins only in Re = "+two.size());
		
	}
}
