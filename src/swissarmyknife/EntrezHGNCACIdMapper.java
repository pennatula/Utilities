/**
 * 
 */
package swissarmyknife;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bridgedb.BridgeDb;
import org.bridgedb.DataSource;
import org.bridgedb.IDMapper;
import org.bridgedb.IDMapperException;
import org.bridgedb.Xref;
import org.pathvisio.core.model.ConverterException;
import org.pathvisio.core.model.Pathway;

/**
 * Maps all EntrezIds to the corresponding HGNC ACs
 * 
 * @author anwesha
 * 
 */
public class EntrezHGNCACIdMapper {
	private static Set<String> HMDB_IDS;
	DataSource ds = null;

	public static void main(String[] args) {
		if (args.length == 3) {
			File inputPwyDir = new File(args[0]);
			String outputFileMet = args[1];
			String bridgeFile = args[2];
			EntrezHGNCACIdMapper wr = new EntrezHGNCACIdMapper();

			/*
			 * Setting up IDMapper
			 */
			try {
				Class.forName("org.bridgedb.rdb.IDMapperRdb");
				IDMapper mapper = BridgeDb.connect("idmapper-pgdb:"
						+ bridgeFile);
				wr.getHMDBIds(inputPwyDir, outputFileMet, mapper);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IDMapperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void getHMDBIds(File inputPwyDir, String outputFileMet,
			IDMapper mapper) {
		for (File pwyfile : inputPwyDir.listFiles()) {
			Pathway pwy = new Pathway();
			
			try {
				pwy.readFromXml(pwyfile, false);
				List<Xref> refs = pwy.getDataNodeXrefs();
				for (Xref ref : refs) {
					ds = ref.getDataSource();
					/*
					 * Only metabolites
					 */
					if (!(ds == null)) {
						System.out.println(ds.getType());
						if(ds.getType().equalsIgnoreCase(
								"metabolite")){
							/*
							 * Id is not an HMDB id
							 */
							if (!ds.getSystemCode().equalsIgnoreCase("Ch")) {
								Set<Xref> hmdbId = mapper.mapID(ref,
										DataSource.getBySystemCode("Ch"));
								for (Xref eId : hmdbId) {
									System.out.println(eId.getId());
									HMDB_IDS.add(eId.getId());
								}
							} else {
								System.out.println(ref.getId());
								HMDB_IDS.add(ref.getId());
							}	
						}
						}
				}
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConverterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IDMapperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		BufferedWriter brOutputGene;
		try {
			brOutputGene = new BufferedWriter(new FileWriter(outputFileMet));
			Iterator<String> it = HMDB_IDS.iterator();
			while (it.hasNext()) {
				brOutputGene.write(it.next() + "\n");
			}
			brOutputGene.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	public EntrezHGNCACIdMapper() {
		HMDB_IDS = new HashSet<String>();
		}
}
