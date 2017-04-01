package com.fourground.raisal.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class MyBatisConfig {

	
	public static final String BASE_PACKAGE = "net.yap.app.mapper.popcornCms";
	public static final String TYPE_ALIASES_PACKAGE = "popcorn.cms.info";
	public static final String CONFIG_LOCATION_PATH = "mybatis-config.xml";
	public static final String MAPPER_LOCATIONS_PATH = "/sqlmap/popcorn-cms/*.xml";
	
	protected void popcornCmsConfigureSqlSessionFactory(SqlSessionFactoryBean sessionFactoryBean, DataSource dataSource) throws IOException {
		PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
		sessionFactoryBean.setConfigLocation(pathResolver.getResource(CONFIG_LOCATION_PATH));
		sessionFactoryBean.setMapperLocations(pathResolver.getResources(MAPPER_LOCATIONS_PATH));
	}
}

//@Configuration
//@MapperScan(basePackages = MyBatisConfig.BASE_PACKAGE, annotationClass = DsPopcornCms.class, sqlSessionFactoryRef = "popcornCmsSqlSessionFactory")
//class PopcornCmsMyBatisConfig extends MyBatisConfig {
//
//	@Bean
//	public SqlSessionFactory popcornCmsSqlSessionFactory(@Qualifier("popcornCmsDataSource") DataSource masterDataSource) throws Exception {
//		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
//		popcornCmsConfigureSqlSessionFactory(sessionFactoryBean, masterDataSource);
//		return sessionFactoryBean.getObject();
//	}
//}