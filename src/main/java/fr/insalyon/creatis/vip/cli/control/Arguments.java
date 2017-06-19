package fr.insalyon.creatis.vip.cli.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;

public class Arguments {
    private Map<String,String> argsWithFlag;
    private List<String> argsWithoutFlag;

    private ArgType action;

    public Arguments(String[] args) {
        if (args.length == 0) {
            System.err.println("No option");
            exit(0);
        }

        argsWithFlag = new HashMap<>();
        argsWithoutFlag= new ArrayList<>();
        switch (args[0]) {
            case "execute":
                action = ArgType.EXECUTE;
                break;
            case "status":
                action = ArgType.STATUS;
                break;
            case "executions":
                action = ArgType.EXECTUIONS;
                break;
            case "download":
                action = ArgType.DOWNLOAD;
                break;
            case "kill":
                action= ArgType.KILL;
                break;
            default:
                action = ArgType.INCORRECT;
        }

        int it = 1;
        while (it < args.length) {
            if (args[it].length()<2||!args[it].substring(0, 2).equals("--")) {

                argsWithoutFlag.add(args[it]);
                it++;
            } else if (args[it].substring(0, 2).equals("--") && (it + 1) < args.length
                    && !args[it + 1].substring(0, 1).equals("--")) {

                argsWithFlag.put(args[it].substring(2), args[it + 1]);
                it += 2;

            } else {
                System.err.println("Error parsing arguments");
                exit(0);
            }
        }

    }

    public Map<String,String> getArgsWithFlag() {
        return argsWithFlag;
    }

    public List<String> getArgsWithoutFlag(){return argsWithoutFlag;}

    public ArgType getAction() {
        return action;
    }


}
