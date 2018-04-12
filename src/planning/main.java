package planning;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.parser.ErrorManager;

import fr.uga.pddl4j.planners.ProblemFactory;


import fr.uga.pddl4j.util.SequentialPlan;





public class main {

	public static void main(String[] args) {
		File domaine = new File("/home/maxence/PATIA/Planification/domain_plan.pddl");
		File plan = new File("/home/maxence/PATIA/Planification/plan2.pddl");
		
		ServerSockPlanner socketcom = null;
		
		try {
			socketcom = new ServerSockPlanner();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		final Ourplaner clamidia = new Ourplaner();
		
 
       final ProblemFactory usine = new ProblemFactory();
       try {
		ErrorManager error = usine.parse(domaine, plan);
       } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       CodedProblem encodedprob = usine.encode();
        
		
		SequentialPlan result = clamidia.search(encodedprob);
		StringBuilder strb = new StringBuilder();
		System.out.println("balise");

		System.out.println(encodedprob.toString(result));
		
		//advanced print/////////////////
		final StringBuilder str = new StringBuilder();
        result.timeSpecifiers().forEach(time ->
        result.getActionSet(time).forEach(a ->
                str.append( encodedprob.toShortString(a)+" ")));
        System.out.println(str);
        System.out.println(str.charAt(5));
		//////////////////////////////////
		System.out.println("fin");
		String concate[] = str.toString().split(" ");
		String envoie = new String();

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
		
		socketcom.writestring(envoie);
		
		
	}

}
