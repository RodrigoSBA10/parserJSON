package org.example.parser;
import jakarta.json.*;
import org.example.Main;

public class ParserJSON {
    JsonReader reader = Json.createReader(Main.class.getResourceAsStream("/nuevo.json"));
    JsonStructure structure;

    public ParserJSON() {
        structure = reader.read();
    }

    public void contenido() {
        JsonValue valores = structure.getValue("");
        if (valores.getValueType() == JsonValue.ValueType.OBJECT) {
            JsonObject objeto = (JsonObject) valores;
            for(String e :  objeto.keySet()) {
                JsonObject valore = (JsonObject) objeto.getValue("/" + e);
                for(String v : valore.keySet()) {
                    System.out.println(v);
                }
            }
        }
    }




}
