//package swissarmyknife;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.rmi.RemoteException;
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.xml.rpc.ServiceException;
//
//import org.pathvisio.wikipathways.webservice.WSOntologyTerm;
//import org.wikipathways.client.WikiPathwaysClient;
//
//public class checkOntology {
//	private final WikiPathwaysClient client;
//	private static String wikipathwaysURL = "http://wikipathways.org/wpi/webservice/webservice.php";
//	private Set<String> iDList;
//	private Set<String> pontIdList;
//	private Set<String> dontIdList;
//
//	public checkOntology(String wpIdFileName, String wpPoFileName,
//			String wpDFileName, String reactIdFileName, String reactPoFileName,
//			String reactDFileName) throws MalformedURLException,
//			ServiceException {
//		client = new WikiPathwaysClient(new URL(wikipathwaysURL));
//	}
//
//	public static void main(String[] args) {
//		System.out.println("Started");
//		String wpIdFileName = args[0];
//		String wpPoFileName = args[1];
//		String wpDFileName = args[2];
//		String reactIdFileName = args[3];
//		String reactPoFileName = args[4];
//		String reactDFileName = args[5];
//		try {
//			checkOntology retriever = new checkOntology(wpIdFileName,
//					wpPoFileName, wpDFileName, reactIdFileName,
//					reactPoFileName, reactDFileName);
//			File wpIdFile = new File(wpIdFileName);
//			File wpPoFile = new File(wpPoFileName);
//			File wpDFile = new File(wpDFileName);
//			File reactIdFile = new File(reactIdFileName);
//			File reactPoFile = new File(reactPoFileName);
//			File reactDFile = new File(reactDFileName);
//			wpPoFile.createNewFile();
//			wpDFile.createNewFile();
//			reactPoFile.createNewFile();
//			reactDFile.createNewFile();
//			BufferedWriter writerwpPo = new BufferedWriter(new FileWriter(
//					wpPoFile));
//			BufferedWriter writerwpD = new BufferedWriter(new FileWriter(
//					wpDFile));
//			BufferedWriter writerRPo = new BufferedWriter(new FileWriter(
//					reactPoFile));
//			BufferedWriter writerRD = new BufferedWriter(new FileWriter(
//					reactDFile));
//			/*
//			 * WikiPathways
//			 */
//			retriever.readPathwayIds(wpIdFile);
//			retriever.retrieveOntologyTerms(wpPoFile, wpDFile, writerwpPo,
//					writerwpD);
//
//			/*
//			 * Reactome
//			 */
//			retriever.readPathwayIds(reactIdFile);
//			retriever.retrieveOntologyTerms(reactPoFile, reactDFile, writerRPo,
//					writerRD);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("Finished");
//	}
//
//	private void readPathwayIds(File IdFile) {
//		iDList = new HashSet<String>();
//		String line;
//		BufferedReader br;
//		try {
//			br = new BufferedReader(new FileReader(IdFile));
//			while ((line = br.readLine()) != null) {
//				iDList.add(line);
//			}
//			br.close();
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
////	private void retrieveOntologyTerms(File PoFile, File DFile,
////			BufferedWriter writerPo, BufferedWriter writerD) throws Exception {
////		pontIdList = new HashSet<String>();
////		dontIdList = new HashSet<String>();
////		for (String id : iDList) {
////			try{
////			WSOntologyTerm[] ontTerms = client.getOntologyTermsByPathway(id);
////			for (WSOntologyTerm ontTerm : ontTerms) {
////				if (ontTerm.getOntology().equalsIgnoreCase("Pathway Ontology")) {
////					System.out.println(ontTerm.getId());
////					pontIdList.add(ontTerm.getId());
////				} else {
////					if (ontTerm.getOntology().equalsIgnoreCase("Disease")) {
////						System.out.println(ontTerm.getId());
////						dontIdList.add(ontTerm.getId());
////					}
////				}
////			}
////		}
////			catch(RemoteException e2){
////			e2.printStackTrace();	
////		}
////		Object[] pontIds = pontIdList.toArray();
////		for (int i = 0; i < pontIdList.size(); i++) {
////			try {
////				writerPo.write(pontIds[i].toString() + "\n");
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
////		}
////		Object[] dontIds = dontIdList.toArray();
////		for (int i = 0; i < dontIdList.size(); i++) {
////			try {
////				writerD.write(dontIds[i].toString() + "\n");
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
////		}
////	}
////}
//}