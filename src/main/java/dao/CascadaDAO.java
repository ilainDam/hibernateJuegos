package dao;

import clasesMapear.Achievements;
import clasesMapear.Games;
import clasesMapear.Genres;
import clasesMapear.Platforms;
import org.example.Conexion;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class CascadaDAO {
    public void addAchievement(int gameId, String nombre, String descripcion) {
        Session session = Conexion.getInstance().getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Games game = session.find(Games.class, gameId);
            Achievements achievement = new Achievements();
            achievement.setName(nombre);
            achievement.setJuego(game);
            achievement.setDescription(descripcion);
            session.persist(achievement);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void removeAchievement(int achievementId) {
        Session session = Conexion.getInstance().getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Achievements achievement = session.find(Achievements.class, achievementId);
            session.remove(achievement);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void addGenreToGame(int gameId, int genreId) {
        Session session = Conexion.getInstance().getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Games game = session.find(Games.class, gameId);
            Genres genre = session.find(Genres.class, genreId);
            genre.getJuegos().add(game);
            session.merge(genre);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void removeGenreFromGame(int gameId, int genreId) {
        Session session = Conexion.getInstance().getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Genres genre = session.find(Genres.class, genreId);
            genre.getJuegos().removeIf(g -> g.getId().equals(gameId));
            session.merge(genre);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void addPlatformToGame(int gameId, int platformId) {
        Session session = Conexion.getInstance().getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Games game = session.find(Games.class, gameId);
            Platforms platform = session.find(Platforms.class, platformId);
            platform.getJuegos().add(game);
            session.merge(platform);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void removePlatformFromGame(int gameId, int platformId) {
        Session session = Conexion.getInstance().getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Platforms platform = session.find(Platforms.class, platformId);
            platform.getJuegos().removeIf(g -> g.getId().equals(gameId));
            session.merge(platform);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void deletePlatformAndGames(int platformId) {
        Session session = Conexion.getInstance().getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Platforms platform = session.find(Platforms.class, platformId);
            session.remove(platform);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}