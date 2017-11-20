package org.ligoj.service.scm;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

/**
 * Conntroller used to create git or svn repository.
 *
 * Created by Nicolas on 20/11/2017.
 */
@RestController
public class ScmService {

    /**
     * The logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ScmService.class);

    /**
     * Command used to create a new git repository
     */
    @Value("${scm.git.command}")
    private String gitCommand;

    /**
     * Command used to create a new svn repository
     */
    @Value("${scm.svn.command}")
    private String svnCommand;


    /**
     * Create a new git repository.
     *
     *
     * @param clientId
     *  Client Identifier
     * @param projectName
     *  Project Name
     * @param subGroup
     *  Sub Group
     *
     * @return
     *  HTTP 200 if the new repository has been created. Otherwise 500
     */
    @RequestMapping(method = RequestMethod.POST, path = "/git")
    public ResponseEntity<Void> createGitProject(@RequestParam String clientId, @RequestParam String projectName, @RequestParam(required = false) String subGroup)
            throws IOException , InterruptedException{
        return this.createProject(this.gitCommand, clientId, projectName, subGroup);
    }

    /**
     * Create a new git repository.
     *
     *
     * @param clientId
     *  Client Identifier
     * @param projectName
     *  Project Name
     * @param subGroup
     *  Sub Group
     *
     * @return
     *  HTTP 200 if the new repository has been created. Otherwise 500
     */
    @RequestMapping(method = RequestMethod.POST, path = "/svn")
    public ResponseEntity<Void> createSvnProject(@RequestParam String clientId, @RequestParam String projectName, @RequestParam(required = false) String subGroup)
            throws IOException , InterruptedException{
      return this.createProject(this.svnCommand, clientId, projectName, subGroup);
    }


    private ResponseEntity<Void> createProject(String command, String clientId, String projectName, String subGroup)
            throws IOException , InterruptedException{
        String subGrp = org.apache.commons.lang3.StringUtils.defaultString(subGroup, "");

        Process p = Runtime.getRuntime().exec(String.format(command, clientId, projectName, subGrp).trim());
        int ret = p.waitFor();

        //log content
        try(InputStream inputStream = p.getInputStream()){
            LOGGER.info(IOUtils.toString(p.getInputStream()));
        }

        return ret == 0? ResponseEntity.ok().build():ResponseEntity.status(500).build();
    }

}
