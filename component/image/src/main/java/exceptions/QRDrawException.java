package exceptions;

import com.nanshen.common.enums.IEnum;
import com.nanshen.common.exceptions.SelfException;

public class QRDrawException extends SelfException {

    public QRDrawException(IEnum iEnum) {
        super(iEnum);
    }

    public QRDrawException(String message) {
        super(message);
    }
}
