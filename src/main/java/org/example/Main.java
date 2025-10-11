package org.example;

import org.example.modelo.Direccion;
import org.example.modelo.Empleado;
import org.example.modelo.Telefono;
import org.example.parser.ParserJSON;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static ParserJSON parser = new ParserJSON();
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int opcion = 0;
        do {
            System.out.println("Menu");
            System.out.println("1. Agregar Persona");
            System.out.println("2. Eliminar Persona");
            System.out.println("3. Ver listado de personas");
            System.out.println("4. Salir");
            opcion = validarNumeros();
            switch (opcion) {
                case 1:
                    agregarEmp();
                    System.out.println("Persona Agregada... Presione cualquier tecla para continuar");
                    scanner.nextLine();
                    break;
                    case 2:
                        eliminarEmpleado();
                        System.out.println("Persona Elimada. Presione cualquier tecla para continuar");
                        scanner.nextLine();
                    break;
                    case 3:
                        verLista();
                        System.out.println("Presione cualquier tecla para continuar");
                        scanner.nextLine();
                        break;
                default:
                    System.out.println("Debes ingresar un numero de la lista del menu");
            }
        }while (opcion != 4);
        scanner.close();
        System.out.println("Hasta luego");
        parser.hacerRespaldo();
    }

    static void agregarEmp() {
        Empleado nuevo = new Empleado();
        System.out.println("Ingres el nombre: ");
        nuevo.setNombre(scanner.nextLine());
        System.out.println("Ingres el apellido: ");
        nuevo.setApellido(scanner.nextLine());
        System.out.println("Ingres el edad: ");
        nuevo.setEdad(validarNumeros());
        agregarDIr(nuevo);
        agregarTelefono(nuevo);
        parser.agregarEmp(nuevo);
    }

    static void eliminarEmpleado() {
        parser.contenido();
        System.out.println("Selecciona el numero del empleado a eliminar: ");
        int num = validarNumeros();
        int empleados = parser.obtenerEmpleados().size();
        if (empleados != 1) {
            if (num > 0) {
                parser.borrarEmpleado(num);
            } else {
                eliminarEmpleado();
            }
        } else {
            System.out.println("No se puede eliminar, esta el minimo de empleados");
        }
    }

    static void verLista() {
        System.out.println("----------------Lista------------");
        parser.contenido();
    }

    static void agregarDIr(Empleado em){
        Direccion dir = new Direccion();
        System.out.println("Ingresa tu calle");
        dir.setCalle(scanner.nextLine());
        System.out.println("Ingresa tu delegacion");
        dir.setCiudad(scanner.nextLine());
        System.out.println("Ingresa tu estado");
        dir.setEstado(scanner.nextLine());
        System.out.println("Ingresa tu CP");
        dir.setCp(validarNumeros());
        em.setDir(dir);
    }

    static void agregarTelefono(Empleado em) {
        List<Telefono> telefonos = new ArrayList<>();
        System.out.println("Cantos telefonos deseas agregar");
        int cantidad = validarNumeros();
        for (int i = 0; i<cantidad;i++){
            System.out.println("Telefono " + (i+1));
            Telefono tel = new Telefono();
            System.out.println("Ingresa el typo:");
            tel.setTipo(scanner.nextLine());
            System.out.println("Ingresa el numero:");
            tel.setNumero(scanner.nextLine());
            telefonos.add(tel);
        }
        em.setTelefonos(telefonos);
    }

    static int validarNumeros() {
        boolean valido = false;
        int numero = 0;
        while (!valido){
            String n = scanner.nextLine();
            try {
                numero = Integer.parseInt(n);
                valido = true;
            }catch (NumberFormatException e){
                System.out.println("Debes ingresar un numero");

            }
        }
        return numero;
    }

}
