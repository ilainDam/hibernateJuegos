package menu;

import java.util.Scanner;

public class Menu {

    public static void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("""
                ===== MENU PRINCIPAL =====
                1. Operaciones con cascada
                2. Consultas HQL
                0. Salir
                """);

            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> MenuCascada.mostrarMenu();
                case 2 -> MenuHQL.mostrarMenu();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción incorrecta");
            }
        } while (opcion != 0);
    }
}
