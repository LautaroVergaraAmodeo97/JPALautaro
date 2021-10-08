package main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidades.Articulo;
import entidades.Categoria;
import entidades.Cliente;
import entidades.DetalleFactura;
import entidades.Domicilio;
import entidades.Factura;

public class PersistenceApp {

	public static void main(String [] args ) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceAppPU");
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			
			Factura factura1 = new Factura();
			
			factura1.setNumero(12);
			factura1.setFecha("10/08/2020");
			
			Domicilio dom = new Domicilio("San Martín",1222);
			Cliente cliente = new Cliente("Pablo","Muñoz",123456789);
			cliente.setDomicilio(dom);
			dom.setCliente(cliente);
			
			factura1.setCliente(cliente);
			
			Categoria perecederos = new Categoria ("Perecederos");
			Categoria lacteos = new Categoria ("Lacteos");
			Categoria limpieza = new Categoria ("Limpieza");
			
			
			Articulo art1 = new Articulo(200,"Yogurt ser",20);
			Articulo art2 = new Articulo(300,"detergente magistral",80);
			
			art1.getCategorias().add(perecederos);
			art1.getCategorias().add(lacteos);
			lacteos.getArticulos().add(art1);
			perecederos.getArticulos().add(art1);
			
			art2.getCategorias().add(limpieza);
			limpieza.getArticulos().add(art2);
			
			DetalleFactura det1 = new DetalleFactura();
			
			det1.setArticulo(art1);
			det1.setCantidad(2);
			det1.setSubtotal(40);
			
			art1.getDetalleFacturas().add(det1);
			factura1.getDetalles().add(det1);
			det1.setFactura(factura1);
			
			
			factura1.setTotal(120);
			
			
			em.persist(factura1);
			
			
			
			em.flush();
			
			em.getTransaction().commit();
		}catch(Exception e) {
			
			em.getTransaction().rollback();
			
		}
	
		em.close();
		emf.close();
		
		
	}
	
}
