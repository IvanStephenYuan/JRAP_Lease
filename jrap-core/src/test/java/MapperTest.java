import com.jingrui.jrap.account.dto.Role;
import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.account.mapper.RoleMapper;
import com.jingrui.jrap.account.mapper.UserMapper;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.core.impl.ServiceRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by hailor on 16/9/21.
 */
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
@Transactional
public class MapperTest {

    @Autowired
    UserMapper userMapper;

    IRequest request;

    User user;

    @Before
    public void setUp() {
        request = new ServiceRequest();
        request.setLocale("zh_CN");
        RequestHelper.setCurrentRequest(request);

        user = new User();
        user.setUserName("test");
    }

    @Test
    public void testprofile() throws Exception {
        userMapper.insert(user);

        List<User> users = userMapper.select(user);
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("test", users.get(0).getUserName());

    }

    @After
    public void tearDown() {

    }
}
