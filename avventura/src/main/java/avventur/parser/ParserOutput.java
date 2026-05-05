package avventur.parser;

import avventur.type.ObjectGame;
import avventur.type.Command;

public class ParserOutput {

    private Command command;

    private ObjectGame object;

    private ObjectGame invObject;

    public ParserOutput(Command command, ObjectGame object) {
        this.command = command;
        this.object = object;
    }

    public ParserOutput(Command command, ObjectGame object, ObjectGame invObejct) {
        this.command = command;
        this.object = object;
        this.invObject = invObejct;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public ObjectGame getObject() {
        return object;
    }

    public void setObject(ObjectGame object) {
        this.object = object;
    }

    public ObjectGame getInvObject() {
        return invObject;
    }

    public void setInvObject(ObjectGame invObject) {
        this.invObject = invObject;
    }

}
