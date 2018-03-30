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
import java.util.Comparator;
import java.util.HashMap;
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
		File plan = new File("/home/maxence/PATIA/Planification/plan.pddl");

		
		
		final HSP clamidia = new HSP();
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
		
		System.out.print(result.toString());
		
		
	}

}
