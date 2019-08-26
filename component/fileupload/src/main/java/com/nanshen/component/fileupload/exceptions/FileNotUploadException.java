package com.nanshen.component.fileupload.exceptions;

import com.nanshen.common.enums.IEnum;
import com.nanshen.common.exceptions.SelfException;

public class FileNotUploadException extends SelfException {

    public FileNotUploadException(IEnum iEnum) {
        super(iEnum);
    }
}
