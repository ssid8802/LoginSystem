package com.LoginSys.LoginSys.service;

import com.LoginSys.LoginSys.Model.UsersModel;
import com.LoginSys.LoginSys.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public  class UsersService
{

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository)
    {
        this.usersRepository = usersRepository;
    }
    public UsersModel registerUser(Integer id, String login, String password, String email)
    {

        if(login == null || password == null)
            return null;
        else{
            UsersModel usersModel = new UsersModel();
            usersModel.setLogin(login);

            String pass = password;
            int key = pass.length();
            char[] pass_arr = pass.toCharArray();
            for(int i = 0; i < key; ++ i)
            {
                pass_arr[i] += key;
            }
            pass = String.valueOf(pass_arr);

            usersModel.setPassword(pass);
            usersModel.setEmail(email);
            usersModel.setId(id);

            return usersRepository.save(usersModel);
        }


    }

    public UsersModel updateUser(Integer id,String login,String password,String email) {

        int key = password.length();
        String pass = password;
        char[] pass_arr = pass.toCharArray();

        for(int i = 0; i < key; ++ i)
        {
            pass_arr[i] += key;
        }

        pass = String.valueOf(pass_arr);
        password=pass;

        UsersModel usersModel = usersRepository.findByLoginAndPassword(login, password).orElse(null);
        if (usersModel != null)
        {
            usersModel.setLogin(login);
            usersModel.setPassword(password);
            usersModel.setEmail(email);
            return usersRepository.save(usersModel);
        }

        return usersModel;
    }


    public UsersModel authenticate(String login, String password)
    {
        int key = password.length();
        String pass = password;
        char[] pass_arr = pass.toCharArray();

        for(int i = 0; i < key; ++ i)
        {
            pass_arr[i] += key;
        }

        pass = String.valueOf(pass_arr);
        password=pass;

        UsersModel usersModel = usersRepository.findByLoginAndPassword(login, password).orElse(null);
        return usersModel;
    }


}
