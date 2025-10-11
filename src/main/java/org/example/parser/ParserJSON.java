package org.example.parser;
import jakarta.json.*;
import org.example.Main;
import org.example.modelo.Direccion;
import org.example.modelo.Empleado;
import org.example.modelo.Telefono;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class ParserJSON {
    JsonReader reader = Json.createReader(Main.class.getResourceAsStream("/nuevo.json"));
    JsonStructure structure;
    List<Integer> pos = new ArrayList<>();
    public ParserJSON() {
        structure = reader.read();
        obtenerPos();
    }



    public List<Empleado> obtenerEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        JsonObject raiz = structure.asJsonObject();
        for (String key : raiz.keySet()) {
            JsonObject datos = raiz.getJsonObject(key);
            Empleado emp = new Empleado();
            emp.setNombre(datos.getString("firstName"));
            emp.setApellido(datos.getString("lastName"));
            emp.setEdad(datos.getInt("age"));
            emp.setDir(obtenerDir(datos.getJsonObject("address")));
            emp.setTelefonos(obtenerTelefonos(datos.getJsonArray("phoneNumbers")));
            empleados.add(emp);
        }
        return empleados;
    }

    private Direccion obtenerDir(JsonObject direccion){
        Direccion dire = new Direccion();
        dire.setCalle(direccion.getString("streetAddress"));
        dire.setCiudad(direccion.getString("city"));
        dire.setEstado(direccion.getString("state"));
        dire.setCp(direccion.getInt("postalCode"));
        return dire;
    }

    private List<Telefono> obtenerTelefonos(JsonArray telefonos) {
        List<Telefono> telefonoList = new ArrayList<>();
        Telefono telefono = new Telefono();
        for (JsonValue tel : telefonos) {
            JsonObject telephone = tel.asJsonObject();
            telefono.setTipo(telephone.getString("type"));
            telefono.setNumero(telephone.getString("number"));
            telefonoList.add(telefono);
        }
        return telefonoList;
    }

    public void agregarEmp(Empleado em){
        obtenerPos();
        JsonObjectBuilder datos =  Json.createObjectBuilder()
                .add("firstName", em.getNombre())
                .add("lastName", em.getApellido())
                .add("age", em.getEdad());
        JsonObjectBuilder dir = Json.createObjectBuilder()
                .add("streetAddress", em.getDir().getCalle())
                .add("city", em.getDir().getCiudad())
                .add("state", em.getDir().getEstado())
                .add("postalCode", em.getDir().getCp());
        JsonArrayBuilder array = Json.createArrayBuilder();
        for (Telefono telefono : em.getTelefonos()) {
            JsonObjectBuilder tel = Json.createObjectBuilder()
                    .add("type", telefono.getTipo())
                    .add("number", telefono.getNumero());
            array.add(tel);
        }
        JsonObject empleado = datos
                .add("address", dir)
                .add("phoneNumbers", array).build();
        JsonPointer pointer = Json.createPointer("/persona #" + (pos.getLast()+1));
        structure = pointer.add(structure, empleado);
    }

    public  void  borrarEmpleado(Integer eliminar){
        JsonPointer pointer = Json.createPointer("/persona #"+eliminar);
        try {
            structure = pointer.remove(structure);
        } catch (Exception ex) {
            System.out.println("Error al borrar el empleado");
        }
    }

    public void contenido() {
        JsonObject raiz = structure.asJsonObject();
        for (String key : raiz.keySet()) {
            System.out.println(key);
            JsonObject persona =  raiz.getJsonObject(key);
            System.out.println("Nombre: " + persona.getString("firstName"));
            System.out.println("Apellido: " + persona.getString("lastName"));
            System.out.println("Edad: " + persona.getInt("age"));
            JsonObject direccion = persona.getJsonObject("address");
            System.out.println("Direccion: " + direccion.getString("streetAddress"));
            System.out.println("Ciudad: " + direccion.getString("city"));
            System.out.println("Estado: " + direccion.getString("state"));
            System.out.println("Cp: " + direccion.getInt("postalCode"));
            JsonArray  telefonos = persona.getJsonArray("phoneNumbers");
            for (JsonValue jsonTelefono : telefonos) {
                JsonObject telefono = jsonTelefono.asJsonObject();
                System.out.println("Tipo " + telefono.getString("type"));
                System.out.println("Numero: " + telefono.getString("number"));
            }
            System.out.println("----------------------------------");
        }
    }

    public void hacerRespaldo(){
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(sw);
        jsonWriter.writeObject(structure.asJsonObject());
        jsonWriter.close();

        try (FileWriter fw = new FileWriter("src/main/resources/nuevo.json")) {
            fw.write(sw.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void obtenerPos(){
        JsonObject raiz = structure.asJsonObject();

        for (String key : raiz.keySet()) {
            String[] numero = key.split("#");
            pos.add(Integer.parseInt(numero[1]));
        }
    }
}
