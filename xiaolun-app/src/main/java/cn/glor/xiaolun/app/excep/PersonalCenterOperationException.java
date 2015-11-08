package cn.glor.xiaolun.app.excep;

/**
 * Created by heylear on 15/10/31.
 */
public class PersonalCenterOperationException extends BaseException {

    public PersonalCenterOperationException(String message) {
        super(message);
    }

    public PersonalCenterOperationException(long errCode, String message) {
        super(errCode, message);
    }
}
