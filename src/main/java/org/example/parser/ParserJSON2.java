package org.example.parser;

import jakarta.json.*;
import org.example.Main;

public class ParserJSON2 {
    JsonReader reader = Json.createReader(Main.class.getResourceAsStream("/personas.json"));
    JsonStructure structure;

    public ParserJSON2() {
        structure = reader.read();
    }

    public void contenido(){
        JsonObject raiz = structure.asJsonObject();
        JsonArray personas = raiz.getJsonArray("personas");
        for (JsonValue value : personas){
            JsonObject persona = value.asJsonObject();
            System.out.println("Nombre: "+persona.getString("firstName"));
            System.out.println("Apellidos: "+persona.getString("lastName"));
            System.out.println("Edad: "+persona.getInt("age"));
            JsonObject direccion = persona.getJsonObject("address");
            System.out.println("Direccion: "+direccion.getString("streetAddress"));
            System.out.println("Ciudad: "+direccion.getString("city"));
            System.out.println("Estado: "+direccion.getString("state"));
            System.out.println("CodigoPostal: "+direccion.getInt("postalCode"));
            JsonArray telefonos = persona.getJsonArray("phoneNumbers");
            for(JsonValue telefono : telefonos){
                JsonObject telefono1 = telefono.asJsonObject();
                System.out.println("Telefono: "+telefono1.getString("number"));
                System.out.println("Telefono: "+telefono1.getString("type"));
            }
            System.out.println("-------------------------------------------------------");
        }
    }
}
