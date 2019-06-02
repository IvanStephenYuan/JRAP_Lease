import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jingrui.jrap.account.controllers.RoleController;
import com.jingrui.jrap.account.controllers.UserController;
import com.jingrui.jrap.account.dto.Role;
import com.jingrui.jrap.account.service.impl.RoleServiceImpl;
import com.jingrui.jrap.core.IRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by hailor on 16/9/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml"})
@Transactional
@Rollback
public class ControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("UTF-8"));


    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    @InjectMocks
    private RoleController roleController;


    private MockMvc mockMvc;

    @Mock
    private RoleServiceImpl roleService;


    @Mock
    private Validator validator;

    @Mock
    private MessageSource messageSource;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    public void testGetRoles() throws Exception {
        List<Role> roleList = new ArrayList<Role>();
        Role firstRole = new Role();
        firstRole.setRoleId(1L);
        roleList.add(firstRole);
        Role secondRole = new Role();
        roleList.add(secondRole);
        when(roleService.select(anyObject(),anyObject(),anyInt(),anyInt())).thenReturn(roleList);
        int userId = 3;

        //测试get型action
        mockMvc.perform(
                get("/sys/role/query"))
                //.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("total", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("rows[0].roleId", is(1)));
    }

    @Test
    public void testSubmitRole() throws Exception {
        List<Role> roleList = new ArrayList<Role>();
        Role firstRole = new Role();
        firstRole.setRoleId(1L);
        roleList.add(firstRole);
        Role secondRole = new Role();
        roleList.add(secondRole);
        when(roleService.batchUpdate(anyObject(),anyObject())).thenReturn(roleList);
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                BindingResult result = (BindingResult)invocation.getArguments()[1];
                result.addError(new ObjectError("test","test"));
                return null;
            }
        }).when(validator).validate(anyObject(),any(BindingResult.class));

        when(messageSource.getMessage(anyString(),anyObject(),anyObject(),anyObject())).thenReturn("abc");


        mockMvc.perform(
                post("/sys/role/submit")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(roleList)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("success", is(false)));
    }

    @Test
    public void testDeleteRole() throws Exception {
        List<Role> roleList = new ArrayList<Role>();
        Role firstRole = new Role();
        firstRole.setRoleId(1L);
        roleList.add(firstRole);
        Role secondRole = new Role();
        roleList.add(secondRole);
        when(roleService.batchDelete(roleList)).thenReturn(2);


        mockMvc.perform(
                post("/sys/role/remove")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(roleList)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("success", is(true)));
    }


}
