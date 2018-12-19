package cc.kinus.kitpvp.commands;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    private String name;
    private String args;
    private String description;

    public SubCommand(String name, String args, String description) {
        this.name = name;
        this.args = args;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract void execute(CommandSender sender, String[] args);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubCommand that = (SubCommand) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (args != null ? !args.equals(that.args) : that.args != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (args != null ? args.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SubCommand{" +
                "name='" + name + '\'' +
                ", args='" + args + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
