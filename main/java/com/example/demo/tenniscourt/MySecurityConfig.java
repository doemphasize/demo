package com.example.demo.tenniscourt;



import com.example.demo.tenniscourt.Security.Handler.ImmocAuthenticationLoginOutHandler;
import com.example.demo.tenniscourt.Security.Handler.MyAccessDeniedHandler;
import com.example.demo.tenniscourt.Security.Handler.MyAuthenticationFailHandler;
import com.example.demo.tenniscourt.Security.Handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    MyAuthenticationFailHandler myAuthenticationFailHandler;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
   private UserDetailsService myUserDetailService;
    @Autowired
    private ImmocAuthenticationLoginOutHandler logoutSuccessHandler;
    @Autowired
    private AccessDeniedHandler myAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http     //针对iframe标签的允许配置
                    .headers()
                    .frameOptions().sameOrigin()
                    .and()
                    .formLogin()
                    //登录成功处理器
                    .successHandler(myAuthenticationSuccessHandler)
                    //失败
                    .failureHandler(myAuthenticationFailHandler)
                    //这个是登录成功之后的页面
               //     .defaultSuccessUrl("/login.html")
                    /**
                     * 这个是处理登录请求的，必须是post，
                     * 且表单属性是username和password
                     */
                   // .loginProcessingUrl("/login")
                    .and()
                    .userDetailsService(myUserDetailService)
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .logout()
                    .logoutSuccessUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .addLogoutHandler(new LogoutHandler() {
                        @Override
                        public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

                        }
                    })
                    .logoutSuccessHandler(logoutSuccessHandler)
                    . and()
                    .exceptionHandling()
                    .accessDeniedHandler(myAccessDeniedHandler)
                    .and()
                    .cors()
                    .and()
                    .csrf().disable()
                    ;

    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder());
    }
}

//这是我的sringsecurity配置


//
//@Configuration
//public class MySecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    MyAuthenticationFailHandler myAuthenticationFailHandler;
//    @Autowired
//    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
//    @Autowired
//    private UserDetailsService myUserDetailService;
//    @Autowired
//    private ImmocAuthenticationLoginOutHandler logoutSuccessHandler;
//    @Autowired
//    private ValidateCodeFilter validateCodeFilter;
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        validateCodeFilter.setAuthenticationFailureHandler(new MyAuthenticationFailHandler());
//        http     .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                //针对iframe标签的允许配置
//                .headers()
//                .frameOptions().sameOrigin()
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/login")
//                .successHandler(myAuthenticationSuccessHandler)
//                .failureHandler(myAuthenticationFailHandler)
//                .and()
//                .userDetailsService(myUserDetailService)
//                .authorizeRequests()
//                .antMatchers("/register").permitAll()
//                .antMatchers("/pdf").permitAll()
//                .antMatchers("/home/**").permitAll()
//                .antMatchers("/picture/**").permitAll()
//                .antMatchers("/search/**").permitAll()
//                .antMatchers("/code/imagecode").permitAll()
//                .antMatchers("/xdx/**").permitAll()
//                .antMatchers("/websocket/**").permitAll()
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .logout()
//                .logoutSuccessUrl("/logout")
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .addLogoutHandler(new LogoutHandler() {
//                    @Override
//                    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
//
//                    }
//                })
//                .logoutSuccessHandler(logoutSuccessHandler)
//                . and()
//                .cors()
//                .and()
//                .csrf().disable()
//
//        ;
//
//    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder());
//    }
//}