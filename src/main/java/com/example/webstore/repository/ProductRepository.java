package com.example.webstore.repository;

import com.example.webstore.models.Product;
import com.example.webstore.util.Util;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private final Util utils;

    public ProductRepository(Util utils) {
        this.utils = utils;
    }

    public List<Product> getProducts() {

        Connection connection = utils.getConnection();
        List<Product> list = new ArrayList<>();
        try (PreparedStatement statement = connection
                .prepareStatement("select * from public.products")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
                product.setDescription(resultSet.getString("description"));
                product.setIcon_base64(resultSet.getString("icon_base64"));
                list.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public Integer addProductIdForPerson(int person_id, int product_id) {
        Integer idOfPersonProduct = null;
        Connection connection = utils.getConnection();
        try (PreparedStatement statement = connection
                .prepareStatement("INSERT INTO public.person_products (person_id,product_id) values (?,?)")) {

            statement.setInt(1, person_id);
            statement.setInt(2, product_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement statement = connection
                .prepareStatement("select person_products_id from public.person_products where person_id=? and product_id=?")) {
            statement.setInt(1, person_id);
            statement.setInt(2, product_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idOfPersonProduct = resultSet.getInt("person_products_id");

                System.out.println(idOfPersonProduct);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idOfPersonProduct;
    }

    public List<Product> getProductsFromShoppingCart(int person_id) {
        Connection connection = utils.getConnection();
        List<Product> list = new ArrayList<>();
        List<Product> listOfProductsId = new ArrayList<>();
        try (PreparedStatement statement = connection
                .prepareStatement("select * from public.person_products where person_id = ?")) {
            statement.setInt(1, person_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("product_id"));
                listOfProductsId.add(product);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        try (PreparedStatement statement = connection.prepareStatement("select * from public.products where id in  (select product_id from public.person_products where person_id = ?)")) {
            statement.setInt(1, person_id);
            ResultSet resultSet = statement.executeQuery();
            int count = 0;

            while (resultSet.next()) {
                Product products = new Product();
                products.setId(resultSet.getInt("id"));

                products.setName(resultSet.getString("name"));
                products.setIcon_base64(resultSet.getString("icon_base64"));
                products.setPrice(resultSet.getInt("price"));
                for (int k = 0; k < listOfProductsId.size(); k++) {
                    if (products.getId().equals(listOfProductsId.get(k).getId())) {
                        count++;
                    }

                }

                products.setCount(count);
                System.out.println(products.getId() + "=" + count);
                count = 0;
                list.add(products);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(list);
        return list;
    }

    public void deleteProduct(Integer product_id, Integer person_id) {
        Integer idOfPersonProduct = 0;
        List<Product> list = new ArrayList<>();
        List<Product> listOfProductsId = new ArrayList<>();
        Connection connection = utils.getConnection();
        try (PreparedStatement statement = connection
                .prepareStatement("select person_products_id from public.person_products where  product_id = ? and  person_id =?")) {

            statement.setInt(1, product_id);
            statement.setInt(2, person_id);
           ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                idOfPersonProduct = resultSet.getInt("person_products_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (PreparedStatement statement = connection
                .prepareStatement("delete from public.person_products where  person_products_id = ?")) {
            statement.setInt(1, idOfPersonProduct);
            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



































//
//
//        List<Product> list = new ArrayList<>();
//        List<Product> listOfProductsId = new ArrayList<>();
//        Connection connection = utils.getConnection();
//        try (PreparedStatement statement = connection
//                     .prepareStatement("delete from public.person_products where  product_id = ? and  person_id =?")) {
//
//            statement.setInt(1, product_id);
//            statement.setInt(2, person_id);
//            statement.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//
//        try (PreparedStatement statement = connection
//                .prepareStatement("select * from public.person_products where person_id = ?")) {
//            statement.setInt(1, person_id);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                Product product = new Product();
//                resultSet.getInt("product_id");
//                product.setId(resultSet.getInt("product_id"));
//                listOfProductsId.add(product);
//            }
//            System.out.println(listOfProductsId);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        try (PreparedStatement statement = connection.prepareStatement("select * from public.products where id in  (select product_id from public.person_products where person_id = ?)")) {
//            statement.setInt(1, person_id);
//            ResultSet resultSet = statement.executeQuery();
//            int count=0;
//            while (resultSet.next()) {
//                Product products = new Product();
//                products.setId(resultSet.getInt("id"));
//                products.setName(resultSet.getString("name"));
//                products.setIcon_base64(resultSet.getString("icon_base64"));
//                products.setPrice(resultSet.getInt("price"));
//                for (int k = 0; k < listOfProductsId.size(); k++) {
//                    if (products.getId().equals(listOfProductsId.get(k).getId())){
//                        count++;
//                    }
//                }
//                System.out.println(count);
//
//                products.setCount(count);
//                System.out.println("From delete: "+products.getId()+"="+count);
//                count=0;
//                list.add(products);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(list);
//        return list;
//
//    }
//}
