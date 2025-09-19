package org.example;

import org.example.modelo.Direccion;
import org.example.modelo.Empleado;
import org.example.parser.ParserJSON;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ParserJSON parser = new ParserJSON();
        //parser.contenido();

        //System.out.println(parser.obtenerEmpleados());
        Empleado em =  new Empleado();
        em.setNombre("Juan");
        em.setApellido("Ramirez");
        em.setEdad(25);
        Direccion dir =  new Direccion("Rafael sierra", "Iztapalapa", "CDMX", 4564);
        em.setDir(dir);
        parser.agrgearEmpleado(em);
        parser.contenido();
        //System.out.println(parser.obtenerEmpleados());
    }
}