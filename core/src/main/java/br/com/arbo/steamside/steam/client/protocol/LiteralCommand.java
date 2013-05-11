package br.com.arbo.steamside.steam.client.protocol;

public class LiteralCommand implements Command {

    private final String literal;

    public LiteralCommand(String literal) {
        this.literal = literal;
    }

    @Override
    public String command() {
        return literal;
    }
}
