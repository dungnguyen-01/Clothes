package com.clothes.repository.service;

import com.clothes.repository.*;
import com.clothes.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductDAO dao;
    @Autowired
    SizeDAO sizeDAO;
    @Autowired
    DetailProductSizeDAO detailProductSizeDAO;
    @Autowired
    ImageDAO  imageDAO;
    @Autowired
    ImageService imageService;
    @Autowired
    UploadService uploadService;
    @Autowired
    ColorDAO colorDAO;
    @Autowired
    ProductColorDAO productColorDAO;


    @Override
    public List<Product> findAll() {
        return dao.findAll();
    }

    @Transactional
    @Override
    public void create(Product product, List<String> sizeIds, List<String> colorIds, List<MultipartFile> imageFileDesc) {
        if (!imageFileDesc.get(0).isEmpty()) {
            List<Image> images = imageService.findByProductId(product.getPid());
            List<String> photos = uploadService.saveList(imageFileDesc);
            for (String photo : photos) {
                System.out.println(photo);
                Image image = new Image(photo, product);
                images.add(image);
            }
            imageService.create(images);
        }

        List<DetailProductSize>detailProductSizes = sizeIds.stream().map(sid-> {
            Size  size = sizeDAO.getById(Integer.parseInt(sid));
            DetailProductSize detailProductSize = new DetailProductSize(null,size,product);
            return  detailProductSize;
        }).toList();

        List<ProductColor> productColors = colorIds.stream().map(cid -> {
            Color color = colorDAO.getById(Integer.parseInt(cid));
            ProductColor productColor = new ProductColor(null,color,product);
            return  productColor;
        }).toList();

        productColorDAO.saveAll(productColors);
        detailProductSizeDAO.saveAll(detailProductSizes);
        product.setDetailProductSizes(detailProductSizes);
        product.setProductColors(productColors);
        dao.save(product);

    }

    @Transactional
    @Override
    public void update(Product product, List<String> sizeIds, List<String> colorIds, List<MultipartFile> imageFileDesc) {

          if (!imageFileDesc.get(0).isEmpty()) {
              List<Image> images = imageService.findByProductId(product.getPid());
              List<String> photos = uploadService.saveList(imageFileDesc);
              for (String photo : photos) {
                  System.out.println(photo);
                  Image image = new Image(photo, product);
                  images.add(image);
              }
              imageService.create(images);
          }

        detailProductSizeDAO.deleteAll(dao.getById(product.getPid()).getDetailProductSizes());
        productColorDAO.deleteAll(dao.getById(product.getPid()).getProductColors());
        dao.save(product);

        if (!sizeIds.isEmpty() && !colorIds.isEmpty()){
            List<DetailProductSize>detailProductSizes = sizeIds.stream().map(sid-> {
                Size  size = sizeDAO.getById(Integer.parseInt(sid));
                DetailProductSize detailProductSize = new DetailProductSize(null,size,product);
                return  detailProductSize;
            }).toList();

            List<ProductColor> productColors = colorIds.stream().map(cid ->{
                Color color = colorDAO.getById(Integer.parseInt(cid));
                ProductColor productColor = new ProductColor(null,color,product);
                return  productColor;
            }).toList();

            productColorDAO.saveAll(productColors);
            detailProductSizeDAO.saveAll(detailProductSizes);

            product.setProductColors(productColors);
            product.setDetailProductSizes(detailProductSizes);
        }
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        Product product = dao.getById(id);
        imageDAO.deleteAll(dao.getById(product.getPid()).getImages());
        uploadService.deleteList(product.getImages().stream().map(Image::getIname).toList());
        uploadService.delete(product.getImage());
        detailProductSizeDAO.deleteAll(dao.getById(product.getPid()).getDetailProductSizes());
        productColorDAO.deleteAll(dao.getById(product.getPid()).getProductColors());
        dao.deleteById(id);
    }


    @Override
    public Integer checkProductName(String pname) {
        return dao.checkProductName(pname);
    }


    @Override
    public Product getById(Integer id) {
        return dao.getById(id);
    }


    // client
    @Override
    public Page<Product> findAllProducrPage(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public Page<Product> findBySpecialIsTrue(Pageable pageable) {
        return dao.findBySpecialIsTrueOrderByCreateDateDesc(pageable);
    }

    @Override
    public Page<Product> findByDiscount(Pageable pageable) {
        return dao.findByDiscount(pageable);
    }

    @Override
    public Page<Product> findByKeywords(String keywords, Pageable pageable) {
        if (keywords != null){
            return dao.findByKeywords("%"+keywords+"%",pageable);
        }
        return dao.findAll(pageable);
    }

    @Override
    public Page<Product> findByCategory(Integer id, Pageable pageable) {
        return dao.findByCategory(id,pageable);
    }

    @Override
    public List<Product> findByCategorys(Integer id) {
        return dao.findByCategorys(id);
    }

    @Override
    public List<Product> findTop5All() {
        return dao.findTop5ByOrderByCreateDateDesc();
    }

    @Override
    public List<Product> findTop5Men(Integer gender) {
        return dao.findTop5ByGenderOrderByCreateDateDesc(gender);
    }

    @Override
    public List<Product> findTop5Discount() {
        return dao.findTop5ByDiscountGreaterThanOrderByCreateDateDesc(0.2);
    }

    @Override
    public List<Product> findTop5Special() {
        return dao.findTop5BySpecialIsTrueOrderByCreateDateDesc();
    }

    @Override
    public List<Product> findTop5New() {
        return dao.findTop5ByOrderByCreateDateDesc();
    }

    @Override
    public void updateSeen(Product product) {
        dao.save(product);
    }

    @Override
    public List<Product> findTop4Men(Integer gender) {
        return dao.findTop4ByGenderOrderByCreateDateDesc(gender);
    }

    @Override
    public Page<Product> findById(Integer id,Pageable pageable) {
        return dao.findByPid(id,pageable);
    }

    @Override
    public Page<Product> findByBestSeller(Pageable pageable) {
        List<Integer> ids= dao.findByBestSellerIds(pageable);
        return dao.findByIds(ids,pageable);
    }
}


