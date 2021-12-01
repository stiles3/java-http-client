/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blacksilicon.lackey;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author stilesma
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    

    public static void main(String[] args) throws IOException {
         Map<String, String> config = new HashMap();

        config.put("Authorization", "Zapp_eyJhbGciOiJIUzI1NiJ9.eyJmbGFnIjoiY2xpZW50IiwicGsiOiI0IiwiZW1haWwiOiJzYWxlc0A5Zm9vZC5uZyJ9.nxsX2JNavXXMiSd1PMp9NQ4TwnNFWupqR8OhlZOiJvs");
        config.put("Content-Type", "application/json");
        
            Map<String, Object> covData = new HashMap();
        covData.put("picLat", 6.6328361);
        covData.put("picLon", 3.3873341);
        covData.put("picState", "Lagos");
        covData.put("cpDist", 7);
        covData.put("devLat",6.6252302);
        covData.put("devLon", 3.3649734);
        covData.put("devState","Lagos");
        
         Response response;
        response = Lackey.POST("http://zapp.ng/zapp/api/v1/dispatch/coverage/price", covData, config);

        String json = response.getBody();
        System.out.println(json);
        // TODO code application logic here
    }
    
}
