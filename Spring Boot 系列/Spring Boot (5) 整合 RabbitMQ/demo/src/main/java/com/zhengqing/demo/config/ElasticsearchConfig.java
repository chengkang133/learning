package com.zhengqing.demo.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * <p> es配置类 </p>
 *
 * @author : zhengqing
 * @description : `RedisConfig.class`为redis配置文件
 * @date : 2019/12/27 14:35
 */
@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

	private final int TIME_OUT = 600 * 1000;
	private String clusterNodes = "192.168.3.221:11002";
	private String clusterName = "my-application";
	private String username = "elastic";
	private String password = "kgtdata@123";

	@Override
	public RestHighLevelClient elasticsearchClient() {
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
		RestClientBuilder builder = RestClient.builder(getHosts());
		builder.setRequestConfigCallback(requestConfig ->
				requestConfig.setConnectionRequestTimeout(TIME_OUT)
						.setConnectTimeout(TIME_OUT)
						.setSocketTimeout(TIME_OUT)
		);
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			builder.setHttpClientConfigCallback(httpAsyncClientBuilder ->
					httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
			);
		}
		return new RestHighLevelClient(builder);
	}

	private HttpHost[] getHosts() {
		String[] serverNodes = clusterNodes.split(",");
		HttpHost[] hosts = new HttpHost[serverNodes.length];
		for (int i = 0; i < serverNodes.length; i++) {
			hosts[i] = HttpHost.create(serverNodes[i]);
		}
		return hosts;
	}

}
