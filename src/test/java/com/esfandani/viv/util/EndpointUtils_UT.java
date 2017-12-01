package com.esfandani.viv.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.esfandani.viv.endpoint.util.EndpointUtils;

@RunWith(SpringJUnit4ClassRunner.class)
public class EndpointUtils_UT {
    @Test
    public void isSubsetTest_Normal_True(){
        boolean equalCase = EndpointUtils.isSubset(new HashSet<>(Arrays.asList("a","b")),new HashSet<>(Arrays.asList("a","b")));
        boolean equalCaseDifferentOrder = EndpointUtils.isSubset(new HashSet<>(Arrays.asList("b","a")),new HashSet<>(Arrays.asList("a","b")));

        boolean partialCase = EndpointUtils.isSubset(new HashSet<>(Arrays.asList("a","b")),new HashSet<>(Arrays.asList("a")));


        Assert.assertEquals(true,equalCase);
        Assert.assertEquals(true,equalCaseDifferentOrder);
        Assert.assertEquals(true,partialCase);

    }

    @Test
    public void isSubsetTest_Normal_False(){
        boolean equalSize = EndpointUtils.isSubset(new HashSet<>(Arrays.asList("c","b")),new HashSet<>(Arrays.asList("a","b")));
        boolean differentSize = EndpointUtils.isSubset(new HashSet<>(Arrays.asList("b")),new HashSet<>(Arrays.asList("b","a")));


        Assert.assertEquals(false,equalSize);
        Assert.assertEquals(false,differentSize);
    }

    @Test
    public void isSubsetTest_EmptySubSet(){
        boolean emptySubset = EndpointUtils.isSubset(new HashSet<>(Arrays.asList("c","b")),new HashSet<>(
                Collections.emptyList()));
        boolean nullSubset = EndpointUtils.isSubset(new HashSet<>(Arrays.asList("b")),null);

        boolean emptySetAndSubset = EndpointUtils.isSubset(new HashSet<>(Arrays.asList()),new HashSet<>(Arrays.asList()));
        Assert.assertEquals(true,emptySubset);
        Assert.assertEquals(true,nullSubset);
        Assert.assertEquals(true,emptySetAndSubset);


    }

}
