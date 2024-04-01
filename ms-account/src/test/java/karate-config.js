function fn () {
  let env = karate.env; // get java system property 'karate.env'
  karate.log('karate.env system property was:', env);

  let port = karate.properties['karate.port'] || '8082';
  let config = {
    baseUrl: 'http://localhost:' + port,
  };

  karate.configure('connectTimeout', 2000);
  karate.configure('readTimeout', 2000);
  return config;
}