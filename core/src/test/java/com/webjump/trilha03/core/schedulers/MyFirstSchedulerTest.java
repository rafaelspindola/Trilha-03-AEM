package com.webjump.trilha03.core.schedulers;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.*;
import org.apache.sling.commons.scheduler.Scheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.util.List;

import static junitx.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith ({MockitoExtension.class, AemContextExtension.class})
public class MyFirstSchedulerTest {
    @Mock
    private Resource resource;
    @Mock
    private ModifiableValueMap valueMap;

    @Mock
    private ResourceResolver resourceResolver;
    @Mock
    private ResourceResolverFactory resourceResolverFactory;
    @InjectMocks
    private MyFirstScheduler myFirstScheduler;

    @Test
    void testRun() throws PersistenceException, LoginException {
        // When getServiceResourceResolver is called with any argument, return the mocked resourceResolver
        when(resourceResolverFactory.getServiceResourceResolver(any())).thenReturn(resourceResolver);
        // When getResource is called with any string argument, return the mocked resource
        when(resourceResolver.getResource(anyString())).thenReturn(resource);
        // When adaptTo is called on the mocked resource, return the mocked valueMap
        when(resource.adaptTo(ModifiableValueMap.class)).thenReturn(valueMap);

        // Call the method under test
        myFirstScheduler.run();

        // Verify that the appropriate methods were called with the expected arguments
        verify(valueMap).put("clientName", "Huawei");
        verify(valueMap).put("codeID", "00456");
        verify(valueMap).put("isNewClient", true);

        // Verify that the changes were saved
        verify(resourceResolver).commit();
    }
}