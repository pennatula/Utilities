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
 * Maps all GeneProduct ids to the corresponding Ensembl Gene IDs
 * 
 * @author anwesha
 * 
 */
public class EnsemblIdMapper {
	private static Set<String> ENSEMBL_IDS;
	DataSource ds = null;

	public static void main(String[] args) {
		if (args.length == 4) {
			File inputPwyDir = new File(args[0]);
			String outputFileGene = args[1];
			String outputFileProt = args[2];
			String bridgeFile = args[3];

			EnsemblIdMapper wr = new EnsemblIdMapper();

			/*
			 * Setting up IDMapper
			 */
			try {
				Class.forName("org.bridgedb.rdb.IDMapperRdb");
				IDMapper mapper = BridgeDb.connect("idmapper-pgdb:"
						+ bridgeFile);
				wr.getEnsemblIds(inputPwyDir, outputFileGene, outputFileProt,
						mapper);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IDMapperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}else{
			System.out.println(args.length);
		}
	}
		
	private void getEnsemblIds(File inputPwyDir, String outputFileGene,
			String outputFileProt, IDMapper mapper) {
		for (File pwyfile : inputPwyDir.listFiles()) {
			Pathway pwy = new Pathway();
			System.out.println(pwy.getMappInfo().getMapInfoName());
			try {
				pwy.readFromXml(pwyfile, false);
				List<Xref> refs = pwy.getDataNodeXrefs();
				
				for (Xref ref : refs) {
					ds = ref.getDataSource();
					/*
					 * Exclude metabolites
					 */
					if (!(ds == null || ds.getType().equalsIgnoreCase(
							"metabolite"))) {
//						countDatanodes = countDatanodes + 1;
						/*
						 * Id is not an Entrez gene id
						 */
						if (!ds.getSystemCode().equalsIgnoreCase("En")) {
							Set<Xref> entrezId = mapper.mapID(ref,
									DataSource.getBySystemCode("En"));
							for (Xref eId : entrezId) {
								ENSEMBL_IDS.add(eId.getId());
							}
						} else {
							ENSEMBL_IDS.add(ref.getId());
						}
						}
				}
			} catch (NullPointerException e) {
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
			brOutputGene = new BufferedWriter(new FileWriter(outputFileGene));
			Iterator<String> it = ENSEMBL_IDS.iterator();
			while (it.hasNext()) {
				brOutputGene.write(it.next() + "\n");
			}
			brOutputGene.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	public EnsemblIdMapper() {
		ENSEMBL_IDS = new HashSet<String>();
		}
}
