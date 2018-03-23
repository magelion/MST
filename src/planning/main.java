package planning;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.encoding.Encoder;
import fr.uga.pddl4j.parser.Domain;
import fr.uga.pddl4j.parser.Parser;
import fr.uga.pddl4j.parser.Problem;
import fr.uga.pddl4j.planners.ProblemFactory;
import fr.uga.pddl4j.planners.hsp.HSP;
import fr.uga.pddl4j.util.SequentialPlan;


public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File domaine = new File("/home/maxence/PATIA/Planification/domain_plan.pddl");
		File plan = new File("/home/maxence/PATIA/Planification/plan.pddl");

		
		
		HSP clamidia = new HSP();
		Parser parse = new Parser();
		try {
			parse.parse(domaine,plan);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Domain domain = parse.getDomain();
        Problem problem = parse.getProblem();
        Encoder.setLogLevel(ProblemFactory.DEFAULT_TRACE_LEVEL);
		CodedProblem encodedprob= Encoder.encode(domain, problem);
		
		SequentialPlan result = clamidia.search(encodedprob);
		
		System.out.print(result.toString());
		
		
	}

}
