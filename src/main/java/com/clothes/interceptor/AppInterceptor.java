package com.clothes.interceptor;

import com.clothes.repository.AboutDAO;
import com.clothes.repository.service.AboutService;
import com.clothes.repository.service.BlogService;
import com.clothes.repository.service.ProductService;
import com.clothes.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AppInterceptor implements HandlerInterceptor {
    @Autowired
    BlogService blogService;
    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;
    @Autowired
    AboutDAO dao;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null){
            modelAndView.addObject("blogs", blogService.findTop3ByOrderBySeen());
            modelAndView.addObject("cart", cartService);
            modelAndView.addObject("about", dao.getById(1));
            modelAndView.addObject("count",cartService.getCount());
            modelAndView.addObject("amount",cartService.getAmount());
            modelAndView.addObject("specials",productService.findTop5Special());
        }
    }
}
