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
import org.pathvisio.core.model.ObjectType;
import org.pathvisio.core.model.Pathway;
import org.pathvisio.core.model.PathwayElement;
import org.pathvisio.core.model.PathwayElement.MAnchor;

/**
 * Count interactions in analysis collections (curated + reactome) & all
 * pathways (Only connected interactions) Check percentage annotated
 * 
 * @author anwesha
 * 
 */
public class InteractionStats {
	private static int PathwayCount;
	private static int InteractionCount;
	private static int AnnotatedInteractionCount;

	public static void main(String[] args) {
		if (args.length == 1) {
			File inputPwyDir = new File(args[0]);

			InteractionStats wr = new InteractionStats();

			/*
			 * Setting up IDMapper
			 */
			wr.getNumInteractions(inputPwyDir);
			System.out.println("Number of pathways = " + PathwayCount);
			System.out.println("Number of connected interactions = "
					+ InteractionCount);
			System.out.println("Number of annotated interactions = "
					+ AnnotatedInteractionCount);
			} else {
			System.out.println(args.length);
		}
	}

	private void getNumInteractions(File inputPwyDir) {
		for (File pwyfile : inputPwyDir.listFiles()) {
			Pathway pwy = new Pathway();
			PathwayCount++;
			// System.out.println(pwy.getMappInfo().getMapInfoName());
			try {
				pwy.readFromXml(pwyfile, false);

				for (PathwayElement e : pwy.getDataObjects()) {
					if (e.getObjectType() == ObjectType.LINE) {
						if (isConnected(e, pwy, "start")) {
							if (isConnected(e, pwy, "end")) {
								InteractionCount++;
								
								if (e.getElementID() != null
										& e.getDataSource() != null) {
									AnnotatedInteractionCount++;
								}
							}
						}
					}
				}
			} catch (ConverterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private boolean isConnected(PathwayElement e, Pathway pwy, String side) {
		boolean connected = false;
		String nodeRef = "";
		if (side.equalsIgnoreCase("start")) {
			nodeRef = e.getStartGraphRef();
		} else {
			nodeRef = e.getEndGraphRef();
		}
		for (PathwayElement e1 : pwy.getDataObjects()) {
			if (e1.getGraphId() != null && nodeRef != null) {
				if (e1.getObjectType() == ObjectType.DATANODE) {
					// System.out.println("Node ref "+nodeRef);
					// System.out.println("graphid "+e1.getGraphId());
					if (nodeRef.equalsIgnoreCase(e1.getGraphId())) {
						connected = true;
					}
				}else if(e1.getObjectType() == ObjectType.LINE){
					for( MAnchor anchor : e1.getMAnchors()){
						if (nodeRef.equalsIgnoreCase(anchor.getGraphId())) {
							connected = true;
						}
					}
				}
			}
		}
		return connected;
	}

	public InteractionStats() {
		InteractionCount = 0;
		AnnotatedInteractionCount = 0;
		PathwayCount = 0;
	}
}
