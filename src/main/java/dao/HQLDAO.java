package dao;

import clasesMapear.Achievements;
import clasesMapear.Games;
import clasesMapear.Genres;
import org.example.Conexion;
import org.hibernate.Session;

import java.util.List;

//Tuve que usar la funcion 'strftime' porque no conseguir que me funcionara con el year() que pedias en las instrucciones

public class HQLDAO {
    public void getAchievementsByGameName(String gameName) {
        Session session = Conexion.getInstance().getSession();
        try {
            List<Achievements> lista = session
                    .createQuery("FROM Achievements a WHERE a.juego.name LIKE :name", Achievements.class)
                    .setParameter("name", "%" + gameName + "%")
                    .list();
            System.out.println("\n--- Logros encontrados para: " + gameName + " ---");
            for (Achievements a : lista) {
                System.out.printf("- %s: %s%n", a.getName(), a.getDescription());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void getGamesBeforeYear(int year) {
        Session session = Conexion.getInstance().getSession();
        try {
            List<Games> lista = session
                    .createQuery(
                            "FROM Games g WHERE function('strftime','%Y', g.releasedDate) < :anioStr ORDER BY g.releasedDate DESC",
                            Games.class)
                    .setParameter("anioStr", String.valueOf(year))
                    .list();
            System.out.println("\n--- Juegos lanzados antes de " + year + " ---");
            for (Games g : lista) {
                System.out.printf("ID: %d | %s | Lanzamiento: %s | Rating: %s | Slug: %s%n",
                        g.getId(), g.getName(), g.getReleasedDate(), g.getRating(), g.getSlug());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void getGamesByPlatform(String platformName) {
        Session session = Conexion.getInstance().getSession();
        try {
            List<Games> lista = session
                    .createQuery("SELECT g FROM Platforms p JOIN p.juegos g WHERE p.name LIKE :pName", Games.class)
                    .setParameter("pName", "%" + platformName + "%")
                    .list();
            System.out.println("\n--- Juegos en la plataforma: " + platformName + " ---");
            for (Games g : lista) {
                System.out.printf("ID: %d | %s | Lanzamiento: %s | Rating: %s | Slug: %s%n",
                        g.getId(), g.getName(), g.getReleasedDate(), g.getRating(), g.getSlug());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void getGenresFrom1996() {
        Session session = Conexion.getInstance().getSession();
        try {
            List<String> lista = session
                    .createQuery(
                            "SELECT DISTINCT gen.name FROM Genres gen JOIN gen.juegos g WHERE function('strftime','%Y', g.releasedDate) = :yearStr",
                            String.class)
                    .setParameter("yearStr", "1996")
                    .list();
            System.out.println("\n--- Géneros activos en 1996 ---");
            for (String genero : lista) {
                System.out.println("- " + genero);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void countGamesByGenreFrom1996() {
        Session session = Conexion.getInstance().getSession();
        try {
            List<Object[]> lista = session
                    .createQuery(
                            "SELECT gen.name, count(g) FROM Genres gen JOIN gen.juegos g " +
                                    "WHERE function('strftime','%Y', g.releasedDate) = :yearStr " +
                                    "GROUP BY gen.name",
                            Object[].class)
                    .setParameter("yearStr", "1996")
                    .list();
            System.out.println("\n--- Estadísticas por Género en 1996 ---");
            for (Object[] fila : lista) {
                String genero = (String) fila[0];
                Long cantidad = (Long) fila[1];
                System.out.printf("Género: %-15s | Juegos lanzados: %d%n", genero, cantidad);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}