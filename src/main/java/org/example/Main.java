package org.example;

import org.example.modelo.Direccion;
import org.example.modelo.Empleado;
import org.example.modelo.Telefono;
import org.example.parser.ParserJSON;

import java.util.Arrays;
import java.util.List;

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
        Telefono tel1 =  new Telefono("Celular", "5510960686");
        Telefono tel2 = new Telefono("Local", "5522911847");
        List<Telefono> telefonos = Arrays.asList(tel1, tel2);
        em.setTelefonos(telefonos);
        parser.agregarEmpleado(em);
        //parser.agregarEmp(em);
        parser.contenido();
        System.out.println("----------------------------eliminar-----------------");
        parser.borrarEmpleado();
        parser.contenido();
        //System.out.println(parser.obtenerEmpleados());
    }
}