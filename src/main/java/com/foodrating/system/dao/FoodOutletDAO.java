package com.foodrating.system.dao;

import com.foodrating.system.models.Rating;
import org.hibernate.Session;
import com.foodrating.system.utils.HibernateUtil;
import com.foodrating.system.models.FoodOutlet;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FoodOutletDAO {

    public void deleteFoodOutlet(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            FoodOutlet outlet = session.get(FoodOutlet.class, id);
            if (outlet != null) {
                session.delete(outlet);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
    public List<FoodOutlet> getSortedOutletsByRating() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM FoodOutlet ORDER BY averageRating DESC", FoodOutlet.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch sorted outlets: " + e.getMessage(), e);
        }
    }

    public static boolean save(FoodOutlet outlet) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(outlet);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public static List<FoodOutlet> getAllFoodOutlets() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            List<FoodOutlet> outlets = session.createQuery("FROM FoodOutlet", FoodOutlet.class).getResultList();
            session.getTransaction().commit();
            return outlets;
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return List.of(); // Boş liste döndür
        }
    }
    public List<Long> getAllOutletIds() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Long> ids = new ArrayList<>();
        try {
            ids = session.createQuery("SELECT id FROM FoodOutlet", Long.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return ids;
    }
    public void updateOutlet(FoodOutlet outlet) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            FoodOutlet existingOutlet = session.get(FoodOutlet.class, outlet.getId());
            if (existingOutlet != null) {
                existingOutlet.setName(outlet.getName());
                existingOutlet.setType(outlet.getType());
                existingOutlet.setAddress(outlet.getAddress());
                session.update(existingOutlet);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static final Logger logger = Logger.getLogger(FoodOutletDAO.class.getName());

    public void addRating(Rating rating) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            FoodOutlet outlet = session.get(FoodOutlet.class, rating.getFoodOutlet().getId());
            if (outlet == null) {
                throw new IllegalArgumentException("Food Outlet not found for ID: " + rating.getFoodOutlet().getId());
            }

            outlet.addRating(rating); // Rating nesnesini ekle
            session.save(rating);

            // Ortalama puanı güncelle
            double newAverage = outlet.getRatings().stream()
                    .mapToInt(Rating::getRatingValue)
                    .average()
                    .orElse(0.0);
            outlet.setAverageRating(newAverage);
            session.update(outlet);

            transaction.commit();
        } catch (Exception e) {
            logger.severe("Error while adding rating: " + e.getMessage());
        }
    }


    private double calculateAverage(List<Rating> ratings) {
        if (ratings.isEmpty()) {
            return 0.0;
        }

        double sum = 0;
        for (Rating r : ratings) {
            sum += r.getRatingValue();
        }

        return sum / ratings.size();
    }
}
