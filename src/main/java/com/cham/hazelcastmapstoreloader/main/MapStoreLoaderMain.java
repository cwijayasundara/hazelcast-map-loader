package com.cham.hazelcastmapstoreloader.main;

import com.cham.hazelcastmapstoreloader.domain.Trade;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.Date;

import static com.hazelcast.core.Hazelcast.newHazelcastInstance;

public class MapStoreLoaderMain {

    public static void main(String[] args) {
        final HazelcastInstance hazelcastInstance = newHazelcastInstance();
        IMap<String, Trade> tradeIMap = hazelcastInstance.getMap("TradeIMap");

        Trade trade1 = new Trade("B-001", "BOND", new Date(System.currentTimeMillis()),"My first BOND");
        Trade trade2 = new Trade("S-001", "SWAP", new Date(System.currentTimeMillis()),"My first SWAP");

        tradeIMap.set(trade1.getId(), trade1);
        tradeIMap.set(trade2.getId(), trade2);

        System.out.println(tradeIMap.get("BOND"));
        System.out.println(tradeIMap.get("SWAP"));
    }

}
