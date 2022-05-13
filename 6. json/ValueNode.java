import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ValueNode extends Node {

    private double valueDouble;
    private boolean valueBoolean;
    private String valueString;

    private boolean doubleFlag = false;
    private boolean booleanFlag = false;
    private boolean stringFlag = false;

    public ValueNode(double value) {
        this.valueDouble = value;
        this.doubleFlag = true;
    }

    public ValueNode(boolean value) {
        this.valueBoolean = value;
        this.booleanFlag = true;
    }

    public ValueNode(String value) {
        if (value != null) {
            this.valueString = value;
            this.stringFlag = true;
        }
    }

    public boolean isNumber() {

        return doubleFlag;
    }

    public boolean isBoolean() {

        return booleanFlag;
    }

    public boolean isString() {

        return stringFlag;
    }

    public boolean isNull() {
        return this.valueString == null;
    }

    public double getNumber() {
        return this.valueDouble;
    }

    public boolean getBoolean() {
        return this.valueBoolean;
    }

    public String getString() {
        return this.valueString;
    }
}