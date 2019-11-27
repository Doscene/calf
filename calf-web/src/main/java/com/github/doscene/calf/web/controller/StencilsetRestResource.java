package com.github.doscene.calf.web.controller;

import java.io.InputStream;

import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>com.github.doscene.calf.web.controller</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@RestController
public class StencilsetRestResource {
    @RequestMapping(value = {"/editor/stencilset"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET}, produces = {"application/json;charset=utf-8"})
    public String getStencilset() {
        InputStream stencilsetStream = getClass().getClassLoader().getResourceAsStream("stencilset.json");
        try {
            return IOUtils.toString(stencilsetStream, "utf-8");
        } catch (Exception e) {
            throw new ActivitiException("Error while loading stencil set", e);
        }
    }
}
