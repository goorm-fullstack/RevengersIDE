package Revengers.IDE.docker.service.callback;

import com.github.dockerjava.api.async.ResultCallbackTemplate;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.StreamType;

/**
 * 필요하지 않는 클래스가 될 것 같습니다.
 * 나중에 지우거나 일단 참고용으로 남겨두겠습니다.
 */
public class TimeoutResultCallback extends ResultCallbackTemplate<TimeoutResultCallback, Frame> {

    public static final int startIndex = StreamType.STDOUT.name().length()+2;
    private final StringBuilder standardOutputLogs;
    private final StringBuilder standardErrorLogs;
    private final StringBuilder exceptions;

    public TimeoutResultCallback(StringBuilder standardOutputLogs,
                                 StringBuilder standardErrorLogs, StringBuilder exceptions) {
        this.standardOutputLogs = standardOutputLogs;
        this.standardErrorLogs = standardErrorLogs;
        this.exceptions = exceptions;
    }


    @Override
    public void onNext(Frame object) {
        if (StreamType.STDOUT.equals(object.getStreamType())) {
            standardOutputLogs.append(object.toString().substring(startIndex));
        } else if (StreamType.STDERR.equals(object.getStreamType())) {
            standardErrorLogs.append(object.toString().substring(startIndex));
        }
    }
}