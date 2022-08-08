package vttp2022.workshop171.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import vttp2022.workshop171.Model.ExchangeRate;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;
    // String redisHost = System.getenv("REDIS_HOST");

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;
    // String redisPort = System.getenv("REDIS_PORT");

    // @Value("${spring.redis.password}")
    // private String redisPassword;
    String redisPassword = System.getenv("REDIS_PASSWORD");

    @Value("${spring.redis.database}")
    private String redisDatabase;
    

    @Bean
    @Scope("singleton")
    public RedisTemplate<String, ExchangeRate> redisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort.get()); 
        config.setPassword(redisPassword);
        Jackson2JsonRedisSerializer jackson2JsonJsonSerializer = new Jackson2JsonRedisSerializer(ExchangeRate.class);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();
        RedisTemplate<String, ExchangeRate> template = new RedisTemplate<String, ExchangeRate>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonJsonSerializer);
        template.setHashKeySerializer(template.getKeySerializer());
        template.setHashValueSerializer(template.getValueSerializer());
        return template;
    }

}
