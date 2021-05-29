package tests;

public class Main {
    public static void main(String[] args) {
//        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .configure().build();
//        try (
//            SessionFactory sessionFactory = new MetadataSources(serviceRegistry)
//                    .addAnnotatedClass(WareHouse.class)
//                    .addAnnotatedClass(Product.class)
//                    .addAnnotatedClass(Sale.class)
//                    .addAnnotatedClass(SaleItem.class)
//                    .buildMetadata().buildSessionFactory();
//            Session session = sessionFactory.openSession()
//                ) {
//            session.beginTransaction();
//            WareHouse wareHouse = session.load(WareHouse.class, 1L);
//            Product product = new Product();
//            product.setName("testProduct");
//            product.setVendorCode(12345);
//            product.setWareHouse(wareHouse);
//            session.save(product);
//
//            SaleItem saleItem = new SaleItem();
//            saleItem.setProductId(product.getId());
//            saleItem.setPricePerOne(5500);
//            product.setLastSalePrice(5500);
//            session.save(product);
//            saleItem.setCount(4);
//
//            Sale sale = new Sale();
//            sale.setWareHouse(wareHouse);
//            sale.setSaleItems(Set.of(saleItem));
//            session.save(sale);

//            Sale sale = session.load(Sale.class, 1L);
//            System.out.println(sale.getSaleItems());
//            System.out.println(sale.getCreateDate());
//            System.out.println(sale.getWareHouse());
//
//            session.getTransaction().commit();
        }

    }

