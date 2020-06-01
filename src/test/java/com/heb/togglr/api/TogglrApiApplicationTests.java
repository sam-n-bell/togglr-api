package com.heb.togglr.api;
import com.heb.togglr.api.client.model.requests.ActiveFeaturesRequest;
import com.heb.togglr.api.client.model.response.AvailableFeaturesList;
import com.heb.togglr.api.controllers.AdminController;
import com.heb.togglr.api.controllers.FeatureEntitiesController;
import com.heb.togglr.api.controllers.FeaturesController;
import com.heb.togglr.api.controllers.KeysController;
import com.heb.togglr.api.entities.*;
import com.heb.togglr.api.repositories.*;
import javassist.tools.web.BadHttpRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
public class TogglrApiApplicationTests {
    @Mock
    private FeatureRepository featureRepository;
    @Mock
    private ConfigsRepository configsRepository;
    @Mock
    private AdminRepository adminRepository;
    @Mock
    private ApplicationsRepository applicationsRepository;
    @Mock
    private KeysRepository keysRepository;
    @InjectMocks
    private FeatureEntitiesController featureEntitiesController;
    @InjectMocks
    private AdminController adminController;
    @InjectMocks
    private KeysController keysController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    //    Testing FeatureEntitiesController's removeFeature method
    @Test
    public void removeFeatureTest() throws Exception {
        Integer featureId = 1;
        when(featureRepository.findById(Mockito.anyInt())).thenReturn(java.util.Optional.of(new FeatureEntity()));
        featureEntitiesController.removeFeature(featureId);
    }

    @Test
    public void removeFeatureConfigs() throws Exception {
        Integer featureId = 1;
        FeatureEntity featureEntity = getMockEntity(featureId);
        when(featureRepository.findById(Mockito.anyInt())).thenReturn(java.util.Optional.of(featureEntity));
        featureEntitiesController.removeFeature(featureId);
        Mockito.verify(featureRepository, Mockito.atLeastOnce()).delete(featureEntity);
    }

    public static FeatureEntity getMockEntity(int id) {
        FeatureEntity featureEntity = new FeatureEntity();
        ConfigsEntity configsEntity = new ConfigsEntity();
        featureEntity.setId(id);
        configsEntity.setFeatureId(id);
        featureEntity.setConfigsById(new ArrayList<>());
        featureEntity.getConfigsById().add(configsEntity);
        return featureEntity;
    }

    @Test
    public void removeKeyTest() throws Exception {
        String keyId = "City";
        int appId = 1;
        String key_name = "1_City";
        KeysEntityPK keysEntityPK = getMockkeysEntityPK(keyId, appId);
        KeysEntity keysEntity = new KeysEntity();
        when(keysRepository.findById(keysEntityPK)).thenReturn(java.util.Optional.of(keysEntity));
        keysController.RemoveKey(key_name);
        Mockito.verify(keysRepository, Mockito.atLeastOnce()).delete(keysEntity);
    }

    public static KeysEntityPK getMockkeysEntityPK(String keyId, int appId) {
        KeysEntityPK keysEntityPK = new KeysEntityPK();
        keysEntityPK.setAppId(appId);
        keysEntityPK.setKeyName(keyId);
        return keysEntityPK;
    }
}