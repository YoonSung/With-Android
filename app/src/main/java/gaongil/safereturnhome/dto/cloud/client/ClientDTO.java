package gaongil.safereturnhome.dto.cloud.client;

import android.app.Activity;
import android.content.Context;

import gaongil.safereturnhome.dto.cloud.ClientStrategy;
import gaongil.safereturnhome.exception.ProcessFailureException;

/**
 * Created by yoon on 15. 7. 10..
 */
public interface ClientDTO {
    void process(ClientStrategy strategy, Context context) throws ProcessFailureException;
}
