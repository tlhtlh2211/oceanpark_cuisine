package com.ocp.cuisine.serviceimplement;

import com.ocp.cuisine.DAO.ProductDao;
import com.ocp.cuisine.JWT.JwtFilter;
import com.ocp.cuisine.POJO.Category;
import com.ocp.cuisine.POJO.Product;
import com.ocp.cuisine.constant.CuisineConstants;
import com.ocp.cuisine.service.ProductService;
import com.ocp.cuisine.util.CuisineUtils;
import com.ocp.cuisine.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImplement implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                if (validateProductMap(requestMap, false)){
                    productDao.save(getProductFromMap(requestMap, false));
                    return CuisineUtils.getResponseEntity(CuisineConstants.PRODUCT_ADDED, HttpStatus.OK);
                }
                return CuisineUtils.getResponseEntity(CuisineConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
            else{
                return CuisineUtils.getResponseEntity(CuisineConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try{
            return new ResponseEntity<List<ProductWrapper>>(productDao.getAllProduct(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                if (validateProductMap(requestMap, true)){
                    Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()){
                        Product product = getProductFromMap(requestMap, true);
                        product.setStatus(optional.get().getStatus());
                        productDao.save(product);
                        return CuisineUtils.getResponseEntity(CuisineConstants.PRODUCT_UPDATED, HttpStatus.OK);
                    }
                    else return CuisineUtils.getResponseEntity(CuisineConstants.PRODUCT_NOT_EXIST, HttpStatus.OK);
                }
                else return CuisineUtils.getResponseEntity(CuisineConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
            else return CuisineUtils.getResponseEntity(CuisineConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            if (jwtFilter.isAdmin()){
                Optional optional = productDao.findById(id);
                if (!optional.isEmpty()){
                    productDao.deleteById(id);
                    return CuisineUtils.getResponseEntity(CuisineConstants.PRODUCT_DELETED, HttpStatus.OK);
                }
                else return CuisineUtils.getResponseEntity(CuisineConstants.PRODUCT_NOT_EXIST, HttpStatus.OK);
            }
            else return CuisineUtils.getResponseEntity(CuisineConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()){
                    productDao.updateProductStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return CuisineUtils.getResponseEntity(CuisineConstants.UPDATE_SUCCESS, HttpStatus.OK);
                }
                else return CuisineUtils.getResponseEntity(CuisineConstants.PRODUCT_NOT_EXIST, HttpStatus.OK);
            }
            else return CuisineUtils.getResponseEntity(CuisineConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return CuisineUtils.getResponseEntity(CuisineConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getProductByCategoryId(Integer id) {
        try{
            if (!productDao.getProductByCategoryId(id).isEmpty()){
                return new ResponseEntity<>(productDao.getProductByCategoryId(id),HttpStatus.OK);
            }
            else return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        try{
            return new ResponseEntity<>(productDao.getProductById(id),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<ProductWrapper>(new ProductWrapper(), HttpStatus.BAD_REQUEST);
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Product product = new Product();
        if (isAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));
        }
        else{
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        product.setStatus("true");

        return product;
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")){
            if (requestMap.containsKey("id") && validateId){
                return true;
            }
            else return !validateId;
        }
        return false;
    }
}
