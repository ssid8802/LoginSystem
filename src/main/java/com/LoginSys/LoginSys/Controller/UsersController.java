package com.LoginSys.LoginSys.Controller;

import com.LoginSys.LoginSys.Model.UsersModel;
import com.LoginSys.LoginSys.service.PhoneVerificationService;
import com.LoginSys.LoginSys.service.UsersService;
import com.LoginSys.LoginSys.service.VerificationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class UsersController
{

    @Autowired
    private final UsersService usersService;

    @Autowired
    private PhoneVerificationService phoneSmsService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model)
    {
        model.addAttribute("registerRequest",new UsersModel());
        return "RegisterPage";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model)
    {
        model.addAttribute("loginRequest",new UsersModel());
        return "LoginPage";
    }

    @GetMapping("/update")
    public String updateProfile(Model model)
    {
        model.addAttribute("updateRequest",new UsersModel());
        return "UpdatePage";
    }


    @PostMapping("/update")
    public String update(@ModelAttribute UsersModel usersModel)
    {

        UsersModel updateUser =usersService.updateUser(usersModel.getId(),usersModel.getLogin(),usersModel.getPassword(),usersModel.getEmail());
        return updateUser==null?"ErrorPage":"UpdateSuccessful";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UsersModel usersModel)
    {

        UsersModel registerUser =usersService.registerUser(usersModel.getId(),usersModel.getLogin(),usersModel.getPassword(),usersModel.getEmail());
        return registerUser==null?"ErrorPage":"Successful";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UsersModel usersModel, Model model)
    {

        UsersModel authenticated = usersService.authenticate(usersModel.getLogin(), usersModel.getPassword());
        if (authenticated != null)
        {
            model.addAttribute("userLogin",authenticated.getLogin());
            return "PersonalPage";
        }
        else return "ErrorPage";
    }


    @PostMapping("/sendOtp")
    public ResponseEntity<String> sendOtp(@RequestParam("phone") String phone)
    {

        VerificationResult result= phoneSmsService.startVerification(phone);

        if(((VerificationResult) result).isValid())
        {
            return new ResponseEntity<>("Otp Sent..",HttpStatus.OK);
        }

        return new ResponseEntity<>("Otp failed to sent..",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam("phone") String phone, @RequestParam("otp") String otp)
    {

        VerificationResult result= phoneSmsService.checkVerification(phone,otp);
        if(result.isValid())
        {
            return "Successful";
        }

        return "ErrorPage";
    }


}
