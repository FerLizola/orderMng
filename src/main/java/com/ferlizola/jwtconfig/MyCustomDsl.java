//package com.ferlizola.jwtconfig;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//
//import com.ferlizola.filters.JwtRequestFilter;
//
//public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
//	
//	AuthenticationManager authenticationManager;
//	
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return authenticationManager;
//	}
//	
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        authenticationManager = http.getSharedObject(AuthenticationManager.class);
//        //http.addFilter(new JwtRequestFilter(authenticationManager));
//    }
//
//    public static MyCustomDsl customDsl() {
//        return new MyCustomDsl();
//    }
//}
