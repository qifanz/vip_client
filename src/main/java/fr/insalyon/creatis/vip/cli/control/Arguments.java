package fr.insalyon.creatis.vip.cli.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.lang.System.exit;

public class Arguments {
	private Map<String, List<String>> listArgs;

	public enum Action {
		EXECUTE, STATUS
	}

	private String action;

	public Arguments(String[] args) {
		if (args.length == 0) {
			System.err.println("No option");
			exit(0);
		}

		listArgs = new HashMap<>();
		switch (args[0]) {
			case "execute":
				action = "execute";
				break;
			case "status":
				action = "status";
				break;
			case "executions":
				action = "executions";
				break;
			case "download":
				action = "download";

				break;
			default:
				System.err.println("Option not correct");
				exit(0);
		}

		int it = 1;
		while (it < args.length) {
			if (!args[it].substring(0, 2).equals("--")) {

				if (listArgs.containsKey("")) {
					listArgs.get("").add(args[it]);
				} else {
					List<String> param=new ArrayList<>();
					param.add(args[it]);
					listArgs.put("", param);
				}
				it++;
			} else if (args[it].substring(0, 2).equals("--") && (it + 1) < args.length
					&& !args[it + 1].substring(0, 1).equals("--")) {
				List<String> param=new ArrayList<>();
				param.add(args[it+1]);
				listArgs.put(args[it].substring(2), param);
				it += 2;

			} else {
				System.err.println("no parameter.");
				exit(0);
			}
		}

	}

	public Map<String, List<String>> getListArgs() {
		return listArgs;
	}

	public String getAction() {
		return action;
	}



}
