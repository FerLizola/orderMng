package com.ferlizola.jwtconfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebSecurityConfig.class })
@TestPropertySource(locations="classpath:application.properties")
public class TestWebSecurityConfig {

	 	@Test
	    public void givenCircularDependency_whenConstructorInjection_thenItFails() {
	        // Empty test; we just want the context to load
	    }
}
