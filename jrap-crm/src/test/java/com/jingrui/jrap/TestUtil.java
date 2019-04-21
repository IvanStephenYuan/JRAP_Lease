package com.jingrui.jrap;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.core.IRequestAware;
import com.jingrui.jrap.flexfield.dto.FlexRuleSet;
import com.jingrui.jrap.mybatis.util.StringUtil;
import com.jingrui.jrap.product.dto.Product;
import com.jingrui.jrap.swagger.SwaggerConfig;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUtil {
    @Autowired
    @Qualifier("sqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;
    static final int BUFFER_SIZE = 1024;
    static final Logger logger= LoggerFactory.getLogger(TestUtil.class);

    public static void main(String[] args) throws IOException, JSQLParserException {
        String sql = "SELECT hou.unit_id           AS organization_id,\n" +
                "       hou.unit_code         AS organization_code,\n" +
                "       hou.name              AS organization_name,\n" +
                "       hou.parent_id         AS org_id,\n" +
                "       phou.unit_code        AS org_code,\n" +
                "       phou.name             AS org_name,\n" +
                "       fcb.company_id        AS company_id,\n" +
                "       fcb.company_full_name AS companyname,\n" +
                "       hou.description       AS remark\n" +
                "  FROM ((select * from hr_org_unit_b hou LEFT JOIN hr_org_unit_b phou ON((hou.parent_id = phou.unit_id))) LEFT JOIN\n" +
                "        fnd_company_b fcb ON((phou.company_id = fcb.company_id)))\n" +
                " WHERE (hou.unit_type = 'ORGANIZATION')\n";
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        select.getSelectBody();
    }

    @Test
    public void generateProperty() throws ClassNotFoundException {
        Class clazz = FlexRuleSet.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                String fieldName = field.getName();
                String propertyName = StringUtil.camelhumpToUnderline(fieldName);
                System.out.println("public static final String FIELD_" + propertyName + " = \"" + fieldName + "\";");
            }

        }
    }

    @Test
    public void productTest(){
        Product product = new Product();
        product.setProductCode("SH100010005");
        product.setVersion(0);

        String sqlId = "com.jingrui.jrap.product.mapper." + StringUtils.capitalize("ProductMapper.select");
        List<Product> list;

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PageHelper.startPage(1, 10);

            list = sqlSession.selectList(sqlId, product);
            if(list.size() > 0){
                System.out.println(list.get(0).getVersion());
            }
        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
