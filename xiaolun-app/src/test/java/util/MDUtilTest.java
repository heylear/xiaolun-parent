package util;

import cn.glor.xiaolun.app.util.MDUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by caosh on 2015/10/28.
 */
public class MDUtilTest {

    @Test
    public void md5() {
        String md = MDUtil.md5("hello");
        assertEquals("5d41402abc4b2a76b9719d911017c592", md);
    }
}
