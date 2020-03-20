package com.tianya.springcloud.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

@Configuration
public class CostumRibbonConfig {

	@Bean
	public IRule ribbonRule() {
		return new RandomRule();
	}

}
