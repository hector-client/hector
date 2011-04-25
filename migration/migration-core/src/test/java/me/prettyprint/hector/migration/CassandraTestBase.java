package me.prettyprint.hector.migration;

import me.prettyprint.hector.api.Cluster;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author trnl
 *         Date: 4/22/11
 *         Time: 1:09 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context.xml")
public abstract class CassandraTestBase {
    @Autowired
    Cluster cluster;
}
