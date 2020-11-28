package com.asiafrank.nettydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringNettyDemoApplication {

	/**
	 * 扩展一个 SpringBootApplication 过程
	 * 1.设置 Context 继承 AnnotationConfigApplicationContext
	 *   a.重写 refresh,finishRefresh,postProcessBeanFactory 方法
	 *     这三个是 Spring 提供的扩展, 且与 BeanFactoryPostProcessor,BeanPostProcessor 密切关联
	 * 2.自定义 Configuration 类，写上自己需要的类，如: NettyServerConfiguration
	 * 3.将 AnnotationConfigApplicationContext 补充完整
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringNettyDemoApplication.class);
		app.setApplicationContextClass(NettyServerApplicationContext.class);
		app.run(args);
	}

}
