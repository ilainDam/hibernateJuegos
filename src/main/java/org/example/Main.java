package org.example;

import clasesMapear.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Asegúrate de que tu hibernate.cfg.xml carga bien
        Session session = Conexion.getInstance().getSession();
        int opcion = 0;

        do {
            System.out.println("\n--- MENÚ HIBERNATE VIDEOJUEGOS ---");
            System.out.println("1. Añadir Logro a un Juego");
            System.out.println("2. Eliminar Logro de un Juego");
            System.out.println("3. Añadir Género a un Juego");
            System.out.println("4. Quitar Género de un Juego");
            System.out.println("5. Añadir Plataforma a un Juego");
            System.out.println("6. Eliminar Plataforma (y sus juegos asociados)");
            System.out.println("--- CONSULTAS HQL ---");
            System.out.println("7. Logros de juegos que empiezan por...");
            System.out.println("8. Juegos lanzados antes del año...");
            System.out.println("9. Juegos de una plataforma concreta");
            System.out.println("10. Géneros distintos del año 1996");
            System.out.println("11. Cantidad de juegos por género en 1996");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1: addAchievement(session); break;
                case 2: removeAchievement(session); break;
                case 3: addGenreToGame(session); break;
                case 4: removeGenreFromGame(session); break;
                case 5: addPlatformToGame(session); break;
                case 6: deletePlatformAndGames(session); break;
                // Consultas HQL
                case 7: hqlLogrosPorNombreJuego(session); break;
                case 8: hqlJuegosAntesDeAnio(session); break;
                case 9: hqlJuegosPorPlataforma(session); break;
                case 10: hqlGeneros1996(session); break;
                case 11: hqlCountJuegosGenero1996(session); break;
            }

        } while (opcion != 0);

        session.close();
        Conexion.getInstance().close();
    }

    // ------------------- MÉTODOS DE GESTIÓN (ABM) -------------------

    private static void addAchievement(Session s) {
        Transaction tx = s.beginTransaction();
        System.out.print("ID del Juego al que añadir logro: ");
        int gameId = sc.nextInt(); sc.nextLine();

        Games juego = s.get(Games.class, gameId);

        if (juego != null) {
            System.out.print("ID Logro (manual): "); // Tu entidad no tiene @GeneratedValue en Achievements
            int idLogro = sc.nextInt(); sc.nextLine();
            System.out.print("Nombre Logro: ");
            String nombre = sc.nextLine();
            System.out.print("Descripción: ");
            String desc = sc.nextLine();

            Achivements ach = new Achivements(idLogro, nombre, desc, juego);
            s.persist(ach);
            tx.commit();
            System.out.println("Logro creado exitosamente.");
        } else {
            System.out.println("Juego no encontrado.");
            tx.rollback();
        }
    }

    private static void removeAchievement(Session s) {
        Transaction tx = s.beginTransaction();
        System.out.print("ID del Logro a borrar: ");
        int achId = sc.nextInt();

        Achivements ach = s.get(Achivements.class, achId);
        if (ach != null) {
            s.remove(ach);
            tx.commit();
            System.out.println("Logro eliminado.");
        } else {
            System.out.println("No existe ese logro.");
            tx.rollback();
        }
    }

    private static void addGenreToGame(Session s) {
        Transaction tx = s.beginTransaction();
        System.out.print("ID Juego: ");
        Games g = s.get(Games.class, sc.nextInt());
        System.out.print("ID Género: ");
        Genres gen = s.get(Genres.class, sc.nextInt());

        if (g != null && gen != null) {
            // Actualizamos ambas listas para coherencia
            gen.getJuegos().add(g);
            g.getGeneros().add(gen);

            s.merge(gen); // Genres es el dueño de la relación (@JoinTable)
            tx.commit();
            System.out.println("Género vinculado al juego.");
        } else {
            System.out.println("ID incorrecto.");
            tx.rollback();
        }
    }

    private static void removeGenreFromGame(Session s) {
        Transaction tx = s.beginTransaction();
        System.out.print("ID Juego: ");
        Games g = s.get(Games.class, sc.nextInt());
        System.out.print("ID Género: ");
        Genres gen = s.get(Genres.class, sc.nextInt());

        if (g != null && gen != null) {
            gen.getJuegos().remove(g);
            g.getGeneros().remove(gen);

            s.merge(gen);
            tx.commit();
            System.out.println("Vinculación eliminada (el juego y el género siguen existiendo).");
        } else {
            tx.rollback();
        }
    }

    private static void addPlatformToGame(Session s) {
        Transaction tx = s.beginTransaction();
        System.out.print("ID Juego: ");
        Games g = s.get(Games.class, sc.nextInt());
        System.out.print("ID Plataforma: ");
        Platforms p = s.get(Platforms.class, sc.nextInt());

        if (g != null && p != null) {
            // Relación ManyToMany directa
            p.getJuegos().add(g);
            g.getPlataformas().add(p);

            s.merge(p); // Platforms es el dueño de la relación (@JoinTable)
            tx.commit();
            System.out.println("Plataforma añadida al juego.");
        } else {
            tx.rollback();
        }
    }

    private static void deletePlatformAndGames(Session s) {
        // REQUISITO: "Borrar una plataforma, y con ello TODOS los juegos que tenga asociados"
        Transaction tx = s.beginTransaction();
        System.out.print("ID Plataforma a borrar: ");
        Platforms p = s.get(Platforms.class, sc.nextInt());

        if (p != null) {
            // Como usamos una lista Java, hacemos una copia para evitar errores de concurrencia al borrar
            List<Games> juegosAsociados = List.copyOf(p.getJuegos());

            for (Games juego : juegosAsociados) {
                // Borramos el juego explícitamente.
                // Hibernate se encargará de borrar las referencias en las tablas intermedias (gamegenres, gameplatforms, logros)
                s.remove(juego);
            }
            // Finalmente borramos la plataforma
            s.remove(p);

            tx.commit();
            System.out.println("Plataforma y " + juegosAsociados.size() + " juegos asociados eliminados.");
        } else {
            System.out.println("Plataforma no encontrada.");
            tx.rollback();
        }
    }

    // ------------------- CONSULTAS HQL -------------------

    private static void hqlLogrosPorNombreJuego(Session s) {
        System.out.print("Introduce inicio del nombre del juego: ");
        String name = sc.nextLine();

        String hql = "SELECT a FROM Achivements a WHERE a.juego.name LIKE :name";
        Query<Achivements> q = s.createQuery(hql, Achivements.class);
        q.setParameter("name", name + "%");

        List<Achivements> lista = q.list();
        System.out.println("--- Resultados ---");
        for(Achivements a : lista) {
            System.out.println("Logro: " + a.getName() + " | Juego: " + a.getJuego().getName());
        }
    }

    private static void hqlJuegosAntesDeAnio(Session s) {
        System.out.print("Introduce año límite: ");
        int anio = sc.nextInt();

        // Función year() de HQL
        String hql = "FROM Games g WHERE year(g.releasedDate) < :anio";
        Query<Games> q = s.createQuery(hql, Games.class);
        q.setParameter("anio", anio);

        for(Games g : q.list()) {
            System.out.println("Juego: " + g.getName() + " (" + g.getReleasedDate() + ")");
        }
    }

    private static void hqlJuegosPorPlataforma(Session s) {
        System.out.print("Nombre exacto de la Plataforma: ");
        String pName = sc.nextLine();

        // Join con la lista de plataformas dentro de Games
        String hql = "SELECT g FROM Games g JOIN g.plataformas p WHERE p.name = :pName";
        Query<Games> q = s.createQuery(hql, Games.class);
        q.setParameter("pName", pName);

        List<Games> list = q.list();
        if(list.isEmpty()) System.out.println("No se encontraron juegos.");
        for(Games g : list) {
            System.out.println("- " + g.getName());
        }
    }

    private static void hqlGeneros1996(Session s) {
        // Distinct para que no salgan repetidos
        String hql = "SELECT DISTINCT gen.name FROM Genres gen JOIN gen.juegos g WHERE year(g.releasedDate) = 1996";
        Query<String> q = s.createQuery(hql, String.class);

        System.out.println("--- Géneros con juegos en 1996 ---");
        for(String nombre : q.list()) {
            System.out.println("- " + nombre);
        }
    }

    private static void hqlCountJuegosGenero1996(Session s) {
        // Agrupación y conteo
        String hql = "SELECT gen.name, count(g) FROM Genres gen JOIN gen.juegos g " +
                "WHERE year(g.releasedDate) = 1996 GROUP BY gen.name";
        Query<Object[]> q = s.createQuery(hql, Object[].class);

        System.out.println("--- Conteo por género (1996) ---");
        for(Object[] fila : q.list()) {
            System.out.println("Género: " + fila[0] + " | Cantidad: " + fila[1]);
        }
    }
}