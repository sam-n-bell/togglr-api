package com.heb.togglr.api;

import com.heb.togglr.api.client.model.requests.ActiveFeaturesRequest;
import com.heb.togglr.api.client.model.response.AvailableFeaturesList;
import com.heb.togglr.api.controllers.FeaturesController;
import com.heb.togglr.api.repositories.FeatureRepository;
import javassist.tools.web.BadHttpRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@Profile({"test"})
@ComponentScan({"com.heb.togglr"})
@SpringBootTest
public class TogglrApiApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private FeaturesController featuresController;

    @MockBean
    private FeatureRepository featureRepository;

//    @Test
//    public AvailableFeaturesList getAllActiveFeatures(@RequestBody ActiveFeaturesRequest activeFeaturesRequest){
//        Mockito.when(featureRepository.findAll()).then(Stream.of(new Feature("")).collect(Collectors.toList()));
//        assertEquals(2,featuresController.getAllActiveFeatures().size());
//
//    }


}
