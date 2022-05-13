public abstract class Node {

    public boolean isValue() {

        return this instanceof ValueNode;
    }

    public boolean isArray() {

        return this instanceof ArrayNode;
    }

    public boolean isObject() {

        return this instanceof ObjectNode;
    }

    public void printSimple()
    {
        StringBuilder sb = new StringBuilder();
        printSimple(this, sb);
        System.out.print(sb.toString());
    }


    public void printJson() {

        // throw new UnsupportedOperationException("printJson has not been implemented!");

        StringBuilder sb = new StringBuilder();
        int indents = 0;
        printJson(this, sb, indents, true);
        System.out.print(sb.toString());
    }

    private void printJson(Node node, StringBuilder sb, int indentCounter, boolean skipComma)
    {
        String INDENT = "  ";

        if (node.isObject()) {
            // sb.append(indent.repeat(this.indentCounter));
            ObjectNode objNode = (ObjectNode) node;
            // check if empty
            if (objNode.size() == 0) {
                sb.append("{}");
            }
            else {
                sb.append("{").append(NL);

                indentCounter++;
                int index = 0;
                for (String name : objNode) {
                    sb.append(INDENT.repeat(indentCounter));
                    sb.append("\"");
                    sb.append(name).append("\": ");
                    // check if last element
                    if (index + 1 == objNode.size()) {
                        printJson(objNode.get(name), sb, indentCounter, true);
                    } else {
                        printJson(objNode.get(name), sb, indentCounter, false);
                        index++;
                    }
                }

                indentCounter--;
                sb.append(INDENT.repeat(indentCounter));
                sb.append("}");
            }
            if (!skipComma)
            {
                sb.append(",");
            }
            sb.append(NL);
        }

        else if (node.isArray())
        {
            ArrayNode arrNode = (ArrayNode) node;

            // if array is empty, do this
            if (arrNode.size()==0)
            {
                sb.append("[]");
            }

            else
            {
                sb.append("[").append(NL);
                indentCounter++;
                int index = 0;
                for(Node aNode : arrNode)
                {
                    sb.append(INDENT.repeat(indentCounter));

                    // check if last element
                    if (index+1 == arrNode.size()) {
                        printJson(aNode, sb, indentCounter, true);
                    }
                    else
                    {
                        printJson(aNode, sb, indentCounter, false);
                        index++;
                    }
                }
                indentCounter--;
                sb.append(INDENT.repeat(indentCounter));

                sb.append("]");
            }
            if (!skipComma)
            {
                sb.append(",");
            }
            sb.append(NL);
        }

        else if (node.isValue())
        {
            ValueNode valNode = (ValueNode) node;
            String valStr = "null";
            if(valNode.isNumber())
            {
                valStr = numberToString(valNode.getNumber());

                // TODO ?
                if (! skipComma) {
                    valStr = valStr + ",";
                }
            }
            else if(valNode.isBoolean())
            {
                valStr = Boolean.toString(valNode.getBoolean());

                // TODO ?
                if (! skipComma) {
                    valStr = valStr + ",";
                }
            }
            else if(valNode.isString())
            {
                valStr = "\"" + valNode.getString() + "\"";

                // TODO does this work?
                if (! skipComma) {
                    valStr = valStr + ",";
                }
            }
            sb.append(String.format("%s%n", valStr));
        }
    }

    private static final String NL = System.lineSeparator();

    private static String numberToString(Double d) {
        String str = Double.toString(d);
        if(str.endsWith(".0")) {
            str = str.substring(0, str.length() - 2);
        }
        return str;
    }

    private void printSimple(Node node, StringBuilder sb)
    {
        if(node.isObject()) {
            sb.append("ObjectNode").append(NL);
            ObjectNode objNode = (ObjectNode) node;

            for(String name : objNode) {
              sb.append(name).append(": ");
              printSimple(objNode.get(name), sb);
            }
        }
        else if(node.isArray()) {
            sb.append("ArrayNode").append(NL);
            ArrayNode arrNode = (ArrayNode) node;

            for(Node aNode : arrNode) {
              printSimple(aNode, sb);
            }
        }
        else if(node.isValue())
        {
            ValueNode valNode = (ValueNode) node;
            String typeStr = "NullValue";
            String valStr = "null";
            if(valNode.isNumber()) {
              typeStr = "NumberValue";
              valStr = numberToString(valNode.getNumber());
            }
            else if(valNode.isBoolean()) {
              typeStr = "BooleanValue";
              valStr = Boolean.toString(valNode.getBoolean());
            }
            else if(valNode.isString()) {
              typeStr = "StringValue";
              valStr = "\"" + valNode.getString() + "\"";
            }
            sb.append(String.format("%s(%s)%n", typeStr, valStr));
        }
    }
}
