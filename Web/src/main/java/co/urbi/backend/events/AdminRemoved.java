package co.urbi.backend.events;

import co.urbi.contracts.Registry.WhitelistAdminRemovedEventResponse;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Log4j2
public class AdminRemoved implements Subscriber<WhitelistAdminRemovedEventResponse> {

    @Override
    public void onSubscribe(Subscription s) {
    }

    @Override
    public void onNext(WhitelistAdminRemovedEventResponse response) {
        log.info(response);
    }

    @Override
    public void onError(Throwable t) {
        log.error(t);
    }

    @Override
    public void onComplete() {
        log.info("Event completed");
    }

}