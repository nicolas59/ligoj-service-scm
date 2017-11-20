package org.ligoj.service.scm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.internal.mockcreation.DefaultMockCreator;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by Nicolas on 20/11/2017.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@WebMvcTest(ScmService.class)
@PrepareForTest(ScmService.class)
public class ScmServiceTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void should_create_svn_project() throws Exception{

        PowerMockito.mockStatic(Runtime.class);

        Runtime mockedRuntime = Mockito.mock(Runtime.class);

        PowerMockito.when(Runtime.getRuntime()).thenReturn(mockedRuntime);

        Process process = mock(Process.class);
        when(process.waitFor()).thenReturn(0);

        when(process.getInputStream()).thenReturn(new ByteArrayInputStream("svn projetc created".getBytes()));
        when(mockedRuntime.exec(eq("svn_script ligoj serviceSvn"))).thenReturn(process);


        this.mvc.perform(post("/svn")
                .param("clientId", "ligoj")
                .param("projectName", "serviceSvn"))
                .andExpect(status().is(200));

        //control
        verify(process, times(1)).waitFor();
        verify(mockedRuntime, times(1)).exec(eq("svn_script ligoj serviceSvn"));

    }

    @Test
    public void should_create_svn_project_with_subgroup() throws Exception{

        PowerMockito.mockStatic(Runtime.class);

        Runtime mockedRuntime = Mockito.mock(Runtime.class);

        PowerMockito.when(Runtime.getRuntime()).thenReturn(mockedRuntime);

        Process process = mock(Process.class);
        when(process.waitFor()).thenReturn(0);

        when(process.getInputStream()).thenReturn(new ByteArrayInputStream("svn projetc created".getBytes()));
        when(mockedRuntime.exec(eq("svn_script ligoj serviceSvn subgroupSvn"))).thenReturn(process);


        this.mvc.perform(post("/svn")
                .param("clientId", "ligoj")
                .param("projectName", "serviceSvn")
                .param("subGroup", "subgroupSvn"))
                .andExpect(status().is(200));

        //control
        verify(process, times(1)).waitFor();
        verify(mockedRuntime, times(1)).exec(eq("svn_script ligoj serviceSvn subgroupSvn"));

    }

    @Test
    public void should_create_git_project() throws Exception{

        PowerMockito.mockStatic(Runtime.class);

        Runtime mockedRuntime = Mockito.mock(Runtime.class);

        PowerMockito.when(Runtime.getRuntime()).thenReturn(mockedRuntime);

        Process process = mock(Process.class);
        when(process.waitFor()).thenReturn(0);

        when(process.getInputStream()).thenReturn(new ByteArrayInputStream("git project created".getBytes()));
        when(mockedRuntime.exec(eq("git_script ligoj serviceGit"))).thenReturn(process);


        this.mvc.perform(post("/git")
                .param("clientId", "ligoj")
                .param("projectName", "serviceGit"))
                .andExpect(status().is(200));

        //control
        verify(process, times(1)).waitFor();
        verify(mockedRuntime, times(1)).exec(eq("git_script ligoj serviceGit"));

    }

    @Test
    public void should_create_git_project_with_subgroup() throws Exception{

        PowerMockito.mockStatic(Runtime.class);

        Runtime mockedRuntime = Mockito.mock(Runtime.class);

        PowerMockito.when(Runtime.getRuntime()).thenReturn(mockedRuntime);

        Process process = mock(Process.class);
        when(process.waitFor()).thenReturn(0);

        when(process.getInputStream()).thenReturn(new ByteArrayInputStream("git project created".getBytes()));
        when(mockedRuntime.exec(eq("git_script ligoj serviceGit subgroupGit"))).thenReturn(process);


        this.mvc.perform(post("/git")
                .param("clientId", "ligoj")
                .param("projectName", "serviceGit")
                .param("subGroup", "subgroupGit"))
                .andExpect(status().is(200));

        //control
        verify(process, times(1)).waitFor();
        verify(mockedRuntime, times(1)).exec(eq("git_script ligoj serviceGit subgroupGit"));

    }


    @Test
    public void should_fail_when_return_code_not_equals_sucess() throws Exception{

        PowerMockito.mockStatic(Runtime.class);

        Runtime mockedRuntime = Mockito.mock(Runtime.class);

        PowerMockito.when(Runtime.getRuntime()).thenReturn(mockedRuntime);

        Process process = mock(Process.class);
        when(process.waitFor()).thenReturn(1);

        when(process.getInputStream()).thenReturn(new ByteArrayInputStream("git project failed".getBytes()));
        when(mockedRuntime.exec(eq("git_script ligoj serviceGit subgroupGit"))).thenReturn(process);


        this.mvc.perform(post("/git")
                .param("clientId", "ligoj")
                .param("projectName", "serviceGit")
                .param("subGroup", "subgroupGit"))
                .andExpect(status().is(500));

        //control
        verify(process, times(1)).waitFor();
        verify(mockedRuntime, times(1)).exec(eq("git_script ligoj serviceGit subgroupGit"));

    }

}
