package com.example.demo.tenniscourt.Security;



import com.example.demo.tenniscourt.pojo.MyUserDetails;
import com.example.demo.tenniscourt.pojo.Role;
import com.example.demo.tenniscourt.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordencoder;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String password="123456"; //这里你改成从数据库查

        log.info("查询的对象：");
            //必须要返回一个实现了UserDetails接口的类
            MyUserDetails myUserDetails = new MyUserDetails(s,passwordencoder.encode(password));
            return myUserDetails;
    }
}
