package com.example.apollo;

import com.ctrip.framework.apollo.ConfigService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiexingxing
 * @Created by 2019-11-10 13:56.
 */
@RestController
@RequestMapping(path = "/configurations")
public class ApolloConfigurationController {

    @RequestMapping(path = "/{key}")
    public String getConfigForKey(@PathVariable("key") String key) {
        return ConfigService.getAppConfig().getProperty(key, "undefined");
    }
}

