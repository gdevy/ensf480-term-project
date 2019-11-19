package server;

public enum ServerCommand {
    logout("LOGO"),
    login("LOGI"),
    search("SEAR"),
    invalid("INVA");

    private String text;

    ServerCommand(String string) {
        this.text = string;
    }

    public String getText() {
        return text;
    }

    public static ServerCommand getServerCommand(String text) {
        for (ServerCommand command : ServerCommand.values()) {
            if (command.text.equals(text)) {
                return command;
            }
        }

        return invalid;
    }
}
