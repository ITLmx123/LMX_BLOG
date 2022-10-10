package com.limengxiang.config;

import com.limengxiang.filter.JwtAuthenticationTokenFilter;
import com.limengxiang.handler.security.AccessDeniedHandlerImpl;
import com.limengxiang.handler.security.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

        @Autowired
        private AuthenticationEntryPointImpl authenticationEntryPoint;

        @Autowired
        private AccessDeniedHandlerImpl accessDeniedHandler;

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    //关闭csrf
                    .csrf().disable()
                    //不通过session获取securityContext
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    //对于登录接口允许匿名访问
                    .antMatchers("/login").anonymous()
                    //对于这些接口需要认证后才能访问
                    .antMatchers("/logout","/comment","/user/userInfo","/upload","/deleteImg").authenticated()
                    //除上面请求全部不需要认证即可访问
                    .anyRequest().permitAll();
            http.logout().disable();
            //配置认证失败和授权失败处理类
            http.exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler);
            //关闭默认的注销功能
            http.logout().disable();
            //把JwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链
            http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
            //允许跨域
            http.cors();
        }


//        @Bean
//        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//            http
//                    //关闭csrf
//                    .csrf().disable()
//                    //不通过session获取securityContext
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and()
//                    .authorizeRequests()
//                    //对于登录接口允许匿名访问
//                    .antMatchers("/login").anonymous()
//                    //对于这些接口需要认证后才能访问
//                    .antMatchers("/logout","/comment","/user/userInfo","/upload","/deleteImg").authenticated()
//                    //除上面请求全部不需要认证即可访问
//                    .anyRequest().permitAll();
//            http.logout().disable();
//            //配置认证失败和授权失败处理类
//            http.exceptionHandling()
//                    .authenticationEntryPoint(authenticationEntryPoint)
//                    .accessDeniedHandler(accessDeniedHandler);
//            //关闭默认的注销功能
//            http.logout().disable();
//            //把JwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链
//            http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//            //允许跨域
//            http.cors();
//            return http.build();
//        }
        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }

