package launch;

import org.apache.catalina.Context;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.codec.FstCodec;
import org.redisson.tomcat.RedissonSessionManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class RedisSessionStore extends SessionManager {
  @Override
  public void configureSessionManager(Cli cli, Context ctx) throws URISyntaxException, IOException {
      System.out.println("Using redis session store: org.redisson.tomcat.RedissonSessionManager");

      String redisUriString=System.getenv("REDIS_URL");

      URI redisUri = URI.create(redisUriString);
      URI redisUriWithoutAuth = new URI(redisUri.getScheme(), null, redisUri.getHost(), redisUri.getPort(), redisUri.getPath(), redisUri.getQuery(), redisUri.getFragment());
      Config config = new Config();
      SingleServerConfig serverConfig = config.useSingleServer()
          .setAddress(redisUriWithoutAuth.toString())
          .setConnectionPoolSize(cli.sessionStorePoolSize)
          .setConnectionMinimumIdleSize(cli.sessionStorePoolSize)
          .setTimeout(cli.sessionStoreOperationTimout)
              .setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(":")+1));
      config.setCodec(new FstCodec());
      File configFile = File.createTempFile("redisson", ".json");
      BufferedWriter bw = new BufferedWriter(new FileWriter(configFile));
      bw.write(config.toJSON());
      RedissonSessionManager redisManager = new RedissonSessionManager();
      redisManager.setConfigPath(configFile.getAbsolutePath());
      ctx.setManager(redisManager);

    }
}
