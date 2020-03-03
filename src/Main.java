public class Main {

    {
        Logger.INSTANCE.log(LogTypes.ERROR, "usual block");
    }


    static {
        Logger.INSTANCE.log(LogTypes.WARNINGS, "static block");
    }

    public static void main(String[] args) {
        Logger.INSTANCE.log(LogTypes.INFO, "start main");

    }

}