package planning;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import fr.uga.pddl4j.encoding.AdapterPlanJavaJson;
import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.exceptions.FileException;
import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.heuristics.relaxation.HeuristicToolKit;
import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.planners.AbstractPlanner;
import fr.uga.pddl4j.planners.ProblemFactory;
import fr.uga.pddl4j.planners.Statistics;
import fr.uga.pddl4j.util.BitOp;
import fr.uga.pddl4j.util.BitState;
import fr.uga.pddl4j.util.MemoryAgent;
import fr.uga.pddl4j.util.Plan;
import fr.uga.pddl4j.util.SequentialPlan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Properties;

import fr.uga.pddl4j.encoding.Encoder;
import fr.uga.pddl4j.parser.Domain;
import fr.uga.pddl4j.parser.Parser;
import fr.uga.pddl4j.parser.Problem;
import fr.uga.pddl4j.planners.hsp.HSP;


public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File domaine = new File("/home/maxence/PATIA/Planification/domain_plan.pddl");
		File plan = new File("/home/maxence/PATIA/Planification/plan2.pddl");

		
		
		final Ourplaner clamidia = new Ourplaner();
		//Parser parse = new Parser();
		
 
       ProblemFactory usine = new ProblemFactory();
       try {
		ErrorManager error = usine.parse(domaine, plan);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       CodedProblem encodedprob = usine.encode();
        
		/*try {
			parse.parse(domaine,plan);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Domain domain = parse.getDomain();
        Problem problem = parse.getProblem();
        Encoder.setLogLevel(ProblemFactory.DEFAULT_TRACE_LEVEL);
		CodedProblem encodedprob= Encoder.encode(domain, problem);*/
		
		SequentialPlan result = clamidia.search(encodedprob);
		StringBuilder strb = new StringBuilder();
		System.out.println("balise");
		//System.out.print(result.toString());
		//strb.append(encodedprob.toString(result));
		//System.out.println(strb.toString());
		System.out.println(encodedprob.toString(result));
		
		//advanced print/////////////////
		final StringBuilder str = new StringBuilder();
        result.timeSpecifiers().forEach(time ->
        result.getActionSet(time).forEach(a ->
                str.append( encodedprob.toShortString(a)+" ")));
//System.out.println(str.toString());
        System.out.println(str);
        System.out.println(str.charAt(5));
		//////////////////////////////////
		System.out.println("fin");
		String concate[] = str.toString().split(" ");
		//System.out.println(concate[1]+" "+concate[2]);
		String envoie = new String();
		/*System.out.println("VERIFICATION CHAINE");
		for (int i = 0; i < concate.length; i++) {
			System.out.print(concate[i]+" ");
		}*/
		for (int i = 0; i < concate.length; i++) {
			switch (concate[i]) {
			case "move" :
				envoie=envoie+" "+concate[i]+" "+concate[i+1]+" "+concate[i+2]+" ";
				i=i+2;
				break;
			case "saisir_palet" :
				envoie=envoie+concate[i]+" ";
				i=i+2;
				break;
			case "deposer_palet" :
				envoie=envoie+concate[i]+" ";
				i=i+2;
				break;
			case "ouvrir_pince" :
				envoie=envoie+concate[i]+" ";
				i=i+1;
				break;
			case "fermer_pince" :
				envoie=envoie+concate[i]+" ";
				i=i+1;
				break;
			default:
				envoie=envoie+"ERROR"+concate[i]+" ";
				break;
			}
		}
		System.out.println(" CHAINE :\n"+envoie);
	}

}
