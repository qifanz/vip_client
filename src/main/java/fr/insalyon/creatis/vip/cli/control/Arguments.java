package fr.insalyon.creatis.vip.cli.control;

import java.util.HashMap;
import java.util.Map;
import static java.lang.System.exit;

public class Arguments {
	private Map<String, String> listArgs;

	public enum Action {
		EXECUTE, STATUS
	};

	String action;

	public Arguments(String[] args) {
		if (args.length == 0) {
			System.err.println("No option");
			exit(0);
		}

		listArgs = new HashMap<String, String>();
		if (args[0].equals("execute")) {
			action = "execute";
		} else if (args[0].equals("status")) {
			action ="status";
		} else if(args[0].equals("executions")){
			action="executions";
		}
		else {
			System.err.println("Option not correct");
			exit(0);
		}

		int it = 1;
		while (it < args.length) {
			if (!args[it].substring(0, 2).equals("--")) {
				if (listArgs.containsKey("")){
					
				}else {
					listArgs.put("", args[it]);
				}
				it++;
			} else if (args[it].substring(0, 2).equals("--") && (it + 1) < args.length
					&& !args[it + 1].substring(0, 1).equals("--")) {

				listArgs.put(args[it].substring(2), args[it + 1]);
				it += 2;

			} else {
				System.err.println("no parameter.");
				exit(0);
			}
		}

	}
	public Map<String,String> getListArgs(){
		return listArgs;
	}
	
	public String getAction(){
		return action;
	}

	public void print() {
		System.out.println(action);
		for (Map.Entry<String, String> entry : listArgs.entrySet()) {
			if (entry.getKey().equals("")) {
				System.out.println("No key: " + entry.getValue());
			} else {
				System.out.println(entry.getKey() + ": " + entry.getValue());
			}
		}
	}

}
