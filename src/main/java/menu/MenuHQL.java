package menu;

import dao.HQLDAO;

import java.util.Scanner;

public class MenuHQL {

    public static void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        HQLDAO dao = new HQLDAO();
        int opcion;

        do {
            System.out.println("""
                ===== MENU HQL =====
                1. Logros por nombre de juego
                2. Juegos antes de un año
                3. Juegos por plataforma
                4. Géneros de juegos de 1996
                5. Juegos por género en 1996
                0. Volver
                """);

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> {
                    System.out.print("Nombre: ");
                    dao.getAchievementsByGameName(sc.nextLine());
                }
                case 2 -> {
                    System.out.print("Año: ");
                    dao.getGamesBeforeYear(sc.nextInt());
                }
                case 3 -> {
                    System.out.print("Plataforma: ");
                    dao.getGamesByPlatform(sc.nextLine());
                }
                case 4 -> dao.getGenresFrom1996();
                case 5 -> dao.countGamesByGenreFrom1996();
            }
        } while (opcion != 0);
    }
}
