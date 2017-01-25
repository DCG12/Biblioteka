import java.util.List;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManageLlibre {

    private static SessionFactory factory;
    public static void main(String[] args) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        ManageLlibre ML = new ManageLlibre();
    }
    public Integer addLlibre(String títol, int nombre_exemplars, String editorial, int nombre_pagines, int any_edicio) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer llibreID = null;
        try {
            tx = session.beginTransaction();
            Llibre llibre = new Llibre(títol, nombre_exemplars, editorial, nombre_pagines, any_edicio);
            llibreID = (Integer) session.save(llibre);
            tx.commit();
    }catch (HibernateException e) {
        if (tx!=null) tx.rollback();
        e.printStackTrace();
    }finally {
        session.close();
    }
        return null;
    }
    public void listLlibre( ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List llibre = session.createQuery("FROM Llibre").list();
            for (Iterator iterator =
                 llibre.iterator(); iterator.hasNext();){
                Llibre libro = (Llibre) iterator.next();
                System.out.print("Id: " + libro.getLlibre_id());
                System.out.print("Titol: " + libro.getTítol());
                System.out.print("Editorial: " + libro.getEditorial());
                System.out.println("Any d'edició: " + libro.getAny_edicio());
                System.out.println(" numero de pagines: " + libro.getNombre_pagines());
                System.out.println(" numero d'exemplars: " + libro.getNombre_exemplars());
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void updateLlibre(int llibre_id, String títol, int nombre_exemplars, String editorial, int nombre_pagines, int any_edicio){

        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Llibre llibre = (Llibre)session.get(Llibre.class, llibre_id);
            llibre.setTítol(títol);
            llibre.setNombre_exemplars(nombre_exemplars);
            llibre.setEditorial( editorial );
            llibre.setNumPaginas( numPaginas );
            llibre.setAnyoEdicion( anyoEdicion );
            session.update(llibre);
            tx.commit();
        }catch (HibernateException e) {if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

}
