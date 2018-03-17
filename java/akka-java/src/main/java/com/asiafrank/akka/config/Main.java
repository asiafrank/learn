package com.asiafrank.akka.config;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Main
 * <p>
 * Created by zhangxf on 12/2/2016.
 */
class Main {
    public static void main(String[] args){
        final Config config = ConfigFactory.load();
        final ActorSystem app1 = ActorSystem.apply("MyApp1", config.getConfig("config-app1").withFallback(config));
        final ActorSystem app2 = ActorSystem.apply("MyApp2", config.getConfig("config-app2").withOnlyPath("akka").withFallback(config));
    }
}
