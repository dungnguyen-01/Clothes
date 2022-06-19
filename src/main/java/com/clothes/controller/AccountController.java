package com.clothes.controller;



import com.clothes.repository.Account;
import com.clothes.repository.RoleDAO;
import com.clothes.repository.service.AccountService;
import com.clothes.security.UserDetailImpl;
import com.clothes.security.UserDetailServiceImpl;
import com.clothes.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
public class AccountController {

    @Autowired
    RoleDAO roleDAO;
    @Autowired
    AccountService accountService;



    @RequestMapping("/account/login")
    public  String login(Model model){

        return "account/login";
    }

    @RequestMapping("/account/login/failure")
    public String securityLoginFaile(Model model) {
        model.addAttribute("message","Thông tin đăng nhập sai!");
        return "forward:/account/login";
    }

    @GetMapping("/account/register")
    public  String registerForm(@ModelAttribute("account") Account account){
        return "account/register";
    }


    @Autowired
    BCryptPasswordEncoder pe;
    @Autowired
    MailService mailService;

    @PostMapping ("/account/register")
    public  String register(Model model, @Validated @ModelAttribute("account") Account account,
                            BindingResult result,
                            @RequestParam("confirmPass") String confirm
                            ) {
        if (result.hasErrors()) {
            model.addAttribute("message", "Có lỗi! kiểm tra thông tin bên dưới.");
            return "account/register";
        } else {

            if (!confirm.equals(account.getPassword())) {
                model.addAttribute("message", "Mật khẩu phải giống nhau.");
                return "account/register";

            } else if (accountService.findByEmail(account.getEmail())) {
                model.addAttribute("message", "Email '" + account.getEmail() + "' đã tồn tại.");
                return "account/register";
            }
            else {
                account.setRole(roleDAO.getById(4));
                String pw = pe.encode(account.getPassword());
                account.setPassword(pw);
                System.out.println(account);
                accountService.create(account);

                mailService.sendActiveAccount(account);
                model.addAttribute("message", "Tạo thành công, check mail kích hoạt tài khoản.");
                return "account/login";
            }
        }
    }

    @GetMapping("/account/active/{id}")
    public String activeAccount(Model model, @PathVariable("id") Integer id){
        Account account = accountService.getById(id);
        if (account.isActivated() == false) {
            account.setActivated(true);
            accountService.create(account);
            model.addAttribute("nofi","nofi");
            return "/account/login";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/account/edit-profile")
    public String edit_profileFrom(Model model, Authentication auth){
        UserDetailImpl userDetail = (UserDetailImpl) auth.getPrincipal();
        model.addAttribute("account", userDetail.getAccount());
        return "/account/edit-profile";
    }

    @PostMapping ("/account/edit-profile")
    public  String edit_profile(Model model,
                                @ModelAttribute("account") Account account,
                                Authentication auth) {
        accountService.update(account);
        model.addAttribute("message", "Cập nhật tài khoản thành công.");
        //Cập nhật lại hệ thống
        UserDetailImpl userDetail = (UserDetailImpl) auth.getPrincipal();
        userDetail.setAccount(account);
        return "/account/edit-profile";
    }


    @GetMapping("/account/change-password")
    public String change_passwordFrom(Model model, Authentication auth){
        UserDetailImpl userDetail = (UserDetailImpl) auth.getPrincipal();
        model.addAttribute("account", userDetail.getAccount());
        return "/account/change-password";
    }

    @PostMapping("/account/change-password")
    public String change_password(Model model,
              Authentication auth,
              @RequestParam("password") String password,
              @RequestParam("newpass") String newpass,
              @RequestParam("confirm") String confirm){

        if (!newpass.equals(confirm)){
                model.addAttribute("message","Xác nhận mật khẩu không giống nhau.");
        }else {
            UserDetailImpl userDetail = (UserDetailImpl) auth.getPrincipal();
            Account account = userDetail.getAccount();
            if(!pe.matches(password, account.getPassword())){
                model.addAttribute("message","Sai mật khẩu.");
            }else {
                account.setPassword(pe.encode(newpass));
                accountService.update(account);
                model.addAttribute("nofi","nofi");
                model.addAttribute("message","Đổi thành công mật khẩu.");
            }
        }
        return "/account/change-password";
    }

    /*
    * forgot password
    * */

    @GetMapping("/account/forgot-password")
    public  String forgotPasswordFrom(Model model){
        return "account/forgot-password";
    }

    @PostMapping("/account/forgot-password")
    public  String forgotPassword(Model model,
                                  @RequestParam("email") String email){

        if (!accountService.existsByEmail(email)){
            model.addAttribute("message",email +" sai hoặc chưa tồn tại!");
        } else {
            Account  account = accountService.getByEmail(email);
            model.addAttribute("message","Vui lòng check email để nhận token.");
            String token = Integer.toHexString(account.getPassword().hashCode());

            mailService.sendPasswordToken(token, email);
            System.out.println("mat kahu la: "+token);
            return "account/reset-password";
        }
        return "account/forgot-password";
    }


    /*
    * reset password
    * */

    @GetMapping("/account/reset-password")
    public  String resetPasswordForm(Model model){
        return "account/reset-password";
    }

    @PostMapping("/account/reset-password")
    public  String resetPassword(Model model,
                                     Authentication auth,
                                     @RequestParam("email") String email,
                                     @RequestParam("token") String token,
                                     @RequestParam("newpass") String newpass,
                                     @RequestParam("confirm") String confirm){
        if (!newpass.equals(confirm)){
            model.addAttribute("message","Xác nhận mật khẩu không giống nhau.");
        }
        else if (!accountService.existsByEmail(email)){
            model.addAttribute("message",email+" không chính xác.");
        }
        else {
            Account account = accountService.getByEmail(email);
            String currentToken = Integer.toHexString(account.getPassword().hashCode());

            if(!token.equalsIgnoreCase(currentToken)){
                model.addAttribute("message","Sai token code.");
            }else {
                account.setPassword(pe.encode(newpass));
                accountService.update(account);
                model.addAttribute("nofi","nofi");
                model.addAttribute("message","Đổi thành công mật khẩu.");
                return "forward:/account/login";
            }
        }
        return "account/reset-password";
    }

    @Autowired
    UserDetailServiceImpl userDetailServiceImpl;

    @RequestMapping("/oauth2/login/success")
    public String oauth2LoginSuccess(Model model, OAuth2AuthenticationToken auth2) {
        OAuth2User user = auth2.getPrincipal();
//		Account account = new Account("113","", user.getAttribute("name"), "",user.getAttribute("email"), "", "email.png", true, List.of());
//		dao.save(account);
        System.out.println(user.getName());
        System.out.println((String) user.getAttribute("email"));
        System.out.println((String) user.getAttribute("name"));

        userDetailServiceImpl.authenticate(auth2);
        model.addAttribute("nofi","nofi");
        return "redirect:/";
    }
}

// chua có them số lượng taij trang detail vẫn default là 1
