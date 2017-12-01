package com.esfandani.viv.endpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.esfandani.viv.endpoint.impl.SimpleServiceEndpointManager;

@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleServiceEndpontManager_UT {

    @Test
    public void test_invoke() {

        //ARRANGE & ACT
        List l1 = new ArrayList();
        List l2 = new ArrayList();
        List l3 = new ArrayList();
        List l4 = new ArrayList();
        List l5 = new ArrayList();

        ServiceEndpointManager manager = SimpleServiceEndpointManager.getInstance();

        Runnable r1 = () -> {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("category", "category");
            l1.addAll(manager.invoke(map));

        };
        Runnable r2 = () -> {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("price", "price");
            map.put("category", "category");
            l2.addAll(manager.invoke(map));
        };
        Runnable r3 = () -> {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("price", "price");
            l3.addAll(manager.invoke(map));
        };
        Runnable r4 = () -> {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("price", "price");
            map.put("nothing", "nothing");
            l4.addAll(manager.invoke(map));
        };
        Runnable r5 = () -> {
            Map<String, Object> map = new HashMap<String, Object>();
            l5.addAll(manager.invoke(map));
        };
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        Thread t3 = new Thread(r3);
        Thread t4 = new Thread(r4);
        Thread t5 = new Thread(r5);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //ASSERT

        Assert.assertEquals(2, l1.size());
        Assert.assertTrue(l1.contains("reza"));
        Assert.assertTrue(l1.contains("esfandani"));

        Assert.assertEquals(2, l2.size());
        Assert.assertTrue(l2.contains("reza"));
        Assert.assertTrue(l2.contains("esfandani"));

        Assert.assertEquals(4, l3.size());
        Assert.assertTrue(l3.contains("reza"));
        Assert.assertTrue(l3.contains("esfandani"));
        Assert.assertTrue(l3.contains(Integer.valueOf(1)));
        Assert.assertTrue(l3.contains(Integer.valueOf(2)));

        Assert.assertNotNull(l4);
        Assert.assertEquals(0, l4.size());

        Assert.assertEquals(4, l5.size());
        Assert.assertTrue(l5.contains("reza"));
        Assert.assertTrue(l5.contains("esfandani"));
        Assert.assertTrue(l5.contains(Integer.valueOf(1)));
        Assert.assertTrue(l5.contains(Integer.valueOf(2)));
    }

}
