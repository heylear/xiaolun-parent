package tool;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caosh on 2015/10/13.
 */
public class XiTongSmsTest {

    public static void main(String[] args) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();

        HttpPost post = new HttpPost("http://115.238.169.142:7788/sms.aspx");

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("userid", "1933"));
        nameValuePairs.add(new BasicNameValuePair("account", "glor"));
        nameValuePairs.add(new BasicNameValuePair("password", "123456"));
        nameValuePairs.add(new BasicNameValuePair("mobile", "13817002276"));
        nameValuePairs.add(new BasicNameValuePair("content", "你好！【小轮汽车】"));
        nameValuePairs.add(new BasicNameValuePair("action", "send"));

        post.setEntity(new UrlEncodedFormEntity(nameValuePairs, Charset.forName("utf-8")));

        HttpResponse response = httpClient.execute(post);
        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}
