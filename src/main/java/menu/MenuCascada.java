package menu;

import dao.CascadaDAO;

import java.util.Scanner;

public class MenuCascada {

    public static void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        CascadaDAO dao = new CascadaDAO();
        int opcion;

        do {
            System.out.println("""
                ===== MENU CASCADA =====
                1. Añadir logro a un juego
                2. Eliminar logro de un juego
                3. Añadir género a un juego
                4. Quitar género de un juego
                5. Añadir plataforma a un juego
                6. Quitar plataforma de un juego
                7. Borrar plataforma y juegos asociados
                0. Volver
                """);

            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> {
                    System.out.print("ID Juego: ");
                    int gameId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nombre logro: ");
                    String nombre = sc.nextLine();
                    System.out.print("Descripción: ");
                    String desc = sc.nextLine();
                    dao.addAchievement(gameId, nombre, desc);
                }
                case 2 -> {
                    System.out.print("ID Logro: ");
                    dao.removeAchievement(sc.nextInt());
                }
                case 3 -> {
                    System.out.print("ID Juego: ");
                    int gameId = sc.nextInt();
                    System.out.print("ID Género: ");
                    dao.addGenreToGame(gameId, sc.nextInt());
                }
                case 4 -> {
                    System.out.print("ID Juego: ");
                    int gameId = sc.nextInt();
                    System.out.print("ID Género: ");
                    dao.removeGenreFromGame(gameId, sc.nextInt());
                }
                case 5 -> {
                    System.out.print("ID Juego: ");
                    int gameId = sc.nextInt();
                    System.out.print("ID Plataforma: ");
                    dao.addPlatformToGame(gameId, sc.nextInt());
                }
                case 6 -> {
                    System.out.print("ID Juego: ");
                    int gameId = sc.nextInt();
                    System.out.print("ID Plataforma: ");
                    dao.removePlatformFromGame(gameId, sc.nextInt());
                }
                case 7 -> {
                    System.out.print("ID Plataforma: ");
                    dao.deletePlatformAndGames(sc.nextInt());
                }
            }
        } while (opcion != 0);
    }
}
