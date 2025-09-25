package org.example.parser;
import jakarta.json.*;
import org.example.Main;
import org.example.modelo.Direccion;
import org.example.modelo.Empleado;
import org.example.modelo.Telefono;
import java.util.ArrayList;
import java.util.List;

public class ParserJSON {
    JsonReader reader = Json.createReader(Main.class.getResourceAsStream("/nuevo.json"));
    JsonStructure structure;

    public ParserJSON() {
        structure = reader.read();
    }

    public List<Empleado> obtenerEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        JsonValue valores = structure.getValue("/");
        JsonObject datos = valores.asJsonObject();

        datos = (JsonObject) datos.get("datos");
        //System.out.println(valores.toString());
        Empleado emp =  new Empleado();
        emp.setNombre(datos.getString("firstName"));
        emp.setApellido(datos.getString("lastName"));
        emp.setEdad(datos.getInt("age"));
        empleados.add(emp);
        emp.setDir(obtenerDir(datos.getJsonObject("address")));
        emp.setTelefonos(obtenerTelefonos(datos.getJsonArray("phoneNumbers")));
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
        //System.out.println(telefonos.size());
        for (int i = 0; i < telefonos.size(); i++) {
            JsonObject jsonTelefono = telefonos.getJsonObject(i);
            telefono.setTipo(jsonTelefono.getString("type"));
            telefono.setNumero(jsonTelefono.getString("number"));
            telefonoList.add(telefono);
        }
        return telefonoList;
    }

    public void agregarEmpleado(Empleado empleado) {
        JsonObjectBuilder objeto = Json.createObjectBuilder();
        JsonPointer pointer;
        JsonObject nuevo = objeto.add("FirstName", empleado.getNombre())
                            .add("LastName", empleado.getApellido())
                            .add("Age", empleado.getEdad()).build();

        JsonObject nuevoDIr = objeto.add("streetAddress", empleado.getDir().getCalle())
                                .add("city", empleado.getDir().getCiudad())
                                .add("state", empleado.getDir().getEstado())
                                .add("postalCode", empleado.getDir().getCp()).build();
        pointer=Json.createPointer("/datos1");
        JsonArrayBuilder array = Json.createArrayBuilder();
        for (Telefono telefono : empleado.getTelefonos()) {
            JsonObject tel = objeto.add("type", telefono.getTipo())
                    .add("number", telefono.getNumero()).build();

            array.add(tel);
        }

        nuevo = pointer.add(nuevo, nuevoDIr);
        structure = pointer.add(structure, nuevo);
        pointer = Json.createPointer("/phoneNumbers");
        nuevo = pointer.add(nuevo, array.build());
        //pointer = Json.createPointer("/datos1");
        structure = pointer.add(structure, nuevo);
    }

    public void agregarEmp(Empleado em){
        JsonObjectBuilder datos =  Json.createObjectBuilder()
                .add("FirstName", em.getNombre())
                .add("LastName", em.getApellido())
                .add("Age", em.getEdad());
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
        JsonPointer pointer = Json.createPointer("/datos1");
        structure = pointer.add(structure, empleado);
    }

    public  void  borrarEmpleado(){
        JsonPointer pointer = Json.createPointer("/datos1");
        structure = pointer.remove(structure);
    }

    public void contenido() {
        JsonValue valores = structure.getValue("");
        JsonObject objeto = (JsonObject) valores;
        for(String e :  objeto.keySet()) {
            System.out.println(e);
            JsonObject valore = (JsonObject) objeto.getValue("/" + e);
            for(String v : valore.keySet()) {
                System.out.println(v + ":");
                if (valore.get(v).getValueType() == JsonValue.ValueType.ARRAY) {
                    JsonArray array = (JsonArray) valore.getJsonArray(v);
                    for(JsonValue j : array){
                        JsonObject obj = (JsonObject) j;
                        for (String k : obj.keySet()) {
                            System.out.println(k + ": " );
                            System.out.println(obj.get(k));
                        }
                    }
                }else if (valore.get(v).getValueType() == JsonValue.ValueType.OBJECT) {
                    JsonObject objeto1 = (JsonObject) valore.get(v);
                    for(String e1 : objeto1.keySet()) {
                        System.out.println(e1 + ":");
                        System.out.println(objeto1.get(e1));
                    }
                } else {
                    System.out.println(valore.get(v));
                }
            }
        }
    }
}
